package com.greentracer.app.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CarbonCalculatorTest {

    @Test
    void testComputeCarEmissionsDiesel() {
        float emissions = CarbonCalculator.computeCarEmissions("diesel", 100);
        assertEquals(16000.f, emissions);
    }

    @Test
    void testComputeCarEmissionsElectric() {
        float emissions = CarbonCalculator.computeCarEmissions("electric", 100);
        assertEquals(7000.f, emissions);
    }

    @Test
    void testComputeCarEmissionsGasoline() {
        float emissions = CarbonCalculator.computeCarEmissions("gasoline", 100);
        assertEquals(18000.f, emissions);
    }

    @Test
    void testComputeCarEmissionsBlankFuel() {
        float emissions = CarbonCalculator.computeCarEmissions("", 100);
        assertEquals(0.f, emissions);
    }

    @Test
    void testComputeBusEmissionsDiesel() {
        float emissions = CarbonCalculator.computeBusEmissions("diesel", 100);
        assertEquals(20000.f, emissions);
    }

    @Test
    void testComputeBusEmissionsElectric() {
        float emissions = CarbonCalculator.computeBusEmissions("electric", 100);
        assertEquals(8000.f, emissions);
    }

    @Test
    void testComputeBusEmissionsBlankFuel() {
        float emissions = CarbonCalculator.computeBusEmissions("", 100);
        assertEquals(0.f, emissions);
    }

    @Test
    void testComputeVeloEmissionsElectric() {
        float emissions = CarbonCalculator.computeVeloEmissions("electric", 100);
        assertEquals(1100.f, emissions);
    }

    @Test
    void testComputeVeloEmissionsNonElectric() {
        float emissions = CarbonCalculator.computeVeloEmissions("non-electric", 100);
        assertEquals(100.f, emissions);
    }

    @Test
    void testComputeAvionEmissionsShortHaul() {
        float emissions = CarbonCalculator.computeAvionEmissions("short haul", 100);
        assertEquals(38700.f, emissions);
    }

    @Test
    void testComputeAvionEmissionsMediumHaul() {
        float emissions = CarbonCalculator.computeAvionEmissions("medium haul", 100);
        assertEquals(40800.f, emissions);
    }

    @Test
    void testComputeAvionEmissionsLongHaul() {
        float emissions = CarbonCalculator.computeAvionEmissions("long haul", 100);
        assertEquals(67300.f, emissions);
    }


    @Test
    void testComputeRepasRestoFastFood() {
        float resto = CarbonCalculator.computeRepasResto("breakfast", "fast food");
        assertEquals(4.25f, resto);
    }

    @Test
    void testComputeRepasRestoTraditional() {
        float resto = CarbonCalculator.computeRepasResto("breakfast", "traditional");
        assertEquals(1.5f, resto);
    }

    @Test
    void testComputeRepasRestoGourmet() {
        float resto = CarbonCalculator.computeRepasResto("breakfast", "gourmet");
        assertEquals(1.f, resto);
    }

    @Test
    void testComputeRepasRestoVegan() {
        float resto = CarbonCalculator.computeRepasResto("breakfast", "vegan");
        assertEquals(0.55f, resto);
    }

    @Test
    void testComputeRepasRestoVegetarian() {
        float resto = CarbonCalculator.computeRepasResto("breakfast", "vegetarian");
        assertEquals(0.35f, resto);
    }
}