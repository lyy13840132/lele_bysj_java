package com.university.lele.global;

public interface URL {
    /**
     * 生成OssToken的url
     */
    String GET_QR_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
            +MyKEY.QR_MYAPP_ID+"&secret="+MyKEY.QR_SECRET;

    String QR_BASE_URL="https://"+MyKEY.OSS_BACKET_NAME+".oss-cn-beijing.aliyuncs.com/qr_code/";
    /**
     * 头像url
     */
    String QR_BASE_HEAD_URL="https://"+MyKEY.OSS_BACKET_NAME+".oss-cn-beijing.aliyuncs.com/"+MyKEY.OSS_HEAD;

    /**
     * 二维码url
     */
    String QR_BASE_QR_URL="https://"+MyKEY.OSS_BACKET_NAME+".oss-cn-beijing.aliyuncs.com/"+MyKEY.OSS_QR_CODE;
    /**
     * 二维码在阿里云文件夹的完整路径
     */
    String QR_URL="oss-cn-beijing.aliyuncs.com/qr_code/";


}
