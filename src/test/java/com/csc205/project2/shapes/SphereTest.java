package com.csc205.project2.shapes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Sphere}.
 *
 * Coverage:
 * - Basic functionality (constructors, getters/setters, color defaulting)
 * - Calculation accuracy (known values with tolerance 1e-9)
 * - Boundary testing (zero radius, very large values finite)
 * - Input validation (negative radius)
 * - Inheritance/polymorphism via {@link ThreeDimensionalShape}
 */
public class SphereTest {

    private static final double TOL = 1e-9;

    @Nested
    @DisplayName("Basic Functionality")
    class BasicFunctionality {

        @Test
        @DisplayName("Constructor sets name/color/radius correctly")
        void constructorSetsFields() {
            Sphere s = new Sphere("Ball", "Blue", 3.0);
            assertEquals("Ball", s.getName());
            assertEquals("Blue", s.getColor());
            assertEquals(3.0, s.getRadius(), TOL);
        }

        @Test
        @DisplayName("Constructor without color defaults color to 'Unspecified'")
        void constructorWithoutColorDefaults() {
            Sphere s = new Sphere("OrphanColor", 2.5);
            assertEquals("OrphanColor", s.getName());
            assertEquals("Unspecified", s.getColor());
            assertEquals(2.5, s.getRadius(), TOL);
        }

        @Test
        @DisplayName("Constructor with null/blank color normalizes to 'Unspecified'")
        void constructorNullOrBlankColorDefaults() {
            Sphere sNull = new Sphere("NullColor", null, 1.0);
            assertEquals("Unspecified", sNull.getColor());

            Sphere sBlank = new Sphere("BlankColor", "   ", 1.0);
            assertEquals("Unspecified", sBlank.getColor());
        }

        @Test
        @DisplayName("Getters/setters behave with valid values")
        void gettersSettersValid() {
            Sphere s = new Sphere("Start", "Red", 1.0);
            s.setName("Renamed");
            s.setColor("Green");
            s.setRadius(4.5);

            assertEquals("Renamed", s.getName());
            assertEquals("Green", s.getColor());
            assertEquals(4.5, s.getRadius(), TOL);
        }
    }

    @Nested
    @DisplayName("Calculation Accuracy")
    class CalculationAccuracy {

        @Test
        @DisplayName("Known values for r=5 (volume and surface area)")
        void knownValuesR5() {
            double r = 5.0;
            Sphere s = new Sphere("Unit", r);

            double expectedVolume = (4.0 / 3.0) * Math.PI * Math.pow(r, 3);
            double expectedSurfaceArea = 4.0 * Math.PI * Math.pow(r, 2);

            assertEquals(expectedVolume, s.getVolume(), TOL);
            assertEquals(expectedSurfaceArea, s.getSurfaceArea(), TOL);
        }
    }

    @Nested
    @DisplayName("Boundary Testing")
    class BoundaryTesting {

        @Test
        @DisplayName("radius=0 → volume=0 and surfaceArea=0")
        void zeroRadiusYieldsZeroes() {
            Sphere s = new Sphere("Flat", 0.0);
            assertEquals(0.0, s.getVolume(), TOL);
            assertEquals(0.0, s.getSurfaceArea(), TOL);
        }

        @Test
        @DisplayName("Very large but finite values compute finite results")
        void veryLargeFinite() {
            // Choose a radius large enough to stress doubles but not overflow:
            // r = 1e100 -> V ~ 4.18879e300, SA ~ 1.25663e201 (both finite in double)
            double r = 1e100;
            Sphere s = new Sphere("Huge", r);

            double vol = s.getVolume();
            double sa = s.getSurfaceArea();

            assertTrue(Double.isFinite(vol), "Volume should be finite for r=1e100");
            assertTrue(Double.isFinite(sa), "Surface area should be finite for r=1e100");
            assertTrue(vol > 0.0 && sa > 0.0);
        }
    }

    @Nested
    @DisplayName("Input Validation")
    class InputValidation {

        @Test
        @DisplayName("Negative radius in constructor throws IllegalArgumentException")
        void negativeRadiusInConstructor() {
            assertThrows(IllegalArgumentException.class, () -> new Sphere("Bad", -1.0));
            assertThrows(IllegalArgumentException.class, () -> new Sphere("Bad", "Blue", -0.0001));
        }

        @Test
        @DisplayName("Negative radius in setter throws IllegalArgumentException")
        void negativeRadiusInSetter() {
            Sphere s = new Sphere("Setter", 1.0);
            assertThrows(IllegalArgumentException.class, () -> s.setRadius(-2.0));
        }
    }

    @Nested
    @DisplayName("Inheritance / Polymorphism")
    class InheritancePolymorphism {

        @Test
        @DisplayName("Treat Sphere as ThreeDimensionalShape and call compute methods")
        void treatAsInterface() {
            ThreeDimensionalShape shape = new Sphere("Poly", 2.0);
            double expectedVol = (4.0 / 3.0) * Math.PI * Math.pow(2.0, 3);
            double expectedSA = 4.0 * Math.PI * Math.pow(2.0, 2);

            assertEquals(expectedVol, shape.getVolume(), TOL);
            assertEquals(expectedSA, shape.getSurfaceArea(), TOL);
        }
    }
}