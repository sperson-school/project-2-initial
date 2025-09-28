# AI Interaction Log

I started by feeding Chat GPT both the readme for Project 2 as well as the README for project one. This provided it some context as to what we are working on, and how I would like my prompts to flow for each class.

I won't paste the entire interaction here because it was VERY wordy and long, but I ended up with these prompts for the different classes in this project:

## Shape3D Prompt:
Generate an abstract class `com.csc205.project2.shapes.Shape3D` that:
- Implements `ThreeDimensionalShape`.
- Declares **protected abstract** `double calculateSurfaceArea()` and `double calculateVolume()`.
- Provides **final** public `getSurfaceArea()`/`getVolume()` that delegate to the abstract calculators.
- Fields: `private String name; private String color;`
- Constructors: `(String name)` and `(String name, String color)`.
- Validation: name must be non-null/non-blank (throw IllegalArgumentException). color may be null/blank → default "Unspecified".
- Getters/setters with same validation rules.
- Provide `protected String baseInfo()` used by subclasses for consistent `toString()` formatting.
- `toString()` outputs `ClassName {name='...', color='...'}`; subclasses append dimensions.
- Add a class-level `java.util.logging.Logger`. Log at INFO for lifecycle/compute events, WARNING for boundary cases, SEVERE before throwing.
- Write JavaDoc like Spring Getting Started (clear, stepwise rationale).
- Add this header at top of the file and fill placeholders:

/**
* AI GENERATION DOCUMENTATION
* ===========================
* AI Tool Used: [Name and version]
* Generation Date: [Date]
*
* Original Prompt:
* "[Paste prompt used]"
*
* Follow-up Prompts (if any):
* 1. "[Refinement prompt 1]"
* 2. "[Refinement prompt 2]"
*
* Manual Modifications:
* - [List changes you made and why]
*
* Formula Verification:
* - Not applicable (no formulas)
    */

## Concrete Shape Prompt:
### It first gave me the template to use:

Generate class `Xxx` in package `com.csc205.project2.shapes` that **extends `Shape3D`** and models a 3D [shape name].

Requirements:
1) Fields & Validation
- Private fields for required dimensions.
- Constructors `(String name, double ...)` and `(String name, String color, double ...)`.
- Validation: all dimensions must be ≥ 0 (zero allowed). Negative → IllegalArgumentException with clear message.
- Getters and setters with same validation.
- Use `java.util.logging` Logger; log INFO on construct/compute, WARNING on boundary (e.g., zero), SEVERE right before throw.

2) Math API
- Implement `protected double calculateSurfaceArea()` and `protected double calculateVolume()` using correct formulas.
- Use `Math.PI` where applicable.
- Spring-style JavaDoc explaining formulas and when to use them.

3) toString()
- Override to append dimensions after `baseInfo()`.

4) AI Header
- Include the full "AI GENERATION DOCUMENTATION" block and cite formula sources (e.g., Wolfram MathWorld, textbooks).


### Then it gave me the specific prompts for each shape:

Use the generic shape template.
Class: Sphere
Field: double radius
Formulas:
- Volume = (4/3) * π * r^3
- Surface Area = 4 * π * r^2

---

Use the generic shape template.
Class: Cube
Field: double sideLength
Formulas:
- Volume = s^3
- Surface Area = 6 * s^2
---

Use the generic shape template.
Class: Cylinder
Fields: double radius, double height
Formulas:
- Volume = π * r^2 * h
- Surface Area = 2 * π * r * (r + h)
---

Use the generic shape template.
Class: RectangularPrism
Fields: double length, double width, double height
Formulas:
- Volume = l * w * h
- Surface Area = 2 * (l*w + l*h + w*h)

---

Use the generic shape template.
Class: Cone
Fields: double radius, double height
Formulas:
- Volume = (1/3) * π * r^2 * h
- Surface Area = π * r * (r + √(r^2 + h^2))    // use slant height

---
*It also gave me a prompt for a bonus factory class for extra credit*

Implement `com.csc205.project2.shapes.ShapeFactory`:

