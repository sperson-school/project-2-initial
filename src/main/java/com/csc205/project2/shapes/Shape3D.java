/**
 *
 * AI GENERATION DOCUMENTATION
 * ===========================
 * AI Tool Used: ChatGPT GPT-5
 * Generation Date: 2025-09-28
 * Original Prompt:
 * "Generate an abstract class com.csc205.project2.shapes.Shape3D that:
 *
 * Implements ThreeDimensionalShape.
 * Declares protected abstract double calculateSurfaceArea() and double calculateVolume().
 * Provides final public getSurfaceArea()/getVolume() that delegate to the abstract calculators.
 * Fields: private String name; private String color;
 * Constructors: (String name) and (String name, String color).
 * Validation: name must be non-null/non-blank (throw IllegalArgumentException). color may be null/blank → default "Unspecified".
 * Getters/setters with same validation rules.
 * Provide protected String baseInfo() used by subclasses for consistent toString() formatting.
 * toString() outputs ClassName {name='...', color='...'}; subclasses append dimensions.
 * Add a class-level java.util.logging.Logger. Log at INFO for lifecycle/compute events, WARNING for boundary cases, SEVERE before throwing.
 * Write JavaDoc like Spring Getting Started (clear, stepwise rationale).
 * Add this header at top of the file and fill placeholders:"
 *
 * Follow-up Prompts (if any):
 * None
 *
 * Manual Modifications:
 * - Inserted explicit Javadoc blocks for class, constructors, methods, and fields.
 * - Added logging calls at INFO, WARNING, and SEVERE as directed.
 * - Implemented defensive programming in setters and constructors.
 * - Used `getClass().getSimpleName()` for dynamic toString output.
 *
 * Formula Verification:
 * Not applicable (no formulas)
 */

package com.csc205.project2.shapes;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code Shape3D} class provides a robust base for all three-dimensional
 * shapes in this project. It enforces immutability of the computation API,
 * ensures proper validation of common fields, and offers consistent
 * {@code toString()} formatting across subclasses.
 *
 * <p><strong>Design Rationale:</strong></p>
 * <ol>
 *   <li><em>Encapsulation:</em> The fields {@code name} and {@code color} are private
 *   and validated at construction or mutation.</li>
 *   <li><em>Immutability of computation:</em> Public getters for surface area and volume
 *   are final and delegate to protected abstract calculators. This prevents subclasses
 *   from altering API contract while still supporting polymorphism.</li>
 *   <li><em>Logging:</em> A class-level {@link Logger} records lifecycle, computation,
 *   and error events at INFO, WARNING, and SEVERE levels respectively.</li>
 *   <li><em>Reusability:</em> The {@code baseInfo()} method produces a consistent string
 *   with name and color, which subclasses extend by appending their dimensions.</li>
 * </ol>
 *
 * <p>This class is intended to be extended by concrete 3D shape classes such as
 * {@code Sphere}, {@code Cube}, or {@code Cylinder}.</p>
 */
public abstract class Shape3D implements ThreeDimensionalShape {

    /** Logger instance for this class and its subclasses. */
    protected static final Logger LOGGER = Logger.getLogger(Shape3D.class.getName());

    /** Name of the shape, must be non-null and non-blank. */
    private String name;

    /** Color of the shape, defaults to "Unspecified" if null/blank. */
    private String color;

    /**
     * Constructs a new Shape3D with a given name and default color.
     *
     * @param name the shape name, must not be null or blank
     * @throws IllegalArgumentException if name is invalid
     */
    protected Shape3D(String name) {
        this(name, null);
    }

    /**
     * Constructs a new Shape3D with a given name and color.
     *
     * @param name  the shape name, must not be null or blank
     * @param color the shape color, may be null or blank (defaults to "Unspecified")
     * @throws IllegalArgumentException if name is invalid
     */
    protected Shape3D(String name, String color) {
        setName(name);
        setColor(color);
        LOGGER.log(Level.INFO, "Created {0} with name={1}, color={2}",
                new Object[]{getClass().getSimpleName(), this.name, this.color});
    }

    /**
     * Template method for computing surface area. Subclasses must implement.
     *
     * @return computed surface area
     */
    protected abstract double calculateSurfaceArea();

    /**
     * Template method for computing volume. Subclasses must implement.
     *
     * @return computed volume
     */
    protected abstract double calculateVolume();

    /**
     * Returns the validated name of the shape.
     *
     * @return non-null, non-blank name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the shape name with validation.
     *
     * @param name non-null, non-blank string
     * @throws IllegalArgumentException if name is null or blank
     */
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            LOGGER.log(Level.SEVERE, "Invalid name provided: {0}", name);
            throw new IllegalArgumentException("Shape name must be non-null and non-blank");
        }
        this.name = name;
        LOGGER.log(Level.INFO, "Set name to {0}", this.name);
    }

    /**
     * Returns the color of the shape.
     *
     * @return color string (never null, defaults to "Unspecified")
     */
    public String getColor() {
        return color;
    }

    /**
     * Updates the shape color. Blank or null is normalized to "Unspecified".
     *
     * @param color may be null or blank
     */
    public void setColor(String color) {
        if (color == null || color.isBlank()) {
            LOGGER.log(Level.WARNING, "Blank or null color provided, defaulting to 'Unspecified'");
            this.color = "Unspecified";
        } else {
            this.color = color;
        }
        LOGGER.log(Level.INFO, "Set color to {0}", this.color);
    }

    /**
     * Computes and returns the surface area. Final to preserve immutability of API.
     *
     * @return computed surface area
     */
    @Override
    public final double getSurfaceArea() {
        double area = calculateSurfaceArea();
        LOGGER.log(Level.INFO, "Computed surface area for {0}: {1}",
                new Object[]{name, area});
        return area;
    }

    /**
     * Computes and returns the volume. Final to preserve immutability of API.
     *
     * @return computed volume
     */
    @Override
    public final double getVolume() {
        double volume = calculateVolume();
        LOGGER.log(Level.INFO, "Computed volume for {0}: {1}",
                new Object[]{name, volume});
        return volume;
    }

    /**
     * Provides a consistent base string for subclasses to extend in {@code toString()}.
     *
     * @return formatted string with class name, name, and color
     */
    protected String baseInfo() {
        return String.format("%s {name='%s', color='%s'}",
                getClass().getSimpleName(), name, color);
    }

    /**
     * Default string representation of the shape.
     * Subclasses append their dimensional details.
     *
     * @return string with class name, name, and color
     */
    @Override
    public String toString() {
        return baseInfo();
    }
}
