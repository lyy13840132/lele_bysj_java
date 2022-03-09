package com.university.lele.data.service.impl;

import com.aliyun.oss.model.OSSObject;
import com.university.lele.account.entity.Studentinfo;
import com.university.lele.account.model.UserModel;
import com.university.lele.account.repositity.StudentinfoRepository;
import com.university.lele.account.repositity.TeacherinfoRepository;
import com.university.lele.account.service.StudentService;
import com.university.lele.cnki.entity.CnkiHighSim;
import com.university.lele.cnki.repositity.CnkiHighSimRepository;
import com.university.lele.data.entity.Controlpointdate;
import com.university.lele.data.entity.GatherFile;
import com.university.lele.data.model.ControlDateLonAndLat;
import com.university.lele.data.model.ExportExcelModel;
import com.university.lele.data.model.FileUserModel;
import com.university.lele.data.repositity.ControlpointdateRepository;
import com.university.lele.data.repositity.GatherFileRepository;
import com.university.lele.data.service.FileService;
import com.university.lele.enums.Code;
import com.university.lele.global.MyKEY;
import com.university.lele.global.Result;
import com.university.lele.global.URL;
import com.university.lele.score.entity.MutualEvaluation;
import com.university.lele.score.entity.TeacherMark;
import com.university.lele.score.repositity.MutualEvaluationRepository;
import com.university.lele.score.repositity.TeacherMarkRepository;
import com.university.lele.siginIn.repositity.SignInRepository;
import com.university.lele.utils.AliyunOssUtil;
import com.university.lele.utils.ExcelUtil;
import org.apache.commons.io.FileUtils;
import org.apache.http.util.TextUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    ControlpointdateRepository controlpointdateRepository;
    @Autowired
    StudentService studentService;
    @Autowired
    StudentinfoRepository studentinfoRepository;
    @Autowired
    TeacherinfoRepository teacherinfoRepository;

    @Autowired
    GatherFileRepository gatherFileRepository;
    @Autowired
    SignInRepository signInRepository;
    @Autowired
    MutualEvaluationRepository mutualEvaluationRepository;
    @Autowired
    CnkiHighSimRepository cnkiHighSimRepository;
    @Autowired
    TeacherMarkRepository markRepository;
    /**
     * 上傳實習資料
     * @param model
     * @return
     * @throws Exception
     */
    @Override
    public Result upLoadDateFile(FileUserModel model) throws Exception {
        try {
           String url= new AliyunOssUtil().upLoadFileToOss(model);
            GatherFile gatherFile=new GatherFile();
           if (!url.isEmpty()){
               gatherFile.setSchoolId(model.getSchoolId());
               gatherFile.setFileName(model.getFile().getOriginalFilename());
               gatherFile.setFileUrl(url);
               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间
               gatherFile.setDate(sdf.format(new Date()).trim());
               gatherFileRepository.save(gatherFile);
               return Result.success("资料上传成功",gatherFile);
           }else {
               return Result.error(Code.FILE_TO_OSS_ERR,"上传阿里云OSS服务器异常");
           }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(Code.UPLODEFILE_ERR,e.getMessage());
        }
    }

    @Override
    public Result loadAvatar(UserModel userModel) {
        // 使用uid查询用户数据
        try {
            String headUrl = new AliyunOssUtil().upLoadImageToOSS(userModel.getFile().getInputStream(), URL.QR_BASE_HEAD_URL, MyKEY.OSS_HEAD, userModel.getId());
            return Result.success("url保存成功！",headUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(Code.MOBILE_HEAD_UPDATE_ERR,"上传头像出现异常！"+e.getMessage());
        }
    }

    @Override
    public Result parseExcel() {
        OSSObject ossObject = new AliyunOssUtil().getOSSClient().getObject(MyKEY.OSS_BACKET_NAME,"dataFile/control_data_oss.xlsx" );
        try {
            Workbook workbook = new XSSFWorkbook(ossObject.getObjectContent());
            List<ControlDateLonAndLat> controlDateLonAndLats = new ArrayList<>();
            try {
                //创建工作表
                Sheet sheet = workbook.getSheetAt(0);
                //获取最后一行是第几行
                int lastRowNum = sheet.getLastRowNum();
                //由于第一行是字段名称，不做读取，后面建表的时候生成字段，因此这里从第二行开始读取，注意第二行的下标是1
                for (int i = 1; i <=lastRowNum; i++) {
                    //获取行
                    Row row = sheet.getRow(i);
                    //进行行的非空判断后，在遍历，避免空指针
                    if (row != null) {
                        //实例化一个List集合，用于存放一行读取出来的所有单元数据
                        List<String> list = new ArrayList<>();
                        //遍历行
                        for (Cell cell : row) {
                            //获得单元格，对单元格进行非空判断
                            if (cell != null) {
                                //设置单元格数据的类型为字符串，这样即使表中数据有其他类型，也不用考虑类型转换，考虑不周有可能发生的类型转换异常
                                cell.setCellType(CellType.STRING);
                                //获取单元格的数据
                                String value = cell.getStringCellValue();
                                //将每个单元格数据加入List集合中
                                list.add(value);
                            }
                        }
                        if (list.size() != 3) {
                            break;
                        }
                        //将读取出的每个行的数据封装成一个controlPointDate对象
                        ControlDateLonAndLat controlDateLonAndLat = new ControlDateLonAndLat();
                        controlDateLonAndLat.setName(list.get(0));
                        controlDateLonAndLat.setLatitude(list.get(1));
                        controlDateLonAndLat.setLongitude(list.get(2));
                        //对象添加到productList集合中，相当于一个productList集合装的就是一个sheet表的数据
                        controlDateLonAndLats.add(controlDateLonAndLat);
                    }
                }
                return Result.success("excel解析成功",controlDateLonAndLats);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
                return Result.error(Code.READ_EXCEL_ERR,"excel解析失败"+e.getMessage());
            }



        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //
    }

    @Override
    public Result findStationListBySchoolId(String schoolId) {
        List<Controlpointdate> stationLists = controlpointdateRepository.findCortolDataListBySchoolId(schoolId);
        if (stationLists.size()<=0){
            return Result.error(Code.CONTROL_DATA_ERR,"还未添加控制点数据！");
        }else {

            return Result.success("加载控制点数据成功！",stationLists);
        }
    }

    @Override
    public Result findMaterialBySchoolId(String schoolId) {
        List<GatherFile> gatherFiles=gatherFileRepository.findMaterailBySchoolId(schoolId);
        return gatherFiles.size()>0?Result.success("查询成功",gatherFiles):
                Result.success("暂时无实习资料，请上传实习资料！");
    }
    

    @Override
    public Result downLoadFile(String fileName) {
        InputStream inputStream = new AliyunOssUtil().downLoadFile(fileName);
        return inputStream!=null? Result.success("下载成功！",inputStream):
                Result.error(Code.DOWN_FILE_ERR,"文件不存在！");

    }

    @Override
    public List<ExportExcelModel> writeExcel(String teacherId) throws Exception {
        List<Studentinfo> studentinfos = studentinfoRepository.queryStudentInfoByTeacherId(teacherId);
        if (studentinfos.size()>0){
            List<ExportExcelModel> excelModels=new ArrayList<>();

            for (Studentinfo info : studentinfos) {
                ExportExcelModel excelModel=new ExportExcelModel();
               excelModel.setUsername(info.getUsername());
               excelModel.setName(info.getName());
               excelModel.setGrade("测绘"+info.getGrade()+"班");
                //查询学生互评记录
                MutualEvaluation mutualInfo = mutualEvaluationRepository.findStudentMutualInfoById(info.getId());
                excelModel.setAvgStar(mutualInfo!=null?mutualInfo.getAvgStar():"无");
                //查询签到记录
                excelModel.setNormalStatusAll(String.valueOf(signInRepository.findSignInByStudentId(info.getId())!=null?signInRepository.findSignInByStudentId(info.getId()).getNormalStatusAll():-1));
                excelModel.setLateStatusAll(String.valueOf(signInRepository.findSignInByStudentId(info.getId())!=null?signInRepository.findSignInByStudentId(info.getId()).getLateStatusAll():-1));
                excelModel.setNormalFieldAll(String.valueOf(signInRepository.findSignInByStudentId(info.getId())!=null?signInRepository.findSignInByStudentId(info.getId()).getNormalFieldAll():-1));
                excelModel.setLateFieldAll(String.valueOf(signInRepository.findSignInByStudentId(info.getId())!=null?signInRepository.findSignInByStudentId(info.getId()).getLateFieldAll():-1));
                //是否有高相似度的查重记录
                List<CnkiHighSim> cnkis = cnkiHighSimRepository.findCnkiInfoListByUsername(info.getUsername());
                if (cnkis.size()>0){
                    excelModel.setIsHighSim("有");
                }else {
                    excelModel.setIsHighSim("没有");
                }

                //教师评定的最终成绩
                TeacherMark mark = markRepository.findTeacherMarkInfoById(info.getId());
                if (mark==null){
                    excelModel.setMark("暂无成绩");
                }else {
                    if (mark.getRank()==5){
                        excelModel.setMark("优秀");
                    }else if (mark.getRank()==4){
                        excelModel.setMark("良好");
                    }else if (mark.getRank()==3){
                        excelModel.setMark("中等");
                    }else if (mark.getRank()==2){
                        excelModel.setMark("合格");
                    }else {
                        excelModel.setMark("不合格");
                    }
                }
               excelModels.add(excelModel);
            }
            return excelModels;
        }
        return null;
    }


}