- Static methods:
    1) `static Shape3D create(Class<? extends Shape3D> type, String name, double... dims)`
    2) `static Shape3D create(Class<? extends Shape3D> type, String name, String color, double... dims)`

Rules:
- Validate: `type` non-null; `name` non-null/non-blank; `dims` non-null (may be empty).
- Attempt to construct via reflection, preferring `(String, String, double...)`, then `(String, double...)`.
- On reflection failure or validation failure, throw `IllegalArgumentException` with a clear message.
- Use `java.util.logging` Logger: INFO on attempts, SEVERE on failures.
- Add Spring-style JavaDoc and the "AI GENERATION DOCUMENTATION" header (explain reflection choice, error handling).
## Moving on to Test Classes:

**SphereTest.java (example prompt; replicate pattern for others)**

Generate JUnit 5 tests for `com.csc205.project2.shapes.Sphere` in class `SphereTest` that cover:

1) Basic Functionality
    - Constructors set name/color/dimensions correctly (color defaulting to "Unspecified" when null/blank).
    - Getters/setters behave with valid values.

2) Calculation Accuracy
    - Known values: radius=5 → Volume ≈ 523.598775598; SurfaceArea ≈ 314.159265358 (use Math.PI for expectations; tolerance 1e-9).

3) Boundary Testing
    - radius=0 → volume=0 and surfaceArea=0.
    - very large values still compute finite results.

4) Input Validation
    - Negative radius in constructor and setter → IllegalArgumentException.

5) Inheritance/Polymorphism
    - Treat as `ThreeDimensionalShape`, call `getVolume()`/`getSurfaceArea()`.

General:
- Use `assertEquals(expected, actual, 1e-9)` for doubles and `assertThrows` for invalids.
- Do not rely on logger output in assertions.

---
CubeTest.java:
Same categories as SphereTest. Use exact expectations:
- sideLength=4 → Volume=64, SurfaceArea=96 (tolerance 1e-12).
---
CylinderTest.java:
Same categories. Known values:
- r=2, h=10 → Volume = 40 * Math.PI; SurfaceArea = 48 * Math.PI (tolerance 1e-9).
- Validate negatives on radius/height.
---
RectangularPrismTest.java:
Same categories. Known values:
- l=3, w=4, h=5 → Volume=60; SurfaceArea=94 (tolerance 1e-12).
- Zero on one dimension → volume=0; surface area matches formula.
- Validate negatives on any dimension.
---
ConeTest.java:
Same categories. Known values:
- r=3, h=4 → Volume = 12 * Math.PI; SurfaceArea = 24 * Math.PI (tolerance 1e-9).
- Zero radius → both zero.
- Validate negatives on radius/height.
---
ShapeFactoryTest.java:
Create tests for `ShapeFactory`:
- Create sphere with `(Class, name, dims...)` and assert type, name, and numeric results.
- Create cylinder with `(Class, name, color, dims...)` and assert color used and values.
- Invalid: blank name, null type, wrong dims count → IllegalArgumentException.
  Use 1e-9 tolerances for numeric comparisons.


## Using the prompts:

---

These are the raw outputs it gave me for each class.

---
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

import java.util.Objects;
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


---

**After giving it the template class I started with the Sphere class:**

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

---

**Cube.java:**

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

---

**Cylinder.java:**

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

---

**RectangularPrism.java:**

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

---

**Cone.java:**
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

---
**ShapeFactory.java**

