package com.knight.JobsFinder.Models;

public enum QuestionType {

    DSA("DSA"),
    SYSTEM_DESIGN("System Design"),
    BEHAVIORAL("Behavioral");

    private final String label;

    QuestionType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}