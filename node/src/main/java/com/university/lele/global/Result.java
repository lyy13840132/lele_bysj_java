package com.university.lele.global;

import com.university.lele.enums.Code;

public class Result {

    private String code;//执行结果，true为执行成功 false为执行失败
    private String message;//返回结果信息，主要用于页面提示信息
    private Object data;//返回数据

    public static Result success(String msg){
        return new Result(Code.OK,msg);
    }

    public static Result success(String msg,Object data){
        return new Result(Code.OK,msg,data);
    }

    public static Result error(String message){
        return new Result(Code.LOGIN_ERR,message);
    }
    public static Result error(String code,String message){
        return new Result(code,message);
    }

    public Result(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public Result(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
}
