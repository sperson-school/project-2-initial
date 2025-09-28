/**
 *
 * AI GENERATION DOCUMENTATION
 * ===========================
 * AI Tool Used: ChatGPT GPT-5
 * Generation Date: 2025-09-28
 * Original Prompt:
 * "Use the generic shape template. Class: RectangularPrism Fields: double length, double width, double height Formulas:
 *
 * Volume = l * w * h
 * Surface Area = 2 * (lw + lh + w*h)"
 *
 * Follow-up Prompts (if any):
 * None
 *
 * Manual Modifications:
 * - Converted generic template to explicit fields {@code length}, {@code width}, and {@code height}.
 * - Implemented rectangular prism formulas for surface area and volume.
 * - Updated {@code toString()} to show all dimensions clearly.
 * - Added references to formula sources.
 *
 * Formula Verification:
 * - Rectangular prism surface area: 2(lw + lh + wh) (Wolfram MathWorld: "Cuboid", https://mathworld.wolfram.com/Cuboid.html)
 * - Rectangular prism volume: lwh (Wolfram MathWorld: "Cuboid", https://mathworld.wolfram.com/Cuboid.html)
 */

package com.csc205.project2.shapes;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code RectangularPrism} class models a rectangular prism (cuboid) in three-dimensional space.
 *
 * <p><strong>Mathematical background:</strong></p>
 * <ul>
 *   <li>Surface Area = 2(lw + lh + wh)</li>
 *   <li>Volume = lwh</li>
 * </ul>
 *
 * <p>These formulas are derived from Euclidean geometry
 * (see Wolfram MathWorld: “Cuboid”, NIST Handbook of Mathematical Functions).</p>
 *
 * <p><strong>Design rationale:</strong></p>
 * <ol>
 *   <li>Encapsulates {@code length}, {@code width}, and {@code height} with validation (must be ≥ 0).</li>
 *   <li>Leverages {@link Shape3D}'s immutable compute API by implementing only
 *       {@code calculateSurfaceArea()} and {@code calculateVolume()}.</li>
 *   <li>Logs lifecycle events (INFO), boundary cases (WARNING), and invalid input (SEVERE).</li>
 *   <li>Provides a clean {@code toString()} that appends dimensions to the base info.</li>
 * </ol>
 */
public class RectangularPrism extends Shape3D {

    /** Logger specific to RectangularPrism computations. */
    private static final Logger LOGGER = Logger.getLogger(RectangularPrism.class.getName());

    /** Length of the prism, must be ≥ 0. */
    private double length;

    /** Width of the prism, must be ≥ 0. */
    private double width;

    /** Height of the prism, must be ≥ 0. */
    private double height;

    // ---------- Constructors ----------

    /**
     * Creates a new RectangularPrism with a given name, length, width, and height.
     *
     * @param name   the name of the prism (validated by {@link Shape3D})
     * @param length the length, must be ≥ 0
     * @param width  the width, must be ≥ 0
     * @param height the height, must be ≥ 0
     * @throws IllegalArgumentException if any dimension is negative
     */
    public RectangularPrism(String name, double length, double width, double height) {
        super(name);
        setLength(length);
        setWidth(width);
        setHeight(height);
        LOGGER.log(Level.INFO, "Created RectangularPrism name={0}, length={1}, width={2}, height={3}",
                new Object[]{getName(), this.length, this.width, this.height});
    }

    /**
     * Creates a new RectangularPrism with a given name, color, length, width, and height.
     *
     * @param name   the name of the prism (validated by {@link Shape3D})
     * @param color  the color (normalized by {@link Shape3D})
     * @param length the length, must be ≥ 0
     * @param width  the width, must be ≥ 0
     * @param height the height, must be ≥ 0
     * @throws IllegalArgumentException if any dimension is negative
     */
    public RectangularPrism(String name, String color, double length, double width, double height) {
        super(name, color);
        setLength(length);
        setWidth(width);
        setHeight(height);
        LOGGER.log(Level.INFO, "Created RectangularPrism name={0}, color={1}, length={2}, width={3}, height={4}",
                new Object[]{getName(), getColor(), this.length, this.width, this.height});
    }

    // ---------- Getters/Setters ----------

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        if (length < 0) {
            LOGGER.log(Level.SEVERE, "Invalid negative length: {0}", length);
            throw new IllegalArgumentException("Length must be >= 0. Provided: " + length);
        }
        if (length == 0.0) {
            LOGGER.log(Level.WARNING, "Length is set to zero.");
        }
        this.length = length;
        LOGGER.log(Level.INFO, "Set length to {0}", this.length);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        if (width < 0) {
            LOGGER.log(Level.SEVERE, "Invalid negative width: {0}", width);
            throw new IllegalArgumentException("Width must be >= 0. Provided: " + width);
        }
        if (width == 0.0) {
            LOGGER.log(Level.WARNING, "Width is set to zero.");
        }
        this.width = width;
        LOGGER.log(Level.INFO, "Set width to {0}", this.width);
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
     * Computes the surface area of the rectangular prism.
     *
     * <p>Formula: {@code 2 * (lw + lh + wh)}</p>
     *
     * @return surface area in square units
     */
    @Override
    protected double calculateSurfaceArea() {
        double sa = 2.0 * (length * width + length * height + width * height);
        LOGGER.log(Level.INFO, "Computed surface area of RectangularPrism (l={0}, w={1}, h={2}): {3}",
                new Object[]{length, width, height, sa});
        return sa;
    }

    /**
     * Computes the volume of the rectangular prism.
     *
     * <p>Formula: {@code l * w * h}</p>
     *
     * @return volume in cubic units
     */
    @Override
    protected double calculateVolume() {
        double vol = length * width * height;
        LOGGER.log(Level.INFO, "Computed volume of RectangularPrism (l={0}, w={1}, h={2}): {3}",
                new Object[]{length, width, height, vol});
        return vol;
    }

    // ---------- toString ----------

    /**
     * Returns a string representation including length, width, and height.
     *
     * @return e.g. {@code RectangularPrism {name='Box', color='Brown'}; length=4.0, width=2.0, height=3.0}
     */
    @Override
    public String toString() {
        return baseInfo() + "; length=" + length + ", width=" + width + ", height=" + height;
    }
}
