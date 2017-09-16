package com.chaoteng.hf.network;

import com.chaoteng.hf.HappyFarmApp;
import com.chaoteng.hf.network.api.AccountService;
import com.chaoteng.hf.utils.CommonUtil;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xuxiaoqiang on 2017/9/4.
 */

public class RetrofitHelper {

	private static OkHttpClient mOkHttpClient;

	static {
		initOkHttpClient();
	}


	public static AccountService getAccountService(){
		return createApi(AccountService.class, ApiConstants.BASEURL);
	}




















































	/**
	 * 根据传入的baseUrl，和api创建retrofit
	 */
	private static <T> T createApi(Class<T> clazz, String baseUrl) {

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(baseUrl)
				.client(mOkHttpClient)
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		return retrofit.create(clazz);
	}






	/**
	 * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,设置UA拦截器
	 */
	private static void initOkHttpClient() {

		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		if (mOkHttpClient == null) {
			synchronized (RetrofitHelper.class) {
				if (mOkHttpClient == null) {
					//设置Http缓存
					Cache cache = new Cache(new File(HappyFarmApp.getInstance()
							.getCacheDir(), "HttpCache"), 1024 * 1024 * 10);

					mOkHttpClient = new OkHttpClient.Builder()
//							.cache(cache)
							.addInterceptor(interceptor)
//							.addNetworkInterceptor(new CacheInterceptor())
//							.addNetworkInterceptor(new StethoInterceptor())
							.retryOnConnectionFailure(true)
							.connectTimeout(30, TimeUnit.SECONDS)
							.writeTimeout(20, TimeUnit.SECONDS)
							.readTimeout(20, TimeUnit.SECONDS)
							.addInterceptor(new UserAgentInterceptor())
							.build();
				}
			}
		}
	}


	/**
	 * 为okhttp添加缓存
	 */
	private static class CacheInterceptor implements Interceptor {

		@Override
		public Response intercept(Chain chain) throws IOException {

			// 有网络时 设置缓存超时时间1个小时
			int maxAge = 60 * 60;
			// 无网络时，设置超时为1天
			int maxStale = 60 * 60 * 24;
			Request request = chain.request();
			if (CommonUtil.isNetworkAvailable(HappyFarmApp.getInstance())) {
				//有网络时只从网络获取
				request = request.newBuilder()
						.cacheControl(CacheControl.FORCE_NETWORK)
						.build();
			} else {
				//无网络时只从缓存中读取
				request = request.newBuilder()
						.cacheControl(CacheControl.FORCE_CACHE)
						.build();
			}
			Response response = chain.proceed(request);
			if (CommonUtil.isNetworkAvailable(HappyFarmApp.getInstance())) {
				response = response.newBuilder()
						.removeHeader("Pragma")
						.header("Cache-Control", "public, max-age=" + maxAge)
						.build();
			} else {
				response = response.newBuilder()
						.removeHeader("Pragma")
						.header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
						.build();
			}
			return response;
		}
	}

	/**
	 * 添加UA拦截器
	 */
	private static class UserAgentInterceptor implements Interceptor {

		@Override
		public Response intercept(Chain chain) throws IOException {

			Request originalRequest = chain.request();
			Request requestWithUserAgent = originalRequest.newBuilder()
					.removeHeader("User-Agent")
					.addHeader("User-Agent", ApiConstants.COMMON_UA_STR)
					.build();
			return chain.proceed(requestWithUserAgent);
		}
	}
}
