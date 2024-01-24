package edu.hitwh.userinformationmaintenanceservice.service.impl;

import edu.hitwh.userinformationmaintenanceservice.entity.Resume;
import edu.hitwh.userinformationmaintenanceservice.mapper.ResumeMapper;
import edu.hitwh.userinformationmaintenanceservice.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResumeServiceImpl implements ResumeService {
    private final ResumeMapper resumeMapper;

    @Autowired
    public ResumeServiceImpl(ResumeMapper resumeMapper) {
        this.resumeMapper = resumeMapper;
    }

    @Override
    public Boolean addResume(Resume resume) {
        return resumeMapper.addResume(resume);
    }

    @Override
    public Boolean updateResume(Resume resume) {
        return resumeMapper.updateResume(resume);
    }

    @Override
    public Boolean deleteResume(Integer resumeId) {
        return resumeMapper.deleteResume(resumeId);
    }
}
