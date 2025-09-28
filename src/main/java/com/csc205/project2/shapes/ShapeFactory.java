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