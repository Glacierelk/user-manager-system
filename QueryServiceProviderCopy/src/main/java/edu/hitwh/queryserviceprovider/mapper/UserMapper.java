package edu.hitwh.queryserviceprovider.mapper;

import edu.hitwh.queryserviceprovider.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 根据id获取用户信息
     * @param id 用户id
     * @return 用户信息
     */
    User getUserInfoById(@Param("id") int id);

    /**
     * 进行用户信息的模糊查询
     * @param name 用户姓名
     * @param username 用户名
     * @param phone 手机号
     * @param offset 偏移量
     * @param pageSize 每页显示的条数
     * @return 用户列表
     */
    List<?> getUserList(@Param("name") String name, @Param("username") String username, @Param("phone") String phone,
                        @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
    Integer getUserCount(@Param("name") String name, @Param("username") String username, @Param("phone") String phone);
}