/**
*
* AI GENERATION DOCUMENTATION
* ===========================
* AI Tool Used: ChatGPT GPT-5 Thinking
* Generation Date: 2025-09-28
* Original Prompt:
* "Implement com.csc205.project2.shapes.ShapeFactory:
*
* Static methods:
* static Shape3D create(Class<? extends Shape3D> type, String name, double... dims)
* static Shape3D create(Class<? extends Shape3D> type, String name, String color, double... dims)
* Rules:
*
* Validate: type non-null; name non-null/non-blank; dims non-null (may be empty).
* Attempt to construct via reflection, preferring (String, String, double...), then (String, double...).
* On reflection failure or validation failure, throw IllegalArgumentException with a clear message.
* Use java.util.logging Logger: INFO on attempts, SEVERE on failures.
* Add Spring-style JavaDoc and the "AI GENERATION DOCUMENTATION" header (explain reflection choice, error handling)."
*
* Follow-up Prompts (if any):
* None
*
* Manual Modifications:
* - Implemented a flexible constructor-matching routine that supports both varargs
*   signatures ((String, String, double...), (String, double...)) and explicit doubles
*   ((String, String, double, double, ...), (String, double, double, ...)).
* - Added detailed INFO logs for attempts and SEVERE logs immediately before throwing.
* - Normalized error messages to be student-friendly and actionable.
*
* Rationale / Reflection Choice:
* - Different shapes may expose either varargs or explicit-arity constructors.
*   Using reflection allows one factory to support both styles without changing code
*   when new shapes are added.
* - Preference order ensures color-aware constructors are chosen when available.
*
* Error Handling:
* - Inputs are validated early with clear IllegalArgumentException messages.
* - If no compatible constructor is found or invocation fails, the factory explains
*   what was tried (preferred signatures and arity) to aid debugging.
*
* Formula Verification:
* Not applicable (no formulas in the factory)
  */

package com.csc205.project2.shapes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* Factory for constructing {@link Shape3D} instances using reflection.
*
* <p><strong>Why reflection?</strong></p>
* <ol>
*   <li>Concrete shapes in this project may declare constructors either with varargs
*       (e.g., {@code (String, String, double...)}) or with explicit doubles
*       (e.g., {@code (String, String, double, double)}).</li>
*   <li>This factory locates and invokes the most suitable constructor at runtime,
*       so you can add new shapes without changing the factory code.</li>
*   <li>We prefer color-aware constructors first to preserve callers’ intent,
*       then fall back to name-only constructors.</li>
* </ol>
*
* <p><strong>Error handling strategy:</strong></p>
* <ul>
*   <li>Inputs are validated: {@code type} must be non-null; {@code name} non-null/non-blank;
*       {@code dims} non-null (but may be empty).</li>
*   <li>On failure to find a compatible constructor or to invoke it,
*       we log at SEVERE and throw an {@link IllegalArgumentException} with a clear message.</li>
* </ul>
*/
public final class ShapeFactory {

    private static final Logger LOGGER = Logger.getLogger(ShapeFactory.class.getName());

    private ShapeFactory() {
        // utility class
    }

    /**
     * Creates a {@link Shape3D} using a preferred constructor search:
     * first {@code (String, String, double...)}, then {@code (String, double...)}.
     * Also accepts explicit double-arity constructors that match {@code dims.length}.
     *
     * @param type the concrete Shape3D class
     * @param name the instance name (must be non-null/non-blank)
     * @param dims non-null array of dimensions (may be empty)
     * @return a new Shape3D instance
     * @throws IllegalArgumentException if validation fails or no compatible constructor can be invoked
     */
    public static Shape3D create(Class<? extends Shape3D> type, String name, double... dims) {
        validateInputs(type, name, dims);
        LOGGER.log(Level.INFO, "Attempting to create {0} with (name, dims): name={1}, dims={2}",
                new Object[]{type.getName(), name, Arrays.toString(dims)});
        try {
            // Try (String, double...) and explicit arity (String, double, double, ...)
            Shape3D instance = tryConstruct(type, /*color*/ null, name, dims);
            if (instance != null) {
                return instance;
            }
            String message = "No compatible constructor found for " + type.getName()
                    + ". Tried (String, double...) and explicit double-arity with length=" + dims.length + ".";
            LOGGER.log(Level.SEVERE, message);
            throw new IllegalArgumentException(message);
        } catch (RuntimeException e) {
            // already logged inside helpers; wrap or rethrow as IllegalArgumentException
            if (e instanceof IllegalArgumentException) {
                throw e;
            }
            String message = "Failed to instantiate " + type.getName() + ": " + e.getMessage();
            LOGGER.log(Level.SEVERE, message, e);
            throw new IllegalArgumentException(message, e);
        }
    }

