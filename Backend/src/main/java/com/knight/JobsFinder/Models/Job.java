package com.knight.JobsFinder.Models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Model for Job.
 */
@Data
@AllArgsConstructor
public class Job {

    /**
     * Name of the company.
     */
    private String companyName;

    /**
     * Number of posts about the company.
     */
    private Integer numberOfPosts;
}
