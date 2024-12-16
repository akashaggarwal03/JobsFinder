package com.knight.JobsFinder.Controller;

import com.knight.JobsFinder.Models.DateRangeEnum;
import com.knight.JobsFinder.Models.Job;
import com.knight.JobsFinder.Models.Question;
import com.knight.JobsFinder.Models.QuestionType;
import com.knight.JobsFinder.Services.JobFinderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class JobFinderController {

    private final JobFinderService jobFinderService;

    @GetMapping("/findJobs/{time}")
    public List<Job> findJobs(@PathVariable("time") DateRangeEnum time) {
        log.info("Finding jobs according to leetcode posts.");
        return jobFinderService.findCompaniesHiring(time);
    }

    @GetMapping("/questions/{companyName}/{time}/{questionType}")
    public List<Question> getQuestions(@PathVariable("companyName") String companyName, @PathVariable("time") DateRangeEnum time,
                                       @PathVariable("questionType")String questionType) {
        System.out.println("This will return the questions.");
        log.info("Company Name:{}, Question Type:{}",companyName,questionType);
        QuestionType qt = QuestionType.valueOf(questionType.toUpperCase());
        List<Question> res = jobFinderService.findQuestions(companyName,time,qt);
        System.out.println("Found the result, result length:" + res.size());
        return res;
    }
}
