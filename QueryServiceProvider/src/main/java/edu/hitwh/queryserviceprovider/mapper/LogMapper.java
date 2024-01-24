package edu.hitwh.queryserviceprovider.mapper;

import edu.hitwh.queryserviceprovider.entity.Log;
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

}
