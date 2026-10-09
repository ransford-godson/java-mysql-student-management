package org.example;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentManager {


        private Connection connection;
        private Scanner scanner;
        private int getValidAge() {

            while (true) {
                try {
                    System.out.print("Enter student age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();

                    if (age < 1 || age > 100) {
                        System.out.println("Age must be between 1 and 100.");
                        continue;
                    }

                    return age;

                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid number.");
                    scanner.nextLine();
                }
            }
        }
        private int getValidId() {
            while (true) {
                try {
                    System.out.print("Enter student ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    if (id < 1) {
                        System.out.println("ID must be greater than 0.");
                        continue;
                    }

                    return id;

                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid ID.");
                    scanner.nextLine();
                }
            }
        }

        public StudentManager(Connection connection , Scanner scanner){
            this.connection=connection;
            this.scanner= scanner;
        }

        public void addStudent(){
            String sql = "INSERT INTO students(name,age,department) VALUES(?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(sql);){

                /*--------------- Accepting Input ------------*/
                System.out.print("Enter student name: ");
                String name = scanner.nextLine();
                int age = getValidAge();
                System.out.print("Enter student department: ");
                String department = scanner.nextLine();

                statement.setString(1, name);
                statement.setInt(2, age);
                statement.setString(3, department);

                int result = statement.executeUpdate();
                System.out.println(result + " student added");

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        public void viewStudents(){
            String sql = "SELECT * FROM students";
            try(Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql)) {

                System.out.printf(
                        "%-5s %-17s %-10s %-25s%n",
                        "ID", "NAME", "AGE", "DEPARTMENT"
                );
                while (result.next()) {

                    int id = result.getInt("id");
                    String name = result.getString("name");
                    int age = result.getInt("age");
                    String department = result.getString("department");

                    System.out.printf(
                            "%-5d %-17s %-10d %-25s%n",
                            id, name, age, department
                    );
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
        public void updateStudentRecord(){
            String sql = "update students set age =? where Id=?";
            try(  PreparedStatement statement = connection.prepareStatement(sql);) {

                int id = getValidId();
                int age = getValidAge();

                statement.setInt(1, age);
                statement.setInt(2, id);

                int result = statement.executeUpdate();
                if (result == 0){
                    System.out.printf("Student with id number %d not found\n" ,id);
                }else {
                    System.out.println("Student record has been updated successfully");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public void deleteStudentRecord(){
            String sql = "DELETE  FROM students WHERE id=?";
            try ( PreparedStatement statement = connection.prepareStatement(sql);){

                int id = getValidId();
                statement.setInt(1, id);
                int result = statement.executeUpdate();
                if (result == 0){
                    System.out.printf("Student with id number %d not found\n" ,id);
                }else {
                    System.out.println("Student record has been deleted successfully");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        public void searchStudentID() {

            String sql = "SELECT * FROM students WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                int id = getValidId();

                statement.setInt(1, id);

                try (ResultSet result = statement.executeQuery()) {

                    if (result.next()) {
                        System.out.println("STUDENT FOUND");
                        System.out.println("ID: " + result.getInt("id"));
                        System.out.println("Name: " + result.getString("name"));
                        System.out.println("Age: " + result.getInt("age"));
                        System.out.println("Department: " + result.getString("department"));
                    } else {
                        System.out.printf("Student with ID %d  not found.%n", id );
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public void searchStudentByName(){
            String sql = "SELECT * FROM students WHERE name LIKE ?";
            try(PreparedStatement statement = connection.prepareStatement(sql);) {
                boolean found = false;

                System.out.print("Enter student name: ");
                String name = scanner.nextLine();
                statement.setString(1,"%" + name + "%");
                try(ResultSet result = statement.executeQuery();) {

                    while(result.next()){
                        System.out.println("SEARCH RESULTS");
                        System.out.println("ID: " + result.getInt("id"));
                        System.out.println("Name: " + result.getString("name"));
                        System.out.println("Age: " + result.getInt("age"));
                        System.out.println("Department: " + result.getString("department"));
                        found=true;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if(!found){
                    System.out.println("No student found");
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        public void searchStudentByDepartment(){
            String sql = "SELECT * FROM students WHERE department LIKE ?";
            try(PreparedStatement statement = connection.prepareStatement(sql);) {
                boolean found = false;

                System.out.print("Enter department name: ");
                String department = scanner.nextLine();

                statement.setString(1,"%" + department + "%");

                try {
                    ResultSet result = statement.executeQuery();
                    while(result.next()){
                        System.out.println("SEARCH RESULTS");
                        System.out.println("ID: " + result.getInt("id"));
                        System.out.println("Name: " + result.getString("name"));
                        System.out.println("Age: " + result.getInt("age"));
                        System.out.println("Department: " + result.getString("department"));
                        found=true;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if(!found){
                    System.out.println("No student found");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


}
