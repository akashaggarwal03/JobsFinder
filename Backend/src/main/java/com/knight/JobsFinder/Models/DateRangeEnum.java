package com.knight.JobsFinder.Models;

import java.util.*;

public enum DateRangeEnum {
    LAST_WEEK(7),
    LAST_MONTH(30),
    LAST_SIX_MONTH(180),
    ANYTIME(1000);

    private final Integer label;

    // Constructor to set the label
    DateRangeEnum (Integer label) {
        this.label = label;
    }

    // Method to get the label
    public Integer getLabel() {
        return label;
    }
}
