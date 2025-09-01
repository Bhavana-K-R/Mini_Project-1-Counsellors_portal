package com.BhavanaProjects.MiniProject1.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Enquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer enqId;
    private String stuName;
    private Long stuPhno;
    private String classMode;
    private String enqStatus;
    @CreationTimestamp
    private LocalDateTime enqCreatedAt;
    @UpdateTimestamp
    private LocalDateTime enqUpdatedAt;
    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;
    @ManyToOne
    @JoinColumn(name="cnslr_id")
    private Counsellor counsellor;
}
