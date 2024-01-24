package edu.hitwh.userinformationmaintenanceservice.mapper;

import edu.hitwh.userinformationmaintenanceservice.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    User getUserByUsername(@Param("username") String username);

    /**
     * 用户注册
     * @param user 用户信息
     * @return 是否注册成功
     */
    @Insert("insert into user(u_name, u_username, u_password, u_gender, u_phone, u_email, u_insert, u_update, u_delete, u_type) " +
            "values(#{name}, #{username}, #{password}, #{gender}, #{phone}, #{email}, NOW(), NOW(), 0, 2)")
    Boolean register(User user);

    /**
     * 删除用户
     * @param userId 用户id
     * @return 是否删除成功
     */
    @Insert("update user set u_delete = 1 where u_id = #{userId} and u_delete = 0")
    Boolean delete(Integer userId);

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 是否更新成功
     */
    @Update("update user set u_name = #{name}, u_gender = #{gender}, u_phone = #{phone}, u_email = #{email}, " +
            "u_update = NOW() where u_id = #{id} and u_delete = 0")
    Boolean updateInformation(User user);

    /**
     * 获取所有用户信息
     * @return 所有用户信息
     */
    List<User> getAllUsers();

    /**
     * 修改用户密码
     * @param id 用户ID
     * @param password 用户密码
     * @return 是否更新成功
     */
    @Update("update user set u_password = #{password} where u_id = #{id} and u_delete = 0")
    Boolean changePassword(Integer id, String password);
}
