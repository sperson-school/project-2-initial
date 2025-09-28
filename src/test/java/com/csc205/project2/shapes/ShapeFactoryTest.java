package com.csc205.project2.shapes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ShapeFactory}.
 *
 * Coverage:
 * - Successful creation via (Class, name, dims...) for Sphere
 * - Successful creation via (Class, name, color, dims...) for Cylinder
 * - Invalid inputs: blank name, null type, wrong dims count -> IllegalArgumentException
 *
 * Notes:
 * - Numeric comparisons use tolerance 1e-9
 * - Tests do not rely on logger output
 */
public class ShapeFactoryTest {

    private static final double TOL = 1e-9;

    @Test
    @DisplayName("Create Sphere with (Class, name, dims...) and verify type, name, and calculations")
    void createSphere_NameDims_Succeeds() {
        // Arrange
        String name = "UnitSphere";
        double r = 5.0;

        // Act
        Shape3D shape = ShapeFactory.create(Sphere.class, name, r);

        // Assert: type & name
        assertTrue(shape instanceof Sphere, "Factory should return a Sphere");
        assertEquals(name, ((Sphere) shape).getName());

        // Assert: calculations
        double expectedVol = (4.0 / 3.0) * Math.PI * Math.pow(r, 3);
        double expectedSA  = 4.0 * Math.PI * Math.pow(r, 2);
        assertEquals(expectedVol, shape.getVolume(), TOL);
        assertEquals(expectedSA,  shape.getSurfaceArea(), TOL);
    }

    @Test
    @DisplayName("Create Cylinder with (Class, name, color, dims...) and verify color and calculations")
    void createCylinder_NameColorDims_Succeeds() {
        // Arrange
        String name = "Can";
        String color = "Teal";
        double r = 2.0, h = 10.0;

        // Act
        Shape3D shape = ShapeFactory.create(Cylinder.class, name, color, r, h);

        // Assert: type, name, color
        assertTrue(shape instanceof Cylinder, "Factory should return a Cylinder");
        Cylinder cyl = (Cylinder) shape;
        assertEquals(name,  cyl.getName());
        assertEquals(color, cyl.getColor());

        // Assert: calculations
        double expectedVol = Math.PI * r * r * h;            // 40π
        double expectedSA  = 2.0 * Math.PI * r * (r + h);    // 48π
        assertEquals(expectedVol, shape.getVolume(), TOL);
        assertEquals(expectedSA,  shape.getSurfaceArea(), TOL);
    }

    @Test
    @DisplayName("Invalid: blank name -> IllegalArgumentException")
    void blankName_Throws() {
        assertThrows(IllegalArgumentException.class,
                () -> ShapeFactory.create(Sphere.class, "   ", 1.0));
        assertThrows(IllegalArgumentException.class,
                () -> ShapeFactory.create(Cylinder.class, "   ", "Red", 1.0, 2.0));
    }

    @Test
    @DisplayName("Invalid: null type -> IllegalArgumentException")
    void nullType_Throws() {
        assertThrows(IllegalArgumentException.class,
                () -> ShapeFactory.create(null, "Thing", 1.0));
        assertThrows(IllegalArgumentException.class,
                () -> ShapeFactory.create(null, "Thing", "Blue", 1.0));
    }

    @Test
    @DisplayName("Invalid: wrong dims count for explicit-arity constructor -> IllegalArgumentException")
    void wrongDimsCount_Throws() {
        // RectangularPrism requires (l, w, h); provide only two dims
        assertThrows(IllegalArgumentException.class,
                () -> ShapeFactory.create(RectangularPrism.class, "BadBox", 3.0, 4.0));

        // Cylinder requires (r, h); provide only one dim on color overload
        assertThrows(IllegalArgumentException.class,
                () -> ShapeFactory.create(Cylinder.class, "BadCan", "Gray", 2.0));
    }
}
