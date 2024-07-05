package com.knight.JobsFinder.Controller;

import com.knight.JobsFinder.Models.Job;
import com.knight.JobsFinder.Models.Question;
import com.knight.JobsFinder.Services.JobFinderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
@AllArgsConstructor
public class JobFinderController {

    private final JobFinderService jobFinderService;

    @GetMapping("/findJobs")
    //ToDo: Put a filter on it to only get jobs-> posted in a certain date range.
    public List<Job> findJobs() {
        //
        log.info("Finding jobs according to leetcode posts.");

        return jobFinderService.findCompaniesHiring();

    }

    @GetMapping("/questions/{companyName}")
    public List<Question> getQuestions(@PathVariable String companyName) {
        System.out.println("This will return the questions.");
        return Collections.EMPTY_LIST;
    }
}
