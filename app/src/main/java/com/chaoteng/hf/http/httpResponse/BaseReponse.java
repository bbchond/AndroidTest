package com.chaoteng.hf.http.httpResponse;

import com.chaoteng.hf.utils.JsonToString;

/**
 * Created by qinwei on 2017/9/5.
 */

public class BaseReponse<BOTY_TYPE> extends JsonToString {
    private ResponseHead responseHead;
    private BOTY_TYPE body;

    public ResponseHead getResponseHead() {
        return responseHead;
    }

    public void setResponseHead(ResponseHead responseHead) {
        this.responseHead = responseHead;
    }

    public BOTY_TYPE getBody() {
        return body;
    }

    public void setBody(BOTY_TYPE body) {
        this.body = body;
    }
}
