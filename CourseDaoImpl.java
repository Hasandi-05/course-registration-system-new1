/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ijse.layered.dao.custom.impl;

import edu.ijse.layered.dao.CourseDao;
import edu.ijse.layered.entity.Course;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseDaoImpl implements CourseDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Course saveCourse(Course course) {
        entityManager.persist(course);
        return course;
    }

    @Override
    public Optional<Course> getCourseById(String id) {
        Course course = entityManager.find(Course.class, id);
        return Optional.ofNullable(course);
    }

    @Override
    public List<Course> getAllCourses() {
        return entityManager.createQuery("SELECT c FROM Course c", Course.class).getResultList();
    }

    @Override
    @Transactional
    public Course updateCourse(Course course) {
        return entityManager.merge(course);
    }

    @Override
    @Transactional
    public void deleteCourse(String id) {
        Course course = entityManager.find(Course.class, id);
        if (course != null) {
            entityManager.remove(course);
        }
    }
}