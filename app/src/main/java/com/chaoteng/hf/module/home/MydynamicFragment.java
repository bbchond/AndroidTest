package com.chaoteng.hf.module.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaoteng.hf.R;
import com.chaoteng.hf.module.base.BaseFragment;

/**
 * Created by xuxiaoqiang on 2017/8/17.
 */

public class MydynamicFragment extends BaseFragment {
	@Override
	public int getLayoutResId() {
		return R.layout.fragment_home_mydynamic;
	}


	public static Fragment newInstance() {
		return new MydynamicFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(getLayoutResId(), container, false);
		return rootView;
	}
}
