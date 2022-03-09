package com.university.lele.data.service.impl;

import com.university.lele.data.entity.Controlpointdate;
import com.university.lele.data.model.ControlDateLonAndLat;
import com.university.lele.data.model.ControlDateXY;
import com.university.lele.data.model.FileUserModel;
import com.university.lele.data.repositity.ControlpointdateRepository;
import com.university.lele.data.service.ControlService;
import com.university.lele.data.service.FileService;
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
public class ControlServiceImpl implements ControlService {
    @Autowired
    ControlpointdateRepository controlpointdateRepository;
    @Autowired
    FileService fileService;
    @Override
    public Result upLoadControlData(FileUserModel fileUserModel) {
        Result dataFromExcelToList = readDataFromExcelToList(fileUserModel.getFile());
        List<ControlDateXY> controlDateXYList = (List) dataFromExcelToList.getData();
        List<Controlpointdate> controlpointdates=new ArrayList<>();
        if (controlDateXYList == null || controlDateXYList.size() <= 0) {
            return Result.error(Code.CONTROL_DATA_NO,"请使用控制点数据的excel模板！！");
        }
        List<ControlDateXY> controlDateXYs=new ArrayList<>();
        Result result = fileService.parseExcel();
        for (ControlDateXY item : controlDateXYList) {
            // 保存数据到数据库
            if (controlpointdateRepository.findPointDateByPointName(item.getName())==null){
                Controlpointdate controlpointdate=new Controlpointdate();
                List<ControlDateLonAndLat> controlDateLonAndLats = (List<ControlDateLonAndLat>) result.getData();
                if (controlDateLonAndLats.size()>0){
                    for (ControlDateLonAndLat c :controlDateLonAndLats) {
                        if (item.getName().equals(c.getName())){
                            controlpointdate.setLatitude(c.getLatitude());
                            controlpointdate.setLongitude(c.getLongitude());
                        }
                    }
                }
                controlpointdate.setName(item.getName());
                controlpointdate.setX(item.getX());
                controlpointdate.setY(item.getY());
                controlpointdate.setZ(item.getZ());
                controlpointdate.setNote(item.getNote());
                controlpointdate.setState(item.getState());
                controlpointdate.setSchoolId(fileUserModel.getSchoolId());
                 controlpointdates.add(controlpointdate);
                controlpointdateRepository.save(controlpointdate);
            }else {
                controlDateXYs.add(item);
            }
        }
        if (controlDateXYs.size()>0){
            return Result.success("部分控制点导入成功！以下控制点存在重复！",controlDateXYs);
        }else {

            return Result.success("控制点导入成功！",controlpointdates);
        }
    }
    @Override
    public Result deleteData(int id) {
        Controlpointdate controlpointdate = controlpointdateRepository.queryControlDataInfo(id);
        if (controlpointdate!=null){
            controlpointdateRepository.delete(controlpointdateRepository.queryControlDataInfo(id));
            return Result.success("删除成功");
        }else {
            return Result.error(Code.CONTROL_DELETE_ERR,"帐号不存在！");
        }
    }

    @Override
    public Result queryControlInfo(int id) {
        Controlpointdate controlpointdate = controlpointdateRepository.queryControlDataInfo(id);
        return controlpointdate!=null?Result.success("查询成功",controlpointdate):Result.error(Code.CONTROL_QUERY_ERR,"数据不存在！");
    }

    @Override
    public Result updateControlDate(ControlDateXY controlDateXY) {
        System.out.println(controlDateXY);
        Controlpointdate controlDataInfo = controlpointdateRepository.
                queryControlDataInfo(controlDateXY.getId());
        if (controlDataInfo != null) {
            controlDataInfo.setName(controlDateXY.getName());
            controlDataInfo.setX(controlDateXY.getX());
            controlDataInfo.setY(controlDateXY.getY());
            controlDataInfo.setZ(controlDateXY.getZ());
            controlDataInfo.setNote(controlDateXY.getNote());
            controlDataInfo.setState(controlDateXY.getState());
            return Result.success("数据更新成功", controlpointdateRepository.save(controlDataInfo));

        } else {
            return Result.error(Code.CONTROL_UPDATE_ERR, "数据不存在，更新失败！");
        }
    }