    /**
     * Creates a {@link Shape3D} using a preferred constructor search:
     * first {@code (String, String, double...)}, then {@code (String, double...)}.
     * Also accepts explicit double-arity constructors that match {@code dims.length}.
     *
     * @param type  the concrete Shape3D class
     * @param name  the instance name (must be non-null/non-blank)
     * @param color the instance color (passed to constructors that accept it)
     * @param dims  non-null array of dimensions (may be empty)
     * @return a new Shape3D instance
     * @throws IllegalArgumentException if validation fails or no compatible constructor can be invoked
     */
    public static Shape3D create(Class<? extends Shape3D> type, String name, String color, double... dims) {
        validateInputs(type, name, dims);
        LOGGER.log(Level.INFO, "Attempting to create {0} with (name, color, dims): name={1}, color={2}, dims={3}",
                new Object[]{type.getName(), name, color, Arrays.toString(dims)});
        try {
            // Prefer (String, String, double...) or explicit arity (String, String, double, ...)
            Shape3D instance = tryConstruct(type, color, name, dims);
            if (instance != null) {
                return instance;
            }
            // Fallback: if no color constructor exists, try name-only forms
            LOGGER.log(Level.INFO, "No (String, String, ...) constructor found. Falling back to (String, ...) for {0}.",
                    new Object[]{type.getName()});
            instance = tryConstruct(type, /*color*/ null, name, dims);
            if (instance != null) {
                return instance;
            }
            String message = "No compatible constructor found for " + type.getName()
                    + ". Tried (String, String, double...), (String, double...) and explicit double-arity with length="
                    + dims.length + ".";
            LOGGER.log(Level.SEVERE, message);
            throw new IllegalArgumentException(message);
        } catch (RuntimeException e) {
            // already logged inside helpers; wrap or rethrow as IllegalArgumentException
            if (e instanceof IllegalArgumentException) {
                throw e;
            }
            String message = "Failed to instantiate " + type.getName() + ": " + e.getMessage();
            LOGGER.log(Level.SEVERE, message, e);
            throw new IllegalArgumentException(message, e);
        }
    }

    // ----------------------------------------------------------------------
    // Internal helpers
    // ----------------------------------------------------------------------

