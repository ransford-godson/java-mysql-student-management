package org.example;
import org.example.service.StudentService;
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
        private StudentService studentService;

    public StudentManager(Connection connection, Scanner scanner , StudentService studentService) {
        this.connection = connection;
        this.scanner = scanner;
        this.studentService= studentService;
    }

        public void addStudent(){

                /*--------------- Accepting Input ------------*/
                System.out.print("Enter student name: ");
                String name = scanner.nextLine();
                int age = getValidAge();
                System.out.print("Enter student department: ");
                String department = scanner.nextLine();

            Student student = new Student(name,age,department);

            boolean success = studentService.addStudent(student);

            if (success) {
                System.out.println("Student added successfully.");
            } else {
                System.out.println("Failed to add student.");
            }
        }
        public void viewStudents(){
            List<Student> students = studentService.getAllStudents();

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

            boolean success = studentService.updateStudentAge(id, age);
            if (success) {
                System.out.println("Student record updated successfully.");
            } else {
                System.out.println("Student not found or update failed.");
            }
            }

        public void deleteStudentRecord(){

                int id = getValidId();

                boolean success = studentService.deleteStudent(id);
                if (success){
                    System.out.printf("Student with ID %d record has been deleted\n",id);
                }
                else
                    System.out.println("Student not found");



            }


        public void searchStudentID() {
                int id = getValidId();
                Student student = studentService.getStudentById(id);
                if (student != null){
                   student.displayInfo();
                }
                else
                    System.out.println("Student not found");

        }
        public void searchStudentByName(){

                System.out.print("Enter student name: ");
                String name = scanner.nextLine();
                List<Student> students = studentService.searchStudentsByName(name);
                if (students.isEmpty()){
                    System.out.println("No student not found");
                }
                else {
                    for(Student student: students){
                        student.displayInfo();
                    }
                }

                    }



        public void searchStudentByDepartment(){

                System.out.print("Enter department name: ");
                String department = scanner.nextLine();

                List<Student> students = studentService.searchStudentsByDepartment(department);
                    if (students.isEmpty()){
                        System.out.println("No student not found");
                    }
                    else {
                        for (Student student : students){
                            student.displayInfo();
                        }
                    }



                }


        }
