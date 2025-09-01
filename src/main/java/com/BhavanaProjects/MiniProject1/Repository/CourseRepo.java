package com.BhavanaProjects.MiniProject1.Repository;

import com.BhavanaProjects.MiniProject1.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {
}
