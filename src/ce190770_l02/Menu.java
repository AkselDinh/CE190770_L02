/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce190770_l02;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Menu class handles the user interface and interaction for the Student Management System.
 * It provides options for creating, finding, updating, deleting, and sorting student records.
 *
 * @author PD
 */
public class Menu {
    // Instance of StudentDTB to manage student records
    private final StudentDTB dtb = new StudentDTB();
    // Counter to track number of students in database
    private int dtbSize = 0;

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
        // Print menu header
        System.out.println("WELCOME TO STUDENT MANAGEMENT");
        // Print available options
        System.out.println("1. Create");
        System.out.println("2. Find and Sort");
        System.out.println("3. Update/Delete");
        System.out.println("4. Report");
        System.out.println("5. Sort");
        System.out.println("-1. Exit");
    }

    /**
     * Handles user's menu selection and calls appropriate methods
     */
    private void selection() {
        try {
            // Prompt for user selection
            System.out.print("Please select: ");
            // Get user input within valid range
            int selection = InputValidation.getIntInputLimit(-1, 5);
            // Process user selection
            switch (selection) {
                case 1:
                    // Add new student
                    addStudentToDTB();
                    break;
                case 2:
                    // Find and sort students
                    findAndSort();
                    break;
                case 3:
                    // Update or delete student
                    updateAndDelete();
                    break;
                case 4:
                    // Display all students
                    report();
                    break;
                case 5:
                    // Sort students
                    sort();
                    break;
                case 0:
                    // Quick add test students
                    quickAddStudent();
                    break;
                case -1:
                    // Exit program
                    System.exit(0);
            }
        } catch (Exception e) {
            // Display any errors that occur
            System.err.println(e.getMessage());
        }
    }

    /**
     * Handles the process of adding a new student to the database
     */
    private void addStudentToDTB() {
        while (true) {
            try {
                // Check if database has reached size limit
                if (dtbSize == 10) {
                    // Ask user if they want to sort before continuing
                    if (InputValidation.continueYN("Do you want to order now?")) {
                        // Sort the database
                        dtb.sort();
                        System.out.println("Database Sorted.");
                        // Create new student after sorting
                        createNewStudent();
                        break;
                    } else {
                        // Return to main menu
                        break;
                    }
                } else {
                    // Create new student if size limit not reached
                    createNewStudent();
                    break;
                }
            } catch (Exception e) {
                // Display any errors
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Creates a new student by gathering all required information
     */
    private void createNewStudent() {
        try {
            // Get validated student information
            String id = validateID();
            String name = inName();
            String semester = inSemester();
            String course = inCourse();

            // Create and add new student to database
            Student student = new Student(id, name, semester, course);
            dtb.add(student);
            // Increment database size counter
            dtbSize++;
            System.out.println("Student created and added to database.");
        } catch (Exception e) {
            // Display any errors
            System.err.println(e.getMessage());
        }
    }

    /**
     * Validates student ID to ensure it's unique
     *
     * @return validated student ID
     */
    private String validateID() {
        while (true) {
            // Get student ID input
            String id = inID();
            // Check if ID already exists
            if (dtb.chkIdExist(id)) {
                System.err.println("------Error! ID already exist.");
            } else {
                // Return ID if it's unique
                return id;
            }
        }
    }

    /**
     * Gets and validates student ID input
     *
     * @return validated student ID
     */
    private String inID() {
        while (true) {
            try {
                // Prompt for ID input
                System.out.print("Enter Student's ID (e. g. CE123456): ");
                // Get and convert input to uppercase
                String id = InputValidation.getSingleStringNonEmpty().toUpperCase();
                
                // Validate ID format
                if (!id.substring(0, 1).matches("[A-Z]+")) {
                    System.err.println("------Error! ID must start with major code (CExxxxxx).");
                } else if (id.length() != 8) {
                    System.err.println("------Error! ID must be at least 8 characters long (CE123456).");
                } else if (!id.substring(2, 7).matches("[0-9]+")) {
                    System.err.println("------Error! ID must be number after major code (XX123456).");
                } else {
                    // Return valid ID
                    return id;
                }
            } catch (Exception e) {
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
        while (true) {
            try {
                // Prompt for name input
                System.out.print("Enter Student's name: ");
                // Get and validate name (no digits allowed)
                String name = InputValidation.getNoDigitMultiString();
                return name;
            } catch (Exception e) {
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
        while (true) {
            try {
                // Prompt for semester input
                System.out.print("Enter Student's semester (e.g. SP25): ");
                // Get and convert input to uppercase
                String semester = InputValidation.getSingleStringNonEmpty().toUpperCase();
                
                // Validate semester format
                if (semester.length() != 4) {
                    System.err.println("------Error! Semester must be 4 characters long (e.g. SP25, SU24, FA23).");
                } else if (!dtb.getValidSemesters().contains(semester.substring(0, 2)) || 
                         !semester.substring(2, 4).matches("[0-9]+")) {
                    System.err.println("------Error! Semester must starts with seasons code followed by years (e.g. SP25, SU24, FA23).");
                } else {
                    return semester;
                }
            } catch (Exception e) {
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
        while (true) {
            try {
                // Display course options
                System.out.println("Select Student's course: ");
                System.out.println("1. Java");
                System.out.println("2. .NET");
                System.out.println("3. C/C++");
                // Get user selection
                int selection = InputValidation.getIntInputLimit(0, 3);
                // Return selected course
                switch (selection) {
                    case 1:
                        return "Java";
                    case 2:
                        return ".NET";
                    case 3:
                        return "C/C++";
                }
            } catch (Exception e) {
                System.err.println("------Error! " + e.getMessage());
            }
        }
    }

    /**
     * Handles finding and sorting students based on different criteria
     */
    private void findAndSort() {
        // Check if database is empty
        if (dtb.dtbIsEmpty()) {
            System.err.println("------Error! Database is empty, please add Student first.");
            return;
        }

        OUTER:
        while (true) {
            try {
                // Display search options
                System.out.println("Find By...");
                System.out.println("1. ID");
                System.out.println("2. Name");
                System.out.println("3. Semester");
                System.out.println("4. Course");
                System.out.println("5. Back to Menu");

                // Get user selection
                int selection = InputValidation.getIntInputLimit(1, 5);
                switch (selection) {
                    case 1:
                        // Search by ID
                        String id = inID();
                        printTable(dtb.findBy(id, StudentDTB.SortType.BY_ID));
                        break;
                    case 2:
                        // Search by name
                        String name = inName();
                        printTable(dtb.findBy(name, StudentDTB.SortType.BY_NAME));
                        break;
                    case 3:
                        // Search by semester
                        String semester = inSemester();
                        printTable(dtb.findBy(semester, StudentDTB.SortType.BY_SEMESTER));
                        break;
                    case 4:
                        // Search by course
                        String course = inCourse();
                        printTable(dtb.findBy(course, StudentDTB.SortType.BY_COURSE));
                        break;
                    case 5:
                        // Return to main menu
                        break OUTER;
                }
            } catch (Exception e) {
                System.err.println("------Error! " + e.getMessage());
            }
        }
    }

    /**
     * Handles updating or deleting student records
     */
    private void updateAndDelete() {
        // Check if database is empty
        if (dtb.dtbIsEmpty()) {
            System.err.println("------Error! Database is empty, please add Student first.");
            return;
        }

        try {
            // Get student ID to update/delete
            String id = inID();
            List<Student> foundStudent = dtb.findBy(id, StudentDTB.SortType.BY_ID);

            // Check if student exists
            if (foundStudent.equals("")) {
                System.err.println("Student's ID not found.");
            } else {
                // Display current student information
                System.out.println("Student's Current Information:");
                System.out.printf("+ %-8s + %-20s + %-8s + %-6s +\n", "ID", "Name", "Semester", "Course");
                System.out.printf("| %-8s | %-20s | %-8s | %-6s |\n",
                        foundStudent.get(0).getId(), foundStudent.get(0).getName(), 
                        foundStudent.get(0).getSemester(), foundStudent.get(0).getCourse());

                OUTER:
                while (true) {
                    // Display update/delete options
                    System.out.println("What do you want to do:");
                    System.out.println("1. Update");
                    System.out.println("2. Delete");
                    System.out.println("3. Back to Menu");
                    try {
                        // Get user selection
                        int selection = InputValidation.getIntInputLimit(1, 2);
                        switch (selection) {
                            case 1:
                                studentUpdate(foundStudent);
                                break OUTER;
                            case 2:
                                studentDelete(foundStudent);
                                break OUTER;
                            case 3:
                                break OUTER;
                        }
                    } catch (Exception e) {
                        System.err.println("------Error! " + e.getMessage());
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates student information
     *
     * @param student List containing the student to update
     */
    public void studentUpdate(List<Student> student) {
        OUTER:
        while (true) {
            // Display update options
            System.out.println("Select information to update:");
            System.out.printf("%-15s %s\n", "1. Name:", student.get(0).getName());
            System.out.printf("%-15s %s\n", "2. Semester:", student.get(0).getSemester());
            System.out.printf("%-15s %s\n", "3. Course:", student.get(0).getCourse());
            System.out.printf("%-15s\n", "4. Exit to Menu");
            try {
                // Get user selection
                int selection = InputValidation.getIntInputLimit(1, 4);
                switch (selection) {
                    case 1:
                        // Update name
                        String name = inName();
                        student.get(0).setName(name);
                        break;
                    case 2:
                        // Update semester
                        String semester = inSemester();
                        student.get(0).setSemester(semester);
                        break;
                    case 3:
                        // Update course
                        String course = inCourse();
                        student.get(0).setCourse(course);
                        break;
                    case 4:
                        // Return to main menu
                        break OUTER;
                }
            } catch (Exception e) {
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
        // Create confirmation message
        String msg = String.format("Are you sure you want to delete student %s ?:", student.get(0).getId());
        while (true) {
            try {
                // Get user confirmation
                boolean selection = InputValidation.continueYN(msg);
                if (selection) {
                    // Delete student if confirmed
                    dtb.deleteStudent(student.get(0).getId());
                    System.out.println("Student Deleted.");
                    break;
                }
            } catch (Exception ex) {
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
        // Check if list is empty
        if (lst.isEmpty()) {
            System.err.println("------Error! No data has been found.");
            return;
        }
        // Print table header
        System.out.printf("+ %-8s + %-20s + %-8s + %-6s +\n", "ID", "Name", "Semester", "Course");
        // Print each student's information
        for (Student student : lst) {
            System.out.printf("| %-8s | %-20s | %-8s | %-6s |\n",
                    student.getId(), student.getName(), student.getSemester(), student.getCourse());
        }
    }

    /**
     * Displays all students in the database
     */
    public void report() {
        // Check if database is empty
        if (dtb.dtbIsEmpty()) {
            System.err.println("------Error! Database is empty, please add Student first.");
        } else {
            // Display all students
            printTable(dtb.report());
        }
    }

    /**
     * Handles sorting students by different criteria
     */
    public void sort() {
        // Check if database is empty
        if (dtb.dtbIsEmpty()) {
            System.err.println("------Error! Database is empty, please add Student first.");
            return;
        }

        OUTER:
        while (true) {
            // Display sorting options
            System.out.println("\nSort Options:");
            System.out.println("1. Sort by Name");
            System.out.println("2. Sort by ID");
            System.out.println("3. Sort by Semester");
            System.out.println("4. Sort by Course");
            System.out.println("5. Back to Menu");
            System.out.print("Enter sort option (1-5): ");

            try {
                // Get user selection
                int sortOption = InputValidation.getIntInputLimit(1, 5);

                switch (sortOption) {
                    case 1:
                        // Sort by name
                        dtb.sort(StudentDTB.SortType.BY_NAME);
                        break;
                    case 2:
                        // Sort by ID
                        dtb.sort(StudentDTB.SortType.BY_ID);
                        break;
                    case 3:
                        // Sort by semester
                        dtb.sort(StudentDTB.SortType.BY_SEMESTER);
                        break;
                    case 4:
                        // Sort by course
                        dtb.sort(StudentDTB.SortType.BY_COURSE);
                        break;
                    case 5:
                        // Return to main menu
                        break OUTER;
                }
                // Display sorted results
                printTable(dtb.report());
            } catch (Exception e) {
                System.err.println("------Error! " + e.getMessage());
            }
        }
    }

    /**
     * Quickly adds test students to the database
     */
    public void quickAddStudent() {
        int i = 1;
        // Add 9 test students
        for (i = 1; i < 10; i++) {
            // Generate test data
            String id = "CE" + String.format("%06d", i);
            String name = "TS " + Character.toString((char) (i + 65));
            String semester = "SP" + String.format("%02d", i);
            String course = "Java";
            // Create and add student
            Student student = new Student(id, name, semester, course);
            dtb.add(student);
            dtbSize++;
        }
        // Display number of students added
        System.out.printf("%d Test students has been added.\n", i - 1);
    }
}
