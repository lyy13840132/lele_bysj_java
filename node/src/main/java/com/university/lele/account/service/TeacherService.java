package com.university.lele.account.service;

import com.university.lele.account.model.*;
import com.university.lele.global.Result;

public interface TeacherService {

    /**
     * 添加教师账户
     * @param teacherinfo
     * @return
     */
     Result singAddTeacher(TeacherModel teacherinfo);

    /**
     * 教师帐号登录
     * @return
     */
    Result teacherLogin(LoginModel loginModel);



    /**
     * 根据账户查询用户全部信息
     * @param teacherId
     * @return
     */
    Result queryUserTeacherInfo(String teacherId);

    /**
     * 修改教师信息
     * @param teacherModel
     * @return
     */
    Result updateTeacher(TeacherModel teacherModel);

    /**
     * 根据教师id查询教师所带学生
     * @param teacherId
     * @return
     */
    Result getStudentList(String teacherId);
   Result getStudentList(String teacherId,int groupName);
    /**
     * 删除教师信息
     * @param teacher_id
     * @return
     */
    Result deleteTeacher(String teacher_id);

   // Result getTeacherList(String schoolId);

    Result queryTeacherList(String id);
    //Result queryTeacherList(String id,String groupName);


    Result updateAvatar(UserModel userModel);

    Result setPhone(UserModel userModel);

    Result setPassword(UserModel userModel);

    Result setSignIn(SetTeacherModel setTeacherModel);

    Result findSignInSet(String teacherId);

    Result setIsSwitch(SetTeacherModel setTeacherModel);
}
