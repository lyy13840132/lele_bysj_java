package com.university.lele.cnki.api;

import com.university.lele.cnki.service.CnkiService;
import com.university.lele.global.Result;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
/**
 * 文档查重
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "cnki")
public class CnkiAPI {

    @Autowired
    CnkiService cnkiService;

    @RequestMapping(value = "/upload", headers = "content-type=multipart/*", method = RequestMethod.POST)
    public Result upload(@RequestParam("files") MultipartFile[] files,@RequestParam("simDeg")double simDeg,
                         @RequestParam("teacherId")String teacherId) {
        System.out.println(simDeg);
        return cnkiService.cnkiFiles(files,simDeg,teacherId);
    }
    @RequestMapping(value = "/plagiarism/{username}",method =RequestMethod.GET)
    public Result finaHighPlagiarism(@PathVariable(value = "username")String username){
        return cnkiService.finaHighPlagiarismByUsername(username);
    }
    @RequestMapping(value = "/deleteAll/{teacherId}",method = RequestMethod.DELETE)
    public Result deleteAll(@PathVariable("teacherId")String teacherId){
        return cnkiService.deleteAll(teacherId);
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result findCnkiInfo(@RequestParam(value = "teacherId",required = true)String teacherId,
                               @RequestParam(value = "username",required = false)String username,
                               @RequestParam(value = "crib",required = false) Integer crib){
        return cnkiService.findCnkiInfo(teacherId,username,crib);
    }
    @RequestMapping(value = "statistics/{teacherId}")
    public Result statistics(@PathVariable(value = "teacherId")String teacherId){
        return cnkiService.statisticsData(teacherId);
    }




}
