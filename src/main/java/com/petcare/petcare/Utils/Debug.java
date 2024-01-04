package com.petcare.petcare.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Debug {
    /**
     *
     * COLORS
     *
     */
    private static final String RESET_COLOR = "\u001B[0m";
    private static final String RED_COLOR = "\u001B[31m";
    private static final String YELLOW_COLOR = "\u001B[33m";
    private static final String GREEN_COLOR = "\u001B[32m";


    /**
     *
     * PREFIXES
     *
     */
    private static final String prefix = RED_COLOR + "[DEBUG] " + RESET_COLOR;
    private static final String successPrefix = GREEN_COLOR + "[DEBUG] " + RESET_COLOR;
    private static final String warningPrefix = YELLOW_COLOR + "[DEBUG] " + RESET_COLOR;

    /**
     *
     * Print a syccess message to the console
     *
     * @param text Message to print
     * @param showPrefix True to show the prefix, false to hide it
     * @param showFooter True to show the footer, false to hide it
     *
     */
    public static void success(String text, boolean showPrefix, boolean showFooter) {
        Throwable throwable = new Throwable();
        StackTraceElement[] stackTraceElements = throwable.getStackTrace();

        System.out.println((showPrefix ? (successPrefix) : "") + text + (showFooter ? footer(stackTraceElements[1].getFileName(), stackTraceElements[1].getLineNumber()) : ""));
    }

    /**
     *
     * Print a warning message to the console
     *
     * @param text Message to print
     * @param showPrefix True to show the prefix, false to hide it
     * @param showFooter True to show the footer, false to hide it
     *
     */
    public static void warning(String text, boolean showPrefix, boolean showFooter) {
        Throwable throwable = new Throwable();
        StackTraceElement[] stackTraceElements = throwable.getStackTrace();

        System.out.println((showPrefix ? (warningPrefix) : "") + text + (showFooter ? footer(stackTraceElements[1].getFileName(), stackTraceElements[1].getLineNumber()) : ""));
    }

    /**
     *
     * Print a message to the console
     *
     * @param text Message to print
     * @param showPrefix True to show the prefix, false to hide it
     * @param showFooter True to show the footer, false to hide it
     *
     */
    public static void print(String text, boolean showPrefix, boolean showFooter) {
        Throwable throwable = new Throwable();
        StackTraceElement[] stackTraceElements = throwable.getStackTrace();

        System.out.println((showPrefix ? (prefix) : "") + text + (showFooter ? footer(stackTraceElements[1].getFileName(), stackTraceElements[1].getLineNumber()) : ""));
    }

    /**
     *
     * Return the footer of the debug
     *
     * @return Footer of the debug
     *
     */
    public static String footer(String filename, int line) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return  " | " + filename + ":" + line + " | " + dtf.format(now);
    }
}
