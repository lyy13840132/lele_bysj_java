package com.university.lele.score.service.impl;

import com.university.lele.enums.Code;
import com.university.lele.global.MyKEY;
import com.university.lele.global.Result;
import com.university.lele.score.entity.Record;
import com.university.lele.score.model.RecordModel;
import com.university.lele.score.repositity.RecordRepository;
import com.university.lele.score.service.RecordService;
import com.university.lele.utils.AliyunOssUtil;
import com.university.lele.utils.Utils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    RecordRepository recordRepository;
    @Override
    public Result add(RecordModel recordModel) {
        Record record=new Record();
        record.setId(Utils.getUUID());
        if (!TextUtils.isEmpty(recordModel.getRecord())){
            record.setRecord(recordModel.getRecord());
        }
        if (!TextUtils.isEmpty(recordModel.getImage())){
            record.setImage(recordModel.getImage());
        }
        record.setStudentId(recordModel.getStudentId());
        record.setTeacherId(recordModel.getTeacherId());
        record.setDate(recordModel.getDate());
        recordRepository.save(record);
        return Result.success("记录数据成功！",record);
    }

    @Override
    public Result uploadImg(MultipartFile file) {
        try {
            String url = new AliyunOssUtil().uploadObject2OSS(new AliyunOssUtil().getOSSClient(), file, MyKEY.OSS_BACKET_NAME, MyKEY.OSS_REOCRD);
            return Result.success("上传图片成功！",url);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(Code.RECORD_ERR,e.getMessage());
        }
    }

    @Override
    public Result findRecordInfoById(String teacherId, String studentId) {
        if (TextUtils.isEmpty(teacherId)){
            Record record = recordRepository.findRecordInfoByStudentId(studentId);
            return record!=null?Result.success("查询成功",record):Result.success("暂无数据");
        }
        if (TextUtils.isEmpty(studentId)){
           Record record= recordRepository.findRecordInfoByTeacherId(teacherId);
           return record!=null?Result.success("查询成功",record):Result.success("暂无数据");
        }
        if (TextUtils.isEmpty(teacherId)&TextUtils.isEmpty(studentId)){
            return Result.error(Code.RECORD_FIND_ERR,"teacherId或者studentId为空！");
        }else {
            List<Record> records=recordRepository.findRecordInfoByStudentIdAndTeacherId(teacherId,studentId);
            if (records.size()>0){
                return Result.success("查询成功！",records);
            }else {
                return Result.success("暂无数据");
            }
        }
    }
}

