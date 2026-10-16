package org.example.service;
import org.example.Student;
import java.util.List;
import org.example.dao.StudentDAO;
import org.example.Student;

public class StudentService {
    private StudentDAO studentDAO;

    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }
    public boolean addStudent(Student student) {
        return studentDAO.addStudent(student);
    }
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }
    public boolean updateStudentAge(int id, int age) {
        if (id < 1) {
            throw new IllegalArgumentException("ID must be greater than zero.");
        }

        if (age < 1 || age > 100) {
            throw new IllegalArgumentException("Age must be between 1 and 100.");
        }

        return studentDAO.updateStudentAge(id, age);
    }
    public boolean deleteStudent(int id) {
        return studentDAO.deleteStudent(id);
    }

    public Student getStudentById(int id) {
        return studentDAO.getStudentById(id);
    }

    public List<Student> searchStudentsByName(String name) {
        return studentDAO.searchStudentsByName(name);
    }

    public List<Student> searchStudentsByDepartment(String department) {
        return studentDAO.searchStudentsByDepartment(department);
    }
}