    @Override
    public Result queryAllList(String schoolId) {
        List<Controlpointdate> controlpointdates=controlpointdateRepository.findCortolDataListBySchoolId(schoolId);
        if (controlpointdates.size()>0){
            return Result.success("查询成功",controlpointdates);
        }else {

            return Result.success("还未添加控制点数据",null);
        }
    }

    @Override
    public Result add(ControlDateXY controlDateXY) {
        Controlpointdate controlpointdate = controlpointdateRepository.findPointDateByPointName(controlDateXY.getName());
        if (controlpointdate==null){
            Controlpointdate controlpoint=new Controlpointdate();
            controlpoint.setName(controlDateXY.getName());
            controlpoint.setX(controlDateXY.getX());
            controlpoint.setY(controlDateXY.getY());
            controlpoint.setZ(controlDateXY.getZ());
            controlpoint.setNote(controlDateXY.getNote());
            controlpoint.setState(controlDateXY.getState());
            controlpoint.setSchoolId(controlDateXY.getSchoolId());
            controlpointdateRepository.save(controlpoint);
            return Result.success("添加成功",controlpoint);
        }else {

            return Result.error(Code.CONTROL_ADTA_ADD_ERR,"数据已存在！");
        }

    }

    @Override
    public Result textFile(MultipartFile file) {
        return readDataFromExcelToList(file);
    }


    /**
     * 解析excel
     * @return
     */
    public Result readDataFromExcelToList(MultipartFile file)  {

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
        if (workbook.getSheetAt(0).getRow(0).getPhysicalNumberOfCells() != 6) {
            return Result.error(Code.READ_EXCEL_ERR, "请使用类型所对应的模板");
        }

        List<ControlDateXY> productList = new ArrayList<>();
        try {
            //创建工作表
            Sheet sheet = workbook.getSheetAt(0);
            //获取最后一行是第几行
            int lastRowNum = sheet.getLastRowNum();
            //由于第一行是字段名称，不做读取，后面建表的时候生成字段，因此这里从第二行开始读取，注意第二行的下标是1
            for (int i = 1; i <= lastRowNum; i++) {
                //获取行
                Row row = sheet.getRow(i);
                //进行行的非空判断后，在遍历，避免空指针
                if (row != null) {
                    //实例化一个List集合，用于存放一行读取出来的所有单元数据
                    List<String> list = new ArrayList<>();
                    //遍历行
                    for (Cell cell : row) {
                        if (cell != null) {
                            //设置单元格数据的类型为字符串，这样即使表中数据有其他类型，也不用考虑类型转换，考虑不周有可能发生的类型转换异常
                            cell.setCellType(CellType.STRING);
                            //获取单元格的数据

                            String value = cell.getStringCellValue();
                            //将每个单元格数据加入List集合中
                            list.add(value);
                        }
                    }
                    if (list.size() != 6) {
                        break;
                    }
                    //将读取出的每个行的数据封装成一个controlPointDate对象
                    ControlDateXY controlDateXY = new ControlDateXY();
                    controlDateXY.setName(list.get(0));
                    controlDateXY.setX(list.get(1));
                    controlDateXY.setY(list.get(2));
                    controlDateXY.setZ(list.get(3));
                    controlDateXY.setNote(list.get(4));
                    controlDateXY.setState(Integer.parseInt(list.get(5)));
                    //将每个Product对象添加到productList集合中，相当于一个productList集合装的就是一个sheet表的数据
                    productList.add(controlDateXY);
                }
            }
            return Result.success("excel解析成功",productList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(Code.READ_EXCEL_ERR,"excel解析失败"+e.getMessage());
        }

    }


}
