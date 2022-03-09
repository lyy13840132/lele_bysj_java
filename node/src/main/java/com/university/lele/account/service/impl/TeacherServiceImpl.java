package com.university.lele.account.service.impl;

import com.university.lele.account.entity.Studentinfo;
import com.university.lele.account.entity.TeacherConfiguration;
import com.university.lele.account.entity.Teacherinfo;
import com.university.lele.account.model.*;

import com.university.lele.account.repositity.CreateASchoolRepository;
import com.university.lele.account.repositity.StudentinfoRepository;
import com.university.lele.account.repositity.TeacherConfigurationRepository;
import com.university.lele.account.repositity.TeacherinfoRepository;
import com.university.lele.account.service.TeacherService;
import com.university.lele.enums.Code;
import com.university.lele.global.Result;
import com.university.lele.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {


    @Autowired
    TeacherConfigurationRepository configurationRepository;

    @Autowired
    TeacherinfoRepository teacherinfoRepository;

    @Autowired
    StudentinfoRepository studentinfoRepository;
    @Autowired
    CreateASchoolRepository createASchoolRepository;

    @Override
    public Result singAddTeacher(TeacherModel teacherModel) {
        Teacherinfo teacherinfo=new Teacherinfo();
        Teacherinfo dbTeacherInfo = teacherinfoRepository.queryTeacherInfoByUsernName(teacherModel.getUsername());
        if (dbTeacherInfo==null){
            teacherinfo.setId(Utils.getUUID());
            teacherinfo.setUsername(teacherModel.getUsername());
            teacherinfo.setName(teacherModel.getName());
            teacherinfo.setSchoolId(teacherModel.getSchoolId());
            teacherinfo.setSchoolName(createASchoolRepository.
                    findSchoolInfoBySchoolId(teacherModel.getSchoolId()).getName());
            teacherinfo.setPassword(teacherModel.getUsername());
            teacherinfo.setUserType(2);
            teacherinfo.setEmail(teacherModel.getEmail());
            teacherinfo.setPhone(teacherModel.getPhone());
            Teacherinfo save = teacherinfoRepository.save(teacherinfo);
            return Result.success("帐号添加成功",save);
        }else {
            return Result.error(Code.TEACHER_ISEXIST,"帐号已存在");
        }

    }


    @Override
    public Result deleteTeacher(String id) {
        Teacherinfo teacherinfo = teacherinfoRepository.findTeacherByTeacherId(id);
        if (teacherinfo!=null){
            teacherinfoRepository.delete(teacherinfo);
            return Result.success("删除成功！");
        }else {
            return Result.error(Code.TEACHER__DELETE_ERR,"删除失败,帐号不存在！");
        }

    }

    @Override
    public Result queryTeacherList(String id) {
        List<Teacherinfo> teacherinfos = teacherinfoRepository.queryAllTeacherList(id);
        if (teacherinfos.size()>0){
            return Result.success("查询成功",teacherinfos);
        }else {

            return Result.success("还未创建教师帐号！");
        }
    }

    @Override
    public Result updateAvatar(UserModel userModel) {
        Teacherinfo teacherinfo = teacherinfoRepository.findTeacherByTeacherId(userModel.getId());
        teacherinfo.setAvatar(userModel.getAvatar());
        teacherinfoRepository.save(teacherinfo);
        return Result.success("头像刷新成功");
    }

    @Override
    public Result setPhone(UserModel userModel) {
        Teacherinfo teacherinfo = teacherinfoRepository.findTeacherByTeacherId(userModel.getId());
        if (teacherinfo!=null){
            teacherinfo.setPhone(userModel.getPhone());
            Teacherinfo save = teacherinfoRepository.save(teacherinfo);
            return Result.success("重置手机号成功！");
        }else {

            return Result.error(Code.TEACHER_SETPHONE_ERR,"设置失败！");
        }
    }

    @Override
    public Result setPassword(UserModel userModel) {
        Teacherinfo teacher = teacherinfoRepository.findTeacherByTeacherId(userModel.getId());
        if (!teacher.getPassword().equals(userModel.getOldPassword())){
            return Result.error(Code.TEACHER_PWD_ERR,"请输入正确的旧密码");
        }else {
            if (userModel.getPassword().isEmpty()){
                return Result.error(Code.PASSWORD_ERR_NO,"请输入新密码！");
            }else {
                teacher.setPassword(userModel.getPassword());
                teacherinfoRepository.save(teacher);
                return Result.success("密码修改成功");
            }

        }

    }

    @Override
    public Result setSignIn(SetTeacherModel setTeacherModel) {
        TeacherConfiguration teacherConfiguration=configurationRepository.findConfigByTeacherId(setTeacherModel.getTeacherId());
        if (teacherConfiguration!=null){
            teacherConfiguration.setSignInDate(setTeacherModel.getSignInDate());
            teacherConfiguration.setExtent(setTeacherModel.getExtent());
            configurationRepository.save(teacherConfiguration);
            return Result.success("设置成功！",teacherConfiguration);
        }else {
            TeacherConfiguration teacherConfiguration1=new TeacherConfiguration();
            teacherConfiguration1.setTeacherId(setTeacherModel.getTeacherId());
            teacherConfiguration1.setSignInDate(setTeacherModel.getSignInDate());
            teacherConfiguration1.setExtent(setTeacherModel.getExtent());
            configurationRepository.save(teacherConfiguration1);
            return Result.success("设置成功！",teacherConfiguration1);
        }
    }

    @Override
    public Result findSignInSet(String teacherId) {
        TeacherConfiguration configuration = configurationRepository.findConfigByTeacherId(teacherId);
            return configuration!=null?Result.success("查询成功！",configuration)
                    :Result.success("没有设置信息");


    }

    @Override
    public Result setIsSwitch(SetTeacherModel setTeacherModel) {
        TeacherConfiguration config = configurationRepository.findConfigByTeacherId(setTeacherModel.getTeacherId());
        if (config!=null){
            config.setScoreShowSwitch(setTeacherModel.getScoreShowSwitch());
            configurationRepository.save(config);
            return Result.success("设置成功！");
        }else {
            TeacherConfiguration configuration=new TeacherConfiguration();
            configuration.setScoreShowSwitch(setTeacherModel.getScoreShowSwitch());
            configurationRepository.save(configuration);
            return Result.success("设置成功！");
        }
    }

    @Override
    public Result teacherLogin(LoginModel loginModel) {

        Teacherinfo teacherinfo=teacherinfoRepository.queryTeacherInfoByUsernName(loginModel.getUsername());
       if (teacherinfo!=null){

           if (loginModel.getPassword().equals(teacherinfo.getPassword())){
               return Result.success("教师帐号登录成功",teacherinfo);
           }else {
               return Result.error(Code.TEACHER_LOGIN_PASSWORD_ERR,"登录密码错误");
           }

       } else {
           return Result.error(Code.TEACHER_LOGIN_ERR,"帐号输入错误或帐号不存在！");
       }
    }

    @Override
    public Result getStudentList(String teacherId){
        List<Studentinfo> studentinfos = studentinfoRepository.queryStudentInfoByTeacherId(teacherId);
        return studentinfos.size()>0?Result.success("学生列表信息",studentinfos)
                :Result.success("请先添加学生帐号！");
    }

    @Override
    public Result getStudentList(String teacherId, int groupName) {
        List<Studentinfo> studentinfos=studentinfoRepository.findGroupListByIdAndGroupName(teacherId,groupName);
        return studentinfos.size()>0?Result.success("学生信息查询成功！",studentinfos):Result.success("该小组无成员");
    }

    @Override
    public Result queryUserTeacherInfo(String teacherid) {
        Teacherinfo teacherInfo = teacherinfoRepository.findTeacherByTeacherId(teacherid);
        if (teacherInfo==null){
            return Result.error(Code.TEACHER_QUERY_ERR,"教师信息不存在！");
        }else {

            return Result.success("查询成功",teacherInfo);
        }
    }

    @Override
    public Result updateTeacher(TeacherModel teacherModel) {
        Teacherinfo teacherInfo = teacherinfoRepository.findTeacherByTeacherId(teacherModel.getId());

        if (teacherInfo!=null){
            teacherInfo.setUsername(teacherModel.getUsername());
            teacherInfo.setName(teacherModel.getName());
            teacherInfo.setEmail(teacherModel.getEmail());
            teacherInfo.setPhone(teacherModel.getPhone());
            Teacherinfo save = teacherinfoRepository.save(teacherInfo);
            return Result.success("更新成功！",save);
        }else {
            return Result.error(Code.TEACHER_UPDATE_ISEXIST,"数据不存在！");
        }
    }



}
