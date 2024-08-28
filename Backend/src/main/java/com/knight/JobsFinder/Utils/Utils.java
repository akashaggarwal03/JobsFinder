package com.knight.JobsFinder.Utils;

import java.util.HashSet;
import java.util.Set;

public class Utils {

    private static final Set<String> companySet = new HashSet<>();

    //Todo: Move this to a db.
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
        companySet.add("goldman sachs");
        companySet.add("flipkart");
        companySet.add("wayfair");
        companySet.add("meta");
        companySet.add("meesho");
        companySet.add("zepto");
        companySet.add("uber");
        companySet.add("adobe");
        companySet.add("bizongo");
        companySet.add("walmart");
        companySet.add("tekion");
        companySet.add("tower research");
        companySet.add("coinswitch");
        companySet.add("paypal");
        companySet.add("payu");
        companySet.add("arcesium");
        companySet.add("qualcomm");
        companySet.add("ola");
        companySet.add("sap labs");
        companySet.add("phonepe");
        companySet.add("nutanix");
        companySet.add("glean");
        companySet.add("hotstar");
        companySet.add("rippling");
        companySet.add("linkedin");
        companySet.add("oracle");
        companySet.add("confluent");
        companySet.add("visa");
        companySet.add("discord");
        companySet.add("expedia");
        companySet.add("doordash");
        companySet.add("myntra");

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
