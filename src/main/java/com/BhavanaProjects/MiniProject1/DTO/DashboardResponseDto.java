package com.BhavanaProjects.MiniProject1.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponseDto {
    private Integer totalEnq;
    private Integer openEnq;
    private Integer enrolledEnq;
    private Integer lostEnq;
}
