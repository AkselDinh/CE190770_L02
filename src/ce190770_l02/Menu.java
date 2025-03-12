/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce190770_l02;

// Import necessary Java utility classes for collections
import java.util.List;
// Import Set class for storing unique elements
import java.util.Set;
// Import logging level constants for configuration
import java.util.logging.Level;
// Import Logger class for application logging
import java.util.logging.Logger;

/**
 * L02 - Student Management System
 *
 * Menu class handles the user interface and interaction for the Student
 * Management System. It provides options for creating, finding, updating,
 * deleting, and sorting student records.
 *
 * @author Dinh Cong Phuc - CE190770 - Mar 7, 2025
 */
public class Menu {

    // Instance of StudentDTB to manage student records
    private final StudentDTB dtb = new StudentDTB();
    // Counter to track number of students in database
    private int dtbSize = 0;
    // Test Student tracking flag to ensure test data is only added once
    private boolean testStudentAdded = false;

    /**
     * Main program loop that displays menu and handles user selections
     */
    public void loop() {
        // Continue showing menu until user exits
        while (true) {
            // Display the main menu options
            printMenu();
            // Handle user's menu selection
            selection();
        }
    }

    /**
     * Displays the main menu options to the user
     */
    private void printMenu() {
        // Print menu header with welcome message
        System.out.println("WELCOME TO STUDENT MANAGEMENT");
        // Print option 1 for creating new students
        System.out.println("1. Create New Student");
        // Print option 2 for finding and sorting students
        System.out.println("2. Find and Sort");
        // Print option 3 for updating or deleting students
        System.out.println("3. Update/Delete Student");
        // Print option 4 for displaying all students
        System.out.println("4. Report");
        // Print option 5 for sorting students
        System.out.println("5. Sort");
        // Print option 0 for exiting the program
        System.out.println("0. Exit");
        // Debug/Utilities for debugging
        System.out.println("-1. Add test students (Debug)(Can only used once)");
    }

    /**
     * Handles user's menu selection and calls appropriate methods
     */
    private void selection() {
        try {
            // Prompt for user selection with message
            System.out.print("Please select: ");
            // Get user input within valid range from -1 to 5
            int selection = InputValidation.getIntInputLimit(-1, 5);
            // Process user selection using switch statement
            switch (selection) {
                case 1:
                    // Add new student when option 1 is selected
                    addStudentToDTB();
                    break;
                case 2:
                    // Find and sort students when option 2 is selected
                    findAndSort();
                    break;
                case 3:
                    // Update or delete student when option 3 is selected
                    updateAndDelete();
                    break;
                case 4:
                    // Display all students when option 4 is selected
                    report();
                    break;
                case 5:
                    // Sort students when option 5 is selected
                    sort();
                    break;
                case -1:
                    // Hidden option for quickly adding test students
                    quickAddStudent();
                    break;
                case 0:
                    // Exit program when option 0 is selected
                    System.exit(0);
            }
        } catch (Exception e) {
            // Display error message if exception occurs
            System.err.println(e.getMessage());
        }
    }

