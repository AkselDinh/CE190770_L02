/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce190770_l02;

/**
 * Student class represents a student entity in the student management system.
 * This class contains basic information about a student including their ID,
 * name, semester, and enrolled courses.
 *
 * @author PD
 */
public class Student {
    // Student's unique identifier (e.g., CE123456)
    private String id;
    // Student's full name
    private String name;
    // Student's current semester (e.g., SP25, FA24)
    private String semester;
    // Student's enrolled course
    private String courses;

    /**
     * Constructor to create a new Student object with all required fields
     *
     * @param id The student's unique identifier
     * @param name The student's full name
     * @param semester The student's current semester
     * @param courses The student's enrolled course
     */
    public Student(String id, String name, String semester, String courses) {
        // Initialize the student ID
        this.id = id;
        // Initialize the student name
        this.name = name;
        // Initialize the student's semester
        this.semester = semester;
        // Initialize the student's course
        this.courses = courses;
    }

    /**
     * Gets the student's ID
     *
     * @return The student's ID as a String
     */
    public String getId() {
        // Return the student's ID
        return id;
    }

    /**
     * Sets the student's ID
     *
     * @param id The new ID to set
     */
    public void setId(String id) {
        // Update the student's ID
        this.id = id;
    }

    /**
     * Gets the student's name
     *
     * @return The student's name as a String
     */
    public String getName() {
        // Return the student's name
        return name;
    }

    /**
     * Sets the student's name
     *
     * @param name The new name to set
     */
    public void setName(String name) {
        // Update the student's name
        this.name = name;
    }

    /**
     * Gets the student's semester
     *
     * @return The student's semester as a String
     */
    public String getSemester() {
        // Return the student's semester
        return semester;
    }

    /**
     * Sets the student's semester
     *
     * @param semester The new semester to set
     */
    public void setSemester(String semester) {
        // Update the student's semester
        this.semester = semester;
    }

    /**
     * Gets the student's enrolled course
     *
     * @return The student's course as a String
     */
    public String getCourse() {
        // Return the student's course
        return courses;
    }

    /**
     * Sets the student's enrolled course
     *
     * @param courses The new course to set
     */
    public void setCourse(String courses) {
        // Update the student's course
        this.courses = courses;
    }
}
