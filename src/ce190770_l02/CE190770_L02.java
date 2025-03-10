/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ce190770_l02;

/**
 * Main class for the Student Management System.
 * This class serves as the entry point for the application and initializes
 * the menu-driven interface for managing student records.
 *
 * @author Dinh Cong Phuc - CE190770 - Mar 7, 2025
 */
public class CE190770_L02 {

    /**
     * The main method that starts the application.
     * Creates a new Menu instance and starts the main program loop.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Create a new instance of the Menu class
        Menu menu = new Menu();
        // Start the main program loop
        menu.loop();
    }

}
