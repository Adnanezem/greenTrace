package com.greentracer.app.internal;

import java.util.Map;

import com.greentracer.app.responses.GreenTracerResponse;

public class CarbonCalculator {
    public static float computeCarEmissions(String fuel, int distance) {
        float emissions = 0;
        if (!fuel.isBlank()) {
            if (fuel.equals("Diesel")) {
                emissions += 160 * distance;
            } else if (fuel.equals("electric")) {
                emissions += 70 * distance;
            } else if (fuel.equals("gasoline")) {
                emissions += 180 * distance;
            }
        }
        return emissions;
    }

    public static float computeBusEmissions(String fuel, int distance) {
        float emissions = 0;
        if (!fuel.isBlank()) {
            if (fuel.equals("Diesel")) {
                emissions += 200 * distance; //1100 pour tt le bus
            } else if (fuel.equals("electric")) {
                emissions += 80 * distance; //200
            }
        }
        return emissions;
    }
}
