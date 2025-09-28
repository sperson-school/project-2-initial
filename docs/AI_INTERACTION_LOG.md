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
