package com.university.lele.account.service.impl;

import com.university.lele.account.entity.CreateASchool;
import com.university.lele.account.entity.Studentinfo;
import com.university.lele.account.entity.Teacherinfo;
import com.university.lele.account.model.*;
import com.university.lele.account.repositity.CreateASchoolRepository;
import com.university.lele.account.repositity.StudentinfoRepository;
import com.university.lele.account.repositity.TeacherinfoRepository;
import com.university.lele.account.service.SchoolService;
import com.university.lele.account.service.StudentService;
import com.university.lele.enums.Code;
import com.university.lele.global.Result;
import com.university.lele.utils.ExcelUtil;
import com.university.lele.utils.Utils;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentinfoRepository studentinfoRepository;
    @Autowired
    TeacherinfoRepository teacherinfoRepository;
    @Autowired
    CreateASchoolRepository createASchoolRepository;

    @Autowired
    SchoolService schoolService;
    @Override
    public Result singAddStudentInfo(StudentModel studentModel) {
        //添加学生信息
        Studentinfo studentinfo=new Studentinfo();
        Studentinfo dbStudentinfo = studentinfoRepository.queryStudentInfoByUsername(studentModel.getUsername());
        if (dbStudentinfo!=null){
           return Result.error(Code.STUDENT_ADD_NO_EMPTY_ERR,"帐号已存在！");
        }else {
            studentinfo.setId(Utils.getUUID());
            studentinfo.setUsername(studentModel.getUsername());
            studentinfo.setName(studentModel.getName());
            studentinfo.setPassword(studentModel.getUsername());
            studentinfo.setGrade(studentModel.getGrade());
            if (studentModel.isGroupType()){
                studentinfo.setGroupType(1);
            }else {
                studentinfo.setGroupType(0);
            }
            studentinfo.setPhone(studentModel.getPhone());
            studentinfo.setGroupName(studentModel.getGroupName());
            if (studentModel.getEmail()==null){
                studentinfo.setEmail("default@gmail.com");
            }else {
                studentinfo.setEmail(studentModel.getEmail());
            }
            Teacherinfo teacherinfo = teacherinfoRepository.findTeacherByTeacherId(studentModel.getTeacherId());
            String schoolId = teacherinfo.getSchoolId();
            CreateASchool createSchool = createASchoolRepository.findSchoolInfoBySchoolId(schoolId);
            studentinfo.setSchoolId(schoolId);
            studentinfo.setSchoolName(createSchool.getName());
            studentinfo.setTeacherName(teacherinfo.getName());
            studentinfo.setTeacherId(studentModel.getTeacherId());
            studentinfo.setUserType(3);
            studentinfoRepository.save(studentinfo);
            return Result.success("添加成功",studentinfo);
        }


    }

    @Override
    public Result studentLogin(LoginModel loginModel) {
        Studentinfo studentinfo=studentinfoRepository.queryStudentInfoByUsername(loginModel.getUsername());
        if (studentinfo!=null){
            if (loginModel.getPassword().equals(studentinfo.getPassword())){

                return  Result.success("登录成功",studentinfo);
            }else {
                return Result.error(Code.STUDENT_LOGIN_PASSWORD_ERR,"登录密码错误！");
            }
        }else{
            return  Result.error(Code.STUDENT_LOGIN_USERNAME_ERR,"账户名或密码错误！请检查后重新登录");
        }
    }



    @Override
    public Result allRegisterStudent(UserModel user) throws Exception {
        // 解析Excel数据
        Result r = readDataFromExcelToList(user.getFile());

        List list = (List) r.getData();
        List<StudentModel> items = list;

        if (items == null || items.size() <= 0) {
            return Result.error(Code.READ_EXCEL_NODATA,"没有数据！！！");
        }
        List<StudentModel> studentModels=new ArrayList<>();
        List<Studentinfo> studentinfos=new ArrayList<>();
        for (StudentModel item : items) {
            // 保存数据到数据库
            //查询如果数据库又该数据就不入库，如果没有就入库
            Studentinfo dbStudentinfo = studentinfoRepository.queryStudentInfoByUsername(item.getUsername());
            if (dbStudentinfo==null){
                Studentinfo studentinfo=new Studentinfo();
                studentinfo.setId(Utils.getUUID());
                studentinfo.setUsername(item.getUsername());
                studentinfo.setName(item.getName());
                studentinfo.setPassword(item.getUsername());
                studentinfo.setGrade(item.getGrade());
                if (item.isGroupType()){
                    studentinfo.setGroupType(1);
                }else {
                    studentinfo.setGroupType(0);
                }
                studentinfo.setGroupName(item.getGroupName());
                studentinfo.setPhone(item.getPhone());
                studentinfo.setEmail(item.getEmail());
                studentinfo.setTeacherId(user.getId());
                Teacherinfo teacher = teacherinfoRepository.findTeacherByTeacherId(user.getId());
                studentinfo.setTeacherName(teacher.getName());
                studentinfo.setUserType(3);
                studentinfo.setSchoolId(teacher.getSchoolId());
                CreateASchool info = createASchoolRepository.findSchoolInfoBySchoolId(teacher.getSchoolId());
                studentinfo.setSchoolName(info.getName());
                studentinfoRepository.save(studentinfo);
                studentinfos.add(studentinfo);
            }else {
                studentModels.add(item);
            }
        }
        if (studentModels.size()>0){
            return Result.success("部分帐号注册成功！以下帐号存在重复！",studentModels);
        }else {

            return Result.success("批量注册成功！","总共："+studentinfos.size()+"条数据");
        }
    }
    @Override
    public Result queryStudentInfoByStudentId(String studentId) {
        Studentinfo studentinfo = studentinfoRepository.queryStudentinfoByStudent_id(studentId);
        return studentinfo!=null?Result.success("查询成功",studentinfo):
                Result.error(Code.STUDENT_QUERY_ERR,"查询失败");
    }

    @Override
    public Result deleteStudentByStudent_id(String studentId) {
        if (studentinfoRepository.queryStudentinfoByStudent_id(studentId)!=null){
            Studentinfo studentinfo = studentinfoRepository.queryStudentinfoByStudent_id(studentId);

            studentinfoRepository.delete(studentinfo);
            return Result.success("删除成功");
        }else {
            return Result.error(Code.STUDENT_DELETE_ERR,"删除失败！");
        }
    }

    @Override
    public Result update(StudentModel studentModel) {

        Studentinfo studentinfo = studentinfoRepository.queryStudentinfoByStudent_id(studentModel.getId());
        if (studentinfo!=null){
            studentinfo.setUsername(studentModel.getUsername());
            studentinfo.setName(studentModel.getName());
            studentinfo.setGrade(studentModel.getGrade());
            if (studentModel.isGroupType()){
                studentinfo.setGroupType(1);
            }else {
                studentinfo.setGroupType(0);
            }
            studentinfo.setGroupName(studentModel.getGroupName());
            studentinfo.setPhone(studentModel.getPhone());
            Teacherinfo teacherinfo = teacherinfoRepository.findTeacherByTeacherId(studentModel.getTeacherId());
            studentinfo.setTeacherName(teacherinfo.getName());
            if (studentModel.getEmail()==null){
                studentinfo.setEmail("default@gmail.com");
            }else {
                studentinfo.setEmail(studentModel.getEmail());
            }
            studentinfo.setTeacherId(studentModel.getTeacherId());
            studentinfoRepository.save(studentinfo);
            return Result.success("更新数据成功",studentinfo);
        }else {
            return Result.error(Code.STUDENT_UPDATE_ERR,"更新失败！");
        }
    }

    @Override
    public Result queryGroupInfoByGroupName(int groupName) {
        List<Studentinfo> studentInfos=studentinfoRepository.queryStudentInfoByGroup_Id(groupName);
        if (studentInfos.size()>0){
            return Result.success("查询成功",studentInfos);
        }else {

            return Result.success("查询失败,无小组成员");
        }
    }





    @Override
    public Result reSettingPwd(SettingUserModel passWordModel) {
        Studentinfo studentinfo = studentinfoRepository.queryStudentInfoByUsername(passWordModel.getId());
        if (studentinfo!=null){
            studentinfo.setPassword(passWordModel.getPassword());
            studentinfoRepository.save(studentinfo);
            return Result.success("重置密码成功！");
        }else {
            return Result.error(Code.STUDENT_PASSWORD_NO,"设置失败，帐号不存在！");
        }
    }

    @Override
    public Result setPhone(UserModel userModel) {
        Studentinfo studentinfo = studentinfoRepository.queryStudentinfoByStudent_id(userModel.getId());
        if (studentinfo!=null){
            studentinfo.setPhone(userModel.getPhone());
            Studentinfo save = studentinfoRepository.save(studentinfo);
            return Result.success("重置手机号成功！");
        }else {

            return Result.error(Code.STUDENT_SETPHONE_ERR,"设置失败！");
        }
    }

    @Override
    public Result updateAvatar(UserModel userModel) {
        Studentinfo studentinfo = studentinfoRepository.queryStudentinfoByStudent_id(userModel.getId());
        studentinfo.setAvatar(userModel.getAvatar());
        studentinfoRepository.save(studentinfo);
        return Result.success("头像刷新成功！");
    }
    @Override
    public Result setPassword(UserModel userModel) {
        Studentinfo studentinfo = studentinfoRepository.queryStudentinfoByStudent_id(userModel.getId());
        if (!studentinfo.getPassword().equals(userModel.getOldPassword())){
            return Result.error(Code.STUDENT_PWD_ERR,"请输入正确的旧密码");
        }else {
            studentinfo.setPassword(userModel.getPassword());
            studentinfoRepository.save(studentinfo);
            return Result.success("密码修改成功");
        }

    }

    @Override
    public Result setLatAndLong(UserModel userModel) {
        Studentinfo studentinfo = studentinfoRepository.queryStudentinfoByStudent_id(userModel.getId());
        if (studentinfo!=null){
            studentinfo.setLatitude(userModel.getLatitude());
            studentinfo.setLongitude(userModel.getLongitude());
            studentinfoRepository.save(studentinfo);
            return Result.success("设置成功！",studentinfo);
        }else {
            return Result.error(Code.STUDENT_FIND_ERR,"未查询到学生信息");
        }
    }

    @Override
    public Result findLatAndLon(String studentId) {
        Studentinfo studentinfo = studentinfoRepository.queryStudentinfoByStudent_id(studentId);
        if (studentinfo!=null){
            StudentLatAndLonModel studentLatAndLonModel=new StudentLatAndLonModel();
            studentLatAndLonModel.setLatitude(studentinfo.getLatitude());
            studentLatAndLonModel.setLongitude(studentinfo.getLongitude());
            return Result.success("查询成功",studentLatAndLonModel);
        }
        return Result.error(Code.STUDENT_FIND_ERR,"未查询到学生信息");
    }

    /**
     * 解析excel
     * @return
     */
    public Result readDataFromExcelToList(MultipartFile file) {

        POIFSFileSystem pfs = null;
        Workbook workbook = null;
        try {
            // 解析xls和xlsx不兼容问题
            workbook = ExcelUtil.getWorkBook(pfs, workbook, file);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error(Code.READ_EXCEL_ERR, "模板保存异常。");
        }
        if (workbook == null) {
            return Result.error(Code.READ_EXCEL_ERR, "请使用模板上传文件");
        }
        // 判断有记录的列数
        if (workbook.getSheetAt(0).getRow(0).getPhysicalNumberOfCells() != 7) {
            return Result.error(Code.READ_EXCEL_ERR, "请使用类型所对应的模板");
        }

        List<StudentModel> studentModelArrayList = new ArrayList<>();
        try {
            //创建工作表
            Sheet sheet = workbook.getSheetAt(0);
            //获取最后一行是第几行
            int lastRowNum = sheet.getLastRowNum();
            //由于第一行是字段名称，不做读取，后面建表的时候生成字段，因此这里从第二行开始读取，注意第二行的下标是1
            for (int i = 1; i <=lastRowNum; i++) {
                //获取行
                Row row = sheet.getRow(i);
                //进行行的非空判断后，在遍历，避免空指针
                if (row != null) {
                    //实例化一个List集合，用于存放一行读取出来的所有单元数据
                    List<String> list = new ArrayList<>();
                    //遍历行
                    for (Cell cell : row) {
                        //获得单元格，对单元格进行非空判断
                        if (cell != null) {
                            //设置单元格数据的类型为字符串，这样即使表中数据有其他类型，也不用考虑类型转换，考虑不周有可能发生的类型转换异常
                            cell.setCellType(CellType.STRING);
                            //获取单元格的数据
                            String value = cell.getStringCellValue();
                            //将每个单元格数据加入List集合中
                            list.add(value);
                        }
                    }
                    if (list.size() != 7) {
                        break;
                    }
                    //将读取出的每个行的数据封装成一个controlPointDate对象

                    StudentModel studentModel = new StudentModel();
                    studentModel.setUsername(list.get(0));
                    studentModel.setName(list.get(1));
                    studentModel.setGrade(Integer.parseInt(list.get(2)));
                    if (list.get(3).equals("是")){
                        studentModel.setGroupType(true);
                    }else {
                        studentModel.setGroupType(false);
                    }
                    studentModel.setGroupName(Integer.parseInt(list.get(4)));
                    studentModel.setPhone(list.get(5));
                    studentModel.setEmail(list.get(6));
                    //将每个Product对象添加到productList集合中，相当于一个productList集合装的就是一个sheet表的数据
                    studentModelArrayList.add(studentModel);
                }
            }
            return Result.success("excel解析成功",studentModelArrayList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(Code.READ_EXCEL_ERR,"excel解析失败"+e.getMessage());
        }

    }

}
