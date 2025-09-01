package com.BhavanaProjects.MiniProject1.Repository;

import com.BhavanaProjects.MiniProject1.Model.Counsellor;
import com.BhavanaProjects.MiniProject1.Model.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounsellorRepo extends JpaRepository<Counsellor, Integer> {
    public Counsellor findByCnslrEmailAndCnslrPwd(String cnslrEmail, String cnslrPwd);
    public Counsellor save(Counsellor cnslr);
    public List<Enquiry> findByCnslrId(Integer cnslrId);
}
