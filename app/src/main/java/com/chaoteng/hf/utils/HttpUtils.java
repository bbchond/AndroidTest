package com.chaoteng.hf.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.BuildConfig;
import rx.schedulers.Schedulers;

/**
 * Http 助手类
 * 采用Retrofit2+okhttp3作为http的基础库
 * Created by qinwei on 2017/8/15.
 */

public class HttpUtils {
    private static Retrofit retrofit_hf;

    static{
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new OkHttpLogger());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Add the interceptor to OkHttpClient
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();

        retrofit_hf = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .baseUrl("http://business-api.ucancho.com")
                .client(client)
                .build();
    }

    public static <T> T hfService(final Class<T> type) {
        return retrofit_hf.create(type);
    }

    public static class OkHttpLogger implements HttpLoggingInterceptor.Logger {
        private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

        @Override
        public void log(String message) {
            logger.info(message);
        }
    }
}
