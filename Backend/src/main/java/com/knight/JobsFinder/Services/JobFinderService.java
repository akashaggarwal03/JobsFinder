package com.knight.JobsFinder.Services;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.knight.JobsFinder.Models.*;
import com.knight.JobsFinder.Utils.Utils;
import Queries.GraphqlQueries;
import com.knight.JobsFinder.Clients.LeetcodeGraphqlClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class JobFinderService {

    private final LeetcodeGraphqlClient leetcodeGraphqlClient;

    private final FireStoreService fireStoreService;


    // Cache for company hiring data with expiration of 10 minutes
    private final Cache<DateRangeEnum, List<Job>> companyHiringCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .build();

    // Cache for questions data with expiration of 10 minutes
    private final Cache<String, List<Question>> questionsCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .build();

    /**
     * This will find companies based on a serch criteria which will be date.
     */
    //Todo: Add Search criteria.
    public List<Job> findCompaniesHiring(DateRangeEnum time){

        log.info("Find companies which are hiring.");

        // Check cache first
        List<Job> cachedJobs = companyHiringCache.getIfPresent(time);
        if (cachedJobs != null) {
            log.info("Returning cached hiring data.");
            return cachedJobs;
        }

        String graphqlQuery = GraphqlQueries.CATEGORY_TOPIC_LIST_QUERY;
        Map<String, Object> variables = prepareVariables(); // Replace with your method to prepare variables



            List<InterviewExperienceResponse> response = new ArrayList<>();

            response.addAll(searchPosts(variables,time));
            variables.put("categories", Arrays.asList("interview-experience"));
            response.addAll(searchPosts(variables,time));

            List<Job> jobs = new ArrayList<>();
            Map<String,Integer> mp = new HashMap<>();

            for(InterviewExperienceResponse ie : response){

                String companyName = Utils.extractCompanyName(ie.getCompanyName());

                mp.put(companyName,mp.getOrDefault(companyName,0)+1);

            }

            Map<String, Integer> sortedMap = mp.entrySet()
                    .stream()
                    .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // Descending order
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (e1, e2) -> e1,
                            LinkedHashMap::new));


            for(var e: sortedMap.entrySet()){

                jobs.add(new Job(e.getKey(),e.getValue()));

            }

        // Store in cache
        companyHiringCache.put(time, jobs);
        return jobs;
    }


    private List<InterviewExperienceResponse> searchPosts(Map<String,Object> variables, DateRangeEnum time){


        String graphqlQuery = GraphqlQueries.CATEGORY_TOPIC_LIST_QUERY;
        List<InterviewExperienceResponse> response = new ArrayList<>();

        long timeStamp = getTimeStampBeforeGivenPeriod(time);

        boolean foundAll = false;
        Integer skip =0;
        //ToDo: Add parallel calls.

        try {
            while (!foundAll) {

                response.addAll(leetcodeGraphqlClient.executeGraphQLQuery(graphqlQuery, variables));

                variables.put("skip", skip);
                skip += 15;

                InterviewExperienceResponse ie = response.get(response.size() - 1);
                Long curr = Long.parseLong(ie.getCreationDate());
                log.info("Current milliseconds :" + curr);
                if (curr < timeStamp )
                    foundAll = true;
//                else if (skip >= 200) { // This added in order to avoid
//                    log.error("Reached Limit " + ie.getCreationDate() + " vs " + timeStamp );
//                    break;
//                }
            }

        } catch (IOException e) {
            // Handle exceptions
            log.error("Ran into an error:{}",e.getMessage());
            e.printStackTrace();
        }

        return  response;

    }

    // Example method to prepare variables
    private Map<String, Object> prepareVariables() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("categories", Arrays.asList("interview-question"));
        variables.put("first", 15);
        variables.put("orderBy", "newest_to_oldest");
        return variables;
    }


    public List<Question> findQuestions(String companyName, DateRangeEnum dateRangeEnum, QuestionType questionType){

        log.info("Finding questions company wise:{} and dateRange:{}",companyName,dateRangeEnum.getLabel());
        String cacheKey = companyName + "-" + dateRangeEnum + "-" + questionType;

        // Check cache first
        List<Question> cachedQuestions = questionsCache.getIfPresent(cacheKey);
        if (cachedQuestions != null) {
            log.info("Returning cached questions data.");
            return cachedQuestions;
        }

        List<Question> questions = new ArrayList<>();

        long timeStamp = getTimeStampBeforeGivenPeriod(dateRangeEnum);

        try{
            List<QueryDocumentSnapshot> data = fireStoreService.getCompanyDocumentsAfterTimestamp(companyName,timeStamp);

            for(QueryDocumentSnapshot document: data){

                Question ques = new Question();
                List<Map<String, Object>> questionData = (List<Map<String, Object>>) document.get("questions");

                if (questionData != null) {
                    for (Map<String, Object> questionMap : questionData) {

                        String type = questionMap.get("questionType").toString();
                        String text = (String) questionMap.get("questionText");
                        if(type.equals(questionType.getLabel()) && !text.equals("NULL")){
                            Question question = new Question();
                            question.setQuestionText(text);
                            questions.add(question);
                        }

                    }
                }

            }

        }catch (Exception ex){
            log.error(ex.getMessage());
        }

        // Store in cache
        questionsCache.put(cacheKey, questions);

        return questions;
    }



    private Long getTimeStampBeforeGivenPeriod(DateRangeEnum dateRangeEnum){

        long currentTimeMillis = System.currentTimeMillis()/1000;

        // Calculate the time for one week ago
        long timeStamp  = currentTimeMillis - TimeUnit.DAYS.toSeconds(dateRangeEnum.getLabel());

        return timeStamp;
    }
}
