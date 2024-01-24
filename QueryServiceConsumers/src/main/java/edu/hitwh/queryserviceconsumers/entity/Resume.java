package edu.hitwh.queryserviceconsumers.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resume {
    private Integer id;
    private Integer userId;
    private String description;
    private String startTime;
    private String endTime;
    private String createTime;
    private String updateTime;
    // 虚拟删除，0表示可用，1表示已删除
    private String deleteStatus;
}
