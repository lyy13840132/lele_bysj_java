package com.university.lele.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.university.lele.global.URL;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    public static String sendPost_inputStream(String url, JSONObject jsonObject,String userId) throws ParseException, IOException {
        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        //装填参数
        StringEntity s = new StringEntity(jsonObject.toString(), "utf-8");
        s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                "application/json"));
        //设置参数到请求对象中
        httpPost.setEntity(s);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体
        org.apache.http.HttpEntity entity = response.getEntity();
        String qrUrl=null;
        InputStream content = entity.getContent();
        try {
             qrUrl = new AliyunOssUtil().upLoadImageToOSS(content, URL.QR_BASE_QR_URL,"qr_code/",userId);

        } catch (Exception e) {
            e.printStackTrace();
            qrUrl=e.getMessage();

        }
        response.close();
        return qrUrl;
    }




    public static void postRequest() {
        String url = "";
        //        请求表
        JSONObject paramMap = new JSONObject();
        paramMap.put("id", 93);
        //        请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("user-agent", "Mozilla/5.0 WindSnowLi-Blog");
        //        整合请求头和请求参数
        HttpEntity<JSONPObject> httpEntity = new HttpEntity(paramMap, headers);
        //JSONPObject body1 = httpEntity.getBody();
        //         请求客户端
        RestTemplate client = new RestTemplate();
        //      发起请求
        JSONObject body = client.postForEntity(url, httpEntity, JSONObject.class).getBody();
        System.out.println("******** POST请求 *********");
        assert body != null;
        //System.out.println(body.toJSONString());
    }

    /**
     * Spring Boot Get请求
     */

    public static String getRequest(String url) {
        //String url = "https://www.blog.firstmeet.xyz/";
        //  请求客户端
        RestTemplate client = new RestTemplate();
        // 发起请求
        String body = client.getForEntity(url, String.class).getBody();
        assert body != null;
        return body;
       // System.out.println(body);
    }




    public static String doGet(String url)   {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                org.apache.http.HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, "UTF-8");
                EntityUtils.consume(entity);
                httpclient.close();
                return result;
            }
            httpclient.close();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return  null;
    }



    public static String doPost(String url, Map<String,String> paramMap)   {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建http Post请求
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {

            if(paramMap!=null){
                List<BasicNameValuePair> list=new ArrayList<>();
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    list.add(new BasicNameValuePair(entry.getKey(),entry.getValue())) ;
                }
                UrlEncodedFormEntity httpEntity=new UrlEncodedFormEntity(list,"utf-8");
                httpPost.setEntity(httpEntity);
            }

            // 执行请求
            response = httpclient.execute(httpPost);

            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                org.apache.http.HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, "UTF-8");
                EntityUtils.consume(entity);
                httpclient.close();
                return result;
            }
            httpclient.close();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

        return  null;
    }
}
