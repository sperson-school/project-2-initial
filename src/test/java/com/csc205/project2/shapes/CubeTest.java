package com.csc205.project2.shapes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Cube}.
 *
 * Coverage:
 * - Basic functionality (constructors, getters/setters, color defaulting)
 * - Calculation accuracy (known values with tolerance 1e-12)
 * - Boundary testing (zero side length, very large values finite)
 * - Input validation (negative side length)
 * - Inheritance/polymorphism via {@link ThreeDimensionalShape}
 */
public class CubeTest {

    private static final double TOL = 1e-12;

    @Nested
    @DisplayName("Basic Functionality")
    class BasicFunctionality {

        @Test
        @DisplayName("Constructor sets name/color/sideLength correctly")
        void constructorSetsFields() {
            Cube c = new Cube("Box", "Red", 3.0);
            assertEquals("Box", c.getName());
            assertEquals("Red", c.getColor());
            assertEquals(3.0, c.getSideLength(), TOL);
        }

        @Test
        @DisplayName("Constructor without color defaults color to 'Unspecified'")
        void constructorWithoutColorDefaults() {
            Cube c = new Cube("OrphanColor", 2.5);
            assertEquals("OrphanColor", c.getName());
            assertEquals("Unspecified", c.getColor());
            assertEquals(2.5, c.getSideLength(), TOL);
        }

        @Test
        @DisplayName("Constructor with null/blank color normalizes to 'Unspecified'")
        void constructorNullOrBlankColorDefaults() {
            Cube cNull = new Cube("NullColor", null, 1.0);
            assertEquals("Unspecified", cNull.getColor());

            Cube cBlank = new Cube("BlankColor", "   ", 1.0);
            assertEquals("Unspecified", cBlank.getColor());
        }

        @Test
        @DisplayName("Getters/setters behave with valid values")
        void gettersSettersValid() {
            Cube c = new Cube("Start", "Blue", 1.0);
            c.setName("Renamed");
            c.setColor("Green");
            c.setSideLength(4.5);

            assertEquals("Renamed", c.getName());
            assertEquals("Green", c.getColor());
            assertEquals(4.5, c.getSideLength(), TOL);
        }
    }

    @Nested
    @DisplayName("Calculation Accuracy")
    class CalculationAccuracy {

        @Test
        @DisplayName("Known values for sideLength=4 (volume and surface area)")
        void knownValuesSide4() {
            double s = 4.0;
            Cube c = new Cube("Unit", s);

            double expectedVolume = 64.0; // 4^3
            double expectedSurfaceArea = 96.0; // 6 * 4^2

            assertEquals(expectedVolume, c.getVolume(), TOL);
            assertEquals(expectedSurfaceArea, c.getSurfaceArea(), TOL);
        }
    }

    @Nested
    @DisplayName("Boundary Testing")
    class BoundaryTesting {

        @Test
        @DisplayName("sideLength=0 → volume=0 and surfaceArea=0")
        void zeroSideLengthYieldsZeroes() {
            Cube c = new Cube("Flat", 0.0);
            assertEquals(0.0, c.getVolume(), TOL);
            assertEquals(0.0, c.getSurfaceArea(), TOL);
        }

        @Test
        @DisplayName("Very large but finite values compute finite results")
        void veryLargeFinite() {
            double s = 1e100; // large but within double range
            Cube c = new Cube("Huge", s);

            double vol = c.getVolume(); // ~1e300
            double sa = c.getSurfaceArea(); // ~6e200

            assertTrue(Double.isFinite(vol), "Volume should be finite for s=1e100");
            assertTrue(Double.isFinite(sa), "Surface area should be finite for s=1e100");
            assertTrue(vol > 0.0 && sa > 0.0);
        }
    }

    @Nested
    @DisplayName("Input Validation")
    class InputValidation {

        @Test
        @DisplayName("Negative sideLength in constructor throws IllegalArgumentException")
        void negativeSideLengthInConstructor() {
            assertThrows(IllegalArgumentException.class, () -> new Cube("Bad", -1.0));
            assertThrows(IllegalArgumentException.class, () -> new Cube("Bad", "Red", -0.0001));
        }

        @Test
        @DisplayName("Negative sideLength in setter throws IllegalArgumentException")
        void negativeSideLengthInSetter() {
            Cube c = new Cube("Setter", 1.0);
            assertThrows(IllegalArgumentException.class, () -> c.setSideLength(-2.0));
        }
    }

    @Nested
    @DisplayName("Inheritance / Polymorphism")
    class InheritancePolymorphism {

        @Test
        @DisplayName("Treat Cube as ThreeDimensionalShape and call compute methods")
        void treatAsInterface() {
            ThreeDimensionalShape shape = new Cube("Poly", 2.0);
            double expectedVol = Math.pow(2.0, 3); // 8
            double expectedSA = 6.0 * Math.pow(2.0, 2); // 24

            assertEquals(expectedVol, shape.getVolume(), TOL);
            assertEquals(expectedSA, shape.getSurfaceArea(), TOL);
        }
    }
}