package com.chaoteng.hf.module.commom;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaoteng.hf.R;
import com.chaoteng.hf.entity.User;
import com.chaoteng.hf.module.base.BaseActivity;
import com.chaoteng.hf.http.httpRequest.LoginRequest;
import com.chaoteng.hf.http.httpResponse.LoginResponse;
import com.chaoteng.hf.http.service.HfHttpService;
import com.chaoteng.hf.network.RetrofitHelper;
import com.chaoteng.hf.utils.CountDownTimerUtils;
import com.chaoteng.hf.utils.JellyInterpolator;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xuxiaoqiang on 2017/8/14.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_login;//登录按钮
    private TextView tv_verification_code;//倒计时按钮
    private View progress;
    private View mInputLayout;
    private ImageView iv_rotate;
    private ProgressBar pb_rotate;
    private float mWidth, mHeight;
    private RelativeLayout rl_phone;
    private EditText et_code;
    float inPutWidth;
    ObjectAnimator animator3;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            animator3.cancel();
            if (msg.what == 1) {
                progress.setVisibility(View.INVISIBLE);
                mInputLayout.setVisibility(View.VISIBLE);
                inputAnimator();
            }else if(msg.what == 2){
                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                iv_rotate.setVisibility(View.VISIBLE);
                pb_rotate.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login);
        initView();
    }

    /**
     * 控件初始化
     */
    private void initView() {
        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_verification_code = (TextView) findViewById(R.id.tv_verification_code);
        progress = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);
        iv_rotate = (ImageView) findViewById(R.id.iv_rotate);
        pb_rotate = (ProgressBar) findViewById(R.id.pb_rotate);
        inPutWidth = mInputLayout.getWidth();
        rl_phone = (RelativeLayout) findViewById(R.id.rl_phone);
        et_code = (EditText) findViewById(R.id.et_code);
        tv_login.setOnClickListener(this);
        tv_verification_code.setOnClickListener(this);
    }


    private void getVerfyCode(){
        RetrofitHelper.getAccountService()
                .getVerifyCode("15506190813")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(User user) {

                    }
                });
    }


    /**
     * 登录接口请求
     */
    private void login() {
        LoginRequest request = new LoginRequest();
        request.setApiname("business");
        request.setMethod("login");
        request.setUsername("320581198712213415");
        request.setPassword("123456");


        HfHttpService.INSTANCE.login(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("qinwei----->", "onError");
                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        //TODO
//                        if(null != loginResponse){
//                            Message msg = new Message();
//                            msg.what = 2;
//                            handler.sendMessageDelayed(msg,3000);
//                        }else{
//                            Message msg = new Message();
//                            msg.what = 1;
//                            handler.sendMessageDelayed(msg,3000);
//                        }
                        Log.e("qinwei----->", "onNext LoginResponse：" + loginResponse.toString());
                    }
                });
    }

    /**
     * 输入框放大的动画
     * */
    private void inputAnimator(){
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 0.0f, 1f);
        animator2.setDuration(1000);
        animator2.start();
        animator2.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                rl_phone.setVisibility(View.VISIBLE);
                et_code.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });
    }

    /**
     * 输入框缩小的动画效果
     * @param w    宽
     */
    private void inputAnimator(float w) {
        AnimatorSet set = new AnimatorSet();

        ValueAnimator animator = ValueAnimator.ofFloat(0, w);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 1f, 0.0f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /**
                 * 动画结束后，先显示加载的动画，然后再隐藏输入框
                 */
                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });
    }

    /**
     * 出现进度动画
     *
     * @param view
     */
    private void progressAnimator(final View view) {
        iv_rotate.setVisibility(View.GONE);
        pb_rotate.setVisibility(View.VISIBLE);
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                // 计算出控件的高与宽
                mWidth = tv_login.getMeasuredWidth();
                mHeight = tv_login.getMeasuredHeight();
                // 隐藏输入框
                rl_phone.setVisibility(View.INVISIBLE);
                et_code.setVisibility(View.INVISIBLE);

                inputAnimator(mWidth);
                login();


                break;
            case R.id.tv_verification_code:
                CountDownTimerUtils countDownTimerUtils = new CountDownTimerUtils(tv_verification_code, 60000, 1000);
                countDownTimerUtils.start();
                getVerfyCode();
                break;
        }
    }
}
