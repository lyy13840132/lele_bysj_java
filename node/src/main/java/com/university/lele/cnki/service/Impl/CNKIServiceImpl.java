package com.university.lele.cnki.service.Impl;

import com.university.lele.account.entity.Studentinfo;
import com.university.lele.account.repositity.StudentinfoRepository;
import com.university.lele.cnki.entity.Cnki;
import com.university.lele.cnki.entity.CnkiHighSim;
import com.university.lele.cnki.ik.IKWordSegmentation;
import com.university.lele.cnki.model.CnkiResult;
import com.university.lele.cnki.model.StatisticModel;
import com.university.lele.cnki.repositity.CnkiHighSimRepository;
import com.university.lele.cnki.repositity.CnkiRepository;
import com.university.lele.cnki.service.CnkiService;
import com.university.lele.cnki.similarity.CosineSimilarity;
import com.university.lele.cnki.similarity.Jaccard;
import com.university.lele.enums.Code;
import com.university.lele.global.MyKEY;
import com.university.lele.global.Result;
import com.university.lele.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.TextUtils;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CNKIServiceImpl implements CnkiService {
    @Autowired
    StudentinfoRepository studentinfoRepository;
    @Autowired
    CnkiRepository cnkiRepository;
    @Autowired
    CnkiHighSimRepository cnkiHighSimRepository;
    /**
     * 上传文件并开始文档查重
     * @param files
     * @return
     */
    @Override
    public Result cnkiFiles(MultipartFile[] files,double simThre,String teacherId) {
        List<MultipartFile> multipartFiles=new ArrayList<>();
        multipartFiles.addAll(Arrays.asList(files));
        for (int i=0;i<files.length;i++){
            if (!files[i].getOriginalFilename().endsWith(".doc")&!files[i].getOriginalFilename().endsWith(".docx")) {
                return Result.error(Code.CNKI_FILE_TYPE_ERR,"此文件夹有除word以外的其他文件！系统只能针对word文档进行查重...");
            }else if (files[i].getOriginalFilename().indexOf("_")!=10){
                return Result.error(Code.CNKI_FILENAME_ERR,"文件夹中文件名称存在错误的命名规则，请查看。正确的命名规则为：2018050007_万**.docx/doc");
            }
        }
        IKWordSegmentation ikWordSegmentation = new IKWordSegmentation();
        CosineSimilarity cosineSimilarity = new CosineSimilarity(); // 余弦相似度
        Jaccard jaccard = new Jaccard(); // Jaccard相似度
        List<CnkiResult> results = new ArrayList<>(); // 将结果类存入List
        for (int i = 0; i < multipartFiles.size() - 1; i++) {
            List<Double> simMax = new ArrayList<Double>();
            for (int j = i + 1; j < multipartFiles.size(); j++) {
                String str1 = readWordtxt(multipartFiles.get(i));
                String str2 = readWordtxt(multipartFiles.get(j));
                str1 = str1.replaceAll("[0-9a-zA-Z]", ""); // 去除数字和字母
                str2 = str2.replaceAll("[0-9a-zA-Z]", "");
                str1 = str1.replaceAll("[0-9a-zA-Z]", ""); // 去除数字和字母
                str2 = str2.replaceAll("[0-9a-zA-Z]", "");
                List<String> list1 = ikWordSegmentation.segStr(str1, true); // 使用IK分词器分词
                List<String> list2 = ikWordSegmentation.segStr(str2, true);
                double conSim = cosineSimilarity.sim(list1, list2); // 余弦相似度
                double JaccSim = jaccard.jaccardSimilarity(list1, list2);
                CnkiResult cnkiResult = new CnkiResult();// 每次都是新的结果
                String fileName1 = getFileName(multipartFiles.get(i));
                String fileName2 = getFileName(multipartFiles.get(j));
                String crib =MyKEY.HIGH_PLAGIARISM_NO;
                double sim = 0; // 存最终结果
                double txtsim = (conSim + JaccSim) / 2;
                sim = (Math.pow(txtsim, 1.5));// 将文本相似度结果平方，，调整相似度
                simMax.add(sim);
                if (sim > simThre || JaccSim > 0.90 || conSim > 0.95) {
                    crib = MyKEY.HIGH_PLAGIARISM;
                }
                String username1 = fileName1.split("_")[0];
                String username2 = fileName2.split("_")[0];
                String name1 = fileName1.split("_")[1].split("\\.")[0];
                String name2 = fileName2.split("_")[1].split("\\.")[0];
                cnkiResult.setUsername1(username1);
                cnkiResult.setUsername2(username2);
                cnkiResult.setName1(name1);
                cnkiResult.setName2(name2);
                cnkiResult.setConSim(conSim);
                cnkiResult.setJaccard_sim(JaccSim);
                cnkiResult.setSim(sim);
                cnkiResult.setCrib(crib);
                results.add(cnkiResult);
            }
        }
        //区分抄袭与自创
       List<Cnki> cnkis=new ArrayList<>();
        for (CnkiResult cnkiResult : results) {
            Cnki cnki=new Cnki();
            cnki.setId(Utils.getUUID());
            cnki.setUsername1(cnkiResult.getUsername1());
            cnki.setUsername2(cnkiResult.getUsername2());
            cnki.setTeacherId(teacherId);
            cnki.setName1(cnkiResult.getName1());
            cnki.setName2(cnkiResult.getName2());
            cnki.setSim(String.valueOf(cnkiResult.getSim()));
            cnki.setConSim(String.valueOf(cnkiResult.getConSim()));
            cnki.setJaccardSim(String.valueOf(cnkiResult.getJaccard_sim()));
            cnki.setCrib(cnkiResult.getCrib().equals("HIGH_PLAGIARISM")?1:0);
            CnkiHighSim cnkiHighSim=new CnkiHighSim();
            if (cnkiResult.getSim() > simThre || cnkiResult.getJaccard_sim() > 0.90 || cnkiResult.getConSim() > 0.95) {
                cnki.setSimRate(1);
                cnkiHighSim.setUsername(cnkiResult.getUsername1());
                cnkiHighSim.setUsernameSim(cnkiResult.getUsername2());
                cnkiHighSim.setName(cnkiResult.getName1());
                cnkiHighSim.setNameSim(cnkiResult.getName2());
                cnkiHighSim.setSim(String.valueOf(cnkiResult.getSim()));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间
                cnkiHighSim.setDate(sdf.format(new Date()).trim());
                cnkiHighSimRepository.save(cnkiHighSim);
            }else {
                cnki.setSimRate(0);
            }
            //将数据保存到数据库
            cnkiRepository.save(cnki);
            cnkis.add(cnki);
        }

        return Result.success("报告查重成功",cnkis);
    }
    @Override
    public Result finaHighPlagiarismByUsername(String username) {
        List<CnkiHighSim> all=new ArrayList<>();
        List<CnkiHighSim> cnkiHighSims=cnkiHighSimRepository.findCnkiInfoListByUsername(username);
        List<CnkiHighSim> cnkiHighSimsSim= cnkiHighSimRepository.findCnkiInfoListByUsernameSim(username);

        if (cnkiHighSims.size()>0){
            all.addAll(cnkiHighSims);

        }
        if (cnkiHighSimsSim.size()>0){
            for (CnkiHighSim c : cnkiHighSimsSim) {
                CnkiHighSim cnkiHighSim=new CnkiHighSim();
                cnkiHighSim.setUsername(c.getUsernameSim());
                cnkiHighSim.setName(c.getNameSim());
                cnkiHighSim.setUsernameSim(c.getUsername());
                cnkiHighSim.setNameSim(c.getName());
                cnkiHighSim.setSim(c.getSim());
                cnkiHighSim.setId(c.getId());
                all.add(cnkiHighSim);
            }
        }
        return cnkiHighSims.size()>0?Result.success("查询成功",all):Result.success("暂无数据");
    }

    @Override
    public Result deleteAll(String teacherId) {
        List<Cnki> cnkis = cnkiRepository.findAllCnkiInfoListByTeacherId(teacherId);
        for (Cnki c : cnkis) {
            cnkiRepository.delete(c);
        }
        return Result.success("清空成功！");
    }

    @Override
    public Result findCnkiInfo(String teacherId, String username, Integer crib) {
        List<Cnki> cnkis=new ArrayList<>();

        if (!TextUtils.isEmpty(username)){
            if (crib==null){

                List<Cnki> cnkis1=cnkiRepository.findCnkiInfoListByUsername(teacherId,username);
                System.out.println(cnkis1.size());
                List<Cnki> usernameSims = cnkiRepository.findCnkiInfoListByUsernameSim(teacherId,username);
                System.out.println(usernameSims.size());
                if (cnkis1.size()>0){
                    cnkis.addAll(cnkis1);
                }
                if (usernameSims.size()>0){
                    cnkis.addAll(usernameSims);
                }

                return cnkis.size()>0?Result.success("查询成功",cnkis):Result.success("暂无数据！");


            }else {
                List<Cnki> cnkiInfos = cnkiRepository.findCnkiInfoByUserNameAndCrib(teacherId, username, crib);
                System.out.println(cnkiInfos.size());

                List<Cnki> cnkiInfoSims = cnkiRepository.findCnkiInfoByUserNameSimAndCrib(teacherId, username, crib);
                System.out.println(cnkiInfoSims.size());
                if (cnkiInfos.size()>0){
                    cnkis.addAll(cnkiInfos);
                }
                if (cnkiInfoSims.size()>0){
                    cnkis.addAll(cnkiInfoSims);
                }
                return cnkis.size()>0?Result.success("查询成功",cnkis):Result.success("暂无数据！");
            }

        }else if (crib==null){
             cnkis=cnkiRepository.findAllCnkiInfoListByTeacherId(teacherId);
            return cnkis.size()>0?Result.success("查询成功",cnkis):Result.success("暂无数据！");

        }else {
            cnkis=cnkiRepository.findCnkiInfoByCrib(teacherId,crib);
            return cnkis.size()>0?Result.success("查询成功",cnkis):Result.success("暂无数据！");
        }
    }

    @Override
    public Result statisticsData(String teacherId) {
        List<Studentinfo> studentinfos = studentinfoRepository.queryStudentInfoByTeacherId(teacherId);
        StatisticModel statisticModel=new StatisticModel();
        statisticModel.setAllNum(studentinfos.size());
        int hignNum=0;
        int cnkinum=0;
        if (studentinfos.size()>0){
            for (Studentinfo s:studentinfos){
                List<CnkiHighSim> highSimNum = cnkiHighSimRepository.findCnkiInfoListByUsername(s.getUsername());
                if (highSimNum.size()>0){
                    ++hignNum;
                }
                List<Cnki> cnkiNums = cnkiRepository.findCnkiInfoListByUsernameSim(teacherId, s.getUsername());
                if (cnkiNums.size()>0){
                    ++cnkinum;
                }
            }
            statisticModel.setCnkiNum(cnkinum);
            statisticModel.setQualifiedNum(cnkinum-hignNum);
            statisticModel.setHighSimNum(hignNum);
            return Result.success("Ok",statisticModel);
        }else {
            return Result.success("暂无数据");
        }


    }

    /**
     * 读取Word文档中的文字值
     *
     * @return 给定Word文档中的文字内容
     */
    public String readWordtxt(MultipartFile file) {
        String buffer = "";
        try {
            if (file.getOriginalFilename().endsWith(".doc")) {
                WordExtractor ex = new WordExtractor(file.getInputStream());
                buffer = ex.getText();
                ex.close();
            } else if (file.getOriginalFilename().endsWith(".docx")) {

                XWPFDocument xdoc = new XWPFDocument(file.getInputStream());
                XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
                buffer = extractor.getText();
                extractor.close();
            } else {
                System.out.println("此文件不是word文件！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }
    public String getFileName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        return originalFilename.split("//.")[0];
    }

}
