package edu.ijse.layered.dao.impl;

import edu.ijse.layered.dao.StudentDao;
import edu.ijse.layered.entity.Student;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentDaoImpl implements StudentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Student saveStudent(Student student) {
        entityManager.persist(student);
        return student;
    }

    @Override
    public Optional<Student> getStudentById(String id) {
        Student student = entityManager.find(Student.class, id);
        return Optional.ofNullable(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return entityManager.createQuery("SELECT s FROM Student s", Student.class).getResultList();
    }

    @Override
    @Transactional
    public Student updateStudent(Student student) {
        return entityManager.merge(student);
    }

    @Override
    @Transactional
    public void deleteStudent(String id) {
        Student student = entityManager.find(Student.class, id);
        if (student != null) {
            entityManager.remove(student);
        }
    }
}