    private static void validateInputs(Class<? extends Shape3D> type, String name, double[] dims) {
        if (type == null) {
            String message = "type must not be null.";
            LOGGER.log(Level.SEVERE, message);
            throw new IllegalArgumentException(message);
        }
        if (name == null || name.isBlank()) {
            String message = "name must be non-null and non-blank.";
            LOGGER.log(Level.SEVERE, message);
            throw new IllegalArgumentException(message);
        }
        if (dims == null) {
            String message = "dims must not be null (use empty array for no dimensions).";
            LOGGER.log(Level.SEVERE, message);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Try to construct using either:
     * <ul>
     *   <li>(String, String, double...) or (String, String, double,double,...)</li>
     *   <li>(String, double...) or (String, double,double,...)</li>
     * </ul>
     * depending on whether {@code color} is provided.
     */
    private static Shape3D tryConstruct(Class<? extends Shape3D> type, String color, String name, double[] dims) {
        // Two passes: prefer varargs-like last-parameter (double[]) matches, then explicit arity matches.
        // But to keep it efficient, we iterate all constructors once and rank viable ones.
        Constructor<?>[] ctors = type.getDeclaredConstructors();

        // 1) Try preferred signatures first (color-aware if color != null).
        Constructor<?> best = null;
        Object[] bestArgs = null;
        int bestRank = Integer.MAX_VALUE; // lower is better

        for (Constructor<?> ctor : ctors) {
            Class<?>[] params = ctor.getParameterTypes();

            // Rank: 0 = (String, String, double[]), 1 = (String, double[]),
            // 2 = (String, String, explicit doubles), 3 = (String, explicit doubles)
            int rank = matchRank(params, color != null, dims.length);
            if (rank < 0) continue; // incompatible

            Object[] args = buildArgs(params, color, name, dims);
            if (args == null) continue; // incompatible upon detail check

            if (rank < bestRank) {
                bestRank = rank;
                best = ctor;
                bestArgs = args;
                if (bestRank == 0) break; // can't do better than perfect varargs color match
            }
        }

        if (best == null) {
            return null;
        }

        try {
            best.setAccessible(true);
            Object instance = best.newInstance(bestArgs);
            return (Shape3D) instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            String message = "Constructor invocation failed for " + type.getName() + " using " + signatureOf(best)
                    + ": " + e.getMessage();
            LOGGER.log(Level.SEVERE, message, e);
            throw new IllegalArgumentException(message, e);
        }
    }

    /**
     * Returns a rank for how well the constructor parameter list matches the desired preference.
     * -1 means incompatible.
     */
    private static int matchRank(Class<?>[] params, boolean needsColor, int dimsLen) {
        // Expected forms:
        // With color:
        //   A) (String, String, double[])          rank 0 (best)
        //   B) (String, String, double, ..., double) exactly dimsLen doubles  rank 2
        // Without color:
        //   C) (String, double[])                   rank 1
        //   D) (String, double, ..., double) exactly dimsLen doubles          rank 3
        if (needsColor) {
            if (params.length >= 3
                    && params[0] == String.class
                    && params[1] == String.class
                    && params[2] == double[].class
                    && params.length == 3) {
                return 0; // perfect varargs color match
            }
            if (params.length == 2 + dimsLen
                    && params[0] == String.class
                    && params[1] == String.class
                    && allDoubles(params, 2)) {
                return 2; // explicit doubles (color)
            }
            return -1;
        } else {
            if (params.length == 2
                    && params[0] == String.class
                    && params[1] == double[].class) {
                return 1; // varargs, name-only
            }
            if (params.length == 1 + dimsLen
                    && params[0] == String.class
                    && allDoubles(params, 1)) {
                return 3; // explicit doubles, name-only
            }
            return -1;
        }
    }

    private static boolean allDoubles(Class<?>[] params, int startIdx) {
        for (int i = startIdx; i < params.length; i++) {
            if (!(params[i] == double.class || params[i] == Double.class)) {
                return false;
            }
        }
        return true;
    }

    private static Object[] buildArgs(Class<?>[] params, String color, String name, double[] dims) {
        try {
            if (color != null) {
                // (String, String, double[]) or (String, String, explicit doubles...)
                if (params.length == 3 && params[0] == String.class && params[1] == String.class
                        && params[2] == double[].class) {
                    return new Object[]{name, color, Arrays.copyOf(dims, dims.length)};
                } else if (params.length == 2 + dims.length
                        && params[0] == String.class && params[1] == String.class
                        && allDoubles(params, 2)) {
                    Object[] args = new Object[params.length];
                    args[0] = name;
                    args[1] = color;
                    for (int i = 0; i < dims.length; i++) {
                        // autoboxing to Double if needed; reflection handles primitive/wrapper
                        args[2 + i] = dims[i];
                    }
                    return args;
                }
            } else {
                // (String, double[]) or (String, explicit doubles...)
                if (params.length == 2 && params[0] == String.class && params[1] == double[].class) {
                    return new Object[]{name, Arrays.copyOf(dims, dims.length)};
                } else if (params.length == 1 + dims.length
                        && params[0] == String.class
                        && allDoubles(params, 1)) {
                    Object[] args = new Object[params.length];
                    args[0] = name;
                    for (int i = 0; i < dims.length; i++) {
                        args[1 + i] = dims[i];
                    }
                    return args;
                }
            }
        } catch (Exception e) {
            // Any unexpected failure in argument building -> indicate incompatibility
            return null;
        }
        return null;
    }

    private static String signatureOf(Constructor<?> ctor) {
        StringBuilder sb = new StringBuilder();
        sb.append(ctor.getDeclaringClass().getSimpleName()).append('(');
        Class<?>[] p = ctor.getParameterTypes();
        for (int i = 0; i < p.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(p[i].getSimpleName());
        }
        sb.append(')');
        return sb.toString();
    }
}

---

### Test Classes

For the test classes, all I did was put the exact prompt it gave me earlier and it outputted everything with no issues. What you see in the classes is exactly what the AI outputted, so I thought it would be redundant to copy everything into this file as well.