package org.example;
import org.example.dao.StudentDAO;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.List;
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
        private StudentDAO studentDAO;

    public StudentManager(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
        this.studentDAO = new StudentDAO(connection);
    }

        public void addStudent(){

                /*--------------- Accepting Input ------------*/
                System.out.print("Enter student name: ");
                String name = scanner.nextLine();
                int age = getValidAge();
                System.out.print("Enter student department: ");
                String department = scanner.nextLine();

            Student student = new Student(name,age,department);

            boolean success = studentDAO.addStudent(student);

            if (success) {
                System.out.println("Student added successfully.");
            } else {
                System.out.println("Failed to add student.");
            }
        }
        public void viewStudents(){
            List<Student> students = studentDAO.getAllStudents();

                System.out.printf(
                        "%-5s %-17s %-10s %-25s%n",
                        "ID", "NAME", "AGE", "DEPARTMENT"
                );

                for ( Student student: students) {

                    student.displayInfo();
                }

        }
        public void updateStudentRecord(){

                int id = getValidId();
                int age = getValidAge();

            boolean success = studentDAO.updateStudentAge(id, age);
            if (success) {
                System.out.println("Student record updated successfully.");
            } else {
                System.out.println("Student not found or update failed.");
            }
            }

        public void deleteStudentRecord(){

                int id = getValidId();

                boolean success = studentDAO.deleteStudent(id);
                if (success){
                    System.out.printf("Student with ID %d record has been deleted\n",id);
                }
                else
                    System.out.println("Student not found");



            }


        public void searchStudentID() {

            String sql = "SELECT * FROM students WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                int id = getValidId();

                statement.setInt(1, id);

                try (ResultSet result = statement.executeQuery()) {

                    if (result.next()) {

                        Student student = new Student(
                                result.getInt("id"),
                                result.getString("name"),
                                result.getInt("age"),
                                result.getString("department"));

                        student.displayInfo();
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
                        Student student = new Student(
                                result.getInt("id"),
                                result.getString("name"),
                                result.getInt("age"),
                                result.getString("department"));

                        student.displayInfo();
                        found=true;
                    }
                }

                if(!found){
                    System.out.println("No student found");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public void searchStudentByDepartment(){
            String sql = "SELECT * FROM students WHERE department LIKE ?";
            try(PreparedStatement statement = connection.prepareStatement(sql);) {
                boolean found = false;

                System.out.print("Enter department name: ");
                String department = scanner.nextLine();

                statement.setString(1,"%" + department + "%");

                try(ResultSet result = statement.executeQuery();) {

                    while(result.next()){

                        Student student = new Student(
                                result.getInt("id"),
                                result.getString("name"),
                                result.getInt("age"),
                                result.getString("department"));
                        student.displayInfo();

                        found=true;
                    }
                }

                if(!found){
                    System.out.println("No student found");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


}
