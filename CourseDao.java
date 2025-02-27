package edu.ijse.layered.dao;

import edu.ijse.layered.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDao {

    Course saveCourse(Course course);

    Optional<Course> getCourseById(String id);

    List<Course> getAllCourses();

    Course updateCourse(Course course);

    void deleteCourse(String id);
}