package ce190770_l02;

import java.util.Scanner;

/**
 * L02 - Student Management System
 *
 * This class contains method and utilities for input validating
 *
 * @author Dinh Cong Phuc - CE190770 - Mar 4, 2025
 */
public class InputValidation {

    // Create a static Scanner object to read user input throughout the program
    public static Scanner sc = new Scanner(System.in);

    /**
     * Gets and validates integer input from user
     *
     * @return valid integer input from user
     * @throws Exception if input cannot be parsed as integer
     */
    public static int getIntInput() throws Exception {
        // Loop until valid input is received
        while (true) {
            try {
                // Parse user input as integer after trimming whitespace
                int in = Integer.parseInt(sc.nextLine().trim());
                // Return valid input
                return in;
            } catch (Exception e) {
                // Throws exception if input cannot be parsed as integer
                throw new Exception("Input must be an integer.");
            }
        }
    }

    /**
     * Validates positive integer input
     *
     * @return Valid positive integer
     * @throws Exception if input cannot be parsed as integer
     */
    public static int getIntInputUnsigned() throws Exception {
        // Loop until valid input is received
        while (true) {
            try {
                // Get integer input
                int in = getIntInput();
                // Check if input is positive
                if (in < 0) {
                    // throw exception if input is negative
                    throw new Exception("Input must be larger than 0.");
                } else {
                    // Return valid input
                    return in;
                }
            } catch (Exception e) {
                // throw IntInput exception if input is invalid
                throw new Exception(e.getMessage());
            }
        }
    }

    /**
     * Validates integer input within specified range (inclusive min - inclusive
     * max)
     *
     * @param min Minimum allowed value
     * @param max Maximum allowed value
     * @return Valid integer within range
     * @throws Exception if input cannot be parsed as integer
     */
    public static int getIntInputLimit(int min, int max) throws Exception {
        // Loop until valid input is received
        while (true) {
            try {
                // Get integer input
                int in = getIntInput();
                // Check if input is within range
                if (in < min || in > max) {
                    // Throw exception if input is out of limit
                    throw new Exception(String.format("Input must be between %d to %d.", min, max));
                } else {
                    // Return valid input
                    return in;
                }
            } catch (Exception e) {
                // Throw IntInput exception if input is invalid
                throw new Exception(e.getMessage());
            }
        }
    }

    /**
     * Validates integer input to ensure it's greater than specified number
     *
     * @param num Minimum allowed value (exclusive)
     * @return Valid integer greater than the specified number
     * @throws Exception if input is invalid or less than specified number
     */
    public static int getIntInputFrom(int num) throws Exception {
        try {
            // Get integer input
            int in = getIntInput();
            // Check if input is greater than specified number
            if (in < num) {
                // Throw exception if input is lower than required
                throw new Exception(String.format("Input must be larger than %d.", num));
            } else {
                // Return valid input
                return in;
            }
        } catch (Exception e) {
            // Throw IntInput exception if input is invalid
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Gets and validates optional integer input that can be null Allows empty
     * input to return null, otherwise ensures positive integer
     *
     * @return Integer object if valid input provided, null if empty input
     */
    public static Integer getIntInputOptional() {
        // Loop until valid input or empty input is received
        while (true) {
            // Read input and remove all whitespace
            String input = sc.nextLine().trim().replaceAll(" +", "");
            // Return null for empty input
            if (input.isEmpty()) {
                return null;
            }
            try {
                // Parse input as integer
                int in = Integer.parseInt(input);
                // Validate if number is non-negative
                if (in >= 0) {
                    return in;
                } else {
                    // Prompt for retry if number is negative
                    System.out.print("Input must be larger or equals 0.");
                }
            } catch (Exception e) {
                // Prompt for retry if input is not a valid integer
                System.out.print("Input is not a valid integer.");
            }
        }
    }

    /**
     * Gets and validates double input from user
     *
     * @return valid double input from user
     */
    public static double getDoubleInput() {
        // Loop until valid input is received
        while (true) {
            try {
                // Parse user input as double
                double in = Double.parseDouble(sc.nextLine().trim());
                // Return valid input
                return in;
            } catch (Exception e) {
                // Display error message for invalid input
                System.out.print("Input is not a valid double. Please try again: ");
            }
        }
    }

    /**
     * Gets and validates non-empty string input from at least 2 words
     *
     * @return valid non-empty string with normalized spaces
     * @throws Exception if input is empty
     */
    public static String getMultiStringNonEmpty() throws Exception {
        // Continuous loop until valid input is received
        while (true) {
            // Reads input, trims whitespace, and normalizes spaces
            String in = sc.nextLine().trim().replaceAll(" +", " ");
            // Checks if input is not empty
            if (!in.isEmpty()) {
                // Returns valid input
                return in;
            } else {
                // Prompts user to enter non-empty input
                throw new Exception("Input should not be empty.");
            }
        }
    }

    /**
     * Gets and validates non-empty string input
     *
     * @return string if valid
     * @throws Exception if input is empty
     */
    public static String getSingleStringNonEmpty() throws Exception {
        // Continuous loop until valid input is received
        // Reads input, trims whitespace, and normalizes spaces
        String in = sc.nextLine().trim().replaceAll(" +", "");
        // Checks if input is not empty
        if (!in.isEmpty()) {
            // Returns valid input
            return in;
        } else {
            // Prompts user to enter non-empty input
            throw new Exception("Input must not be empty!");
        }
    }

    /**
     * Gets and validates string input that must contain only letters and spaces
     * Uses regex to ensure no digits are present
     *
     * @return valid string containing only letters and spaces
     */
    public static String getNoDigitMultiString() throws Exception {
        String in = "";
        try {
            // Get non-empty string input first
            in = getMultiStringNonEmpty();
            // Keep prompting until input contains only letters and spaces
            while (!in.matches("[a-zA-Z ]+")) {
                // Print error if input is not valid
                throw new Exception("Please only enter letters.");
            }
        } catch (Exception e) {
            // Print error message if exception occurs
            throw new Exception(e.getMessage());
        }
        // Return valid input
        return in;
    }

    /**
     * Method to handle continuation prompt
     *
     * @return boolean indicating whether to continue or not
     */
    public static boolean continueYN(String message) throws Exception {
        // Infinite loop until valid input received
        while (true) {
            // Start of try block for exception handling
            try {
                // Prompts user for Y/N input
                System.out.printf("%s (Y/N):", message);
                // Gets first character of validated input in uppercase
                String input = getSingleStringNonEmpty().toUpperCase();
                // If input is Y, return true
                if (input.equals("Y")) {
                    return true;
                }
                // If input is N, return false
                if (input.equals("N")) {
                    return false;
                }
                // Prompts for valid input
                System.err.println("Please enter 'Y' to proceed or 'N' to reject.");
                // Catches any exceptions
            } catch (Exception ex) {
                // Prints the exception message
                System.err.println(ex.getMessage());
            }
        }
    }
}
