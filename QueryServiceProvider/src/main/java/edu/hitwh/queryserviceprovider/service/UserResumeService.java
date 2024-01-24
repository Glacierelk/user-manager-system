package edu.hitwh.queryserviceprovider.service;

import edu.hitwh.queryserviceprovider.entity.Pagination;
import edu.hitwh.queryserviceprovider.entity.Resume;

import java.util.List;

public interface UserResumeService {
    /**
     * 根据用户id获取用户的简历
     * @param userId 用户id
     * @return 用户的简历列表
     */
    List<Resume> getResumesByUserId(Integer userId);

    /**
     * 查询用户简历列表
     * @param userId 用户ID
     * @param pageSize 页面大小
     * @param currentPage 当前页面
     * @return 分页
     */
    Pagination getResumesList(Integer userId, Integer pageSize, Integer currentPage);
}
