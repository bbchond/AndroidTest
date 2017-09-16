package com.chaoteng.hf;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by xuxiaoqiang on 2017/9/4.
 */

public class HappyFarmApp extends Application {

	private static HappyFarmApp happyFarmApp;

	public static HappyFarmApp getInstance() {
		return happyFarmApp;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Stetho.initialize(
				Stetho.newInitializerBuilder(this)
						.enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
						.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
						.build());
		happyFarmApp = this;
	}
}
