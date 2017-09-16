package com.chaoteng.hf.utils;

import com.google.gson.Gson;

/**
 * Created by qinwei on 2017/9/5.
 */

public class JsonToString {
    private static Gson _gson=new Gson();
    @Override
    public String toString() {
        return _gson.toJson(this);
    }
}
