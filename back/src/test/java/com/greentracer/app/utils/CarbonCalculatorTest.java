package com.greentracer.app.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarbonCalculatorTest {

    @Test
    public void testComputeCarEmissionsDiesel() {
        float emissions = CarbonCalculator.computeCarEmissions("diesel", 100);
        assertEquals(16000, emissions);
    }

    @Test
    public void testComputeCarEmissionsElectric() {
        float emissions = CarbonCalculator.computeCarEmissions("electric", 100);
        assertEquals(7000, emissions);
    }

    @Test
    public void testComputeCarEmissionsGasoline() {
        float emissions = CarbonCalculator.computeCarEmissions("gasoline", 100);
        assertEquals(18000, emissions);
    }

    @Test
    public void testComputeCarEmissionsBlankFuel() {
        float emissions = CarbonCalculator.computeCarEmissions("", 100);
        assertEquals(0, emissions);
    }

    @Test
    public void testComputeBusEmissionsDiesel() {
        float emissions = CarbonCalculator.computeBusEmissions("diesel", 100);
        assertEquals(20000, emissions);
    }

    @Test
    public void testComputeBusEmissionsElectric() {
        float emissions = CarbonCalculator.computeBusEmissions("electric", 100);
        assertEquals(8000, emissions);
    }

    @Test
    public void testComputeBusEmissionsBlankFuel() {
        float emissions = CarbonCalculator.computeBusEmissions("", 100);
        assertEquals(0, emissions);
    }
}