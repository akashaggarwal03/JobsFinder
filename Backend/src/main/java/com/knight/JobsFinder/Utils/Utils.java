package com.knight.JobsFinder.Utils;

import java.util.HashSet;
import java.util.Set;

public class Utils {

    private static final Set<String> companySet = new HashSet<>();

    static {
        // Initialize the set with known company names
        companySet.add("mindtickle");
        companySet.add("google");
        companySet.add("microsoft");
        companySet.add("chargebee");
        companySet.add("dream11");
        companySet.add("groww");
        companySet.add("sumo logic");
        companySet.add("box");
        companySet.add("innovaccer");
        companySet.add("amazon");
        // Add more as needed
    }

    public static String extractCompanyName(String title) {
        for (String company : companySet) {
            if (title.contains(company)) {
                return company;
            }
        }
        return ""; // Default value if no matching company found
    }


}
