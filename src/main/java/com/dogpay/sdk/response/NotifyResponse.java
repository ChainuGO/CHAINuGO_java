package com.dogpay.sdk.response;

public class NotifyResponse {

    private Integer code; // 处理是否成功
    private String msg; // 处理后消息提示
    private Object data; // 返回数据

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public NotifyResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public NotifyResponse(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static NotifyResponse ok(Object data) {
        return new NotifyResponse(0, "success", data);
    }

    public static NotifyResponse fail(String msg) {
        return new NotifyResponse(-1, msg);
    }
}
