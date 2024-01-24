package edu.hitwh.queryserviceprovider.service.impl;

import edu.hitwh.queryserviceprovider.entity.Pagination;
import edu.hitwh.queryserviceprovider.entity.Resume;
import edu.hitwh.queryserviceprovider.mapper.ResumeMapper;
import edu.hitwh.queryserviceprovider.service.UserResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserResumeServiceImpl implements UserResumeService {
    private final ResumeMapper resumeMapper;

    @Autowired
    public UserResumeServiceImpl(ResumeMapper resumeMapper) {
        this.resumeMapper = resumeMapper;
    }

    @Override
    public List<Resume> getResumesByUserId(Integer userId) {
        return resumeMapper.getResumesByUserId(userId);
    }

    @Override
    public Pagination getResumesList(Integer userId, Integer pageSize, Integer currentPage) {
        Pagination pagination = new Pagination();
        pagination.setList(resumeMapper.getResumesListByUserId(userId, pageSize, (currentPage - 1) * pageSize));
        pagination.setTotalCount(resumeMapper.getResumesListCount(userId));
        pagination.setCurrentPage(currentPage);
        pagination.setPageSize(pageSize);
        return pagination;
    }
}
