/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce190770_l02;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * StudentDTB (Student Database) class manages the collection of student records.
 * This class provides functionality for adding, finding, updating, deleting,
 * and sorting student records. It also maintains valid courses and semesters.
 *
 * @author PD
 */
public class StudentDTB {
    // List to store all student records
    private ArrayList<Student> studentdtb = new ArrayList<>();
    // Set of valid courses that can be assigned to students
    private final Set<String> courses = new HashSet<>();
    // Set of valid semester prefixes (SP, SU, FA)
    private final Set<String> semesters = new HashSet<>();

    // Static initialization block to set up valid courses and semesters
    {
        // Add valid courses to the set
        courses.add("Java");
        courses.add(".NET");
        courses.add("C/C++");

        // Add valid semester prefixes
        semesters.add("SP");  // Spring
        semesters.add("SU");  // Summer
        semesters.add("FA");  // Fall
    }

    /**
     * Adds a new student to the database
     *
     * @param student The student object to add
     */
    public void add(Student student) {
        // Add the student to the database
        studentdtb.add(student);
    }

    /**
     * Finds students based on a search input and specified criteria
     *
     * @param input The search term to look for
     * @param sortType The type of attribute to search by (ID, Name, Semester, Course)
     * @return List of students matching the search criteria
     */
    public List<Student> findBy(String input, SortType sortType) {
        // Create list to store matching students
        List<Student> res = new ArrayList<>();
        
        // Switch based on search criteria
        switch (sortType) {
            case BY_ID:
                // Search by student ID
                for (Student student : studentdtb) {
                    // If input matches any student in database, add student to result
                    if (matchesInput(student.getId(), input)) {
                        res.add(student);
                    }
                }
                return res;

            case BY_NAME:
                // Search by student name
                for (Student student : studentdtb) {
                    // If input matches any student in database, add student to result
                    if (matchesInput(student.getName(), input)) {
                        res.add(student);
                    }
                }
                return res;

            case BY_SEMESTER:
                // Search by semester
                for (Student student : studentdtb) {
                    // If input matches any student in database, add student to result
                    if (matchesInput(student.getSemester(), input)) {
                        res.add(student);
                    }
                }
                return res;

            case BY_COURSE:
                // Search by course
                for (Student student : studentdtb) {
                    // If input matches any student in database, add student to result
                    if (matchesInput(student.getCourse(), input)) {
                        res.add(student);
                    }
                }
                return res;
        }
        return res;
    }

    /**
     * Deletes a student from the database by their ID
     *
     * @param id The ID of the student to delete
     */
    public void deleteStudent(String id) {
        // Iterate through the database to find and remove the student
        for (Student student : studentdtb) {
            // If input matches any student in database, remove student from database
            if (matchesInput(student.getId(), id)) {
                studentdtb.remove(student);
            }
        }
    }

    /**
     * Checks if a string attribute contains the input string (case-insensitive)
     *
     * @param attr The attribute to check
     * @param input The input string to search for
     * @return true if the attribute contains the input, false otherwise
     */
    public boolean matchesInput(String attr, String input) {
        // Convert both strings to uppercase and check if attr contains input
        return attr.toUpperCase().contains(input.toUpperCase());
    }

    /**
     * Checks if a student ID already exists in the database
     *
     * @param input The ID to check
     * @return true if the ID exists, false otherwise
     */
    public boolean chkIdExist(String input) {
        // Flag to track if ID exists
        boolean exist = false;
        // Check each student in the database
        for (Student student : studentdtb) {
            // If input matches any student, set flag to true then break out of loops
            if (matchesInput(student.getId(), input)) {
                exist = true;
                break;
            }
        }
        // return existence of studentID
        return exist;
    }

    /**
     * Gets the set of valid courses
     *
     * @return Set of valid course names
     */
    public Set<String> getValidCourses() {
        return courses;
    }

    /**
     * Gets the set of valid semester prefixes
     *
     * @return Set of valid semester prefixes
     */
    public Set<String> getValidSemesters() {
        return semesters;
    }

    /**
     * Enumeration of sort types for student records
     */
    public enum SortType {
        BY_ID,      // Sort by student ID
        BY_NAME,    // Sort by student name
        BY_SEMESTER,// Sort by semester
        BY_COURSE   // Sort by course
    }

    /**
     * Sorts the student database by the specified criteria
     *
     * @param sortType The criteria to sort by
     */
    public void sort(SortType sortType) {
        // Call mergeSort with the specified sort type
        mergeSort(studentdtb, sortType);
    }

