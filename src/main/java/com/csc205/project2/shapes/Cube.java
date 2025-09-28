/**
 *
 * AI GENERATION DOCUMENTATION
 * ===========================
 * AI Tool Used: ChatGPT GPT-5
 * Generation Date: 2025-09-28
 * Original Prompt:
 * "Use the generic shape template. Class: Cube Field: double sideLength Formulas:
 *
 * Volume = s^3
 * Surface Area = 6 * s^2"
 *
 * Follow-up Prompts (if any):
 * None
 *
 * Manual Modifications:
 * - Converted generic template to explicit field {@code sideLength}.
 * - Implemented cube formulas for surface area and volume.
 * - Updated {@code toString()} to show side length clearly.
 * - Added references to formula sources.
 *
 * Formula Verification:
 * - Cube surface area: 6s² (Wolfram MathWorld: "Cube", https://mathworld.wolfram.com/Cube.html)
 * - Cube volume: s³ (Wolfram MathWorld: "Cube", https://mathworld.wolfram.com/Cube.html)
 */

package com.csc205.project2.shapes;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code Cube} class models a cube in three-dimensional space.
 *
 * <p><strong>Mathematical background:</strong></p>
 * <ul>
 *   <li>Surface Area = 6s²</li>
 *   <li>Volume = s³</li>
 * </ul>
 *
 * <p>These formulas are derived from classical Euclidean geometry
 * (see Wolfram MathWorld, NIST Handbook of Mathematical Functions).</p>
 *
 * <p><strong>Design rationale:</strong></p>
 * <ol>
 *   <li>Encapsulates {@code sideLength} with full validation (must be ≥ 0).</li>
 *   <li>Leverages {@link Shape3D}'s immutable compute API by implementing only
 *       {@code calculateSurfaceArea()} and {@code calculateVolume()}.</li>
 *   <li>Logs lifecycle events (INFO), boundary cases (WARNING), and invalid input (SEVERE).</li>
 *   <li>Provides a clean {@code toString()} that appends {@code sideLength} to the base info.</li>
 * </ol>
 */
public class Cube extends Shape3D {

    /** Logger specific to Cube computations. */
    private static final Logger LOGGER = Logger.getLogger(Cube.class.getName());

    /** The length of a side of the cube, must be ≥ 0. */
    private double sideLength;

    // ---------- Constructors ----------

    /**
     * Creates a new Cube with a given name and side length.
     *
     * @param name       the name of the cube (validated by {@link Shape3D})
     * @param sideLength the side length, must be ≥ 0
     * @throws IllegalArgumentException if side length is negative
     */
    public Cube(String name, double sideLength) {
        super(name);
        setSideLength(sideLength);
        LOGGER.log(Level.INFO, "Created Cube name={0}, sideLength={1}", new Object[]{getName(), this.sideLength});
    }

    /**
     * Creates a new Cube with a given name, color, and side length.
     *
     * @param name       the name of the cube (validated by {@link Shape3D})
     * @param color      the color (normalized by {@link Shape3D})
     * @param sideLength the side length, must be ≥ 0
     * @throws IllegalArgumentException if side length is negative
     */
    public Cube(String name, String color, double sideLength) {
        super(name, color);
        setSideLength(sideLength);
        LOGGER.log(Level.INFO, "Created Cube name={0}, color={1}, sideLength={2}",
                new Object[]{getName(), getColor(), this.sideLength});
    }

    // ---------- Getters/Setters ----------

    /**
     * Returns the side length of the cube.
     *
     * @return the side length
     */
    public double getSideLength() {
        return sideLength;
    }

    /**
     * Sets the side length of the cube. Must be ≥ 0.
     *
     * @param sideLength new side length
     * @throws IllegalArgumentException if side length is negative
     */
    public void setSideLength(double sideLength) {
        if (sideLength < 0) {
            LOGGER.log(Level.SEVERE, "Invalid negative side length: {0}", sideLength);
            throw new IllegalArgumentException("Side length must be >= 0. Provided: " + sideLength);
        }
        if (sideLength == 0.0) {
            LOGGER.log(Level.WARNING, "Side length is set to zero.");
        }
        this.sideLength = sideLength;
        LOGGER.log(Level.INFO, "Set side length to {0}", this.sideLength);
    }

    // ---------- Math API ----------

    /**
     * Computes the surface area of the cube.
     *
     * <p>Formula: {@code 6 * s^2}</p>
     *
     * @return surface area in square units
     */
    @Override
    protected double calculateSurfaceArea() {
        double sa = 6.0 * sideLength * sideLength;
        LOGGER.log(Level.INFO, "Computed surface area of Cube (s={0}): {1}", new Object[]{sideLength, sa});
        return sa;
    }

    /**
     * Computes the volume of the cube.
     *
     * <p>Formula: {@code s^3}</p>
     *
     * @return volume in cubic units
     */
    @Override
    protected double calculateVolume() {
        double vol = sideLength * sideLength * sideLength;
        LOGGER.log(Level.INFO, "Computed volume of Cube (s={0}): {1}", new Object[]{sideLength, vol});
        return vol;
    }

    // ---------- toString ----------

    /**
     * Returns a string representation including the side length.
     *
     * @return e.g. {@code Cube {name='Box', color='Red'}; sideLength=3.0}
     */
    @Override
    public String toString() {
        return baseInfo() + "; sideLength=" + sideLength;
    }
}
