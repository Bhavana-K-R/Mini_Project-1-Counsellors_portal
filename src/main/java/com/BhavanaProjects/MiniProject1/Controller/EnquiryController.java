package com.BhavanaProjects.MiniProject1.Controller;
import com.BhavanaProjects.MiniProject1.DTO.EnqFilterRequestDto;
import com.BhavanaProjects.MiniProject1.DTO.EnquiryDto;
import com.BhavanaProjects.MiniProject1.Model.Course;
import com.BhavanaProjects.MiniProject1.Model.Enquiry;
import com.BhavanaProjects.MiniProject1.Service.CourseService;
import com.BhavanaProjects.MiniProject1.Service.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class EnquiryController {

    @Autowired
    private EnquiryService enquiryService;
    @Autowired
    private CourseService courseService;
    @GetMapping("add-enquiry")
    public String loadAddEnquiryForm(Model model){
        model.addAttribute("enqDto", new EnquiryDto());
        model.addAttribute("courses", enquiryService.getCourses());
        return "add-enquiry";
    }
    @PostMapping("/add-enquiry")
    public String addEnquiry(@ModelAttribute("enqDto") EnquiryDto enqDto,
                             Model model,
                             HttpServletRequest req) {

        HttpSession session = req.getSession(false);
        Integer counsellorId = (Integer) session.getAttribute("CID");

        boolean success;
        String redirectUrl;

        if (enqDto.getEnqId() != null) {
            // Existing enquiry → update
            success = enquiryService.updateEnquiry(enqDto);
            redirectUrl = "/view-enquiries"; // after edit, redirect to view-enquiries
            if (success) {
                model.addAttribute("smsg", "Enquiry updated successfully");
            } else {
                model.addAttribute("emsg", "Failed to update enquiry");
            }
        } else {
            // New enquiry → create
            success = enquiryService.createEnquiry(enqDto, counsellorId);
            redirectUrl = "/add-enquiry"; // after add, redirect to add-enquiry itself
            if (success) {
                model.addAttribute("smsg", "Enquiry saved successfully");
            } else {
                model.addAttribute("emsg", "Failed to add enquiry");
            }
        }

        // Repopulate courses and form data
        model.addAttribute("courses", courseService.getCourses());
        model.addAttribute("enqDto", enqDto);

        // Add redirect URL for Thymeleaf auto-redirect
        model.addAttribute("redirectUrl", redirectUrl);

        return "add-enquiry"; // stay on same page
    }
   @GetMapping("view-enquiries")
    public String viewEnquiries(Model model, HttpServletRequest req){
        HttpSession session = req.getSession(false);
        Integer counsellorId=(Integer) session.getAttribute("CID");
       List<Enquiry> enquiryList=enquiryService.getAllEnquiries(counsellorId);

       model.addAttribute("filter",new EnqFilterRequestDto());
       model.addAttribute("courses", enquiryService.getCourses());

       model.addAttribute("enqs",enquiryList);
       return "view-enquiry";
   }
    @PostMapping("filter-enqs")
    public String filterEnquiries(@ModelAttribute("filter") EnqFilterRequestDto filter,Model model, HttpServletRequest req){
        HttpSession session = req.getSession(false);
        Integer counsellorId=(Integer) session.getAttribute("CID");
        List<Enquiry> enquiryList=enquiryService.getFilteredEnquiry(filter,counsellorId);
        model.addAttribute("courses", enquiryService.getCourses());
        model.addAttribute("enqs",enquiryList);
        return "view-enquiry";
    }

    @GetMapping("/edit-enquiry")
    public String editEnquiry(@RequestParam("enqId") Integer enqId, Model model){
        Enquiry enquiry = enquiryService.getEnquiry(enqId);
        EnquiryDto enquiryDto = new EnquiryDto();
        BeanUtils.copyProperties(enquiry, enquiryDto);
        enquiryDto.setCourseId(enquiry.getCourse().getCourseId());
        model.addAttribute("enqDto", enquiryDto);
        model.addAttribute("courses", enquiryService.getCourses());
        return "add-enquiry"; // redirect to add-enquiry page for editing
    }

    @GetMapping("/delete-enquiry/{enqId}")
    public String removeEnquiry(@PathVariable("enqId") Integer enqId, HttpServletRequest req, Model model) {
        HttpSession session = req.getSession(false);
        Integer counsellorId = (Integer) session.getAttribute("CID");
        boolean isDeleted = enquiryService.removeEnquiry(enqId, counsellorId);
        if (isDeleted) {
            model.addAttribute("smsg", "Enquiry deleted successfully");
        } else {
            model.addAttribute("emsg", "Failed to delete enquiry");
        }

        // Reload the list in ascending order
        List<Enquiry> enquiryList = enquiryService.getAllEnquiries(counsellorId);
        model.addAttribute("enqs", enquiryList);

        // Add filter object and courses for Thymeleaf
        model.addAttribute("filter", new EnqFilterRequestDto());
        model.addAttribute("courses", enquiryService.getCourses());

        return "view-enquiry";
    }
}
