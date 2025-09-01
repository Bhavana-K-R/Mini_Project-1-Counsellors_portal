package com.BhavanaProjects.MiniProject1.Service;

import com.BhavanaProjects.MiniProject1.Model.Course;
import com.BhavanaProjects.MiniProject1.Repository.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseRepo courseRepo;

    @Override
   public List<Course> getCourses(){
       return courseRepo.findAll();
   }
}