    /**
     * Handles the process of adding a new student to the database
     */
    private void addStudentToDTB() {
        // Continue until a valid student is added or user cancels
        while (true) {
            try {
                // Check if database has reached size limit of 10 students
                if (dtbSize == 10) {
                    // If size limit reached, ask user if they want to sort before continuing
                    if (InputValidation.continueYN("Do you want to order now?")) {
                        // Sort the database if user confirms
                        dtb.sort();
                        // Display confirmation message for sorting
                        System.out.println("Database Sorted.");
                        // Create new student after sorting
                        createNewStudent();
                        // Exit the loop after creating a student
                        break;
                    } else {
                        // Return to main menu if user declines to sort
                        break;
                    }
                } else {
                    // Create new student if size limit not reached
                    createNewStudent();
                    // Exit the loop after creating a student
                    break;
                }
            } catch (Exception e) {
                // Display error message if exception occurs
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Creates a new student by gathering all required information
     */
    private void createNewStudent() {
        try {
            // Get and validate student ID
            String id = inID();
            // Check if ID already exists in database
            if (dtb.chkIdExist(id)) {
                // Show notification if ID already exists
                System.out.println("------Notify: Student ID already exist in database, if you wish to modify, use function Update/Delete instead.");
                // Return to calling method without creating student
                return;
            }
            // Get and validate student name
            String name = inName();

            // Create new student object with ID and name
            Student student = new Student(id, name);

            // Add semesters and courses to the student
            addSemesterAndCourses(student);

            // Add completed student record to database
            dtb.add(student);
            // Increment database size counter
            dtbSize++;
            // Display confirmation message
            System.out.println("Students Database Updated.");
        } catch (Exception e) {
            // Display error message if exception occurs
            System.err.println(e.getMessage());
        }
    }

    /**
     * Adds semesters and courses to a student
     *
     * @param student The student to add semesters and courses to
     */
    private void addSemesterAndCourses(Student student) {
        // Loop until all semesters are added or user opts to stop
        while (true) {
            try {
                // Get and validate semester input from user
                String semester = inSemester();

                // Add courses for the specified semester
                addCoursesForSemester(student, semester);

                // Ask if user wants to add another semester
                if (!InputValidation.continueYN("Do you want to add another semester?")) {
                    // Exit loop if user doesn't want to add more semesters
                    break;
                }
            } catch (Exception e) {
                // Display error message if exception occurs
                System.err.println("------Error! " + e.getMessage());
            }
        }
    }

    /**
     * Adds courses for a specific semester
     *
     * @param student The student to add courses to
     * @param semester The semester to add courses for
     */
    private void addCoursesForSemester(Student student, String semester) {

        if (student.getCourses(semester).size() >= 3) {
            System.err.println("------Error! Student already enrolled in all 3 courses for this semester");
            return;
        }

        // Loop until all courses are added or user opts to stop
        while (true) {
            try {
                // Get course selection from user
                String course = inCourse();

                // Try to add the course to the student's semester
                if (!student.addCourse(semester, course)) {
                    // Display error if course already exists for this semester
                    System.err.println("------Error! Course already exists for this semester.");
                } else {
                    // Display success message if course added
                    System.out.println("Course added successfully.");
                }

                if (student.getCourses(semester).size() >= 3) {
                    System.out.println("Student already enrolled in all 3 courses for this semester");
                    break;
                }

                // Ask if user wants to add another course for this semester
                if (!InputValidation.continueYN("Do you want to add another course for this semester?")) {
                    // Exit loop if user doesn't want to add more courses
                    break;
                }
            } catch (Exception e) {
                // Display error message if exception occurs
                System.err.println("------Error! " + e.getMessage());
            }
        }
    }

    /**
     * Gets and validates student ID input
     *
     * @return validated student ID
     */
    private String inID() {
        // Continue until a valid ID is entered
        while (true) {
            try {
                // Prompt user for student ID input with example
                System.out.print("Enter Student's ID (e. g. CE123456): ");
                // Get non-empty string input and convert to uppercase
                String id = InputValidation.getSingleStringNonEmpty().toUpperCase();

                // First check if the ID is the correct length
                if (id.length() != 8) {
                    // Error if ID isn't exactly 8 characters long
                    System.err.println("------Error! ID must be 8 characters long (CE123456).");
                } else if (!id.substring(0, 2).matches("[A-Z]{2}")) {
                    // Error if first two characters aren't uppercase letters (major code)
                    System.err.println("------Error! ID must start with major code (CExxxxxx).");
                } else if (!id.substring(2).matches("[0-9]{6}")) {
                    // Error if characters 3-8 aren't all digits
                    System.err.println("------Error! ID must have 6 digits of number after major code (CE123456).");
                } else {
                    // Return the ID if all validation checks pass
                    return id;
                }
            } catch (Exception e) {
                // Display error message if exception occurs
                System.err.println("------Error! " + e.getMessage());
            }
        }
    }

    /**
     * Gets and validates student name input
     *
     * @return validated student name
     */
    private String inName() {
        // Continue until a valid name is entered
        while (true) {
            try {
                // Prompt user for student name
                System.out.print("Enter Student's name: ");
                // Get and validate name (no digits allowed)
                String name = InputValidation.getNoDigitMultiString();
                // Return validated name
                return name;
            } catch (Exception e) {
                // Display error message if exception occurs during validation
                System.err.println("------Error! " + e.getMessage());
            }
        }
    }

    /**
     * Gets and validates semester input
     *
     * @return validated semester
     */
    private String inSemester() {
        // Continue until a valid semester is entered
        while (true) {
            try {
                // Prompt user for semester input with format examples
                System.out.print("Enter Student's semester, seasons followed by years (e.g. SP25, SU24, FA23): ");
                // Get non-empty string input and convert to uppercase
                String semester = InputValidation.getSingleStringNonEmpty().toUpperCase();

                // Series of validation checks for semester format
                if (semester.length() != 4) {
                    // Error if semester isn't exactly 4 characters long
                    System.err.println("------Error! Semester must be 4 characters long (e.g. SP25, SU24, FA23).");
                } else if (!dtb.getValidSemesters().contains(semester.substring(0, 2))
                        || !semester.substring(2, 4).matches("[0-9]+")) {
                    // Error if first 2 characters aren't valid season codes or last 2 aren't digits
                    System.err.println("------Error! Semester must starts with seasons code followed by years (e.g. SP25, SU24, FA23).");
                } else {
                    // Return the semester if all validation checks pass
                    return semester;
                }
            } catch (Exception e) {
                // Display error message if exception occurs
                System.err.println("------Error! " + e.getMessage());
            }
        }
    }

    /**
     * Gets and validates course selection
     *
     * @return selected course name
     */
    private String inCourse() {
        // Continue until a valid course is selected
        while (true) {
            try {
                // Display available course options to user
                System.out.println("Select Student's course: ");
                // Display option 1: Java
                System.out.println("1. Java");
                // Display option 2: .NET
                System.out.println("2. .NET");
                // Display option 3: C/C++
                System.out.println("3. C/C++");
                // Get user's numerical selection (1-3)
                int selection = InputValidation.getIntInputLimit(1, 3);
                // Return course name based on numerical selection
                switch (selection) {
                    case 1:
                        // Return "Java" for selection 1
                        return "Java";
                    case 2:
                        // Return ".NET" for selection 2
                        return ".NET";
                    case 3:
                        // Return "C/C++" for selection 3
                        return "C/C++";
                }
            } catch (Exception e) {
                // Display error message if exception occurs
                System.err.println("------Error! " + e.getMessage());
            }
        }
    }

    /**
     * Handles finding and sorting students based on different criteria
     */
    private void findAndSort() {
        // Check if database is empty before proceeding
        if (dtb.dtbIsEmpty()) {
            // Display error message if database is empty
            System.err.println("------Error! Database is empty, please add Student first.");
            // Return to main menu
            return;
        }

        // Label for outer loop to allow breaking out from nested structure
        OUTER:
        while (true) {
            try {
                // Display search criteria options to user
                System.out.println("Find By...");
                // Display option 1: search by ID
                System.out.println("1. ID");
                // Display option 2: search by name
                System.out.println("2. Name");
                // Display option 3: search by semester
                System.out.println("3. Semester");
                // Display option 4: search by course
                System.out.println("4. Course");
                // Display option 0: return to main menu
                System.out.println("0. Back to Menu");

                // Get user selection for search criteria (0-4)
                int selection = InputValidation.getIntInputLimit(0, 4);
                // Process user selection using switch statement
                switch (selection) {
                    case 1:
                        // For option 1: get ID input from user
                        String id = inID();
                        // Find students matching ID and display results
                        printTable(dtb.findBy(id, StudentDTB.SortType.BY_ID));
                        break;
                    case 2:
                        // For option 2: get name input from user
                        String name = inName();
                        // Find students matching name and display results
                        printTable(dtb.findBy(name, StudentDTB.SortType.BY_NAME));
                        break;
                    case 3:
                        // For option 3: get semester input from user
                        String semester = inSemester();
                        // Find students matching semester and display results
                        printTable(dtb.findBy(semester, StudentDTB.SortType.BY_SEMESTER));
                        break;
                    case 4:
                        // For option 4: get course input from user
                        String course = inCourse();
                        // Find students matching course and display results
                        printTable(dtb.findBy(course, StudentDTB.SortType.BY_COURSE));
                        break;
                    case 0:
                        // For option 0: exit the find and sort loop
                        break OUTER;
                }
            } catch (Exception e) {
                // Display error message if exception occurs
                System.err.println("------Error! " + e.getMessage());
            }
        }
    }

    /**
     * Handles updating or deleting student records
     */
    private void updateAndDelete() {
        // Check if database is empty before proceeding with update/delete
        if (dtb.dtbIsEmpty()) {
            // Display error message if no students exist in database
            System.err.println("------Error! Database is empty, please add Student first.");
            // Return to main menu without further processing
            return;
        }

        try {
            // Get the ID of the student to update or delete
            String id = inID();
            // Search for student with the specified ID
            List<Student> foundStudent = dtb.findBy(id, StudentDTB.SortType.BY_ID);

            // Check if student with the given ID was found
            if (foundStudent.isEmpty()) {
                // Display error message if no matching student was found
                System.err.println("Student's ID not found.");
            } else {
                // Display header for student information
                System.out.println("Student's Current Information:");
                // Print formatted table header with column names
                System.out.printf("+-----------+----------------------+------------+------------+%n");
                System.out.printf("| %-9s | %-20s | %-10s | %-10s |%n", "ID", "Name", "Semester", "Course");
                System.out.printf("+-----------+----------------------+------------+------------+%n");

                // Access the found student (first match in the list)
                Student student = foundStudent.get(0);
                // Get all semesters for the student
                Set<String> semesters = student.getSemesters();

                // Handle case where student has no semesters
                if (semesters.isEmpty()) {
                    // Display student with "None" values for semester and course
                    System.out.printf("| %-9s | %-20s | %-10s | %-10s |%n",
                            student.getId(), student.getName(), "None", "None");
                } else {
                    // Track whether this is the first row (for displaying ID and name only once)
                    boolean firstRow = true;

                    // Iterate through each semester
                    for (String semester : semesters) {
                        // Get courses for the current semester
                        Set<String> courses = student.getCourses(semester);

                        // Handle case where semester has no courses
                        if (courses.isEmpty()) {
                            // Display semester with "None" for course
                            if (firstRow) {
                                // First row includes student ID and name
                                System.out.printf("| %-9s | %-20s | %-10s | %-10s |%n",
                                        student.getId(), student.getName(), semester, "None");
                                // Mark first row as processed
                                firstRow = false;
                            } else {
                                // Subsequent rows leave ID and name cells empty
                                System.out.printf("| %-9s | %-20s | %-10s | %-10s |%n",
                                        "", "", semester, "None");
                            }
                        } else {
                            // Display each course for the current semester
                            for (String course : courses) {
                                if (firstRow) {
                                    // First row includes student ID and name
                                    System.out.printf("| %-9s | %-20s | %-10s | %-10s |%n",
                                            student.getId(), student.getName(), semester, course);
                                    // Mark first row as processed
                                    firstRow = false;
                                } else {
                                    // Subsequent rows leave ID and name cells empty
                                    System.out.printf("| %-9s | %-20s | %-10s | %-10s |%n",
                                            "", "", semester, course);
                                }
                            }
                        }
                    }
                }
                // Print table footer/separator line
                System.out.printf("+-----------+----------------------+------------+------------+%n");

                // Label for outer loop to allow breaking out from nested switch
                OUTER:
                while (true) {
                    // Display options for updating or deleting student
                    System.out.println("What do you want to do:");
                    // Option 1: Update student information
                    System.out.println("1. Update Student's Info.");
                    // Option 2: Delete student from database
                    System.out.println("2. Delete Student.");
                    // Option 0: Return to main menu
                    System.out.println("0. Back to Menu.");

                    // Get user's selection (0-2)
                    int selection = InputValidation.getIntInputLimit(0, 2);
                    // Process selection with switch statement
                    switch (selection) {
                        case 1:
                            // Call method to update student information
                            studentUpdate(foundStudent);
                            // Continue the outer loop after update
                            break;
                        case 2:
                            // Call method to delete student
                            studentDelete(foundStudent);
                            // Exit the update/delete menu after deletion
                            break OUTER;
                        case 0:
                            // Return to main menu
                            break OUTER;
                    }
                }
            }
        } catch (Exception ex) {
            // Display error message if exception occurs during update/delete process
            System.err.println("------Error! " + ex.getMessage());
        }
    }

    /**
     * Updates student information
     *
     * @param student List containing the student to update
     */
    public void studentUpdate(List<Student> student) {
        // Label for outer loop to allow breaking out from nested switch
        OUTER:
        while (true) {
            // Display current student information in table format
            printTable(student);
            // Display available update options
            System.out.println("Select information to update:");
            // Option 1: Update student name
            System.out.println("1. Name");
            // Option 2: Add semesters and courses
            System.out.println("2. Add Semester/Courses");
            // Option 3: Remove semesters or courses
            System.out.println("3. Remove Semester/Courses");
            // Option 0: Return to main menu
            System.out.println("0. Exit to Menu");

            try {
                // Get user's selection (0-3)
                int selection = InputValidation.getIntInputLimit(0, 3);
                // Process selection with switch statement
                switch (selection) {
                    case 1:
                        // Get new name input from user
                        String name = inName();
                        // Update student's name with new value
                        student.get(0).setName(name);
                        // Continue the loop to show updated information
                        break;
                    case 2:
                        // Add new semesters and courses to the student
                        addSemesterAndCourses(student.get(0));
                        // Continue the loop to show updated information
                        break;
                    case 3:
                        // Remove existing semesters or courses from student
                        removeSemesterOrCourses(student.get(0));
                        // Continue the loop to show updated information
                        break;
                    case 0:
                        // Exit the update menu and return to main menu
                        break OUTER;
                }
            } catch (Exception e) {
                // Display error message if exception occurs during update
                System.err.println("------Error! " + e.getMessage());
            }
        }
    }

    /**
     * Removes semester or courses from a student
     *
     * @param student The student to remove semester/courses from
     */
    private void removeSemesterOrCourses(Student student) {
        // Check if the student has any semesters to modify
        if (student.getSemesters().isEmpty()) {
            // Display error message if student has no semesters
            System.err.println("No Semester to modify.");
            // Return to calling method without further processing
            return;
        }

        // Continue until all desired removals are complete or user exits
        while (true) {
            try {
                // Display header for current semesters and courses
                System.out.println("Current Semesters and Courses:");
                // Iterate through each semester to display its courses
                for (String semester : student.getSemesters()) {
                    // Display semester name with newline for better readability
                    System.out.println("\nSemester: " + semester);
                    // Display courses header
                    System.out.println("Courses:");
                    // List each course in the current semester with bullet points
                    for (String course : student.getCourses(semester)) {
                        System.out.println("- " + course);
                    }
                }

                // Prompt user for semester to modify
                System.out.print("\nModifying Semester. ");
                // Get valid semester input from user
                String semester = inSemester();

                // Verify that the specified semester exists for this student
                if (!student.getSemesters().contains(semester)) {
                    // Display error if semester not found and restart the loop
                    System.err.println("------Error! Semester not found.");
                    continue;
                }

                // Display options for removal
                System.out.println("1. Remove entire semester");
                System.out.println("2. Remove specific courses");
                // Get user selection (1-2)
                int selection = InputValidation.getIntInputLimit(1, 2);

                // Process based on user selection
                if (selection == 1) {
                    // Option 1: Remove the entire semester and all its courses
                    student.removeSemester(semester);
                    // Display success message
                    System.out.println("Semester removed successfully.");
                } else {
                    // Option 2: Remove specific courses from the semester
                    removeCoursesFromSemester(student, semester);
                }

                // Check if student has any semesters left after removal
                if (student.getSemesters().isEmpty()) {
                    // Display notification and exit loop if no semesters remain
                    System.out.println("No Semester left to modify.");
                    break;
                }

                // Ask if user wants to remove more semesters or courses
                if (!InputValidation.continueYN("Do you want to remove more semesters/courses?")) {
                    // Exit the loop if user doesn't want to remove more
                    break;
                }
            } catch (Exception e) {
                // Display error message if exception occurs during removal
                System.err.println("------Error! " + e.getMessage());
            }
        }
    }

    /**
     * Removes courses from a specific semester
     *
     * @param student The student to remove courses from
     * @param semester The semester to remove courses from
     */
    private void removeCoursesFromSemester(Student student, String semester) {
        // Continue until all desired course removals are complete or user exits
        while (true) {
            try {
                // Display header with semester name
                System.out.println("\nCurrent courses for semester " + semester + ":");
                // List each course in the semester with bullet points
                for (String course : student.getCourses(semester)) {
                    System.out.println("- " + course);
                }

                // Get course selection from user
                String course = inCourse();

                // Attempt to remove the selected course
                if (student.removeCourse(semester, course)) {
                    // Display success message if course was found and removed
                    System.out.println("Course removed successfully.");
                } else {
                    // Display error if course wasn't found in the semester
                    System.err.println("------Error! Course not found in this semester.");
                }

                // Check if the semester still exists (it's automatically removed when empty)
                if (!student.getSemesters().contains(semester)) {
                    // Display notification that semester was deleted due to having no courses
                    System.out.printf("Semester %s deleted since there is no course left.\n", semester);
                    // Exit the course removal loop
                    break;
                }

                // Ask if user wants to remove another course from this semester
                if (!InputValidation.continueYN("Do you want to remove another course from this semester?")) {
                    // Exit the loop if user doesn't want to remove more courses
                    break;
                }
            } catch (Exception e) {
                // Display error message if exception occurs during course removal
                System.err.println("------Error! " + e.getMessage());
            }
        }
    }

    /**
     * Deletes a student from the database
     *
     * @param student List containing the student to delete
     */
    public void studentDelete(List<Student> student) {
        // Continue until deletion is successful or canceled
        while (true) {
            try {
                // Format confirmation message with student ID
                String msg = String.format("Are you sure you want to delete student %s ?:", student.get(0).getId());
                // Ask for user confirmation (Y/N)
                if (InputValidation.continueYN(msg)) {
                    // Attempt to delete student if user confirms
                    if (dtb.deleteStudent(student.get(0).getId())) {
                        // Display success message if deletion was successful
                        System.out.println("Student Deleted.");
                        // Exit the deletion loop
                        break;
                    }
                    // If deleteStudent returned false, continue the loop to retry
                } else {
                    // Exit the deletion loop if user cancels
                    break;
                }
            } catch (Exception ex) {
                // Display error message if exception occurs during deletion
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * Displays student information in a formatted table
     *
     * @param lst List of students to display
     */
    public void printTable(List<Student> lst) {
        // Check if the list is empty before attempting to display
        if (lst.isEmpty()) {
            // Display error message if no students were found to display
            System.err.println("------Error! No data has been found.");
            // Return to calling method without further processing
            return;
        }

        // Print formatted table header with separator lines and column titles
        System.out.printf("+-----------+----------------------+------------+------------+%n");
        System.out.printf("| %-9s | %-20s | %-10s | %-10s |%n", "ID", "Name", "Semester", "Course");
        System.out.printf("+-----------+----------------------+------------+------------+%n");

        // Iterate through each student in the list
        for (Student student : lst) {
            // Get all semesters for the current student
            Set<String> semesters = student.getSemesters();

            // Handle case where student has no semesters
            if (semesters.isEmpty()) {
                // Display a single row with "None" for semester and course
                System.out.printf("| %-9s | %-20s | %-10s | %-10s |%n",
                        student.getId(), student.getName(), "None", "None");
            } else {
                // Flag to track whether this is the first row for this student
                boolean firstRow = true;

                // Process each semester for this student
                for (String semester : semesters) {
                    // Get all courses for the current semester
                    Set<String> courses = student.getCourses(semester);

                    // Handle case where semester has no courses
                    if (courses.isEmpty()) {
                        // Display the semester with "None" for course
                        if (firstRow) {
                            // First row includes student ID and name
                            System.out.printf("| %-9s | %-20s | %-10s | %-10s |%n",
                                    student.getId(), student.getName(), semester, "None");
                            // Mark first row as processed
                            firstRow = false;
                        } else {
                            // Subsequent rows have empty ID and name cells
                            System.out.printf("| %-9s | %-20s | %-10s | %-10s |%n",
                                    "", "", semester, "None");
                        }
                    } else {
                        // Process each course in the current semester
                        for (String course : courses) {
                            // Format and display the current semester-course combination
                            if (firstRow) {
                                // First row includes student ID and name
                                System.out.printf("| %-9s | %-20s | %-10s | %-10s |%n",
                                        student.getId(), student.getName(), semester, course);
                                // Mark first row as processed
                                firstRow = false;
                            } else {
                                // Subsequent rows have empty ID and name cells
                                System.out.printf("| %-9s | %-20s | %-10s | %-10s |%n",
                                        "", "", semester, course);
                            }
                        }
                    }
                }
            }
            // Print separator line between students for readability
            System.out.printf("+-----------+----------------------+------------+------------+%n");
        }
    }

    /**
     * Displays all students in the database
     */
    public void report() {
        // Check if the database is empty before attempting to display
        if (dtb.dtbIsEmpty()) {
            // Display error message if no students exist in database
            System.err.println("------Error! Database is empty, please add Student first.");
        } else {
            // Get all students from database and display them in a table
            printTable(dtb.report());
        }
    }

    /**
     * Handles sorting students by different criteria
     */
    public void sort() {
        // Check if the database is empty before attempting to sort
        if (dtb.dtbIsEmpty()) {
            // Display error message if no students exist in database
            System.err.println("------Error! Database is empty, please add Student first.");
            // Return to main menu without further processing
            return;
        }

        // Label for outer loop to allow breaking out from nested structures
        OUTER:
        while (true) {
            // Display sort menu header with newline for better readability
            System.out.println("\nSort Options:");
            // Display option 1: sort by student name
            System.out.println("1. Sort by Name");
            // Display option 2: sort by student ID
            System.out.println("2. Sort by ID");
            // Display option 3: sort by semester
            System.out.println("3. Sort by Semester");
            // Display option 4: sort by course
            System.out.println("4. Sort by Course");
            // Display option 0: return to main menu
            System.out.println("0. Back to Menu");
            // Prompt for user input
            System.out.print("Enter sort option (0-4): ");

            try {
                // Get user selection for sort criteria (0-4)
                int sortOption = InputValidation.getIntInputLimit(0, 4);

                // Process selection using switch statement
                switch (sortOption) {
                    case 1:
                        // Sort by student name (case-insensitive)
                        dtb.sort(StudentDTB.SortType.BY_NAME);
                        break;
                    case 2:
                        // Sort by student ID
                        dtb.sort(StudentDTB.SortType.BY_ID);
                        break;
                    case 3:
                        // Sort by semester (chronological order)
                        dtb.sort(StudentDTB.SortType.BY_SEMESTER);
                        break;
                    case 4:
                        // Sort by course name
                        dtb.sort(StudentDTB.SortType.BY_COURSE);
                        break;
                    case 0:
                        // Return to main menu
                        break OUTER;
                }
                // Display sorted students after sorting is complete
                printTable(dtb.report());
            } catch (Exception e) {
                // Display error message if exception occurs during sorting
                System.err.println("------Error! " + e.getMessage());
            }
        }
    }

    /**
     * Quickly adds test students to the database
     */
    public void quickAddStudent() {
        // Check if test students have already been added
        if (testStudentAdded) {
            // Display error message if function has already been used
            System.err.println("You can only use this function once.");
            // Return to main menu without adding students
            return;
        }
        // Set flag to prevent repeated use of this function
        testStudentAdded = true;

        // Initialize counter for student loop
        int i = 1;
        // Loop to create and add 9 test students (indices 1-9)
        for (i = 1; i < 10; i++) {
            // Generate ID with format "CExxxxxx" where x is a digit
            String id = "CE" + String.format("%06d", i);
            // Generate name in format "TS X" where X is a letter (A-I)
            String name = "TS " + Character.toString((char) (i + 65));

            // Create new student object with generated ID and name
            Student student = new Student(id, name);

            // Add test data: Spring semester with Java and C/C++ courses
            student.addCourse("SP" + String.format("%02d", i), "Java");
            student.addCourse("SP" + String.format("%02d", i), "C/C++");
            // Add test data: Fall semester with .NET course
            student.addCourse("FA" + String.format("%02d", i), ".NET");

            // Add the student to the database
            dtb.add(student);
            // Increment counter of students in database
            dtbSize++;
        }
        // Display success message with count of added students
        System.out.printf("%d Test Student have been added.\n", i - 1);
    }
}
