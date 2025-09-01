package com.BhavanaProjects.MiniProject1.DTO;

import com.BhavanaProjects.MiniProject1.Model.Counsellor;
import com.BhavanaProjects.MiniProject1.Model.Course;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
public class EnquiryDto {
    private Integer enqId;
    private String stuName;
    private Long stuPhno;
    private String classMode;
    private String enqStatus;
    private Integer courseId;

}
