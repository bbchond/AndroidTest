package com.chaoteng.hf.module.home;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.chaoteng.hf.adapter.pager.HomePagerAdapter;
import com.chaoteng.hf.module.AppBarStateChangeListener;
import com.chaoteng.hf.module.base.BaseFragment;
import com.chaoteng.hf.module.commom.MainActivity;
import com.chaoteng.hf.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.socks.library.KLog;

import java.math.BigDecimal;

/**
 * 主界面
 * Created by xuxiaoqiang on 2017/8/14.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private ImageView img_tog;//标题按钮
    private ImageView iv_main_pic;//主界面图片
    private SlidingTabLayout mSlidingTab;
    private ViewPager mViewPager;
    private AppBarLayout mAppBarLayout;
    private View divider;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutResId(), container, false);
        initView(rootView);
        return rootView;
    }

    /**
     * 初始化
     */
    private void initView(View view) {
        img_tog = (ImageView) view.findViewById(R.id.img_tog);
        iv_main_pic = (ImageView) view.findViewById(R.id.iv_main_pic);
        mSlidingTab = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mAppBarLayout = (AppBarLayout) view.findViewById(R.id.appBarLayout);
        divider = view.findViewById(R.id.divider);

        //设置主界面上半部分主要图片
        iv_main_pic.setImageResource(R.mipmap.index_cover);

        img_tog.setOnClickListener(this);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                KLog.d("STATE", state.name());
                if( state == State.EXPANDED ) {

                    //展开状态

                }else if(state == State.COLLAPSED){

                    //折叠状态
                    divider.setVisibility(View.VISIBLE);
                }else {

                    //中间状态
                    divider.setVisibility(View.GONE);
                }
            }
        });


        setPicByScreenWidth();// 缩放图片

        initViewPager();

        iv_main_pic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        float x = event.getX();
                        float y = event.getY();
                        Log.e("qinwei------>", "x:" + x + "; y:" + y);
                        if(sub(x,1000.00) && sub(y,1200.00) && sub(800.00,x) && sub(1000.00,y)){
                            Toast.makeText(getActivity(),"种地",Toast.LENGTH_LONG).show();
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    public static boolean sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b2.compareTo(b1) == 1;
    }

    private void initViewPager() {

        HomePagerAdapter mHomeAdapter = new HomePagerAdapter(getChildFragmentManager(),
                getActivity());
        mViewPager.setAdapter(mHomeAdapter);
        mSlidingTab.setViewPager(mViewPager);
    }

    /**
     * 图片根据屏幕宽度等比例缩放
     */
    private void setPicByScreenWidth() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.index_cover);
        WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        // 获取屏幕的默认分辨率
        Display display = manager.getDefaultDisplay();
        int bwidth = bitmap.getWidth();
        int bHeight = bitmap.getHeight();

        int width = display.getWidth();
        int height = width * bHeight / bwidth;
        ViewGroup.LayoutParams para = iv_main_pic.getLayoutParams();
        para.height = height;
        iv_main_pic.setLayoutParams(para);
        bitmap.recycle();
        bitmap = null;
    }

    /**
     * 打开侧滑功能
     * */
    void toggleDrawer() {
        Activity activity = getActivity();
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).toggleDrawer();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_tog:
                toggleDrawer();
                break;
        }
    }
}
