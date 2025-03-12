/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce190770_l02;

import java.util.*;

/**
 * L02 - Student Management System
 *
 * Student class represents a student entity in the student management system.
 * This class contains basic information about a student including their ID,
 * name, and a map of semesters to courses.
 *
 * @author Dinh Cong Phuc - CE190770 - Mar 7, 2025
 */
public class Student {

    // Student's unique identifier (e.g., CE123456)
    private String id;
    // Student's full name
    private String name;
    // Map to store semesters and their associated courses
    // Key: semester (e.g., "SP25"), Value: Set of courses for that semester
    private final Map<String, Set<String>> semesterCourses;

    /**
     * Constructor to create a new Student object with all required fields
     *
     * @param id The student's unique identifier
     * @param name The student's full name
     */
    public Student(String id, String name) {
        // Initialize the student ID
        this.id = id;
        // Initialize the student name
        this.name = name;
        // Initialize the semester-courses map
        this.semesterCourses = new HashMap<>();
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
     * Gets all semesters for the student
     *
     * @return Set of all semesters
     */
    public Set<String> getSemesters() {
        // Return set of all semesters
        return new HashSet<>(semesterCourses.keySet());
    }

    /**
     * Gets courses for a specific semester
     *
     * @param semester The semester to get courses for
     * @return Set of courses for the semester, or empty set if semester not found
     */
    public Set<String> getCourses(String semester) {
        // Return courses for the semester, or empty set if semester doesn't exist
        return semesterCourses.getOrDefault(semester, new HashSet<>());
    }

    /**
     * Gets all semester-courses mappings
     *
     * @return Map of all semester-courses mappings
     */
    public Map<String, Set<String>> getAllSemesterCourses() {
        // Return the entire semester-courses map
        return semesterCourses;
    }

    /**
     * Adds a course to a semester
     *
     * @param semester The semester to add the course to
     * @param course The course to add
     * @return true if course was added, false if it was already present
     */
    public boolean addCourse(String semester, String course) {
        // Get or create course set for the semester
        Set<String> courses = semesterCourses.computeIfAbsent(semester, k -> new HashSet<>());
        // Add the course and return whether it was added
        return courses.add(course);
    }

    /**
     * Removes a course from a semester
     *
     * @param semester The semester to remove the course from
     * @param course The course to remove
     * @return true if course was removed, false if it wasn't found
     */
    public boolean removeCourse(String semester, String course) {
        // Get courses for the semester
        Set<String> courses = semesterCourses.get(semester);
        // If semester exists and course is removed, return true
        if (courses != null && courses.remove(course)) {
            // If semester has no more courses, remove the semester
            if (courses.isEmpty()) {
                semesterCourses.remove(semester);
            }
            return true;
        }
        return false;
    }

    /**
     * Removes an entire semester and all its courses
     *
     * @param semester The semester to remove
     * @return true if semester was removed, false if it wasn't found
     */
    public boolean removeSemester(String semester) {
        // Remove semester and return whether it existed
        return semesterCourses.remove(semester) != null;
    }
}
