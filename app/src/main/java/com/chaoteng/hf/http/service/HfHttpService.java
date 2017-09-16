package com.chaoteng.hf.http.service;

import com.chaoteng.hf.http.httpRequest.LoginRequest;
import com.chaoteng.hf.http.httpResponse.LoginResponse;
import com.chaoteng.hf.utils.HttpUtils;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by qinwei on 2017/8/15.
 */

public interface HfHttpService {
    public static HfHttpService INSTANCE = HttpUtils.hfService(HfHttpService.class);

    @POST("/")
    public Observable<LoginResponse> login(@Body LoginRequest request);

    @FormUrlEncoded
    @POST("/")
    public Observable<LoginResponse> login(@Field("apiname") String apiname,@Field("method") String method,
                                           @Field("username") String username,@Field("password") String password);
}
