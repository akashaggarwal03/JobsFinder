package com.knight.JobsFinder.Controller;

import com.knight.JobsFinder.Models.DateRangeEnum;
import com.knight.JobsFinder.Models.Job;
import com.knight.JobsFinder.Models.Question;
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

    @GetMapping("/questions/{companyName}")
    public List<Question> getQuestions(@PathVariable String companyName) {
        System.out.println("This will return the questions.");
        return Collections.EMPTY_LIST;
    }
}
