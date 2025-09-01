package com.BhavanaProjects.MiniProject1.Service;

import com.BhavanaProjects.MiniProject1.DTO.DashboardResponseDto;
import com.BhavanaProjects.MiniProject1.Model.Counsellor;
import com.BhavanaProjects.MiniProject1.Model.Enquiry;
import com.BhavanaProjects.MiniProject1.Repository.CounsellorRepo;
import com.BhavanaProjects.MiniProject1.Repository.EnquiryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class CounsellorServiceImpl implements CounsellorService {
    @Autowired
    CounsellorRepo counsellorRepo;
    @Autowired
    EnquiryRepo enquiryRepo;
@Override
    public Counsellor loginCnslr(String cnslrEmail, String cnslrPwd){
    return counsellorRepo.findByCnslrEmailAndCnslrPwd(cnslrEmail,cnslrPwd);
}
@Override
    public boolean registerCnslr(Counsellor cnslr){
    Counsellor savedCnslr = counsellorRepo.save(cnslr);
    return savedCnslr.getCnslrId()!=null;
}
@Override
    public DashboardResponseDto getDashboardInfo(Integer cnslrId){
    List<Enquiry> enqList=enquiryRepo.findByCounsellorCnslrIdOrderByEnqIdAsc(cnslrId);
    int totalCnt=enqList.size();

    /*int openCnt=enqList.stream()
                          .filter(e->e.getEnqStatus().equals("OPEN"))
                          .collect(Collectors.toList())
                          .size();
    int lostCnt=enqList.stream()
            .filter(e->e.getEnqStatus().equals("LOST"))
            .collect(Collectors.toList())
            .size();
    int enrolledCnt=enqList.stream()
            .filter(e->e.getEnqStatus().equals("ENROLLED"))
            .collect(Collectors.toList())
            .size();*/
//We can also write the above logic simply like below
    Map<String,Long> statusWiseCnt=enqList.stream()
            .collect(Collectors.groupingBy(Enquiry::getEnqStatus,Collectors.counting()));
    int openCnt=statusWiseCnt.getOrDefault("OPEN",0l).intValue();//if value present in map return it to the dashboard or else return 0
    int lostCnt=statusWiseCnt.getOrDefault("LOST",0l).intValue();
    int enrolledCnt=statusWiseCnt.getOrDefault("ENROLLED",0l).intValue();

    //We can also use find by example method as below if we don't want to create our own method in repository
            /*Counsellor cnslr=new Counsellor();
            cnslr.setCnslrId(cnslrId);
            Enquiry enquiry=new Enquiry();
            enquiry.setCounsellor(cnslr);
            List<Enquiry> enquiryList=enquiryRepo.findAll(Example.of(enquiry));*/

    DashboardResponseDto responseDto=DashboardResponseDto.builder()
                                                               .totalEnq(totalCnt)
                                                               .lostEnq(lostCnt)
                                                               .openEnq(openCnt)
                                                               .enrolledEnq(enrolledCnt)
                                                               .build();

      /*//We can also create object using new keyword and set the date as below if we don't want to use builder design pattern @Builder and Builder()
    DashboardResponseDto responseDto=new DashboardResponseDto();
    responseDto.setTotalEnq(totalCnt);
    responseDto.setOpenEnq(openCnt);
    responseDto.setEnrolledEnq(enrolledCnt);
    responseDto.setLostEnq(lostCnt);*/

    return responseDto;
}
}