    /**
     * Default sort method that sorts by name
     */
    public void sort() {
        // Sort by name by default
        sort(SortType.BY_NAME);
    }

    /**
     * Gets all students in the database
     *
     * @return List of all students
     */
    public List<Student> report() {
        return studentdtb;
    }

    /**
     * Checks if the database is empty
     *
     * @return true if the database is empty, false otherwise
     */
    public boolean dtbIsEmpty() {
        return studentdtb.isEmpty();
    }

    /**
     * Merges two sorted arrays into one sorted array based on the specified criteria
     *
     * @param leftArray The left sub-array
     * @param rightArray The right sub-array
     * @param array The original array to store merged result
     * @param sortType The criteria to sort by
     */
    private void merge(List<Student> leftArray, List<Student> rightArray, List<Student> array, SortType sortType) {
        // Get sizes of sub-arrays
        int leftSize = leftArray.size();
        int rightSize = rightArray.size();
        // Initialize indices
        int i = 0;  // Index for merged array
        int l = 0;  // Index for left array
        int r = 0;  // Index for right array

        // Merge while both arrays have elements
        while (l < leftSize && r < rightSize) {
            // Get students to compare from both arrays
            Student leftStudent = leftArray.get(l);
            Student rightStudent = rightArray.get(r);
            // Flag to determine which element should be taken next
            // If true: take from left array, If false: take from right array
            boolean shouldTakeLeft = false;

            // Compare based on sort type and set shouldTakeLeft flag
            switch (sortType) {
                case BY_ID:
                    // Compare student IDs lexicographically
                    // shouldTakeLeft will be true if leftStudent's ID comes before or equals rightStudent's ID
                    shouldTakeLeft = leftStudent.getId().compareTo(rightStudent.getId()) <= 0;
                    break;
                case BY_NAME:
                    // Normalize names by trimming and converting to uppercase for case-insensitive comparison
                    String leftName = leftStudent.getName().trim().toUpperCase();
                    String rightName = rightStudent.getName().trim().toUpperCase();
                    // shouldTakeLeft will be true if leftStudent's name comes before or equals rightStudent's name
                    shouldTakeLeft = leftName.compareTo(rightName) <= 0;
                    break;
                case BY_SEMESTER:
                    // Compare semesters lexicographically
                    // shouldTakeLeft will be true if leftStudent's semester comes before or equals rightStudent's semester
                    shouldTakeLeft = leftStudent.getSemester().compareTo(rightStudent.getSemester()) <= 0;
                    break;
                case BY_COURSE:
                    // Compare courses lexicographically
                    // shouldTakeLeft will be true if leftStudent's course comes before or equals rightStudent's course
                    shouldTakeLeft = leftStudent.getCourse().compareTo(rightStudent.getCourse()) <= 0;
                    break;
            }

            // Based on the comparison result, take the appropriate element
            if (shouldTakeLeft) {
                // If shouldTakeLeft is true, the left element should come first
                array.set(i, leftArray.get(l));
                i++;  // Move to next position in merged array
                l++;  // Move to next element in left array
            } else {
                // If shouldTakeLeft is false, the right element should come first
                array.set(i, rightArray.get(r));
                i++;  // Move to next position in merged array
                r++;  // Move to next element in right array
            }
        }

        // Copy remaining elements from left array if any
        while (l < leftSize) {
            array.set(i, leftArray.get(l));
                // Increase index i of merged array
                i++;
                // Increase index l of left array
                l++;
        }

        // Copy remaining elements from right array if any
        while (r < rightSize) {
            array.set(i, rightArray.get(r));
                 // Increase index i of merged array
                i++;
                // Increase index r of right array
                r++;
        }
    }

    /**
     * Main merge sort method that recursively divides and sorts the array
     *
     * @param array The array to be sorted
     * @param sortType The criteria to sort by
     */
    private void mergeSort(List<Student> array, SortType sortType) {
        // Get array length
        int length = array.size();
        // Base case: if array has 1 or fewer elements, it's already sorted
        if (length <= 1) {
            return;
        }
        
        // Calculate middle point
        int middle = length / 2;
        // Create left and right subarrays
        List<Student> leftArray = new ArrayList<>(array.subList(0, middle));
        List<Student> rightArray = new ArrayList<>(array.subList(middle, length));

        // Recursively sort both halves
        mergeSort(leftArray, sortType);
        mergeSort(rightArray, sortType);
        // Merge the sorted halves
        merge(leftArray, rightArray, array, sortType);
    }
}
