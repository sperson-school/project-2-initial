package com.csc205.project2.shapes;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Sophisticated driver class that demonstrates polymorphism, comparative analysis,
 * interactive features, and performance timing for 3D shapes.
 *
 * Features:
 * - Polymorphic shape management using Shape3D references
 * - Interactive shape creation with user input validation
 * - Comparative analysis (largest volume, surface area, efficiency ratios)
 * - Performance timing for calculation operations
 * - Professional formatted output with detailed statistics
 */
public class ShapeDriver {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Shape3D> shapes = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("=== 3D Shape Analysis System ===\n");

        // Create some default shapes to demonstrate
        createDefaultShapes();

        // Interactive menu
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1 -> displayAllShapes();
                case 2 -> createInteractiveShape();
                case 3 -> performComparativeAnalysis();
                case 4 -> runPerformanceTest();
                case 5 -> clearAllShapes();
                case 6 -> {
                    System.out.println("Thank you for using the 3D Shape Analysis System!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please try again.\n");
            }
        }

        scanner.close();
    }

    /**
     * Creates a set of default shapes to demonstrate the system
     */
    private static void createDefaultShapes() {
        System.out.println("Creating default demonstration shapes...\n");

        // Using polymorphism - all stored as Shape3D references
        shapes.add(new Sphere("Red Ball", "Red", 5.0));
        shapes.add(new Cube("Blue Box", "Blue", 4.0));
        shapes.add(new Cylinder("Silver Can", "Silver", 2.0, 5.0));
        shapes.add(new RectangularPrism("Brown Package", "Brown", 6.0, 4.0, 3.0));

        System.out.println("✓ Created " + shapes.size() + " default shapes\n");
    }

    /**
     * Displays the main menu options
     */
    private static void displayMenu() {
        System.out.println("=== Main Menu ===");
        System.out.println("1. Display All Shapes");
        System.out.println("2. Create New Shape");
        System.out.println("3. Comparative Analysis");
        System.out.println("4. Performance Test");
        System.out.println("5. Clear All Shapes");
        System.out.println("6. Exit");
        System.out.println();
    }

    /**
     * Displays all shapes with professional formatting
     */
    private static void displayAllShapes() {
        if (shapes.isEmpty()) {
            System.out.println("No shapes available. Create some shapes first!\n");
            return;
        }

        System.out.println("=== Created Shapes ===\n");

        for (int i = 0; i < shapes.size(); i++) {
            Shape3D shape = shapes.get(i);
            System.out.printf("%d. %s%n", i + 1, shape);
            System.out.printf("   - Surface Area: %.2f square units%n", shape.getSurfaceArea());
            System.out.printf("   - Volume: %.2f cubic units%n", shape.getVolume());
            System.out.printf("   - Efficiency Ratio (V/SA): %.4f%n%n",
                    shape.getVolume() / shape.getSurfaceArea());
        }
    }

    /**
     * Interactive shape creation with user input validation
     */
    private static void createInteractiveShape() {
        System.out.println("=== Create New Shape ===");
        System.out.println("Available shape types:");
        System.out.println("1. Sphere");
        System.out.println("2. Cube");
        System.out.println("3. Cylinder");
        System.out.println("4. Rectangular Prism");
        System.out.println();

        int shapeType = getIntInput("Select shape type (1-4): ");

        try {
            String name = getStringInput("Enter shape name: ");
            String color = getStringInput("Enter color (or press Enter for default): ");
            if (color.trim().isEmpty()) {
                color = null; // Will default to "Unspecified"
            }

            Shape3D newShape = switch (shapeType) {
                case 1 -> createSphere(name, color);
                case 2 -> createCube(name, color);
                case 3 -> createCylinder(name, color);
                case 4 -> createRectangularPrism(name, color);
                default -> {
                    System.out.println("Invalid shape type selected.\n");
                    yield null;
                }
            };

            if (newShape != null) {
                shapes.add(newShape);
                System.out.printf("✓ Successfully created: %s%n%n", newShape);
            }

        } catch (IllegalArgumentException e) {
            System.out.printf("✗ Error creating shape: %s%n%n", e.getMessage());
        }
    }

    private static Sphere createSphere(String name, String color) {
        double radius = getDoubleInput("Enter radius: ");
        return color != null ? new Sphere(name, color, radius) : new Sphere(name, radius);
    }

    private static Cube createCube(String name, String color) {
        double side = getDoubleInput("Enter side length: ");
        return color != null ? new Cube(name, color, side) : new Cube(name, side);
    }

    private static Cylinder createCylinder(String name, String color) {
        double radius = getDoubleInput("Enter radius: ");
        double height = getDoubleInput("Enter height: ");
        return color != null ? new Cylinder(name, color, radius, height) : new Cylinder(name, radius, height);
    }

    private static RectangularPrism createRectangularPrism(String name, String color) {
        double length = getDoubleInput("Enter length: ");
        double width = getDoubleInput("Enter width: ");
        double height = getDoubleInput("Enter height: ");
        return color != null ? new RectangularPrism(name, color, length, width, height)
                : new RectangularPrism(name, length, width, height);
    }

    /**
     * Performs comprehensive comparative analysis of all shapes
     */
    private static void performComparativeAnalysis() {
        if (shapes.isEmpty()) {
            System.out.println("No shapes available for analysis. Create some shapes first!\n");
            return;
        }

        System.out.println("=== Comparative Analysis Results ===\n");

        // Find shapes with extreme values
        Shape3D largestVolume = Collections.max(shapes, Comparator.comparing(Shape3D::getVolume));
        Shape3D largestSurfaceArea = Collections.max(shapes, Comparator.comparing(Shape3D::getSurfaceArea));
        Shape3D smallestVolume = Collections.min(shapes, Comparator.comparing(Shape3D::getVolume));
        Shape3D smallestSurfaceArea = Collections.min(shapes, Comparator.comparing(Shape3D::getSurfaceArea));

        // Calculate efficiency ratios
        Shape3D mostEfficient = Collections.max(shapes,
                Comparator.comparing(s -> s.getVolume() / s.getSurfaceArea()));
        Shape3D leastEfficient = Collections.min(shapes,
                Comparator.comparing(s -> s.getVolume() / s.getSurfaceArea()));

        // Statistical analysis
        double avgVolume = shapes.stream().mapToDouble(Shape3D::getVolume).average().orElse(0.0);
        double avgSurfaceArea = shapes.stream().mapToDouble(Shape3D::getSurfaceArea).average().orElse(0.0);
        double totalVolume = shapes.stream().mapToDouble(Shape3D::getVolume).sum();
        double totalSurfaceArea = shapes.stream().mapToDouble(Shape3D::getSurfaceArea).sum();

        // Shape type distribution
        Map<String, Long> typeDistribution = shapes.stream()
                .collect(Collectors.groupingBy(s -> s.getClass().getSimpleName(), Collectors.counting()));

        // Display results
        System.out.printf("📊 EXTREMES:%n");
        System.out.printf("• Largest Volume: %s (%.2f cubic units)%n",
                largestVolume.getName(), largestVolume.getVolume());
        System.out.printf("• Largest Surface Area: %s (%.2f square units)%n",
                largestSurfaceArea.getName(), largestSurfaceArea.getSurfaceArea());
        System.out.printf("• Smallest Volume: %s (%.2f cubic units)%n",
                smallestVolume.getName(), smallestVolume.getVolume());
        System.out.printf("• Smallest Surface Area: %s (%.2f square units)%n%n",
                smallestSurfaceArea.getName(), smallestSurfaceArea.getSurfaceArea());

        System.out.printf("⚡ EFFICIENCY:%n");
        System.out.printf("• Most Efficient (V/SA): %s (%.4f)%n",
                mostEfficient.getName(), mostEfficient.getVolume() / mostEfficient.getSurfaceArea());
        System.out.printf("• Least Efficient (V/SA): %s (%.4f)%n%n",
                leastEfficient.getName(), leastEfficient.getVolume() / leastEfficient.getSurfaceArea());

        System.out.printf("📈 STATISTICS:%n");
        System.out.printf("• Total Shapes: %d%n", shapes.size());
        System.out.printf("• Average Volume: %.2f cubic units%n", avgVolume);
        System.out.printf("• Average Surface Area: %.2f square units%n", avgSurfaceArea);
        System.out.printf("• Combined Volume: %.2f cubic units%n", totalVolume);
        System.out.printf("• Combined Surface Area: %.2f square units%n%n", totalSurfaceArea);

        System.out.printf("🏷️  SHAPE DISTRIBUTION:%n");
        typeDistribution.forEach((type, count) ->
                System.out.printf("• %s: %d (%.1f%%)%n", type, count,
                        100.0 * count / shapes.size()));
        System.out.println();
    }

    /**
     * Runs performance tests to measure calculation speeds
     */
    private static void runPerformanceTest() {
        if (shapes.isEmpty()) {
            System.out.println("No shapes available for performance testing. Create some shapes first!\n");
            return;
        }

        System.out.println("=== Performance Test ===\n");
        System.out.println("Running performance analysis on " + shapes.size() + " shapes...\n");

        final int iterations = 100000;

        // Test volume calculations
        final long[] startTime = {System.nanoTime()};
        for (int i = 0; i < iterations; i++) {
            for (Shape3D shape : shapes) {
                shape.getVolume(); // Triggers calculation
            }
        }
        long volumeTime = System.nanoTime() - startTime[0];

        // Test surface area calculations
        startTime[0] = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            for (Shape3D shape : shapes) {
                shape.getSurfaceArea(); // Triggers calculation
            }
        }
        long surfaceAreaTime = System.nanoTime() - startTime[0];

        // Individual shape performance
        System.out.printf("⏱️  PERFORMANCE RESULTS (%,d iterations):%n%n", iterations);

        System.out.printf("Volume Calculations:%n");
        System.out.printf("• Total Time: %.2f ms%n", volumeTime / 1_000_000.0);
        System.out.printf("• Average per Operation: %.2f ns%n",
                volumeTime / (double)(iterations * shapes.size()));
        System.out.printf("• Operations per Second: %,.0f%n%n",
                (iterations * shapes.size()) / (volumeTime / 1_000_000_000.0));

        System.out.printf("Surface Area Calculations:%n");
        System.out.printf("• Total Time: %.2f ms%n", surfaceAreaTime / 1_000_000.0);
        System.out.printf("• Average per Operation: %.2f ns%n",
                surfaceAreaTime / (double)(iterations * shapes.size()));
        System.out.printf("• Operations per Second: %,.0f%n%n",
                (iterations * shapes.size()) / (surfaceAreaTime / 1_000_000_000.0));

        // Per-shape-type performance breakdown
        System.out.printf("📋 PER-SHAPE-TYPE BREAKDOWN:%n");
        Map<String, List<Shape3D>> shapesByType = shapes.stream()
                .collect(Collectors.groupingBy(s -> s.getClass().getSimpleName()));

        // Replace the problematic section around lines 290-295 with:
        shapes.forEach(shape -> {
            long shapeStartTime = System.nanoTime();
            double volume = shape.getVolume();
            double surfaceArea = shape.getSurfaceArea();
            long shapeEndTime = System.nanoTime();

            long computationTime = shapeEndTime - shapeStartTime;

            System.out.printf("   - Volume: %.2f cubic units%n", volume);
            System.out.printf("   - Surface Area: %.2f square units%n", surfaceArea);
            System.out.printf("   - Computation Time: %d nanoseconds%n", computationTime);
        });
        System.out.println();
    }

    /**
     * Clears all shapes from the collection
     */
    private static void clearAllShapes() {
        int count = shapes.size();
        shapes.clear();
        System.out.printf("✓ Cleared %d shapes from the system.%n%n", count);
    }

    /**
     * Utility method for getting validated integer input
     */
    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    /**
     * Utility method for getting validated double input
     */
    private static double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value < 0) {
                    System.out.println("Please enter a non-negative number.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /**
     * Utility method for getting string input
     */
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
