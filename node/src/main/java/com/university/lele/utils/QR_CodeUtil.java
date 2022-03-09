package com.university.lele.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.university.lele.account.entity.Studentinfo;
import com.university.lele.account.repositity.StudentinfoRepository;
import com.university.lele.global.MyKEY;
import com.university.lele.global.Result;
import com.university.lele.global.URL;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class QR_CodeUtil {
    @Autowired
    StudentinfoRepository studentinfoRepository;
    /**
     * 上传微信二维码图片
     * 查询阿里云文件夹是否有该账户的二维码，如果有就返回url，没有就生成再返回url
     * @return
     */
    @RequestMapping(value = "/upload/QR_Code/{id}",method = RequestMethod.GET)
    public Result uploadQR_Code(@PathVariable("id") String id){

        try {
            //获取token
            String token = HttpUtil.getRequest(URL.GET_QR_TOKEN_URL);
            JSONObject jsonToken = JSON.parseObject(token);
            //处理json字符串
            JSONObject jsonPath = JSONObject.parseObject(MyKEY.QR_CODE_JSON);
            jsonPath.put("path","/pages/login/index?scene="+id);
            String qrUrl = HttpUtil.sendPost_inputStream(MyKEY.QR_CODE_BASE_URL + jsonToken.get("access_token"),
                    JSON.parseObject(jsonPath.toJSONString()), id);
            //将qrUrl 保存至数据库
            Studentinfo studentinfo = studentinfoRepository.queryStudentinfoByStudent_id(id);
            if (studentinfo!=null){
                studentinfo.setQrCode(qrUrl);
                studentinfoRepository.save(studentinfo);
            }

            return Result.success("二维码创建成功并上传",qrUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("二维码创建失败"+e.getMessage());
        }

    }
}
