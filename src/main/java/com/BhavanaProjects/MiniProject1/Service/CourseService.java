package com.BhavanaProjects.MiniProject1.Service;

import com.BhavanaProjects.MiniProject1.Model.Course;
import com.BhavanaProjects.MiniProject1.Model.Enquiry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService {

    public List<Course> getCourses();
}
