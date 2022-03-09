package com.university.lele.global;

public interface MyKEY {
    /**
     * 教师类型的帐号
     */
    int TYPE_TEACHER = 2;
    /**
     * 学生类型的帐号
     */
    int TYPE_STUDENT = 3;

    /**
     * 超级管理员帐号
     */
    String ADMIN = "admin";
    /**
     * 超级管理员密码
     */
    String PASSWORD = "123456";
    /**
     * bmob中的appid和KEY
     */
    String BMOB_ID = "50066077f442afd1e4d4c987751acc49";
    String BMOB_KEY = "50066077f442afd1e4d4c987751acc49";


    //阿里云数据库
    String OSS_ENDPOINT = "oss-cn-beijing.aliyuncs.com";
    String OSS_ACCESS_KEY_ID = "LTAI5t5yAftvWNZhH3V5SNX9";
    String OSS_ACCESS_KEY_SECRE = "EHco7RelSsHIYlZlTFfPRsx54fiU99";
    String OSS_BACKET_NAME = "wanqile";
    String OSS_HEAD = "mobile_head/";//账户头像存储地址
   String OSS_QR_CODE="qr_code/";//二维码文件夹
    String OSS_DATAFILE_FOLDER = "dataFile/";//实习资料文件夹



    //小程序二维码
    String QR_CODE_BASE_URL="https://api.weixin.qq.com/wxa/getwxacode?access_token=";
    String QR_MYAPP_ID="wx8d47784eb393a5e4";
    String QR_SECRET="600a22d91033e499a09b917cb0a8ec0f";
    String QR_CODE_JSON = "{'path':'page/index/index?scene=lyy','width':'430'}";

    String SESSION_STUDENT_IS = "student_id";
    String SESSION_USERNAME = "username";
    String SCHOOL_PASSWORD = "123456";
    String RECORD_FORDER = "record/";
    String OSS_REOCRD = "record/";
    int NORMAL_STATUS = 0;
    int LATE_STATUS = 1;
    int NORMAI_FIELD = 2;
    int LATE_FIELD = 3;

    //评分等级优秀、良好、中等、及格、不及格
    int MARK_STATUS_EXCELLENT=5;//优秀
    int MARK_STATUS_GOOD=4;//良好
    int MARK_STATUS_MEDIUM=3;//中等
    int MARK_STATUS_PASSES=2;//及格
    int MARK_STATUS_NO_PASSES=1;//不及格


    String HIGH_PLAGIARISM = "HIGH_PLAGIARISM";
    String HIGH_PLAGIARISM_NO = "HIGH_PLAGIARISM_NO";


     static final String TEACHER_MARK_COLLECT_EXCEL = "成绩汇总";
}

