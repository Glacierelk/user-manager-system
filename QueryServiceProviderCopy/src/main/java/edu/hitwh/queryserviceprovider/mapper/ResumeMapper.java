package edu.hitwh.queryserviceprovider.mapper;

import edu.hitwh.queryserviceprovider.entity.Resume;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface ResumeMapper {
    /**
     * 根据用户id获取简历
     * @param userId 用户id
     * @return 简历
     */
    List<Resume> getResumesByUserId(@Param("userId") int userId);

    /**
     * 根据用户id获取简历列表
     * @param userId 用户id
     * @param pageSize 页面大小
     * @param offset 偏移量
     * @return 简历列表
     */
    List<Resume> getResumesListByUserId(@Param("userId") Integer userId,
                                        @Param("pageSize") Integer pageSize,
                                        @Param("offset") Integer offset);
    @Select("SELECT COUNT(*) FROM resume WHERE r_user_id = #{userId} AND r_delete = 0")
    Integer getResumesListCount(Integer userId);
}
