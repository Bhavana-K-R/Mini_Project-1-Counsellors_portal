package com.BhavanaProjects.MiniProject1.Repository;


import com.BhavanaProjects.MiniProject1.Model.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnquiryRepo extends JpaRepository<Enquiry, Integer> {
    //public Enquiry saveEnquiry(Enquiry enquiry);
    List<Enquiry> findByCounsellorCnslrIdOrderByEnqIdAsc(Integer counsellorCnslrId);
}
