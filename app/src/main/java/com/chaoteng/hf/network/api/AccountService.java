package com.chaoteng.hf.network.api;

import com.chaoteng.hf.entity.User;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface AccountService {

  /**
   * 获取验证码
   * @param phone
   * @return
   */
  @FormUrlEncoded
  @POST("/api/msg/code")
  Observable<User> getVerifyCode(@Field("phone") String phone);

  @FormUrlEncoded
  @POST("/api/login")
  Observable<User> logingByMsg(@Field("phone") String phone,@Field("code") String code);
}
