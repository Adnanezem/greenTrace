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
        float resto = CarbonCalculator.computeRepasResto("fast food");
        assertEquals(4610f, resto);
    }

    @Test
    void testComputeRepasRestoTraditional() {
        float resto = CarbonCalculator.computeRepasResto("traditional");
        assertEquals(4489f, resto);
    }

    @Test
    void testComputeRepasRestoVegan() {
        float resto = CarbonCalculator.computeRepasResto("vegan");
        assertEquals(1170f, resto);
    }

    @Test
    void testComputeRepasRestoVegetarian() {
        float resto = CarbonCalculator.computeRepasResto("vegetarian");
        assertEquals(1530f, resto);
    }


    @Test
    void testComputeRepasMaisonTraditional() {
        float resto = CarbonCalculator.computeRepasMaison("breakfast","traditional");
        float resto2 = CarbonCalculator.computeRepasMaison("lunch","traditional");

        assertEquals(839f, resto);
        assertEquals(2980f, resto2);
    }

    @Test
    void testComputeRepasMaisonVegan() {
        float resto = CarbonCalculator.computeRepasMaison("breakfast","vegan");
        float resto2 = CarbonCalculator.computeRepasMaison("lunch","vegan");

        assertEquals(649.5f, resto);
        assertEquals(390, resto2);
    }

    @Test
    void testComputeRepasMaisonVegetarian() {
        float resto = CarbonCalculator.computeRepasMaison("breakfast","vegetarian");
        float resto2 = CarbonCalculator.computeRepasMaison("lunch","vegetarian");

        assertEquals(839f, resto);
        assertEquals(510f, resto2);
    }
}