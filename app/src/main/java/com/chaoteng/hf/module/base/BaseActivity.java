package com.chaoteng.hf.module.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chaoteng.hf.R;
import com.jaeger.library.StatusBarUtil;

/**
 * Created by xuxiaoqiang on 2017/8/14.
 */

public class BaseActivity extends AppCompatActivity{

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
		super.onCreate(savedInstanceState, persistentState);
		setStatusBar();
	}

	protected void setStatusBar() {
		StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
	}
}
