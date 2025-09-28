/**
 *
 * AI GENERATION DOCUMENTATION
 * ===========================
 * AI Tool Used: ChatGPT GPT-5
 * Generation Date: 2025-09-28
 * Original Prompt:
 * "Use the generic shape template. Class: Sphere Field: double radius Formulas:
 *
 * Volume = (4/3) * π * r^3
 * Surface Area = 4 * π * r^2"
 *
 * Follow-up Prompts (if any):
 * None
 *
 * Manual Modifications:
 * - Converted generic dims[] array to a single explicit field {@code radius}.
 * - Implemented correct formulas for surface area and volume of a sphere.
 * - Updated {@code toString()} to include radius explicitly.
 * - Added references to formula sources.
 *
 * Formula Verification:
 * - Sphere surface area: 4πr² (Wolfram MathWorld: "Sphere", https://mathworld.wolfram.com/Sphere.html)
 * - Sphere volume: (4/3)πr³ (Wolfram MathWorld: "Sphere", https://mathworld.wolfram.com/Sphere.html)
 */

package com.csc205.project2.shapes;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code Sphere} class models a perfect sphere in three-dimensional space.
 *
 * <p><strong>Mathematical background:</strong></p>
 * <ul>
 *   <li>Surface Area = 4πr²</li>
 *   <li>Volume = (4/3)πr³</li>
 * </ul>
 *
 * <p>These formulas are derived from classical geometry and calculus, and are
 * widely documented (see Wolfram MathWorld, NIST Handbook of Mathematical Functions).</p>
 *
 * <p><strong>Design rationale:</strong></p>
 * <ol>
 *   <li>Encapsulates {@code radius} with full validation (must be ≥ 0).</li>
 *   <li>Leverages {@link Shape3D}'s immutable compute API: subclasses only implement
 *       {@code calculateSurfaceArea()} and {@code calculateVolume()}.</li>
 *   <li>Logs lifecycle events (INFO), boundary cases (WARNING), and invalid input (SEVERE).</li>
 *   <li>Provides a clean {@code toString()} that appends {@code radius} to the base info.</li>
 * </ol>
 */
public class Sphere extends Shape3D {

    /** Logger specific to Sphere computations. */
    private static final Logger LOGGER = Logger.getLogger(Sphere.class.getName());

    /** Radius of the sphere, must be ≥ 0. */
    private double radius;

    // ---------- Constructors ----------

    /**
     * Creates a new Sphere with a given name and radius.
     *
     * @param name   the name of the sphere (validated by {@link Shape3D})
     * @param radius the radius, must be ≥ 0
     * @throws IllegalArgumentException if radius is negative
     */
    public Sphere(String name, double radius) {
        super(name);
        setRadius(radius);
        LOGGER.log(Level.INFO, "Created Sphere name={0}, radius={1}", new Object[]{getName(), this.radius});
    }

    /**
     * Creates a new Sphere with a given name, color, and radius.
     *
     * @param name   the name of the sphere (validated by {@link Shape3D})
     * @param color  the color (normalized by {@link Shape3D})
     * @param radius the radius, must be ≥ 0
     * @throws IllegalArgumentException if radius is negative
     */
    public Sphere(String name, String color, double radius) {
        super(name, color);
        setRadius(radius);
        LOGGER.log(Level.INFO, "Created Sphere name={0}, color={1}, radius={2}",
                new Object[]{getName(), getColor(), this.radius});
    }

    // ---------- Getters/Setters ----------

    /**
     * Returns the radius of this sphere.
     *
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Sets the radius of this sphere. Must be ≥ 0.
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

    // ---------- Math API ----------

    /**
     * Computes the surface area of the sphere.
     *
     * <p>Formula: {@code 4 * Math.PI * r^2}</p>
     *
     * @return surface area in square units
     */
    @Override
    protected double calculateSurfaceArea() {
        double sa = 4.0 * Math.PI * radius * radius;
        LOGGER.log(Level.INFO, "Computed surface area of Sphere (r={0}): {1}", new Object[]{radius, sa});
        return sa;
    }

    /**
     * Computes the volume of the sphere.
     *
     * <p>Formula: {@code (4.0 / 3.0) * Math.PI * r^3}</p>
     *
     * @return volume in cubic units
     */
    @Override
    protected double calculateVolume() {
        double vol = (4.0 / 3.0) * Math.PI * radius * radius * radius;
        LOGGER.log(Level.INFO, "Computed volume of Sphere (r={0}): {1}", new Object[]{radius, vol});
        return vol;
    }

    // ---------- toString ----------

    /**
     * Returns a string representation including the radius.
     *
     * @return e.g. {@code Sphere {name='Ball', color='Blue'}; radius=5.0}
     */
    @Override
    public String toString() {
        return baseInfo() + "; radius=" + radius;
    }
}