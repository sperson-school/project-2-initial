package com.csc205.project2.shapes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Cone}.
 *
 * Coverage:
 * - Basic functionality (constructors, getters/setters, color defaulting)
 * - Calculation accuracy (known values with tolerance 1e-9)
 * - Boundary testing (zero dimensions, very large values finite)
 * - Input validation (negative radius or height)
 * - Inheritance/polymorphism via {@link ThreeDimensionalShape}
 */
public class ConeTest {

    private static final double TOL = 1e-9;

    @Nested
    @DisplayName("Basic Functionality")
    class BasicFunctionality {

        @Test
        @DisplayName("Constructor sets name/color/radius/height correctly")
        void constructorSetsFields() {
            Cone c = new Cone("Cone1", "Tan", 3.0, 4.0);
            assertEquals("Cone1", c.getName());
            assertEquals("Tan", c.getColor());
            assertEquals(3.0, c.getRadius(), TOL);
            assertEquals(4.0, c.getHeight(), TOL);
        }

        @Test
        @DisplayName("Constructor without color defaults color to 'Unspecified'")
        void constructorWithoutColorDefaults() {
            Cone c = new Cone("Plain", 3.0, 4.0);
            assertEquals("Plain", c.getName());
            assertEquals("Unspecified", c.getColor());
            assertEquals(3.0, c.getRadius(), TOL);
            assertEquals(4.0, c.getHeight(), TOL);
        }

        @Test
        @DisplayName("Constructor with null/blank color normalizes to 'Unspecified'")
        void constructorNullOrBlankColorDefaults() {
            Cone cNull = new Cone("NullColor", null, 2.0, 2.0);
            assertEquals("Unspecified", cNull.getColor());

            Cone cBlank = new Cone("BlankColor", "   ", 2.0, 2.0);
            assertEquals("Unspecified", cBlank.getColor());
        }

        @Test
        @DisplayName("Getters/setters behave with valid values")
        void gettersSettersValid() {
            Cone c = new Cone("Start", "Blue", 1.0, 2.0);
            c.setName("Renamed");
            c.setColor("Green");
            c.setRadius(4.5);
            c.setHeight(6.5);

            assertEquals("Renamed", c.getName());
            assertEquals("Green", c.getColor());
            assertEquals(4.5, c.getRadius(), TOL);
            assertEquals(6.5, c.getHeight(), TOL);
        }
    }

    @Nested
    @DisplayName("Calculation Accuracy")
    class CalculationAccuracy {

        @Test
        @DisplayName("Known values for r=3, h=4 (volume and surface area)")
        void knownValuesR3H4() {
            double r = 3.0, h = 4.0;
            Cone c = new Cone("Unit", r, h);

            double expectedVolume = 12.0 * Math.PI;  // (1/3)πr²h = (1/3)π*9*4
            double expectedSurfaceArea = 24.0 * Math.PI; // πr(r+√(r²+h²)) = π*3*(3+5)

            assertEquals(expectedVolume, c.getVolume(), TOL);
            assertEquals(expectedSurfaceArea, c.getSurfaceArea(), TOL);
        }
    }

    @Nested
    @DisplayName("Boundary Testing")
    class BoundaryTesting {

        @Test
        @DisplayName("Zero radius → volume=0 and surface area=0")
        void zeroRadius() {
            Cone c = new Cone("Flat", 0.0, 5.0);
            assertEquals(0.0, c.getVolume(), TOL);
            assertEquals(0.0, c.getSurfaceArea(), TOL);
        }

        @Test
        @DisplayName("Zero height → volume=0, surface area = base area only")
        void zeroHeight() {
            double r = 3.0;
            Cone c = new Cone("FlatHeight", r, 0.0);

            double expectedVolume = 0.0;
            double expectedSurfaceArea = Math.PI * r * (r + Math.sqrt(r * r + 0.0)); // πr(r+r) = 2πr²

            assertEquals(expectedVolume, c.getVolume(), TOL);
            assertEquals(expectedSurfaceArea, c.getSurfaceArea(), TOL);
        }

        @Test
        @DisplayName("Very large but finite values compute finite results")
        void veryLargeFinite() {
            double r = 1e100, h = 1e100;
            Cone c = new Cone("Huge", r, h);

            double vol = c.getVolume(); // ~1e300
            double sa = c.getSurfaceArea(); // ~π*1e100*(something ~1e100)

            assertTrue(Double.isFinite(vol), "Volume should be finite");
            assertTrue(Double.isFinite(sa), "Surface area should be finite");
            assertTrue(vol > 0.0 && sa > 0.0);
        }
    }

    @Nested
    @DisplayName("Input Validation")
    class InputValidation {

        @Test
        @DisplayName("Negative radius in constructor throws IllegalArgumentException")
        void negativeRadiusInConstructor() {
            assertThrows(IllegalArgumentException.class, () -> new Cone("Bad", -1.0, 5.0));
            assertThrows(IllegalArgumentException.class, () -> new Cone("Bad", "Red", -0.1, 5.0));
        }

        @Test
        @DisplayName("Negative height in constructor throws IllegalArgumentException")
        void negativeHeightInConstructor() {
            assertThrows(IllegalArgumentException.class, () -> new Cone("Bad", 2.0, -5.0));
            assertThrows(IllegalArgumentException.class, () -> new Cone("Bad", "Red", 2.0, -0.1));
        }

        @Test
        @DisplayName("Negative radius/height in setter throws IllegalArgumentException")
        void negativeInSetter() {
            Cone c = new Cone("Setter", 2.0, 5.0);
            assertThrows(IllegalArgumentException.class, () -> c.setRadius(-2.0));
            assertThrows(IllegalArgumentException.class, () -> c.setHeight(-3.0));
        }
    }

    @Nested
    @DisplayName("Inheritance / Polymorphism")
    class InheritancePolymorphism {

        @Test
        @DisplayName("Treat Cone as ThreeDimensionalShape and call compute methods")
        void treatAsInterface() {
            ThreeDimensionalShape shape = new Cone("Poly", 3.0, 4.0);
            double expectedVol = 12.0 * Math.PI;
            double expectedSA = 24.0 * Math.PI;

            assertEquals(expectedVol, shape.getVolume(), TOL);
            assertEquals(expectedSA, shape.getSurfaceArea(), TOL);
        }
    }
}