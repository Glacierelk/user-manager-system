package edu.hitwh.loginservice.mapper;

import edu.hitwh.loginservice.entity.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {
    /**
     * 记录日志
     * @param log 日志
     */
    @Insert("insert into log (l_user_id, l_description, l_create_time) VALUES (#{userId}, #{description}, now())")
    void insertLog(Log log);

}
