package edu.hitwh.userinformationmaintenanceservice.service.impl;

import edu.hitwh.userinformationmaintenanceservice.entity.Log;
import edu.hitwh.userinformationmaintenanceservice.entity.User;
import edu.hitwh.userinformationmaintenanceservice.mapper.UserMapper;
import edu.hitwh.userinformationmaintenanceservice.service.UserService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Boolean register(String username, String password) {
        User user = userMapper.getUserByUsername(username);
        if (user != null) {
            return false;
        }
        user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userMapper.register(user);
    }

    @Override
    public Boolean delete(Integer userId) {
        return userMapper.delete(userId);
    }

    @Override
    public Boolean updateInformation(User user) {
        return userMapper.updateInformation(user);
    }

    @Override
    public InputStream getTemplate() throws FileNotFoundException {
        return new FileInputStream("User-Management-System/UserInformationMaintenanceService/src/main/resources/templates/user_import.xlsx");
    }

    @Override
    public InputStream export() {
        List<User> users = userMapper.getAllUsers();
        try (Workbook workbook = new HSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("用户信息");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("姓名");
            headerRow.createCell(1).setCellValue("用户名");
            headerRow.createCell(2).setCellValue("性别");
            headerRow.createCell(3).setCellValue("电话");
            headerRow.createCell(4).setCellValue("邮箱");

            int rowNumber = 1;
            for (User user : users) {
                Row row = sheet.createRow(rowNumber++);
                if (user.getName() != null) {
                    row.createCell(0).setCellValue(user.getName());
                }
                row.createCell(1).setCellValue(user.getUsername());
                if (user.getGender() != null) {
                    row.createCell(2).setCellValue(user.getGender() == 1 ? "男" : "女");
                }
                if (user.getPhone() != null) {
                    row.createCell(3).setCellValue(user.getPhone());
                }
                if (user.getEmail() != null) {
                    row.createCell(4).setCellValue(user.getEmail());
                }
            }

            for (int i = 0; i <= 4; ++i)
                sheet.autoSizeColumn(i);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean importUser(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName != null && fileName.endsWith(".xlsx")) {
            try {
                DataFormatter formatter = new DataFormatter();
                Workbook workbook = WorkbookFactory.create(file.getInputStream());
                Sheet sheet = workbook.getSheetAt(0);
                int lastRowNum = sheet.getLastRowNum();
                for (int i = 2; i <= lastRowNum; i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) {
                        continue;
                    }
                    if (row.getCell(1) == null) {
                        throw new RuntimeException("必填项不能为空");
                    }

                    User user = new User();
                    user.setName(formatter.formatCellValue(row.getCell(0)));
                    user.setUsername(formatter.formatCellValue(row.getCell(1)));
                    user.setGender(Objects.equals(formatter.formatCellValue(row.getCell(2)), "女") ? 0 : 1);
                    user.setPhone(formatter.formatCellValue(row.getCell(3)));
                    user.setPassword("123456");
                    String email = formatter.formatCellValue(row.getCell(4));
                    if (email == null || isValidEmail(email) || Objects.equals(email, "")) {
                        user.setEmail(email);
                    } else {
                        throw new Exception("邮箱格式不正确");
                    }

                    if (userMapper.getUserByUsername(user.getUsername()) != null)
                        throw new SQLException();
                    userMapper.register(user);
                }
            } catch (Exception e) {
                if (e.getMessage() != null && (e.getMessage().equals("必填项不能为空") || e.getMessage().equals("邮箱格式不正确"))) {
                    throw new RuntimeException(e.getMessage());
                } else if (e instanceof SQLException) {
                    throw new RuntimeException("导入失败，存在重复的用户名");
                } else {
                    throw new RuntimeException("导入失败");
                }
            }
            return true;
        } else {
            throw new RuntimeException("不是有效的 Excel 文件");
        }
    }

    @Override
    public Boolean changePassword(Integer id, String password) {
        return userMapper.changePassword(id, password);
    }

    public static boolean isValidEmail(String email) {
        String EMAIL_REGEX = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
