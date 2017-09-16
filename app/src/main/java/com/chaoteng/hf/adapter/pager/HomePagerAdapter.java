package com.chaoteng.hf.adapter.pager;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chaoteng.hf.R;
import com.chaoteng.hf.module.home.MyFarmFragment;
import com.chaoteng.hf.module.home.MydynamicFragment;

/**
 * Created by hcc on 16/8/4 14:12
 * 100332338@qq.com
 * <p/>
 * 主界面Fragment模块Adapter
 * 部分用于HomeFragment.java文件
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

  private final String[] TITLES;

  private Fragment[] fragments;


  public HomePagerAdapter(FragmentManager fm, Context context) {

    super(fm);
    TITLES = context.getResources().getStringArray(R.array.sections);
    fragments = new Fragment[TITLES.length];
  }


  @Override
  public Fragment getItem(int position) {

    if (fragments[position] == null) {
      switch (position) {
        case 0:
          fragments[position] = MyFarmFragment.newIntance();
          break;
        case 1:
          fragments[position] = MydynamicFragment.newInstance();
          break;
        default:
          break;
      }
    }
    return fragments[position];
  }


  @Override
  public int getCount() {

    return TITLES.length;
  }


  @Override
  public CharSequence getPageTitle(int position) {

    return TITLES[position];
  }
}
