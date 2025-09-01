package com.BhavanaProjects.MiniProject1.Service;

import com.BhavanaProjects.MiniProject1.DTO.DashboardResponseDto;
import com.BhavanaProjects.MiniProject1.DTO.EnqFilterRequestDto;
import com.BhavanaProjects.MiniProject1.DTO.EnquiryDto;
import com.BhavanaProjects.MiniProject1.Model.Course;
import com.BhavanaProjects.MiniProject1.Model.Enquiry;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface EnquiryService {
    public List<Course> getCourses();
    public List<Enquiry> getAllEnquiries(Integer CounsellorId);
    public Enquiry getEnquiry(Integer enqId);
    public List<Enquiry> getFilteredEnquiry(EnqFilterRequestDto enqFilterRequestDto,  Integer CounsellorId);
    public boolean createEnquiry(EnquiryDto enquiryDto, Integer CounsellorId);
    public boolean updateEnquiry(EnquiryDto enquirDto);
    public boolean removeEnquiry(Integer enquiryId, Integer counsellorId);
}
