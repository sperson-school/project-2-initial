package com.csc205.project2;

import com.csc205.project2.shapes.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * GUI-based demonstration of the 3D Shape inheritance hierarchy with modern dark theme.
 *
 * Features:
 * - Interactive shape creation through popup dialogs
 * - Visual display of shape information and calculations
 * - Comprehensive comparative analysis of created shapes
 * - Performance testing and benchmarking
 * - Shape type distribution statistics
 * - Default shape creation for quick setup
 * - Modern dark mode UI with enhanced styling
 * - All logging directed to terminal/console
 */
public class ShapeDriverGUI extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(ShapeDriver.class.getName());
    private final List<Shape3D> shapes = new ArrayList<>();
    private JTextArea displayArea;
    private JButton createButton, analyzeButton, performanceButton, clearButton, defaultShapesButton;

    // Dark mode color scheme
    private static final Color BACKGROUND_COLOR = new Color(30, 30, 30);
    private static final Color PANEL_COLOR = new Color(45, 45, 45);
    private static final Color TEXT_COLOR = new Color(220, 220, 220);
    private static final Color ACCENT_COLOR = new Color(100, 150, 255);
    private static final Color BUTTON_COLOR = new Color(60, 60, 60);
    private static final Color BUTTON_HOVER_COLOR = new Color(80, 80, 80);
    private static final Color SUCCESS_COLOR = new Color(76, 175, 80);
    private static final Color WARNING_COLOR = new Color(255, 193, 7);
    private static final Color ERROR_COLOR = new Color(244, 67, 54);

    public ShapeDriverGUI() {
        setupLookAndFeel();
        initializeGUI();
        LOGGER.info("ShapeDriver GUI initialized with dark theme");
    }

    private void setupLookAndFeel() {
        try {
            // Use cross-platform LAF for consistent theming, especially on macOS
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

            // Core dark colors
            UIManager.put("control", PANEL_COLOR);
            UIManager.put("info", PANEL_COLOR);
            UIManager.put("nimbusBase", BACKGROUND_COLOR);
            UIManager.put("nimbusLightBackground", PANEL_COLOR);
            UIManager.put("nimbusFocus", ACCENT_COLOR);
            UIManager.put("text", TEXT_COLOR);
            UIManager.put("menu", PANEL_COLOR);
            UIManager.put("menuText", TEXT_COLOR);
            UIManager.put("popupMenuBorder", BorderFactory.createLineBorder(ACCENT_COLOR));
            UIManager.put("PopupMenu.background", PANEL_COLOR);
            UIManager.put("MenuItem.background", PANEL_COLOR);
            UIManager.put("MenuItem.foreground", TEXT_COLOR);
            UIManager.put("MenuItem.selectionBackground", ACCENT_COLOR.darker());
            UIManager.put("MenuItem.selectionForeground", Color.WHITE);

            // OptionPane
            UIManager.put("OptionPane.background", BACKGROUND_COLOR);
            UIManager.put("OptionPane.messageForeground", TEXT_COLOR);
            UIManager.put("OptionPane.messageAreaBorder", BorderFactory.createEmptyBorder());
            UIManager.put("OptionPane.foreground", TEXT_COLOR);

            // Buttons
            UIManager.put("Button.background", BUTTON_COLOR);
            UIManager.put("Button.foreground", TEXT_COLOR);
            UIManager.put("Button.focus", BUTTON_HOVER_COLOR);
            UIManager.put("Button.select", BUTTON_HOVER_COLOR);
            UIManager.put("Button.border", BorderFactory.createLineBorder(ACCENT_COLOR, 1));
            UIManager.put("Button.disabledText", new Color(140,140,140));

            // ComboBox
            UIManager.put("ComboBox.background", PANEL_COLOR);
            UIManager.put("ComboBox.foreground", TEXT_COLOR);
            UIManager.put("ComboBox.selectionBackground", ACCENT_COLOR.darker());
            UIManager.put("ComboBox.selectionForeground", Color.WHITE);
            UIManager.put("ComboBox.buttonBackground", BUTTON_COLOR);
            UIManager.put("ComboBox.buttonHighlight", BUTTON_HOVER_COLOR);
            UIManager.put("ComboBox.border", BorderFactory.createLineBorder(ACCENT_COLOR, 1));
            UIManager.put("ComboBox.disabledForeground", new Color(120,120,120));

            // Text components
            UIManager.put("TextField.background", PANEL_COLOR);
            UIManager.put("TextField.foreground", TEXT_COLOR);
            UIManager.put("TextField.caretForeground", TEXT_COLOR);
            UIManager.put("TextField.selectionBackground", ACCENT_COLOR);
            UIManager.put("TextField.selectionForeground", Color.WHITE);
            UIManager.put("TextField.border", BorderFactory.createLineBorder(ACCENT_COLOR, 1));

            UIManager.put("TextArea.background", PANEL_COLOR);
            UIManager.put("TextArea.foreground", TEXT_COLOR);
            UIManager.put("TextArea.caretForeground", TEXT_COLOR);

            // Lists
            UIManager.put("List.background", PANEL_COLOR);
            UIManager.put("List.foreground", TEXT_COLOR);
            UIManager.put("List.selectionBackground", ACCENT_COLOR.darker());
            UIManager.put("List.selectionForeground", Color.WHITE);

            // Scrollbars
            UIManager.put("ScrollPane.background", BACKGROUND_COLOR);
            UIManager.put("ScrollBar.background", PANEL_COLOR);
            UIManager.put("ScrollBar.thumb", BUTTON_COLOR);
            UIManager.put("ScrollBar.track", BACKGROUND_COLOR);

            // Labels
            UIManager.put("Label.foreground", TEXT_COLOR);
            UIManager.put("Label.background", BACKGROUND_COLOR);

            // ToolTips
            UIManager.put("ToolTip.background", PANEL_COLOR);
            UIManager.put("ToolTip.foreground", TEXT_COLOR);
            UIManager.put("ToolTip.border", BorderFactory.createLineBorder(ACCENT_COLOR));

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Could not set UI colors: " + e.getMessage());
        }
    }

    private void initializeGUI() {
        setTitle("3D Shape Analysis System - Dark Mode");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Set dark background
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Create header panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Create main display area with modern styling
        displayArea = new JTextArea(25, 60);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        displayArea.setBackground(PANEL_COLOR);
        displayArea.setForeground(TEXT_COLOR);
        displayArea.setCaretColor(TEXT_COLOR);
        displayArea.setBorder(new EmptyBorder(15, 15, 15, 15));
        displayArea.setText("=== 3D Shape Analysis System ===\n\n" +
                          "Shape Analysis Application\n\n" +
                          "Quick Start:\n" +
                          "- Click 'Quick Demo' to create sample shapes\n" +
                          "- Use 'Create Shape' to build custom shapes\n" +
                          "- Select 'Analyze Shapes' for comparative analysis\n" +
                          "- Run 'Test Performance' to benchmark calculations\n\n" +
                          "Select an option to begin.");

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 2),
            "Shape Display",
            0, 0,
            new Font("Segoe UI", Font.BOLD, 14),
            ACCENT_COLOR));
        scrollPane.getViewport().setBackground(PANEL_COLOR);
        scrollPane.setBackground(BACKGROUND_COLOR);

        add(scrollPane, BorderLayout.CENTER);

        // Create enhanced button panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        // Window styling
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(true);

        // Add some padding around the entire content
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        // Title
        JLabel titleLabel = new JLabel("3D Shape Analysis System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(ACCENT_COLOR);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Advanced Shape Analytics with Performance Testing", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_COLOR);

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);

        return headerPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 15, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

        // Create styled buttons
        createButton = createStyledButton("Create Shape", "Create new 3D shapes", SUCCESS_COLOR);
        createButton.addActionListener(new CreateShapeListener());

        defaultShapesButton = createStyledButton("Quick Demo", "Create sample shapes instantly", ACCENT_COLOR);
        defaultShapesButton.addActionListener(e -> createDefaultShapes());

        analyzeButton = createStyledButton("Analyze Shapes", "Comprehensive shape analysis", WARNING_COLOR);
        analyzeButton.addActionListener(new AnalyzeShapesListener());
        analyzeButton.setEnabled(false);

        performanceButton = createStyledButton("Test Performance", "Benchmark calculation speeds", new Color(156, 39, 176));
        performanceButton.addActionListener(e -> testPerformance());
        performanceButton.setEnabled(false);

        clearButton = createStyledButton("Clear All", "Remove all shapes", ERROR_COLOR);
        clearButton.addActionListener(e -> clearShapes());
        clearButton.setEnabled(false);

        JButton exitButton = createStyledButton("Exit", "Close the application", new Color(96, 125, 139));
        exitButton.addActionListener(e -> {
            int result = showStyledConfirmDialog(
                "Are you sure you want to exit?",
                "Exit Application");
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        buttonPanel.add(createButton);
        buttonPanel.add(defaultShapesButton);
        buttonPanel.add(analyzeButton);
        buttonPanel.add(performanceButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);

        return buttonPanel;
    }

    private JButton createStyledButton(String text, String tooltip, Color accentColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(BUTTON_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accentColor, 2),
            new EmptyBorder(10, 15, 10, 15)
        ));
        button.setToolTipText(tooltip);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });

        return button;
    }

    private class CreateShapeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedType = showShapeSelectionDialog();
            if (selectedType != null) {
                LOGGER.log(Level.INFO, "User selected shape type: {0}", selectedType);
                createShape(selectedType);
            }
        }
    }

    /**
     * Custom dark-themed shape selection dialog to avoid system default styling issues.
     */
    private String showShapeSelectionDialog() {
        final String[] shapeTypes = {"Sphere", "Cube", "Cylinder", "RectangularPrism", "Cone"};
        final JDialog dialog = new JDialog(this, "Select Shape Type", true);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(BACKGROUND_COLOR);

        JLabel title = new JLabel("Select a Shape Type", SwingConstants.CENTER);
        title.setForeground(ACCENT_COLOR);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setBorder(new EmptyBorder(10,10,10,10));
        dialog.add(title, BorderLayout.NORTH);

        JComboBox<String> combo = new JComboBox<>(shapeTypes);
        combo.setBackground(PANEL_COLOR);
        combo.setForeground(TEXT_COLOR);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 1));
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(BACKGROUND_COLOR);
        center.add(combo);
        dialog.add(center, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setBackground(BACKGROUND_COLOR);
        JButton ok = createStyledButton("OK", "Confirm selection", ACCENT_COLOR);
        JButton cancel = createStyledButton("Cancel", "Cancel selection", ERROR_COLOR);
        buttons.add(ok);
        buttons.add(cancel);
        dialog.add(buttons, BorderLayout.SOUTH);

        final String[] selection = {null};
        ok.addActionListener(a -> { selection[0] = (String) combo.getSelectedItem(); dialog.dispose(); });
        cancel.addActionListener(a -> { selection[0] = null; dialog.dispose(); });

        dialog.setSize(380, 180);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setVisible(true);
        return selection[0];
    }

    /**
     * Custom dark-themed input dialog to replace JOptionPane.showInputDialog.
     */
    private String showCustomInputDialog(String message, String title) {
        final JDialog dialog = new JDialog(this, title, true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(BACKGROUND_COLOR);
        ((JComponent) dialog.getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel msgLabel = new JLabel(message);
        msgLabel.setForeground(TEXT_COLOR);
        msgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        msgLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        dialog.add(msgLabel, BorderLayout.NORTH);

        JTextField inputField = new JTextField(20);
        inputField.setBackground(PANEL_COLOR);
        inputField.setForeground(TEXT_COLOR);
        inputField.setCaretColor(TEXT_COLOR);
        inputField.setFont(new Font("Consolas", Font.PLAIN, 14));
        inputField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR),
            new EmptyBorder(8, 8, 8, 8)
        ));
        dialog.add(inputField, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setBackground(BACKGROUND_COLOR);
        JButton ok = createStyledButton("OK", "Confirm input", ACCENT_COLOR);
        JButton cancel = createStyledButton("Cancel", "Cancel input", ERROR_COLOR);
        buttons.add(ok);
        buttons.add(cancel);
        dialog.add(buttons, BorderLayout.SOUTH);

        final String[] input = {null};
        ok.addActionListener(a -> {
            input[0] = inputField.getText();
            dialog.dispose();
        });
        cancel.addActionListener(a -> {
            input[0] = null;
            dialog.dispose();
        });

        // Set focus to the input field when the dialog opens
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent e) {
                inputField.requestFocusInWindow();
            }
        });

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setVisible(true);
        return input[0];
    }

    private void createShape(String shapeType) {
        try {
            // Get basic info using custom dialogs
            String name = showCustomInputDialog("Enter shape name:", "Create Shape - Name");
            if (name == null) return; // User cancelled
            if (name.trim().isEmpty()) {
                showStyledMessage("Name cannot be empty!", "Input Error", false);
                return;
            }

            String color = showCustomInputDialog("Enter shape color (optional):", "Create Shape - Color");
            if (color == null) color = ""; // Default to empty if cancelled, rather than exiting flow

            Shape3D shape = null;

            switch (shapeType) {
                case "Sphere":
                    shape = createSphere(name, color);
                    break;
                case "Cube":
                    shape = createCube(name, color);
                    break;
                case "Cylinder":
                    shape = createCylinder(name, color);
                    break;
                case "RectangularPrism":
                    shape = createRectangularPrism(name, color);
                    break;
                case "Cone":
                    shape = createCone(name, color);
                    break;
            }

            if (shape != null) {
                shapes.add(shape);
                updateDisplay();
                analyzeButton.setEnabled(true);
                clearButton.setEnabled(true);
                performanceButton.setEnabled(true);

                LOGGER.log(Level.INFO, "Successfully created {0}: {1}",
                    new Object[]{shapeType, shape.toString()});

                showStyledMessage(
                    "Shape created successfully!\n" + shape.toString(),
                    "Success",
                    true);
            }
        } catch (NumberFormatException ex) {
            LOGGER.log(Level.WARNING, "Invalid number format entered.", ex);
            showStyledMessage("Invalid number format. Please enter valid numbers for dimensions.", "Input Error", false);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error creating shape: " + ex.getMessage(), ex);
            showStyledMessage(
                "Error creating shape: " + ex.getMessage(),
                "Error",
                false);
        }
    }

    private Shape3D createSphere(String name, String color) {
        String radiusStr = showCustomInputDialog("Enter radius:", "Create Sphere");
        if (radiusStr == null) return null;

        double radius = Double.parseDouble(radiusStr);
        return color.trim().isEmpty() ?
            new Sphere(name, radius) :
            new Sphere(name, color, radius);
    }

    private Shape3D createCube(String name, String color) {
        String sideStr = showCustomInputDialog("Enter side length:", "Create Cube");
        if (sideStr == null) return null;

        double side = Double.parseDouble(sideStr);
        return color.trim().isEmpty() ?
            new Cube(name, side) :
            new Cube(name, color, side);
    }

    private Shape3D createCylinder(String name, String color) {
        String radiusStr = showCustomInputDialog("Enter radius:", "Create Cylinder");
        if (radiusStr == null) return null;

        String heightStr = showCustomInputDialog("Enter height:", "Create Cylinder");
        if (heightStr == null) return null;

        double radius = Double.parseDouble(radiusStr);
        double height = Double.parseDouble(heightStr);

        return color.trim().isEmpty() ?
            new Cylinder(name, radius, height) :
            new Cylinder(name, color, radius, height);
    }

    private Shape3D createRectangularPrism(String name, String color) {
        String lengthStr = showCustomInputDialog("Enter length:", "Create Rectangular Prism");
        if (lengthStr == null) return null;

        String widthStr = showCustomInputDialog("Enter width:", "Create Rectangular Prism");
        if (widthStr == null) return null;

        String heightStr = showCustomInputDialog("Enter height:", "Create Rectangular Prism");
        if (heightStr == null) return null;

        double length = Double.parseDouble(lengthStr);
        double width = Double.parseDouble(widthStr);
        double height = Double.parseDouble(heightStr);

        return color.trim().isEmpty() ?
            new RectangularPrism(name, length, width, height) :
            new RectangularPrism(name, color, length, width, height);
    }

    private Shape3D createCone(String name, String color) {
        String radiusStr = showCustomInputDialog("Enter radius:", "Create Cone");
        if (radiusStr == null) return null;

        String heightStr = showCustomInputDialog("Enter height:", "Create Cone");
        if (heightStr == null) return null;

        double radius = Double.parseDouble(radiusStr);
        double height = Double.parseDouble(heightStr);

        return color.trim().isEmpty() ?
            new Cone(name, radius, height) :
            new Cone(name, color, radius, height);
    }

    private class AnalyzeShapesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (shapes.isEmpty()) {
                showStyledMessage(
                    "No shapes to analyze!",
                    "Warning",
                    false);
                return;
            }

            LOGGER.log(Level.INFO, "Performing comprehensive analysis on {0} shapes", shapes.size());
            performComparativeAnalysis();
        }
    }

    /**
     * Performs comprehensive comparative analysis of all shapes (similar to ShapeDriver)
     */
    private void performComparativeAnalysis() {
        StringBuilder analysis = new StringBuilder();
        analysis.append("=== COMPREHENSIVE COMPARATIVE ANALYSIS ===\n\n");

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
        analysis.append("EXTREMES:\n");
        analysis.append(String.format("- Largest Volume: %s (%.2f cubic units)\n",
                largestVolume.getName(), largestVolume.getVolume()));
        analysis.append(String.format("- Largest Surface Area: %s (%.2f square units)\n",
                largestSurfaceArea.getName(), largestSurfaceArea.getSurfaceArea()));
        analysis.append(String.format("- Smallest Volume: %s (%.2f cubic units)\n",
                smallestVolume.getName(), smallestVolume.getVolume()));
        analysis.append(String.format("- Smallest Surface Area: %s (%.2f square units)\n\n",
                smallestSurfaceArea.getName(), smallestSurfaceArea.getSurfaceArea()));

        analysis.append("EFFICIENCY:\n");
        analysis.append(String.format("- Most Efficient (V/SA): %s (%.4f)\n",
                mostEfficient.getName(), mostEfficient.getVolume() / mostEfficient.getSurfaceArea()));
        analysis.append(String.format("- Least Efficient (V/SA): %s (%.4f)\n\n",
                leastEfficient.getName(), leastEfficient.getVolume() / leastEfficient.getSurfaceArea()));

        analysis.append("STATISTICS:\n");
        analysis.append(String.format("- Total Shapes: %d\n", shapes.size()));
        analysis.append(String.format("- Average Volume: %.2f cubic units\n", avgVolume));
        analysis.append(String.format("- Average Surface Area: %.2f square units\n", avgSurfaceArea));
        analysis.append(String.format("- Combined Volume: %.2f cubic units\n", totalVolume));
        analysis.append(String.format("- Combined Surface Area: %.2f square units\n\n", totalSurfaceArea));

        analysis.append("SHAPE DISTRIBUTION:\n");
        typeDistribution.forEach((type, count) ->
                analysis.append(String.format("- %s: %d (%.1f%%)\n", type, count,
                        100.0 * count / shapes.size())));

        // Display in a styled dark theme dialog
        showStyledDialog(analysis.toString(), "Comprehensive Analysis Results", 650, 500);

        LOGGER.log(Level.INFO, "Comprehensive analysis completed successfully");
    }

    private void updateDisplay() {
        StringBuilder display = new StringBuilder();
        display.append("=== 3D Shape Analysis System ===\n\n");

        if (shapes.isEmpty()) {
            display.append("Shape Analysis Application\n\n");
            display.append("Quick Start:\n");
            display.append("- Click 'Quick Demo' to create sample shapes\n");
            display.append("- Use 'Create Shape' to build custom shapes\n");
            display.append("- Select 'Analyze Shapes' for comparative analysis\n");
            display.append("- Run 'Test Performance' to benchmark calculations\n\n");
            display.append("Select an option to begin.");
        } else {
            display.append(String.format("Created Shapes (%d total):\n\n", shapes.size()));

            for (int i = 0; i < shapes.size(); i++) {
                Shape3D shape = shapes.get(i);
                display.append(String.format("%d. %s\n", i + 1, shape.toString()));
                display.append(String.format("   Surface Area: %.2f square units\n", shape.getSurfaceArea()));
                display.append(String.format("   Volume: %.2f cubic units\n", shape.getVolume()));
                display.append(String.format("   Efficiency Ratio (V/SA): %.4f\n\n",
                        shape.getVolume() / shape.getSurfaceArea()));
            }

            display.append("Note: Use 'Analyze Shapes' for detailed comparisons.");
        }

        displayArea.setText(display.toString());
        LOGGER.log(Level.INFO, "Display updated with {0} shapes", shapes.size());
    }

    private void clearShapes() {
        int result = showStyledConfirmDialog(
            "Are you sure you want to clear all shapes?\n\nThis action cannot be undone.",
            "Confirm Clear All Shapes"
        );

        if (result == JOptionPane.YES_OPTION) {
            int shapeCount = shapes.size();
            shapes.clear();
            updateDisplay();
            analyzeButton.setEnabled(false);
            clearButton.setEnabled(false);
            performanceButton.setEnabled(false);

            LOGGER.log(Level.INFO, "Cleared {0} shapes from the system", shapeCount);
            showStyledMessage("Successfully cleared " + shapeCount + " shapes.", "Clear Complete", true);
        }
    }

    private volatile boolean performanceCancelled = false;

    private void testPerformance() {
        if (shapes.isEmpty()) {
            showStyledMessage(
                "No shapes available for performance testing.\nPlease create some shapes first.",
                "Performance Test - No Data",
                false);
            return;
        }

        performanceCancelled = false;
        LOGGER.log(Level.INFO, "Starting performance analysis on {0} shapes", shapes.size());

        JDialog progressDialog = new JDialog(this, "Running Performance Test", true);
        progressDialog.setLayout(new BorderLayout());
        progressDialog.getContentPane().setBackground(BACKGROUND_COLOR);

        JTextArea progressArea = new JTextArea(12, 60);
        progressArea.setEditable(false);
        progressArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        progressArea.setBackground(PANEL_COLOR);
        progressArea.setForeground(TEXT_COLOR);
        progressArea.setBorder(new EmptyBorder(10,10,10,10));

        JScrollPane progressScrollPane = new JScrollPane(progressArea);
        progressScrollPane.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 1));
        progressDialog.add(progressScrollPane, BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.setBackground(BACKGROUND_COLOR);
        JButton cancelBtn = createStyledButton("Cancel", "Cancel performance test", ERROR_COLOR);
        south.add(cancelBtn);
        progressDialog.add(south, BorderLayout.SOUTH);

        cancelBtn.addActionListener(a -> {
            performanceCancelled = true;
            cancelBtn.setEnabled(false);
            progressArea.append("Cancellation requested... finishing current iteration.\n");
        });

        progressDialog.setSize(700, 360);
        progressDialog.setLocationRelativeTo(this);

        SwingWorker<String, String> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() {
                StringBuilder results = new StringBuilder();
                results.append("=== PERFORMANCE TEST RESULTS ===\n\n");
                publish("Initializing performance test...");
                publish(String.format("Shapes: %d", shapes.size()));

                final int iterations = 100000;
                publish(String.format("Planned iterations: %,d", iterations));

                long startTime; long volumeTime; long surfaceAreaTime;

                // Volume test
                publish("Starting volume calculations...");
                startTime = System.nanoTime();
                for (int i = 0; i < iterations && !performanceCancelled; i++) {
                    for (Shape3D s : shapes) { s.getVolume(); }
                    if (i % 10000 == 0 && i > 0) publish(String.format("Volume progress: %,d / %,d (%.1f%%)", i, iterations, i*100.0/iterations));
                }
                volumeTime = System.nanoTime() - startTime;
                publish(performanceCancelled ? "Volume test aborted early." : "Volume calculations complete.");

                // Surface area test (only if not cancelled)
                if (!performanceCancelled) {
                    publish("Starting surface area calculations...");
                    startTime = System.nanoTime();
                    for (int i = 0; i < iterations && !performanceCancelled; i++) {
                        for (Shape3D s : shapes) { s.getSurfaceArea(); }
                        if (i % 10000 == 0 && i > 0) publish(String.format("Surface area progress: %,d / %,d (%.1f%%)", i, iterations, i*100.0/iterations));
                    }
                    surfaceAreaTime = System.nanoTime() - startTime;
                    publish(performanceCancelled ? "Surface area test aborted early." : "Surface area calculations complete.");
                } else {
                    surfaceAreaTime = -1L;
                }

                publish("Compiling results...");

                results.append(String.format("Shapes Tested: %d\n", shapes.size()));
                results.append(String.format("Iterations (planned): %,d\n", iterations));
                results.append(String.format("Cancelled: %s\n\n", performanceCancelled ? "Yes" : "No"));

                results.append("VOLUME CALCULATIONS:\n");
                results.append(String.format("- Total Time: %.2f ms\n", volumeTime/1_000_000.0));
                if (!performanceCancelled)
                    results.append(String.format("- Avg / Operation: %.2f ns\n", volumeTime / (double)(iterations * shapes.size())));
                results.append('\n');

                if (!performanceCancelled) {
                    results.append("SURFACE AREA CALCULATIONS:\n");
                    results.append(String.format("- Total Time: %.2f ms\n", surfaceAreaTime/1_000_000.0));
                    results.append(String.format("- Avg / Operation: %.2f ns\n", surfaceAreaTime / (double)(iterations * shapes.size())));
                    results.append('\n');
                }

                results.append("PER-SHAPE SAMPLE (single computation timing)\n");
                for (Shape3D s : shapes) {
                    long t0 = System.nanoTime();
                    double v = s.getVolume();
                    double sa = s.getSurfaceArea();
                    long t1 = System.nanoTime();
                    results.append(String.format("- %s: volume=%.2f, surface=%.2f, compute=%d ns\n", s.getClass().getSimpleName(), v, sa, (t1-t0)));
                }

                publish("Performance test " + (performanceCancelled ? "cancelled." : "completed."));
                return results.toString();
            }

            @Override
            protected void process(List<String> chunks) {
                for (String msg : chunks) {
                    progressArea.append(msg + "\n");
                    progressArea.setCaretPosition(progressArea.getDocument().getLength());
                }
            }

            @Override
            protected void done() {
                progressDialog.dispose();
                try {
                    String results = get();
                    showStyledDialog(results, "Performance Test Results", 700, 600);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, "Performance test failed", ex);
                    showStyledMessage("Performance test failed: " + ex.getMessage(), "Performance Test Error", false);
                }
            }
        };

        worker.execute();
        progressDialog.setVisible(true);
    }

    private void createDefaultShapes() {
        // Create a set of default shapes with random parameters
        String[] defaultNames = {"Sample Sphere", "Sample Cube", "Sample Cylinder", "Sample Box", "Sample Cone"};
        String[] colors = {"Red", "Blue", "Silver", "Brown", "Green"};

        shapes.clear(); // Clear existing shapes for clean demo

        for (int i = 0; i < defaultNames.length; i++) {
            String name = defaultNames[i];
            String color = colors[i % colors.length];

            // Create varied dimensions for better demo
            double radius = 2 + Math.random() * 3;
            double side = 1.5 + Math.random() * 3.5;
            double height = 3 + Math.random() * 4;
            double length = 2 + Math.random() * 4;
            double width = 1.5 + Math.random() * 3;

            // Create each shape with realistic parameters
            switch (i) {
                case 0 -> shapes.add(new Sphere(name, color, radius));
                case 1 -> shapes.add(new Cube(name, color, side));
                case 2 -> shapes.add(new Cylinder(name, color, radius, height));
                case 3 -> shapes.add(new RectangularPrism(name, color, length, width, height));
                case 4 -> shapes.add(new Cone(name, color, radius, height));
            }
        }

        updateDisplay();
        analyzeButton.setEnabled(true);
        clearButton.setEnabled(true);
        performanceButton.setEnabled(true);

        LOGGER.log(Level.INFO, "Created default shapes: {0}", Arrays.toString(defaultNames));
        showStyledMessage(
            "Successfully created 5 sample shapes.\n\n" +
            "You can now analyze their properties and test performance.",
            "Quick Demo Complete",
            true);
    }

    // Utility methods for styled dark theme dialogs
    private void showStyledDialog(String content, String title, int width, int height) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(BACKGROUND_COLOR);

        JTextArea textArea = new JTextArea(content);
        textArea.setEditable(false);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        textArea.setBackground(PANEL_COLOR);
        textArea.setForeground(TEXT_COLOR);
        textArea.setCaretColor(TEXT_COLOR);
        textArea.setBorder(new EmptyBorder(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 1));
        scrollPane.getViewport().setBackground(PANEL_COLOR);
        scrollPane.setBackground(BACKGROUND_COLOR);

        JButton closeButton = createStyledButton("Close", "Close this dialog", ACCENT_COLOR);
        closeButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(closeButton);

        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setSize(width, height);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private int showStyledConfirmDialog(String message, String title) {
        final JDialog dialog = new JDialog(this, title, true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(BACKGROUND_COLOR);
        ((JComponent) dialog.getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        JTextArea msgArea = new JTextArea(message);
        msgArea.setEditable(false);
        msgArea.setLineWrap(true);
        msgArea.setWrapStyleWord(true);
        msgArea.setBackground(BACKGROUND_COLOR);
        msgArea.setForeground(TEXT_COLOR);
        msgArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        msgArea.setBorder(null);

        // Calculate preferred height based on line count
        int lineCount = message.split("\n").length;
        int approxCharsPerLine = 60; // heuristic width target
        int extraLines = Math.max(0, (message.length() / approxCharsPerLine) - lineCount);
        int totalLinesEstimate = lineCount + extraLines;
        int lineHeight = msgArea.getFontMetrics(msgArea.getFont()).getHeight();
        int contentHeight = Math.min(Math.max(totalLinesEstimate * lineHeight + 20, 140), 380);
        int contentWidth = 520; // fixed comfortable width for confirm dialogs

        JScrollPane scrollPane = new JScrollPane(msgArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 1));
        scrollPane.setPreferredSize(new Dimension(contentWidth, contentHeight));
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setBackground(BACKGROUND_COLOR);
        JButton yes = createStyledButton("Yes", "Confirm action", SUCCESS_COLOR);
        JButton no = createStyledButton("No", "Cancel action", ERROR_COLOR);
        buttons.add(yes);
        buttons.add(no);
        dialog.add(buttons, BorderLayout.SOUTH);

        final int[] result = {JOptionPane.NO_OPTION};
        yes.addActionListener(a -> { result[0] = JOptionPane.YES_OPTION; dialog.dispose(); });
        no.addActionListener(a -> { result[0] = JOptionPane.NO_OPTION; dialog.dispose(); });

        dialog.setMinimumSize(new Dimension(560, 220));
        dialog.pack();
        // Enforce width & avoid over-shrinking
        Dimension d = dialog.getSize();
        d.width = Math.max(d.width, 560);
        d.height = Math.max(d.height, 200);
        dialog.setSize(d);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setVisible(true);
        return result[0];
    }

    private void showStyledMessage(String message, String title, boolean isSuccess) {
        final JDialog dialog = new JDialog(this, title, true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(BACKGROUND_COLOR);
        ((JComponent) dialog.getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        JTextArea msgArea = new JTextArea(message);
        msgArea.setEditable(false);
        msgArea.setLineWrap(true);
        msgArea.setWrapStyleWord(true);
        msgArea.setBackground(BACKGROUND_COLOR);
        msgArea.setForeground(TEXT_COLOR);
        msgArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        msgArea.setBorder(null);

        // Dynamic sizing heuristics
        int lineCount = message.split("\n").length;
        int approxCharsPerLine = 65;
        int extraLines = Math.max(0, (message.length() / approxCharsPerLine) - lineCount);
        int totalLinesEstimate = lineCount + extraLines;
        int lineHeight = msgArea.getFontMetrics(msgArea.getFont()).getHeight();
        int contentHeight = Math.min(Math.max(totalLinesEstimate * lineHeight + 24, 160), 480);
        int contentWidth = 600; // Wider for info dialogs

        JScrollPane scrollPane = new JScrollPane(msgArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 1));
        scrollPane.setPreferredSize(new Dimension(contentWidth, contentHeight));
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setBackground(BACKGROUND_COLOR);
        JButton ok = createStyledButton("OK", "Acknowledge message", isSuccess ? SUCCESS_COLOR : ACCENT_COLOR);
        buttons.add(ok);
        dialog.add(buttons, BorderLayout.SOUTH);

        ok.addActionListener(a -> dialog.dispose());

        dialog.setMinimumSize(new Dimension(620, 240));
        dialog.pack();
        Dimension d = dialog.getSize();
        d.width = Math.max(d.width, 620);
        d.height = Math.max(d.height, 240);
        dialog.setSize(d);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        LOGGER.info("Starting 3D Shape Analysis System GUI");

        // Set look and feel
       /*
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // Fall back to default look and feel if system L&F fails
            System.err.println("Could not set system look and feel: " + e.getMessage());
        }
*/
        SwingUtilities.invokeLater(() -> {
            new ShapeDriverGUI().setVisible(true);
            LOGGER.info("GUI is now visible and ready for user interaction");
        });
    }
}
