package edu.hitwh.userinformationmaintenanceservice.service;

import edu.hitwh.userinformationmaintenanceservice.entity.Log;
import edu.hitwh.userinformationmaintenanceservice.entity.Pagination;

import java.io.InputStream;
import java.util.List;

public interface LoggingService {
    /**
     * 系统管理员查看日志信息
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 返回在给定时间段中产生的日志
     */
    Pagination queryLogs(String startTime, String endTime, Integer currentPage, Integer pageSize);

    /**
     * 导出给定时间段内的日志
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 日志Excel文件
     */
    InputStream export(String startTime, String endTime);
}
