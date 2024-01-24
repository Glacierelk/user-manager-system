package edu.hitwh.userinformationmaintenanceservice.mapper;

import edu.hitwh.userinformationmaintenanceservice.entity.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LogMapper {
    /**
     * 记录日志
     * @param log 日志
     */
    @Insert("insert into log (l_user_id, l_description, l_create_time) VALUES (#{userId}, #{description}, now())")
    void insertLog(Log log);

    /**
     * 系统管理员查询日志
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 在给定时间段内的日志信息
     */
    List<Log> queryLogs(@Param("st") String startTime,
                        @Param("et") String endTime,
                        @Param("pageSize") Integer pageSize,
                        @Param("offset") Integer offset);
    Integer queryLogsCount(@Param("st") String startTime, @Param("et") String endTime);

    /**
     * 查询一个时间段内的日志, 用于导出
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 日志列表
     */
    List<Log> queryAllLogs(@Param("st") String startTime, @Param("et") String endTime);
}
