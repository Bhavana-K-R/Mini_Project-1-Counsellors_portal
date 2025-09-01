package com.BhavanaProjects.MiniProject1.Service;

import com.BhavanaProjects.MiniProject1.DTO.EnqFilterRequestDto;
import com.BhavanaProjects.MiniProject1.DTO.EnquiryDto;
import com.BhavanaProjects.MiniProject1.Model.Counsellor;
import com.BhavanaProjects.MiniProject1.Model.Course;
import com.BhavanaProjects.MiniProject1.Model.Enquiry;
import com.BhavanaProjects.MiniProject1.Repository.CounsellorRepo;
import com.BhavanaProjects.MiniProject1.Repository.CourseRepo;
import com.BhavanaProjects.MiniProject1.Repository.EnquiryRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnquiryServiceImpl implements EnquiryService{
    @Autowired
    EnquiryRepo enquiryRepo;
    @Autowired
    CourseRepo courseRepo;
    @Autowired
    private CounsellorRepo counsellorRepo;

    @Override
    public List<Enquiry> getAllEnquiries(Integer counsellorId){
        return enquiryRepo.findByCounsellorCnslrIdOrderByEnqIdAsc(counsellorId);
}
@Override
    public Enquiry getEnquiry(Integer enqId){
    return enquiryRepo.findById(enqId).orElseThrow();
}
@Override
    public List<Enquiry> getFilteredEnquiry(EnqFilterRequestDto enqFilterRequestDto, Integer counsellorId){
        //Query by Example implementation (Query by example for Dynamic Query preparation)
    Counsellor counsellor=counsellorRepo.findById(counsellorId).orElseThrow();//when foreign key has to be inserted ,fetch it in this way and then insert this object into the other where foreign key is required
    Enquiry enq=new Enquiry();
    enq.setCounsellor(counsellor);
    if(enqFilterRequestDto.getClassMode()!=null && !"".equals(enqFilterRequestDto.getClassMode())){
        enq.setClassMode(enqFilterRequestDto.getClassMode());
    }
    if(enqFilterRequestDto.getCourseId()!=null && enqFilterRequestDto.getCourseId()>0){
        Course course=courseRepo.findById(enqFilterRequestDto.getCourseId()).orElseThrow();
        enq.setCourse(course);
    }
    if(enqFilterRequestDto.getEnqStatus()!=null && !"".equals(enqFilterRequestDto.getEnqStatus())){
        enq.setEnqStatus(enqFilterRequestDto.getEnqStatus());
    }
    return enquiryRepo.findAll(Example.of(enq));
}
@Override
    public boolean createEnquiry(EnquiryDto enquiryDto, Integer CounsellorId){
    Counsellor cnslr = counsellorRepo.findById(CounsellorId).orElseThrow();
    Course course=courseRepo.findById(enquiryDto.getCourseId()).orElseThrow();
    Enquiry enquiry=new Enquiry();
    BeanUtils.copyProperties(enquiryDto,enquiry);//Copy dto object data to enquiry object
    enquiry.setCounsellor(cnslr);//Association mapping
    enquiry.setCourse(course);//Association mapping
    Enquiry savedEnq=enquiryRepo.save(enquiry);
    return savedEnq.getEnqId()!=null;
}
@Override
    public boolean updateEnquiry(EnquiryDto enquiryDto){
    Optional<Enquiry> byId=enquiryRepo.findById(enquiryDto.getEnqId());
    if(byId.isPresent()){
        Enquiry enquiry=byId.get();
        BeanUtils.copyProperties(enquiryDto, enquiry, "enqId", "counsellor", "enqCreatedAt", "enqUpdatedAt");
        // Update course association
        if(enquiryDto.getCourseId() != null){
            Course course = courseRepo.findById(enquiryDto.getCourseId()).orElseThrow();
            enquiry.setCourse(course);
        }
        enquiryRepo.save(enquiry);
        return true;
    }
    return false;
}
    @Override
    public List<Course> getCourses(){
        return courseRepo.findAll();
    }

    @Override
    public boolean removeEnquiry(Integer enquiryId, Integer counsellorId){
        Optional<Enquiry> opt = enquiryRepo.findById(enquiryId);

        if (opt.isPresent()) {
            Enquiry enquiry = opt.get();
            // ensure that this enquiry belongs to the counsellor
            if (enquiry.getCounsellor().getCnslrId().equals(counsellorId)) {
                enquiryRepo.delete(enquiry);
                return true;
            }
        }
        return false;
    }
}
