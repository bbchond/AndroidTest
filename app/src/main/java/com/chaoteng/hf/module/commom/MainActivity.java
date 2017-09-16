package com.chaoteng.hf.module.commom;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.chaoteng.hf.R;
import com.chaoteng.hf.module.home.HomeFragment;
import com.zaaach.citypicker.CityPickerActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;//侧滑界面
    HomeFragment mHomeFragment;//主界面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        //初始化Fragment
        initFragments();
        //初始化侧滑菜单
        initNavigationView();
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void initViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
    }

    /**
     * 侧滑界面
     */
    private void initNavigationView() {
        mNavigationView.setItemIconTintList(null);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void initFragments() {
        mHomeFragment = new HomeFragment();
        // 添加显示第一个fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, mHomeFragment)
                .show(mHomeFragment).commit();
    }

    /**
     * 侧滑界面点击事件
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_home:
                //订单记录

                mDrawerLayout.closeDrawers();
                break;
            case R.id.item_vip:
                //地址管理
                Intent intent1 = new Intent(MainActivity.this,AreaChoose.class);
                startActivity(intent1);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.item_download:
                //农场信息

                mDrawerLayout.closeDrawers();
                break;
            case R.id.item_favourite:
                //意见反馈

                mDrawerLayout.closeDrawers();
                break;
            case R.id.item_history:
                //账户设置

                mDrawerLayout.closeDrawers();
                break;
            case R.id.item_group:
                //客服电话

                mDrawerLayout.closeDrawers();
                break;
            case R.id.item_tracker:
                //帮助

                mDrawerLayout.closeDrawers();
                break;
            default:
        }
        return true;
    }

    /**
     * DrawerLayout侧滑菜单开关
     */
    public void toggleDrawer() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

}
