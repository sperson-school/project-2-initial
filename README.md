# Project 2: AI-Assisted 3D Geometric Shapes with Inheritance

## Overview
Welcome to the modern evolution of the classic inheritance project! In this assignment, you'll explore Object-Oriented Programming through inheritance while learning to effectively collaborate with AI tools for code generation, testing, and documentation.

### Learning Objectives
By completing this project, you will:
- Master inheritance concepts including abstract classes and polymorphism
- Learn effective AI-assisted development workflows
- Practice test-driven development with automated test generation
- Develop skills in code validation and quality assurance
- Experience modern software documentation practices

## Project Structure
```
project-2-ai-shapes/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── csc205/
│   │               └── project2/
│   │                   ├── shapes/
│   │                   │   ├── ThreeDimensionalShape.java  # Provided interface
│   │                   │   ├── Shape3D.java                # Abstract base class
│   │                   │   ├── Sphere.java                 # Required shape
│   │                   │   ├── Cube.java                   # Required shape
│   │                   │   ├── Cylinder.java               # Required shape
│   │                   │   ├── RectangularPrism.java       # Required shape
│   │                   │   └── [YourChoice].java           # Your selected shape
│   │                   └── ShapeDriver.java                # Main demonstration class
│   └── test/
│       └── java/
│           └── com/
│               └── csc205/
│                   └── project2/
│                       ├── shapes/
│                       │   ├── SphereTest.java
│                       │   ├── CubeTest.java
│                       │   └── [etc...]
│                       └── ShapeTestSuite.java
├── docs/
│   ├── AI_INTERACTION_LOG.md
│   └── REFLECTION.md
├── README.md
└── pom.xml (if using Maven)
```

## Getting Started

### Prerequisites
- Java 21 or higher
- Your favorite IDE (IntelliJ IDEA, Eclipse, VS Code)
- Access to an AI assistant (ChatGPT, Claude, GitHub Copilot, etc.)
- JUnit 5 for testing

### Setup Instructions
1. **Fork and Clone**
    - Navigate to https://github.com/UltimateSandbox/project-2-initial and fork the project
    - Clone the project using your IDE and the link to your own repo

2. **Set Up Your Development Environment**
    - Open the project in your IDE
    - Ensure JUnit 5 is configured for testing (verify JUnit 5 dependency in `pom.xml`)
    - Verify Java version compatibility (does your template compile correctly - you can use the Maven package target to check this.  In the Maven tab, navigate to `Project 2 --> Lifecycle --> package` and double click it to run the build)

3. **Initialize Documentation**
    - Create your `AI_INTERACTION_LOG.md` file
    - Start documenting your AI tool interactions from day one

## Phase 1: AI-Assisted Class Design (35% of grade)

### Given Interface: ThreeDimensionalShape
You are provided with this interface that all shapes must implement:

```java
package com.csc205.project2.shapes;

public interface ThreeDimensionalShape {

    double getSurfaceArea();
    double getVolume();

}
```

### Step 1: Generate the Abstract Base Class
Use your chosen AI assistant to create `Shape3D.java` with these requirements:

**Must Include:**
- Implements the `ThreeDimensionalShape` interface
- Concrete implementations of `getVolume()` and `getSurfaceArea()` that call the abstract methods
- Common properties: `name` (String), `color` (String)  (Properties in Java are typically private fields with public getters and setters)
- Constructor(s) for initialization
- `toString()` method that formats output consistently
- Getter/setter methods as appropriate

**AI Prompting Tips:**
- Be specific about method signatures and return types
- Request proper JavaDoc documentation
- Ask for input validation where appropriate

### Step 2: Create Five Concrete Shape Classes
Generate the following shapes using AI assistance:

1. **Sphere** - Properties: radius
2. **Cube** - Properties: sideLength
3. **Cylinder** - Properties: radius, height
4. **RectangularPrism** - Properties: length, width, height
5. **[Your Choice]** - Select any 3D geometric shape (examples: Cone, Pyramid, Torus, Ellipsoid, Octahedron)

**For Each Shape Class:**
- Extends Shape3D (which implements ThreeDimensionalShape)
- Implement the abstract calculation methods from Shape3D
- Include proper constructors with validation
- Override `toString()` with shape-specific formatting
- Add any shape-specific methods if needed

**Documentation Requirement:**
Each class must include this comment header:
```java
/**
 * AI GENERATION DOCUMENTATION
 * ===========================
 * AI Tool Used: [Name and version]
 * Generation Date: [Date]
 * 
 * Original Prompt:
 * "[Your exact prompt here]"
 * 
 * Follow-up Prompts (if any):
 * 1. "[Refinement prompt 1]"
 * 2. "[Refinement prompt 2]"
 * 
 * Manual Modifications:
 * - [List any changes you made to the AI output]
 * - [Explain why changes were necessary]
 * 
 * Formula Verification:
 * - Volume formula verified against: [source]
 * - Surface area formula verified against: [source]
 */
```

## Phase 2: AI-Generated Testing Suite (25% of grade)

### Step 3: Comprehensive Unit Testing
For each shape class, generate JUnit 5 test classes that include:

