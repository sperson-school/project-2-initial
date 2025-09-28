package com.csc205.project2.shapes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link RectangularPrism}.
 *
 * Coverage:
 * - Basic functionality (constructors, getters/setters, color defaulting)
 * - Calculation accuracy (known values with tolerance 1e-12)
 * - Boundary testing (zero dimensions, very large values finite)
 * - Input validation (negative length/width/height)
 * - Inheritance/polymorphism via {@link ThreeDimensionalShape}
 */
public class RectangularPrismTest {

    private static final double TOL = 1e-12;

    @Nested
    @DisplayName("Basic Functionality")
    class BasicFunctionality {

        @Test
        @DisplayName("Constructor sets name/color/length/width/height correctly")
        void constructorSetsFields() {
            RectangularPrism p = new RectangularPrism("Box", "Brown", 3.0, 4.0, 5.0);
            assertEquals("Box", p.getName());
            assertEquals("Brown", p.getColor());
            assertEquals(3.0, p.getLength(), TOL);
            assertEquals(4.0, p.getWidth(), TOL);
            assertEquals(5.0, p.getHeight(), TOL);
        }

        @Test
        @DisplayName("Constructor without color defaults color to 'Unspecified'")
        void constructorWithoutColorDefaults() {
            RectangularPrism p = new RectangularPrism("Plain", 1.0, 2.0, 3.0);
            assertEquals("Plain", p.getName());
            assertEquals("Unspecified", p.getColor());
            assertEquals(1.0, p.getLength(), TOL);
            assertEquals(2.0, p.getWidth(), TOL);
            assertEquals(3.0, p.getHeight(), TOL);
        }

        @Test
        @DisplayName("Constructor with null/blank color normalizes to 'Unspecified'")
        void constructorNullOrBlankColorDefaults() {
            RectangularPrism pNull = new RectangularPrism("NullColor", null, 2.0, 2.0, 2.0);
            assertEquals("Unspecified", pNull.getColor());

            RectangularPrism pBlank = new RectangularPrism("BlankColor", "   ", 2.0, 2.0, 2.0);
            assertEquals("Unspecified", pBlank.getColor());
        }

        @Test
        @DisplayName("Getters/setters behave with valid values")
        void gettersSettersValid() {
            RectangularPrism p = new RectangularPrism("Start", "Gray", 1.0, 1.5, 2.0);
            p.setName("Renamed");
            p.setColor("Green");
            p.setLength(4.0);
            p.setWidth(5.0);
            p.setHeight(6.0);

            assertEquals("Renamed", p.getName());
            assertEquals("Green", p.getColor());
            assertEquals(4.0, p.getLength(), TOL);
            assertEquals(5.0, p.getWidth(), TOL);
            assertEquals(6.0, p.getHeight(), TOL);
        }
    }

    @Nested
    @DisplayName("Calculation Accuracy")
    class CalculationAccuracy {

        @Test
        @DisplayName("Known values for l=3, w=4, h=5 (volume and surface area)")
        void knownValuesL3W4H5() {
            double l = 3.0, w = 4.0, h = 5.0;
            RectangularPrism p = new RectangularPrism("Unit", l, w, h);

            double expectedVolume = 60.0; // lwh
            double expectedSurfaceArea = 94.0; // 2(lw + lh + wh) = 2(12 + 15 + 20)

            assertEquals(expectedVolume, p.getVolume(), TOL);
            assertEquals(expectedSurfaceArea, p.getSurfaceArea(), TOL);
        }
    }

    @Nested
    @DisplayName("Boundary Testing")
    class BoundaryTesting {

        @Test
        @DisplayName("Zero in one dimension → volume=0, surface area matches formula")
        void zeroDimensionBehavior() {
            double l = 0.0, w = 4.0, h = 5.0;
            RectangularPrism p = new RectangularPrism("Flat", l, w, h);

            double expectedVolume = 0.0;
            double expectedSurfaceArea = 2.0 * (l * w + l * h + w * h); // 2(0 + 0 + 20) = 40

            assertEquals(expectedVolume, p.getVolume(), TOL);
            assertEquals(expectedSurfaceArea, p.getSurfaceArea(), TOL);
        }

        @Test
        @DisplayName("Very large but finite values compute finite results")
        void veryLargeFinite() {
            double l = 1e100, w = 2e100, h = 3e100;
            RectangularPrism p = new RectangularPrism("Huge", l, w, h);

            double vol = p.getVolume(); // ~6e300
            double sa = p.getSurfaceArea(); // ~2*(2e200 + 3e200 + 6e200) ~ 2.2e201

            assertTrue(Double.isFinite(vol), "Volume should be finite");
            assertTrue(Double.isFinite(sa), "Surface area should be finite");
            assertTrue(vol > 0.0 && sa > 0.0);
        }
    }

    @Nested
    @DisplayName("Input Validation")
    class InputValidation {

        @Test
        @DisplayName("Negative length in constructor throws IllegalArgumentException")
        void negativeLengthConstructor() {
            assertThrows(IllegalArgumentException.class, () -> new RectangularPrism("Bad", -1.0, 2.0, 3.0));
            assertThrows(IllegalArgumentException.class, () -> new RectangularPrism("Bad", "Red", -1.0, 2.0, 3.0));
        }

        @Test
        @DisplayName("Negative width in constructor throws IllegalArgumentException")
        void negativeWidthConstructor() {
            assertThrows(IllegalArgumentException.class, () -> new RectangularPrism("Bad", 1.0, -2.0, 3.0));
            assertThrows(IllegalArgumentException.class, () -> new RectangularPrism("Bad", "Red", 1.0, -2.0, 3.0));
        }

        @Test
        @DisplayName("Negative height in constructor throws IllegalArgumentException")
        void negativeHeightConstructor() {
            assertThrows(IllegalArgumentException.class, () -> new RectangularPrism("Bad", 1.0, 2.0, -3.0));
            assertThrows(IllegalArgumentException.class, () -> new RectangularPrism("Bad", "Red", 1.0, 2.0, -3.0));
        }

        @Test
        @DisplayName("Negative dimensions in setters throw IllegalArgumentException")
        void negativeInSetters() {
            RectangularPrism p = new RectangularPrism("Setter", 1.0, 2.0, 3.0);
            assertThrows(IllegalArgumentException.class, () -> p.setLength(-1.0));
            assertThrows(IllegalArgumentException.class, () -> p.setWidth(-2.0));
            assertThrows(IllegalArgumentException.class, () -> p.setHeight(-3.0));
        }
    }

    @Nested
    @DisplayName("Inheritance / Polymorphism")
    class InheritancePolymorphism {

        @Test
        @DisplayName("Treat RectangularPrism as ThreeDimensionalShape and call compute methods")
        void treatAsInterface() {
            ThreeDimensionalShape shape = new RectangularPrism("Poly", 3.0, 4.0, 5.0);
            double expectedVol = 60.0;
            double expectedSA = 94.0;

            assertEquals(expectedVol, shape.getVolume(), TOL);
            assertEquals(expectedSA, shape.getSurfaceArea(), TOL);
        }
    }
}
