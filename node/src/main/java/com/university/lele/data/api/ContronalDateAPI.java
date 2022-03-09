package com.university.lele.data.api;

import com.university.lele.data.model.ControlDateXY;
import com.university.lele.data.model.FileUserModel;
import com.university.lele.data.service.ControlService;
import com.university.lele.data.service.FileService;
import com.university.lele.enums.Code;
import com.university.lele.global.Result;
import com.university.lele.utils.ExcelUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("station")
public class ContronalDateAPI {

    @Autowired
    FileService fileService;
    @Autowired
    ControlService controlService;

    /**
     * 批量导入控制点
     * @param
     * @return
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public Result addStationDate( FileUserModel fileUserModel){
        String postfix = ExcelUtil.getPostfix(fileUserModel.getFile().getOriginalFilename());
        if (!"xlsx".equals(postfix) && !"xls".equals(postfix)) {
            return Result.error(Code.EXCEL_XLSX_OR_XLS_ERR,"导入失败，请选择正确的文件格式支持xlsx或xls");
        }
        return controlService.upLoadControlData(fileUserModel);
    }
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public Result deleteData(@PathVariable(value = "id")int id){
        return controlService.deleteData(id);

    }
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Result update(@RequestBody @Validated ControlDateXY controlDateXY){
        return controlService.updateControlDate(controlDateXY);

    }
    @RequestMapping(value = "/findById/{id}",method = RequestMethod.GET)
    public Result query(@PathVariable(value = "id") int id){
        return controlService.queryControlInfo(id);

    }
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result findList(@RequestParam (value = "schoolId") String schoolId){
        return controlService.queryAllList(schoolId);
    }
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(@RequestBody @Validated ControlDateXY controlDateXY){
        return controlService.add(controlDateXY);
    }

}
