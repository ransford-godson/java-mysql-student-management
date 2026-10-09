package org.example;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/java_practice";
        String username = "root";
        String password = "RANSFORD@123";

        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        try(  Connection connection = DriverManager.getConnection(url, username, password);
        ) {
            StudentManager studentManager = new StudentManager(connection, scanner);
            while (isRunning) {
                /*--------------- MENU ------------*/
                System.out.println("===== STUDENT MANAGEMENT =====");
                System.out.println("1. Add student");
                System.out.println("2. View students");
                System.out.println("3. Update student");
                System.out.println("4. Delete student");
                System.out.println("5. Search student");
                System.out.println("6. Exit");
                /*--------------- Choice ------------*/

                System.out.print("Enter your choice(1-6): ");
                int choice = scanner.nextInt();

                scanner.nextLine(); //clearing the buffer

                // 4. Perform operation
                switch (choice) {
                    case 1 -> studentManager.addStudent();
                    case 2 -> studentManager.viewStudents();
                    case 3 -> studentManager.updateStudentRecord();
                    case 4 -> studentManager.deleteStudentRecord();
                    case 5 -> {
                        System.out.println("1. Search student by ID");
                        System.out.println("2. Search student by name");
                        System.out.println("3. Search student by department");
                        System.out.print("Choose one option:");
                        int option = scanner.nextInt();
                        scanner.nextLine();
                        switch (option){
                            case 1 -> studentManager.searchStudentID();
                            case 2 -> studentManager.searchStudentByName();
                            case 3 -> studentManager.searchStudentByDepartment();
                            default -> System.out.println("Invalid option");
                        }
                    }

                    case 6 -> {
                        System.out.println("Goodbye!");
                        isRunning = false;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        scanner.close();



    }
}