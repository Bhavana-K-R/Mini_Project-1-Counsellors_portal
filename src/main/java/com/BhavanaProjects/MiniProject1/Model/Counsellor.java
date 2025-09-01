package com.BhavanaProjects.MiniProject1.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Counsellor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cnslrId ;
    private String cnslrName;
    @Column(unique = true, nullable = false)
    private String cnslrEmail;
    @Column(nullable = false)
    private String cnslrPwd;
    @Column(unique = true, nullable = true)
    private Long cnslrPhno;
    @CreationTimestamp
    private LocalDateTime cnslrCreatedAt;
    @UpdateTimestamp
    private LocalDateTime cnslrUpdatedAt;
    @OneToMany(mappedBy = "counsellor")
    private List<Enquiry> enquiries;

}
