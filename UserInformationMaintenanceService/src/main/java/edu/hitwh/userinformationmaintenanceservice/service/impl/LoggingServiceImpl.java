package edu.hitwh.userinformationmaintenanceservice.service.impl;

import edu.hitwh.userinformationmaintenanceservice.entity.Log;
import edu.hitwh.userinformationmaintenanceservice.entity.Pagination;
import edu.hitwh.userinformationmaintenanceservice.mapper.LogMapper;
import edu.hitwh.userinformationmaintenanceservice.service.LoggingService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Service
public class LoggingServiceImpl implements LoggingService {
    private final LogMapper logMapper;

    @Autowired
    public LoggingServiceImpl(LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    @Override
    public Pagination queryLogs(String startTime, String endTime, Integer currentPage, Integer pageSize) {
        if (Objects.equals(startTime, "null"))
            startTime = null;
        if (Objects.equals(endTime, "null"))
            endTime = null;
        Pagination pagination = new Pagination();
        pagination.setTotalCount(logMapper.queryLogsCount(startTime, endTime));
        pagination.setCurrentPage(currentPage);
        pagination.setPageSize(pageSize);
        pagination.setList(logMapper.queryLogs(startTime, endTime, pageSize, (currentPage - 1) * pageSize));
        return pagination;
    }

    @Override
    public InputStream export(String startTime, String endTime) {
        if (Objects.equals(startTime, "null"))
            startTime = null;
        if (Objects.equals(endTime, "null"))
            endTime = null;
        List<Log> logs = logMapper.queryAllLogs(startTime, endTime);
        try (Workbook workbook = new HSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("日志");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("日志ID");
            headerRow.createCell(1).setCellValue("用户ID");
            headerRow.createCell(2).setCellValue("日志描述");
            headerRow.createCell(3).setCellValue("记录时间");

            int rowNumber = 1;
            for (Log log : logs) {
                Row row = sheet.createRow(rowNumber++);
                row.createCell(0).setCellValue(log.getId());
                if (log.getUserId() != null)
                    row.createCell(1).setCellValue(log.getUserId());
                if (log.getDescription() != null)
                    row.createCell(2).setCellValue(log.getDescription());
                row.createCell(3).setCellValue(log.getCreateTime());
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            return null;
        }
    }
}
