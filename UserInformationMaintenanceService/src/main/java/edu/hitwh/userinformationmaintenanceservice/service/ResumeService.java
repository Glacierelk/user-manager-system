package edu.hitwh.userinformationmaintenanceservice.service;

import edu.hitwh.userinformationmaintenanceservice.entity.Resume;

public interface ResumeService {
    /**
     * 添加简历
     * @param resume 简历
     * @return 是否添加成功
     */
    Boolean addResume(Resume resume);

    /**
     * 更新简历
     * @param resume 简历
     * @return 是否更新成功
     */
    Boolean updateResume(Resume resume);

    /**
     * 删除简历
     * @param resumeId 简历ID
     * @return 是否删除成功
     */
    Boolean deleteResume(Integer resumeId);
}
