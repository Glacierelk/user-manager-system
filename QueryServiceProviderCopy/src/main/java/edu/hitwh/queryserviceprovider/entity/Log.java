package edu.hitwh.queryserviceprovider.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    private Integer id;
    private Integer userId;
    private String description;
    private String createTime;
}
