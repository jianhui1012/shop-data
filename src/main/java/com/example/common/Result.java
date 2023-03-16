package com.example.common;

public class Result<T> {
    private String code;
    private String msg;
    private T data;
    public static int SUCCESS_CODE = 200;
    public static int FAIL_CODE = 200;
    public static String SUCCESS_STR = "成功";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public static Result success() {
        Result result = new Result<>();
        result.setCode(String.valueOf(SUCCESS_CODE));
        result.setMsg(SUCCESS_STR);
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(data);
        result.setCode(String.valueOf(SUCCESS_CODE));
        result.setMsg(SUCCESS_STR);
        return result;
    }

    public static Result error(int code, String msg) {
        Result result = new Result();
        result.setCode(String.valueOf(code));
        result.setMsg(msg);
        return result;
    }
}
