/**
 *
 * AI GENERATION DOCUMENTATION
 * ===========================
 * AI Tool Used: ChatGPT GPT-5
 * Generation Date: 2025-09-28
 * Original Prompt:
 * "Use the generic shape template. Class: Cone Fields: double radius, double height Formulas:
 *
 * Volume = (1/3) * π * r^2 * h
 * Surface Area = π * r * (r + √(r^2 + h^2)) // use slant height"
 *
 * Follow-up Prompts (if any):
 * None
 *
 * Manual Modifications:
 * - Converted generic template to explicit fields {@code radius} and {@code height}.
 * - Implemented cone formulas for surface area (using slant height) and volume.
 * - Updated {@code toString()} to show radius and height clearly.
 * - Added references to formula sources.
 *
 * Formula Verification:
 * - Cone surface area: πr(r + √(r² + h²)) (Wolfram MathWorld: "Cone", https://mathworld.wolfram.com/Cone.html)
 * - Cone volume: (1/3)πr²h (Wolfram MathWorld: "Cone", https://mathworld.wolfram.com/Cone.html)
 */

package com.csc205.project2.shapes;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code Cone} class models a right circular cone in three-dimensional space.
 *
 * <p><strong>Mathematical background:</strong></p>
 * <ul>
 *   <li>Surface Area = πr(r + √(r² + h²))</li>
 *   <li>Volume = (1/3)πr²h</li>
 * </ul>
 *
 * <p>The surface area formula uses the slant height
 * {@code l = √(r² + h²)}, combining the base area and lateral area.</p>
 *
 * <p><strong>Design rationale:</strong></p>
 * <ol>
 *   <li>Encapsulates {@code radius} and {@code height} with validation (must be ≥ 0).</li>
 *   <li>Implements only the mathematical calculators while leaving the compute API immutable in {@link Shape3D}.</li>
 *   <li>Logs lifecycle events (INFO), boundary cases (WARNING), and invalid input (SEVERE).</li>
 *   <li>Provides a clean {@code toString()} that appends radius and height to the base info.</li>
 * </ol>
 */
public class Cone extends Shape3D {

    /** Logger specific to Cone computations. */
    private static final Logger LOGGER = Logger.getLogger(Cone.class.getName());

    /** Radius of the cone base, must be ≥ 0. */
    private double radius;

    /** Height of the cone, must be ≥ 0. */
    private double height;

    // ---------- Constructors ----------

    /**
     * Creates a new Cone with a given name, radius, and height.
     *
     * @param name   the name of the cone (validated by {@link Shape3D})
     * @param radius the radius, must be ≥ 0
     * @param height the height, must be ≥ 0
     * @throws IllegalArgumentException if radius or height is negative
     */
    public Cone(String name, double radius, double height) {
        super(name);
        setRadius(radius);
        setHeight(height);
        LOGGER.log(Level.INFO, "Created Cone name={0}, radius={1}, height={2}",
                new Object[]{getName(), this.radius, this.height});
    }

    /**
     * Creates a new Cone with a given name, color, radius, and height.
     *
     * @param name   the name of the cone (validated by {@link Shape3D})
     * @param color  the color (normalized by {@link Shape3D})
     * @param radius the radius, must be ≥ 0
     * @param height the height, must be ≥ 0
     * @throws IllegalArgumentException if radius or height is negative
     */
    public Cone(String name, String color, double radius, double height) {
        super(name, color);
        setRadius(radius);
        setHeight(height);
        LOGGER.log(Level.INFO, "Created Cone name={0}, color={1}, radius={2}, height={3}",
                new Object[]{getName(), getColor(), this.radius, this.height});
    }

    // ---------- Getters/Setters ----------

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        if (radius < 0) {
            LOGGER.log(Level.SEVERE, "Invalid negative radius: {0}", radius);
            throw new IllegalArgumentException("Radius must be >= 0. Provided: " + radius);
        }
        if (radius == 0.0) {
            LOGGER.log(Level.WARNING, "Radius is set to zero.");
        }
        this.radius = radius;
        LOGGER.log(Level.INFO, "Set radius to {0}", this.radius);
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        if (height < 0) {
            LOGGER.log(Level.SEVERE, "Invalid negative height: {0}", height);
            throw new IllegalArgumentException("Height must be >= 0. Provided: " + height);
        }
        if (height == 0.0) {
            LOGGER.log(Level.WARNING, "Height is set to zero.");
        }
        this.height = height;
        LOGGER.log(Level.INFO, "Set height to {0}", this.height);
    }

    // ---------- Math API ----------

    /**
     * Computes the surface area of the cone using slant height.
     *
     * <p>Formula: {@code Math.PI * r * (r + Math.sqrt(r * r + h * h))}</p>
     *
     * @return surface area in square units
     */
    @Override
    protected double calculateSurfaceArea() {
        double slantHeight = Math.sqrt(radius * radius + height * height);
        double sa = Math.PI * radius * (radius + slantHeight);
        LOGGER.log(Level.INFO, "Computed surface area of Cone (r={0}, h={1}): {2}",
                new Object[]{radius, height, sa});
        return sa;
    }

    /**
     * Computes the volume of the cone.
     *
     * <p>Formula: {@code (1.0 / 3.0) * Math.PI * r * r * h}</p>
     *
     * @return volume in cubic units
     */
    @Override
    protected double calculateVolume() {
        double vol = (1.0 / 3.0) * Math.PI * radius * radius * height;
        LOGGER.log(Level.INFO, "Computed volume of Cone (r={0}, h={1}): {2}",
                new Object[]{radius, height, vol});
        return vol;
    }

    // ---------- toString ----------

    /**
     * Returns a string representation including the radius and height.
     *
     * @return e.g. {@code Cone {name='IceCreamCone', color='Tan'}; radius=2.0, height=5.0}
     */
    @Override
    public String toString() {
        return baseInfo() + "; radius=" + radius + ", height=" + height;
    }
}