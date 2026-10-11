package org.example.dao;

import org.example.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private Connection connection;

    public StudentDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students(name,age,department) VALUES(?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setString(1, student.getName());
            statement.setInt(2, student.getAge());
            statement.setString(3, student.getDepartment());
            int result = statement.executeUpdate();
            if (result != 0) {
                return true;
            } else
                return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;
    }

    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM students";
        List<Student> students = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {
            while (result.next()) {
                Student student = new Student(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getInt("age"),
                        result.getString("department"));
                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public boolean updateStudentAge(int id, int age) {
        String sql = "update students set age =? where Id=?";
        try (PreparedStatement statement =
                     connection.prepareStatement(sql);) {


            statement.setInt(1, age);
            statement.setInt(2, id);

            int result = statement.executeUpdate();
            if (result != 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteStudent(int id) {
        String sql = "DELETE  FROM students WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setInt(1, id);
            int result = statement.executeUpdate();
            if (result != 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Student getStudentById(int id) {

        String sql = "SELECT * FROM students WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setInt(1, id);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    Student student = new Student(
                            result.getInt("id"),
                            result.getString("name"),
                            result.getInt("age"),
                            result.getString("department"));

                    return student;
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Student> searchStudentsByName(String name) {
        String sql = "SELECT * FROM students WHERE name LIKE ?";
        List<Student> students = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql);) {


            statement.setString(1, "%" + name + "%");
            try (ResultSet result = statement.executeQuery();) {

                while (result.next()) {
                    Student student = new Student(
                            result.getInt("id"),
                            result.getString("name"),
                            result.getInt("age"),
                            result.getString("department"));

                    students.add(student);

                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return students;
    }

    public List<Student> searchStudentsByDepartment(String department) {
        String sql = "SELECT * FROM students WHERE department LIKE ?";
        List<Student> students = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setString(1, "%" + department + "%");

            try (ResultSet result = statement.executeQuery();) {

                while (result.next()) {

                    Student student = new Student(
                            result.getInt("id"),
                            result.getString("name"),
                            result.getInt("age"),
                            result.getString("department"));

                    students.add(student);
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();

        }

        return students;
    }
}



