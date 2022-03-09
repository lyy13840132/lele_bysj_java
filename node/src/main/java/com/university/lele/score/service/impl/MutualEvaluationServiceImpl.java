package com.university.lele.score.service.impl;

import com.university.lele.account.entity.Studentinfo;
import com.university.lele.account.repositity.StudentinfoRepository;
import com.university.lele.account.repositity.TeacherinfoRepository;
import com.university.lele.enums.Code;
import com.university.lele.global.Result;
import com.university.lele.score.entity.MutualEvaluation;
import com.university.lele.score.entity.MutualEvaluationInfo;
import com.university.lele.score.model.MutualEvaluationModel;
import com.university.lele.score.model.SortMutualModel;
import com.university.lele.score.model.StudentMutualScore;
import com.university.lele.score.repositity.MutualEvaluationInfoRepository;
import com.university.lele.score.repositity.MutualEvaluationRepository;
import com.university.lele.score.service.MutualEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MutualEvaluationServiceImpl implements MutualEvaluationService {
    @Autowired
    StudentinfoRepository studentinfoRepository;
    @Autowired
    TeacherinfoRepository teacherinfoRepository;
    @Autowired
    MutualEvaluationInfoRepository mutualEvaluationInfoRepository;
    @Autowired
    MutualEvaluationRepository mutualEvaluationRepository;
    private MutualEvaluation dbMutualEvaluation;
    private MutualEvaluation mutualEvaluation;

    @Override
    public Result mutualEvaluation(MutualEvaluationModel evaluationModel) {
        List<StudentMutualScore> studentMutualScores = evaluationModel.getStudentMutualScores();
        if (studentMutualScores.size() > 0) {
            List<MutualEvaluationInfo> mutualEvaluationInfos = new ArrayList<>();
            for (StudentMutualScore s : studentMutualScores) {
                MutualEvaluationInfo mutualEvaluationInfo = new MutualEvaluationInfo();
                mutualEvaluationInfo.setMarkStudentId(evaluationModel.getMarkStudentId());
                mutualEvaluationInfo.setStudentId(s.getStudentId());
                Studentinfo studentinfo = studentinfoRepository.queryStudentinfoByStudent_id(s.getStudentId());
                if (studentinfo != null) {
                    mutualEvaluationInfo.setName(studentinfo.getName());
                    mutualEvaluationInfo.setUsername(studentinfo.getUsername());
                    mutualEvaluationInfo.setTeacherId(studentinfo.getTeacherId());
                    mutualEvaluationInfo.setGroupName(studentinfo.getGroupName());
                    mutualEvaluationInfo.setGroupType(studentinfo.getGroupType());
                }
                mutualEvaluationInfo.setStarNum(s.getStarNum());
                mutualEvaluationInfo.setComment(s.getComment());
                mutualEvaluationInfos.add(mutualEvaluationInfo);
                mutualEvaluationInfoRepository.save(mutualEvaluationInfo);
            }
            return Result.success("互评成功！", mutualEvaluationInfos);
        } else {
            return Result.error(Code.MUTUAL_ADD_ERR, "请先先评分后提交奥！");
        }
    }

    @Override
    public Result findMutualInfo(String studentId) {
        return null;
    }

    @Override
    public Result studentRanking(String teacherId, int groupName) {
        if (isFinishMutual(teacherId,groupName)) {
            //开始查询学生互评排名
            List<String> list = new ArrayList<>();
            List<MutualEvaluationInfo> mutualEvaluationInfos = mutualEvaluationInfoRepository.findMutualInfoByTeacherIdAndGroupName(teacherId, groupName);
            if (mutualEvaluationInfos.size() > 0) {
                Map<String, List<MutualEvaluationInfo>> collect = mutualEvaluationInfos.stream().collect(Collectors.groupingBy(MutualEvaluationInfo::getStudentId));
                for (Map.Entry<String, List<MutualEvaluationInfo>> stuMap : collect.entrySet()) {
                    String studentId = stuMap.getKey();
                    List<MutualEvaluationInfo> mutualEvaluationInfos1 = collect.get(studentId);
                    int num = 0;
                    for (MutualEvaluationInfo m : mutualEvaluationInfos1) {
                        num = num + m.getStarNum();
                        dbMutualEvaluation = mutualEvaluationRepository.
                                findStudentMutualInfoById(studentId);
                        if (dbMutualEvaluation !=null){
                            dbMutualEvaluation.setTeacherId(m.getTeacherId());
                            dbMutualEvaluation.setGroupName(m.getGroupName());
                            dbMutualEvaluation.setGroupType(m.getGroupType());
                            list.add(m.getComment());
                            dbMutualEvaluation.setCommentList(list.toString());
                            dbMutualEvaluation.setStarNum(num);
                            dbMutualEvaluation.setName(m.getName());
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间
                            dbMutualEvaluation.setDate(sdf.format(new Date()).trim());
                            mutualEvaluationRepository.save(dbMutualEvaluation);
                        }else {
                            mutualEvaluation = new MutualEvaluation();
                            mutualEvaluation.setStudentId(m.getStudentId());
                            mutualEvaluation.setTeacherId(m.getTeacherId());
                            mutualEvaluation.setGroupName(m.getGroupName());
                            mutualEvaluation.setGroupType(m.getGroupType());
                            list.add(m.getComment());
                            mutualEvaluation.setCommentList(list.toString());
                            mutualEvaluation.setStarNum(num);
                            mutualEvaluation.setName(m.getName());
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间
                            mutualEvaluation.setDate(sdf.format(new Date()).trim());
                            mutualEvaluationRepository.save(mutualEvaluation);
                        }
                    }
                }

            } else {
                return Result.success("请先完成学生互评！");
            }
            List<MutualEvaluation> mutualEvaluations = mutualEvaluationRepository.findAll();
            if (mutualEvaluations.size() > 0) {
                for (MutualEvaluation e : mutualEvaluations) {

                    double d = new BigDecimal((float) e.getStarNum() / (mutualEvaluations.size() - 1)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    e.setAvgStar(String.valueOf(d));
                    mutualEvaluationRepository.save(e);
                }
                Collections.sort(mutualEvaluations, new Comparator<MutualEvaluation>() {
                    @Override
                    public int compare(MutualEvaluation o1, MutualEvaluation o2) {
                        //升序
                        return o2.getStarNum().compareTo(o1.getStarNum());
                    }
                });
                System.out.println(mutualEvaluations);
                return Result.success("查询成功,本小组排序后的名单为：", mutualEvaluations);
            }
        }
        return Result.success("请先完成学生互评！");
    }

    @Override
    public Result allMutualInfos(String teacherId) {
        List<MutualEvaluation> mutualEvaluations = mutualEvaluationRepository.findStudentMutualInfosByTeacherId(teacherId);
        return mutualEvaluations.size()>0?Result.success("查询成功",mutualEvaluations):Result.success("暂无互评数据");
    }

    @Override
    public Result sortMutual(String teacherId) {
        List<Studentinfo> studentinfos = studentinfoRepository.queryStudentInfoByTeacherId(teacherId);

        List<MutualEvaluation> mutuals = mutualEvaluationRepository.findStudentMutualInfosByTeacherId(teacherId);
        List<SortMutualModel> sortMutualModels=new ArrayList<>();
        if (studentinfos.size()>0){
            for (Studentinfo s : studentinfos) {
                SortMutualModel sortMutualModel=new SortMutualModel();
                sortMutualModel.setUsername(s.getUsername());
                sortMutualModel.setName(s.getName());
                sortMutualModel.setGroupName(s.getGroupName());
                sortMutualModel.setGroupType(s.getGroupType());
                MutualEvaluation mutual = mutualEvaluationRepository.findStudentMutualInfoById(s.getId());
                if (mutual!=null){
                    sortMutualModel.setAvgStar(Double.valueOf(mutual.getAvgStar()));
                    sortMutualModel.setStarNum(mutual.getStarNum());
                }else {
                    sortMutualModel.setAvgStar(0.0);
                    sortMutualModel.setStarNum(0);
                }

                sortMutualModels.add(sortMutualModel);
            }
        }

        if (sortMutualModels.size() > 0) {

            Collections.sort(sortMutualModels, new Comparator<SortMutualModel>() {
                @Override
                public int compare(SortMutualModel o1, SortMutualModel o2) {
                    //升序
                    return o2.getAvgStar().compareTo(o1.getAvgStar());
                }
            });
            return Result.success("OK", sortMutualModels);
        }else {
            return Result.success("暂无数据");
        }

    }

    /**
     * 判断学生互评是否完全结束
     * @param teacherId
     * @param groupName
     * @return
     */
    public  boolean isFinishMutual(String teacherId,int groupName){
        List<MutualEvaluationInfo> mutualEvaluationInfos = mutualEvaluationInfoRepository.findMutualInfoByTeacherIdAndGroupName(teacherId, groupName);
        List<Studentinfo> studentinfos = studentinfoRepository.findGroupListByIdAndGroupName(teacherId, groupName);
        if (mutualEvaluationInfos.size()>0&studentinfos.size()>0){
            return mutualEvaluationInfos.size()==studentinfos.size()*(studentinfos.size()-1);
        }else {
            return false;
        }
    }
}