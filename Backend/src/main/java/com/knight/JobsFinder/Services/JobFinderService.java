package com.knight.JobsFinder.Services;

import com.knight.JobsFinder.Models.InterviewExperienceResponse;
import com.knight.JobsFinder.Models.Job;
import com.knight.JobsFinder.Utils.Utils;
import org.apache.http.HttpResponse;
import Queries.GraphqlQueries;
import com.knight.JobsFinder.Clients.LeetcodeGraphqlClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class JobFinderService {

    private final LeetcodeGraphqlClient leetcodeGraphqlClient;

    /**
     * This will find companies based on a serch criteria which will be date.
     */
    //Todo: Add Search criteria.
    public List<Job> findCompaniesHiring(){

        log.info("Find companies which are hiring.");

        String graphqlQuery = GraphqlQueries.CATEGORY_TOPIC_LIST_QUERY;
        Map<String, Object> variables = prepareVariables(); // Replace with your method to prepare variables

        try {
            List<InterviewExperienceResponse> response = leetcodeGraphqlClient.executeGraphQLQuery(graphqlQuery, variables);

            List<Job> jobs = new ArrayList<>();
            Map<String,Integer> mp = new HashMap<>();

            for(InterviewExperienceResponse ie : response){

                String companyName = Utils.extractCompanyName(ie.getCompanyName());

                mp.put(companyName,mp.getOrDefault(companyName,0)+1);

            }


            for(var e: mp.entrySet()){

                jobs.add(new Job(e.getKey(),e.getValue()));

            }

            return jobs;

        } catch (IOException e) {
            // Handle exceptions
            log.error("Ran into an error:{}",e.getMessage());
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;

    }

    // Example method to prepare variables
    private Map<String, Object> prepareVariables() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("categories", Arrays.asList("interview-experience"));
        variables.put("first", 15);
        variables.put("orderBy", "hot");
        return variables;
    }
}
