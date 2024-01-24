package edu.hitwh.userinformationmaintenanceservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {
    private Integer totalCount;
    private Integer currentPage;
    private Integer pageSize;
    List<?> list;
}
