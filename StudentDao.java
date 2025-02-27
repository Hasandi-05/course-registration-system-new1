package edu.ijse.layered.dao;

import edu.ijse.layered.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDao {

    Student saveStudent(Student student);

    Optional<Student> getStudentById(String id);

    List<Student> getAllStudents();

    Student updateStudent(Student student);

    void deleteStudent(String id);
}