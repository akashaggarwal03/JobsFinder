package com.knight.JobsFinder.Services;

import com.knight.JobsFinder.Models.DateRangeEnum;
import com.knight.JobsFinder.Models.InterviewExperienceResponse;
import com.knight.JobsFinder.Models.Job;
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

    /**
     * This will find companies based on a serch criteria which will be date.
     */
    //Todo: Add Search criteria.
    public List<Job> findCompaniesHiring(DateRangeEnum time){

        log.info("Find companies which are hiring.");

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

            return jobs;
    }


    private List<InterviewExperienceResponse> searchPosts(Map<String,Object> variables, DateRangeEnum time){


        String graphqlQuery = GraphqlQueries.CATEGORY_TOPIC_LIST_QUERY;
        List<InterviewExperienceResponse> response = new ArrayList<>();

        long currentTimeMillis = System.currentTimeMillis()/1000;

        // Calculate the time for one week ago
        long oneWeekAgoMillis = currentTimeMillis - TimeUnit.DAYS.toSeconds(time.getLabel());

        boolean foundAll = false;
        Integer skip =15;
        //ToDo: Add parallel calls.

        try {
            while (!foundAll) {

                response.addAll(leetcodeGraphqlClient.executeGraphQLQuery(graphqlQuery, variables));

                variables.put("skip", skip);
                skip += 15;

                InterviewExperienceResponse ie = response.get(response.size() - 1);
                Long curr = Long.parseLong(ie.getCreationDate());
                log.info("Current milliseconds :" + curr);
                if (curr < oneWeekAgoMillis)
                    foundAll = true;
                else if (skip >= 150) {
                    log.error("Reached Limit " + ie.getCreationDate() + " vs " + oneWeekAgoMillis);
                    break;
                }
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
}
