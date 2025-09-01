package com.BhavanaProjects.MiniProject1.Service;

import com.BhavanaProjects.MiniProject1.DTO.DashboardResponseDto;
import com.BhavanaProjects.MiniProject1.Model.Counsellor;
import org.springframework.stereotype.Service;

@Service
public interface CounsellorService {
    public Counsellor loginCnslr(String cnslrEmail, String cnslrPwd);
    public boolean registerCnslr(Counsellor cnslr);
    public DashboardResponseDto getDashboardInfo(Integer cnslrId);


}
