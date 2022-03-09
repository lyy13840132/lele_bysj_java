package com.university.lele.score.api;

import com.university.lele.global.Result;
import com.university.lele.score.model.RecordModel;
import com.university.lele.score.service.RecordService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping(value = "record")
public class RecordAPI {
    @Autowired
    RecordService recordService;
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(@RequestBody @Validated RecordModel recordModel){
        return recordService.add(recordModel);
    }
    @RequestMapping(value = "/upload_img",method = RequestMethod.POST)
    public Result uploadImg(@RequestParam(value = "file") MultipartFile file){
        return recordService.uploadImg(file);
    }

    @RequestMapping(value = "/findById",method = RequestMethod.GET)
    public Result findById(@RequestParam(value = "teacherId")String teacherId,
                           @RequestParam(value = "studentId") String studentId){
        return recordService.findRecordInfoById(teacherId,studentId);
    }

}
