package edu.hitwh.userinformationmaintenanceservice.mapper;

import edu.hitwh.userinformationmaintenanceservice.entity.Resume;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ResumeMapper {
    /**
     * 添加履历
     * @param resume 履历
     * @return 是否添加成功
     */
    @Insert("insert into resume (r_user_id, r_description, r_start_date, r_end_date, r_insert, r_update, r_delete) " +
            "values (#{userId}, #{description}, #{startTime}, #{endTime}, now(), now(), 0)")
    Boolean addResume(Resume resume);

    /**
     * 删除履历
     * @param r_id 履历id
     * @return 是否删除成功
     */
    @Update("update resume set r_delete = 1 where r_id = #{id} and r_delete = 0")
    Boolean deleteResume(Integer r_id);

    /**
     * 更新履历
     * @param resume 履历
     * @return 是否更新成功
     */
    @Update("update resume set r_description = #{description}, r_start_date = #{startTime}, r_end_date = #{endTime}, " +
            "r_update = now() where r_id = #{id} and r_delete = 0")
    Boolean updateResume(Resume resume);

}
