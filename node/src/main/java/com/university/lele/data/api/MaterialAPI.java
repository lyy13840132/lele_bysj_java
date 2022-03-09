package com.university.lele.data.api;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.university.lele.data.model.ExportExcelModel;
import com.university.lele.data.model.FileUserModel;
import com.university.lele.data.service.FileService;
import com.university.lele.global.MyKEY;
import com.university.lele.global.Result;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
public class MaterialAPI {
    @Autowired
    FileService fileService;

    /**
     * 上传实习资料数据到阿里云
     * @param
     * @return
     */
    @PostMapping(value = "/material/upload")
    @ResponseBody
    public Result dewn(FileUserModel fileUserModel)throws Exception{
        return fileService.upLoadDateFile(fileUserModel);
    }
    @RequestMapping(value = "/material/findById",method = RequestMethod.GET)
    public Result findMaterialById(@RequestParam(value = "schoolId")String schoolId){
        return fileService.findMaterialBySchoolId(schoolId);
    }
    @RequestMapping(value = "/material/downLoad",method = RequestMethod.GET)
    public Result downLoadFile(@RequestParam(value = "fileName") String fileName){
        return fileService.downLoadFile(fileName);
    }

    /**
     * 成绩文件汇总成excel然后下载
     * @param teacherId
     * @return
     */
    @RequestMapping(value = "/export/{teacherId}",method = RequestMethod.GET)
    public void export(HttpServletResponse response, @PathVariable(value = "teacherId")String teacherId)throws Exception{
        List<ExportExcelModel> excelModels = fileService.writeExcel(teacherId);
        if(excelModels==null&excelModels.size()<=0){
            response.getOutputStream().print(JSON.toJSONString(Result.success("数据为空！")));
            response.flushBuffer();
            return;
        }
        String fileName = MyKEY.TEACHER_MARK_COLLECT_EXCEL;
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition" , "attachment;filename=" + URLEncoder.encode(fileName+".xlsx"  , "utf-8"));

        EasyExcel.write(response.getOutputStream() , ExportExcelModel.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .sheet("成绩汇总")
                .doWrite(excelModels);

        response.flushBuffer();
    }
}
