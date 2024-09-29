package com.example.gymfitness.utils;

public class StringUtils {
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        String[] words = str.split("\\s+");
        StringBuilder capitalizedStr = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                capitalizedStr.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return capitalizedStr.toString().trim();
    }
}
