package cn.xiaofuge.x.api.response;

import java.io.Serializable;

public class Response<T> implements Serializable {

    private static final long serialVersionUID = 7000723935764546321L;

    private String code;
    private String info;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setCode("0000");
        response.setInfo("success");
        response.setData(data);
        return response;
    }

    public static <T> Response<T> error(String info) {
        Response<T> response = new Response<>();
        response.setCode("0001");
        response.setInfo(info);
        return response;
    }
}
