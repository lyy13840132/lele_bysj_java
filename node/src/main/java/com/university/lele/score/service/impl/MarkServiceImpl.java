package com.university.lele.score.service.impl;

import com.university.lele.account.repositity.TeacherinfoRepository;
import com.university.lele.global.MyKEY;
import com.university.lele.global.Result;
import com.university.lele.score.entity.TeacherMark;
import com.university.lele.score.model.StatusCollectModel;
import com.university.lele.score.model.TeacherMarkModel;
import com.university.lele.score.repositity.TeacherMarkRepository;
import com.university.lele.score.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MarkServiceImpl implements MarkService {

    @Autowired
    TeacherMarkRepository markRepository;
    @Autowired
    TeacherinfoRepository teacherinfoRepository;
    @Override
    public Result add(TeacherMarkModel teacherModel) {
        TeacherMark markInfo = markRepository.findTeacherMarkInfoById(teacherModel.getStudentId());
        if (markInfo!=null){
            markInfo.setRank(teacherModel.getRank());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间
            markInfo.setDate(sdf.format(new Date()).trim());
            markRepository.save(markInfo);
            return Result.success("修改成功",markInfo);
        }else {
            TeacherMark teacherMark=new TeacherMark();
            teacherMark.setRank(teacherModel.getRank());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间
            teacherMark.setDate(sdf.format(new Date()).trim());
            teacherMark.setTeacherId(teacherModel.getTeacherId());
            teacherMark.setStudentId(teacherModel.getStudentId());
            markRepository.save(teacherMark);
            return Result.success("评分成功",teacherMark);
        }

    }

    @Override
    public Result findMarkInfoById(String studentId) {
        TeacherMark markInfo = markRepository.findTeacherMarkInfoById(studentId);

        return markInfo!=null?Result.success("查询成功！",markInfo):
                Result.success("数据不存在");
    }

    @Override
    public Result findAllMarkInfoById(String teacherId) {
        List<TeacherMark> markInfos = markRepository.findAllTeacherMarkInfoById(teacherId);

        return markInfos.size()>0?Result.success("查询成功！",markInfos):
                Result.success("暂无评分数据！");
    }

    @Override
    public Result statisticsMark(String teacherId) {
        List<TeacherMark> teacherMarks = markRepository.findAll();
        StatusCollectModel statusCollectModel=new StatusCollectModel();
        int EXCELLENT=0; int GOOD=0;   int MEDIUM=0;  int PASSES=0; int NO_PASSES=0;
        statusCollectModel.setTeacherName(teacherinfoRepository.findTeacherByTeacherId(teacherId).getName());
        statusCollectModel.setUsername(teacherinfoRepository.findTeacherByTeacherId(teacherId).getUsername());
        for (TeacherMark m : teacherMarks) {

            switch (m.getRank()){
                case MyKEY.MARK_STATUS_EXCELLENT:
                    statusCollectModel.setExcellent(++EXCELLENT);
                    break;
                case MyKEY.MARK_STATUS_GOOD:
                    statusCollectModel.setGood(++GOOD);
                    break;
                case MyKEY.MARK_STATUS_MEDIUM:
                    statusCollectModel.setMedium(++MEDIUM);
                    break;
                case MyKEY.MARK_STATUS_PASSES:
                    statusCollectModel.setPasses(++PASSES);
                    break;
                case MyKEY.MARK_STATUS_NO_PASSES:
                    statusCollectModel.setNo_passes(++NO_PASSES);
                    break;

            }
        }
        return Result.success("统计结果为：",statusCollectModel);
    }
}
