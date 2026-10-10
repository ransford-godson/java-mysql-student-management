package org.example;

public class Student {
    private int id;
    private String name;
    private int age;
    private String department;
    public Student(int id, String name, int age, String department) {
        if (id < 1) {
            throw new IllegalArgumentException("ID must be greater than zero.");
        }
        if (age < 1 || age > 100) {
            throw new IllegalArgumentException("Age must be between 1 and 100.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (department == null || department.isBlank()) {
            throw new IllegalArgumentException("Department cannot be empty.");
        }

        this.id = id;
        this.name = name;
        this.age = age;
        this.department = department;
    }

    public Student(String name, int age, String department) {
        if (age < 1 || age > 100) {
            throw new IllegalArgumentException("Age must be between 1 and 100.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (department == null || department.isBlank()) {
            throw new IllegalArgumentException("Department cannot be empty.");
        }

        this.name = name;
        this.age = age;
        this.department = department;
    }
//GETTERS
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String getDepartment() {
        return department;
    }
/* display info*/
public void displayInfo() {
    System.out.printf(
            "%-5d %-17s %-10d %-25s%n",
            id, name, age, department
    );
}

}
