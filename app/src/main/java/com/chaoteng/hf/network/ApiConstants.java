package com.chaoteng.hf.network;

import android.os.Build;
import android.webkit.WebSettings;

import com.chaoteng.hf.HappyFarmApp;

/**
 * Created by xuxiaoqiang on 2017/9/4.
 */

public class ApiConstants {

	public static final String COMMON_UA_STR = getUserAgent();

//	public static final String BASEURL = "http://farm.565tech.com/";
	public static final String BASEURL = "http://101.132.33.39:8080";


	private static String getUserAgent() {
		String userAgent = "";
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			try {
				userAgent = WebSettings.getDefaultUserAgent(HappyFarmApp.getInstance());
			} catch (Exception e) {
				userAgent = System.getProperty("http.agent");
			}
		} else {
			userAgent = System.getProperty("http.agent");
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0, length = userAgent.length(); i < length; i++) {
			char c = userAgent.charAt(i);
			if (c <= '\u001f' || c >= '\u007f') {
				sb.append(String.format("\\u%04x", (int) c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

}