**Test Categories:**
- **Basic Functionality**: Constructor, getters, setters
- **Calculation Accuracy**: Volume and surface area with known values
- **Boundary Testing**: Zero values, very small/large numbers
- **Input Validation**: Negative values, null inputs
- **Inheritance Testing**: Polymorphic behavior verification

**Example Test Scenarios for Sphere:**
```java
// Test with known mathematical results
@Test void sphereVolumeCalculation() {
    // Volume of sphere with radius 3 should be 4/3 * π * 3³ = 113.097...
}

// Test boundary conditions
@Test void sphereWithZeroRadius() {
    // What should happen? Document your decision.
}

// Test polymorphism
@Test void sphereAsShape3D() {
    // Verify it works when treated as Shape3D reference
}
```

### Step 4: Advanced Driver Class
Create a sophisticated `ShapeDriver.java` that demonstrates:

- **Polymorphism**: Array/List of Shape3D references holding different shapes
- **Comparative Analysis**: Which shape has the largest volume for given constraints?
- **Interactive Features**: Allow user to create shapes with custom parameters
- **Performance Timing**: Measure calculation speeds (optional)
- **Formatted Output**: Professional presentation of results

**Sample Output Format:**
```
=== 3D Shape Analysis System ===

Created Shapes:
1. Sphere {name='Red Ball', radius=5.0}
   - Surface Area: 314.16 square units
   - Volume: 523.60 cubic units

2. Cube {name='Blue Box', side=4.0}
   - Surface Area: 96.00 square units  
   - Volume: 64.00 cubic units

[... other shapes ...]

Analysis Results:
- Largest Volume: Red Ball (523.60)
- Largest Surface Area: Red Ball (314.16)
- Most Efficient (Volume/Surface): Blue Box (0.67)
```

## Phase 3: Validation and Enhancement (25% of grade)

### Step 5: Code Review Process
**Mathematical Verification:**
- Verify all formulas against reliable mathematical sources
- Test calculations with known values (use online calculators for verification)
- Document your verification process in your reflection (in REFLECTION.md)

**Code Quality Review:**
- Check for proper Java naming conventions
- Ensure consistent code style across all classes
- Verify error handling and input validation
- Test edge cases thoroughly

**AI Output Analysis:**
- Compare outputs from different AI tools (if available)
- Identify areas where AI struggled or excelled
- Document any corrections or improvements you made

## Assessment and Submission (15% of grade)

### Required Deliverables
1. **Complete Source Code**: All Java files with proper AI documentation
2. **Test Suite**: Comprehensive JUnit tests with good coverage
3. **Documentation Package**:
    - REFLECTION.md (1-3 page analysis of the experience)

### Submission Process
1. **Commit and Push**: Ensure all code is committed to your GitHub repository
2. **Public Repository**: Make sure your repository is public
3. **Submit Links**: Provide your GitHub repository URL in your assignment submission

### Reflection Questions (Address in REFLECTION.md)
- **AI Effectiveness**: Where did AI tools excel? Where did they struggle?
- **Code Quality Comparison**: How does AI-generated code compare to manual coding?
- **Learning Experience**: What did you learn about inheritance AND AI-assisted development?
- **Validation Process**: How did you ensure the AI-generated code was correct?
- **Future Applications**: How will you use AI tools in future programming projects?

## Bonus Opportunity

### Shape Factory Pattern (+15 points)
Consult your AI about what an OOP factory pattern is, then implement a factory pattern that creates shapes from Class and string/dimension inputs:
```java
Shape3D shape = ShapeFactory.create(Sphere.class, "Red Ball", 5.0);
```
- Ensure the factory handles invalid inputs gracefully
- Document the factory implementation and usage in your reflection
- Include tests for the factory method

## Helpful Resources

### Mathematical Formulas
- **Sphere**: Volume = (4/3)πr³, Surface Area = 4πr²
- **Cube**: Volume = s³, Surface Area = 6s²
- **Cylinder**: Volume = πr²h, Surface Area = 2πr(r + h)
- **Rectangular Prism**: Volume = lwh, Surface Area = 2(lw + lh + wh)

### AI Prompting Best Practices
1. **Be Specific**: Include exact variable names and method signatures
2. **Request Documentation**: Always ask for JavaDoc comments on your methods and classes
3. **Specify Standards**: Mention Java naming conventions
4. **Ask for Validation**: Request input checking and error handling
5. **Iterate**: Use follow-up prompts to refine the output

### Testing Resources and Best Practices
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Java Math Class Documentation](https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html)
- Online geometric calculators for verification - Google "3D shape volume calculator"
- For tests that don't pass or are causing compile issues, break down the problem and ask your AI tool for help debugging specific issues.
- Don't spend a lot of time trying to fix a test that does not compile. If you can't get it to compile, comment it out and move on.
- If the test compiles, but fails the assertion, you can utilize the @Disabled annotation on the method to skip the test and move on.

## Support and Questions
- Utilize our Discord channel for common questions
- Use office hours for complex inheritance or AI tool questions or contact me via Canvas or Discord

---

**Remember**: The goal isn't just to get working code from AI, but to understand, validate, and improve that code while mastering inheritance concepts. Document your learning journey thoroughly!
