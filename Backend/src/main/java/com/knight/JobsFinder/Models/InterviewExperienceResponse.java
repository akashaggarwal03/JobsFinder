package com.knight.JobsFinder.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
public class InterviewExperienceResponse {

    @JsonProperty("title")
    private String companyName;

    @JsonProperty("creationDate")
    private String creationDate;

}
