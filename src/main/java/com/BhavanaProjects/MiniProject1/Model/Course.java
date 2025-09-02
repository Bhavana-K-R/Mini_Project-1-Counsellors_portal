package com.BhavanaProjects.MiniProject1.Model;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer courseId;
   private String courseName;
   @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Enquiry> enquiries;
}
