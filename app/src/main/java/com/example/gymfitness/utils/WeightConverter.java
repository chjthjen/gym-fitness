package com.example.gymfitness.utils;

public class WeightConverter {
    public static float lbToKg(float pounds) {
        final float POUND_TO_KG_CONVERSION_FACTOR = 0.45359237f;
        return roundToTwoDecimalPlaces(pounds * POUND_TO_KG_CONVERSION_FACTOR);
    }

    public static float roundToTwoDecimalPlaces(float value) {
        return Math.round(value * 100.0f) / 100.0f;
    }

}
