package com.csc205.project2.shapes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Cylinder}.
 *
 * Coverage:
 * - Basic functionality (constructors, getters/setters, color defaulting)
 * - Calculation accuracy (known values with tolerance 1e-9)
 * - Boundary testing (zero dimensions, very large values finite)
 * - Input validation (negative radius or height)
 * - Inheritance/polymorphism via {@link ThreeDimensionalShape}
 */
public class CylinderTest {

    private static final double TOL = 1e-9;

    @Nested
    @DisplayName("Basic Functionality")
    class BasicFunctionality {

        @Test
        @DisplayName("Constructor sets name/color/radius/height correctly")
        void constructorSetsFields() {
            Cylinder c = new Cylinder("Can", "Silver", 2.0, 5.0);
            assertEquals("Can", c.getName());
            assertEquals("Silver", c.getColor());
            assertEquals(2.0, c.getRadius(), TOL);
            assertEquals(5.0, c.getHeight(), TOL);
        }

        @Test
        @DisplayName("Constructor without color defaults color to 'Unspecified'")
        void constructorWithoutColorDefaults() {
            Cylinder c = new Cylinder("Plain", 3.0, 4.0);
            assertEquals("Plain", c.getName());
            assertEquals("Unspecified", c.getColor());
            assertEquals(3.0, c.getRadius(), TOL);
            assertEquals(4.0, c.getHeight(), TOL);
        }

        @Test
        @DisplayName("Constructor with null/blank color normalizes to 'Unspecified'")
        void constructorNullOrBlankColorDefaults() {
            Cylinder cNull = new Cylinder("NullColor", null, 1.0, 2.0);
            assertEquals("Unspecified", cNull.getColor());

            Cylinder cBlank = new Cylinder("BlankColor", "   ", 1.0, 2.0);
            assertEquals("Unspecified", cBlank.getColor());
        }

        @Test
        @DisplayName("Getters/setters behave with valid values")
        void gettersSettersValid() {
            Cylinder c = new Cylinder("Start", "Blue", 1.0, 2.0);
            c.setName("Renamed");
            c.setColor("Green");
            c.setRadius(4.5);
            c.setHeight(9.0);

            assertEquals("Renamed", c.getName());
            assertEquals("Green", c.getColor());
            assertEquals(4.5, c.getRadius(), TOL);
            assertEquals(9.0, c.getHeight(), TOL);
        }
    }

    @Nested
    @DisplayName("Calculation Accuracy")
    class CalculationAccuracy {

        @Test
        @DisplayName("Known values for r=2, h=10 (volume and surface area)")
        void knownValuesR2H10() {
            double r = 2.0;
            double h = 10.0;
            Cylinder c = new Cylinder("Unit", r, h);

            double expectedVolume = 40.0 * Math.PI; // πr²h = π*4*10
            double expectedSurfaceArea = 48.0 * Math.PI; // 2πr(r+h) = 2π*2*12

            assertEquals(expectedVolume, c.getVolume(), TOL);
            assertEquals(expectedSurfaceArea, c.getSurfaceArea(), TOL);
        }
    }

    @Nested
    @DisplayName("Boundary Testing")
    class BoundaryTesting {

        @Test
        @DisplayName("radius=0 or height=0 → volume=0, surface area non-negative")
        void zeroDimensionYieldsZeroVolume() {
            Cylinder c1 = new Cylinder("FlatR", 0.0, 5.0);
            assertEquals(0.0, c1.getVolume(), TOL);
            assertEquals(2.0 * Math.PI * 0.0 * (0.0 + 5.0), c1.getSurfaceArea(), TOL);

            Cylinder c2 = new Cylinder("FlatH", 3.0, 0.0);
            assertEquals(0.0, c2.getVolume(), TOL);
            assertEquals(2.0 * Math.PI * 3.0 * (3.0 + 0.0), c2.getSurfaceArea(), TOL);
        }

        @Test
        @DisplayName("Very large but finite values compute finite results")
        void veryLargeFinite() {
            double r = 1e100;
            double h = 1e100;
            Cylinder c = new Cylinder("Huge", r, h);

            double vol = c.getVolume(); // ~π * 1e300
            double sa = c.getSurfaceArea(); // ~2π * 1e200 * (2e100)

            assertTrue(Double.isFinite(vol), "Volume should be finite for r=h=1e100");
            assertTrue(Double.isFinite(sa), "Surface area should be finite for r=h=1e100");
            assertTrue(vol > 0.0 && sa > 0.0);
        }
    }

    @Nested
    @DisplayName("Input Validation")
    class InputValidation {

        @Test
        @DisplayName("Negative radius in constructor throws IllegalArgumentException")
        void negativeRadiusInConstructor() {
            assertThrows(IllegalArgumentException.class, () -> new Cylinder("Bad", -1.0, 5.0));
            assertThrows(IllegalArgumentException.class, () -> new Cylinder("Bad", "Gray", -0.1, 5.0));
        }

        @Test
        @DisplayName("Negative height in constructor throws IllegalArgumentException")
        void negativeHeightInConstructor() {
            assertThrows(IllegalArgumentException.class, () -> new Cylinder("Bad", 2.0, -5.0));
            assertThrows(IllegalArgumentException.class, () -> new Cylinder("Bad", "Gray", 2.0, -0.1));
        }

        @Test
        @DisplayName("Negative radius/height in setter throws IllegalArgumentException")
        void negativeInSetter() {
            Cylinder c = new Cylinder("Setter", 2.0, 5.0);
            assertThrows(IllegalArgumentException.class, () -> c.setRadius(-2.0));
            assertThrows(IllegalArgumentException.class, () -> c.setHeight(-3.0));
        }
    }

    @Nested
    @DisplayName("Inheritance / Polymorphism")
    class InheritancePolymorphism {

        @Test
        @DisplayName("Treat Cylinder as ThreeDimensionalShape and call compute methods")
        void treatAsInterface() {
            ThreeDimensionalShape shape = new Cylinder("Poly", 2.0, 10.0);
            double expectedVol = 40.0 * Math.PI;
            double expectedSA = 48.0 * Math.PI;

            assertEquals(expectedVol, shape.getVolume(), TOL);
            assertEquals(expectedSA, shape.getSurfaceArea(), TOL);
        }
    }
}