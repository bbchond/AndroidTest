package com.chaoteng.hf.http.httpResponse;

import com.chaoteng.hf.utils.JsonToString;

/**
 * Created by qinwei on 2017/9/5.
 */

public class ResponseHead extends JsonToString{
    private String success;
    private String code;
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
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
}
