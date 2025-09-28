/**
 *
 * AI GENERATION DOCUMENTATION
 * ===========================
 * AI Tool Used: ChatGPT GPT-5
 * Generation Date: 2025-09-28
 * Original Prompt:
 * "Use the generic shape template. Class: Cylinder Fields: double radius, double height Formulas:
 *
 * Volume = π * r^2 * h
 * Surface Area = 2 * π * r * (r + h)"
 *
 * Follow-up Prompts (if any):
 * None
 *
 * Manual Modifications:
 * - Converted generic template to explicit fields {@code radius} and {@code height}.
 * - Implemented cylinder formulas for surface area and volume.
 * - Updated {@code toString()} to show radius and height clearly.
 * - Added references to formula sources.
 *
 * Formula Verification:
 * - Cylinder surface area: 2πr(r + h) (Wolfram MathWorld: "Cylinder", https://mathworld.wolfram.com/Cylinder.html)
 * - Cylinder volume: πr²h (Wolfram MathWorld: "Cylinder", https://mathworld.wolfram.com/Cylinder.html)
 */

package com.csc205.project2.shapes;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code Cylinder} class models a right circular cylinder in three-dimensional space.
 *
 * <p><strong>Mathematical background:</strong></p>
 * <ul>
 *   <li>Surface Area = 2πr(r + h)</li>
 *   <li>Volume = πr²h</li>
 * </ul>
 *
 * <p>These formulas are derived from classical Euclidean geometry
 * (see Wolfram MathWorld, NIST Handbook of Mathematical Functions).</p>
 *
 * <p><strong>Design rationale:</strong></p>
 * <ol>
 *   <li>Encapsulates {@code radius} and {@code height} with validation (must be ≥ 0).</li>
 *   <li>Leverages {@link Shape3D}'s immutable compute API by implementing only
 *       {@code calculateSurfaceArea()} and {@code calculateVolume()}.</li>
 *   <li>Logs lifecycle events (INFO), boundary cases (WARNING), and invalid input (SEVERE).</li>
 *   <li>Provides a clean {@code toString()} that appends {@code radius} and {@code height} to the base info.</li>
 * </ol>
 */
public class Cylinder extends Shape3D {

    /** Logger specific to Cylinder computations. */
    private static final Logger LOGGER = Logger.getLogger(Cylinder.class.getName());

    /** Radius of the cylinder base, must be ≥ 0. */
    private double radius;

    /** Height of the cylinder, must be ≥ 0. */
    private double height;

    // ---------- Constructors ----------

    /**
     * Creates a new Cylinder with a given name, radius, and height.
     *
     * @param name   the name of the cylinder (validated by {@link Shape3D})
     * @param radius the radius, must be ≥ 0
     * @param height the height, must be ≥ 0
     * @throws IllegalArgumentException if radius or height is negative
     */
    public Cylinder(String name, double radius, double height) {
        super(name);
        setRadius(radius);
        setHeight(height);
        LOGGER.log(Level.INFO, "Created Cylinder name={0}, radius={1}, height={2}",
                new Object[]{getName(), this.radius, this.height});
    }

    /**
     * Creates a new Cylinder with a given name, color, radius, and height.
     *
     * @param name   the name of the cylinder (validated by {@link Shape3D})
     * @param color  the color (normalized by {@link Shape3D})
     * @param radius the radius, must be ≥ 0
     * @param height the height, must be ≥ 0
     * @throws IllegalArgumentException if radius or height is negative
     */
    public Cylinder(String name, String color, double radius, double height) {
        super(name, color);
        setRadius(radius);
        setHeight(height);
        LOGGER.log(Level.INFO, "Created Cylinder name={0}, color={1}, radius={2}, height={3}",
                new Object[]{getName(), getColor(), this.radius, this.height});
    }

    // ---------- Getters/Setters ----------

    /**
     * Returns the radius of the cylinder base.
     *
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Sets the radius of the cylinder base. Must be ≥ 0.
     *
     * @param radius new radius
     * @throws IllegalArgumentException if radius is negative
     */
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

    /**
     * Returns the height of the cylinder.
     *
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the height of the cylinder. Must be ≥ 0.
     *
     * @param height new height
     * @throws IllegalArgumentException if height is negative
     */
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
     * Computes the surface area of the cylinder.
     *
     * <p>Formula: {@code 2 * Math.PI * r * (r + h)}</p>
     *
     * @return surface area in square units
     */
    @Override
    protected double calculateSurfaceArea() {
        double sa = 2.0 * Math.PI * radius * (radius + height);
        LOGGER.log(Level.INFO, "Computed surface area of Cylinder (r={0}, h={1}): {2}",
                new Object[]{radius, height, sa});
        return sa;
    }

    /**
     * Computes the volume of the cylinder.
     *
     * <p>Formula: {@code Math.PI * r^2 * h}</p>
     *
     * @return volume in cubic units
     */
    @Override
    protected double calculateVolume() {
        double vol = Math.PI * radius * radius * height;
        LOGGER.log(Level.INFO, "Computed volume of Cylinder (r={0}, h={1}): {2}",
                new Object[]{radius, height, vol});
        return vol;
    }

    // ---------- toString ----------

    /**
     * Returns a string representation including the radius and height.
     *
     * @return e.g. {@code Cylinder {name='Can', color='Silver'}; radius=2.0, height=5.0}
     */
    @Override
    public String toString() {
        return baseInfo() + "; radius=" + radius + ", height=" + height;
    }
}