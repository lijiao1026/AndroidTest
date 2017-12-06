package com.demo.lijao.MyActivity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.demo.lijao.test.R;
import com.demo.lijao.util.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.interfaces.OnHttpResponseListener;
import zuo.biao.library.util.StringUtil;

/**
 * 登录
 *
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, OnBottomDragListener,TextWatcher,OnHttpResponseListener {

    private static final String TAG = "LoginActivity";

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login, this);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();


       /*
        //仅测试用
        HttpRequest.translate("library", 0, new OnHttpResponseListener() {

            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                showShortToast("测试Http请求:翻译library结果为\n" + resultJson);
            }
        });*/

    }

    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private TextView userNameText;

    private TextView passwordText;
    private ImageView userNameDelete;
    private ImageView passwordDelete;
    private Button loginButton;
    @Override
    public void initView() {
    userNameText=(TextView) findViewById(R.id.userName);
    passwordText=(TextView) findViewById(R.id.password);
    userNameDelete=(ImageView) findViewById(R.id.deleteUserName);
    passwordDelete=(ImageView) findViewById(R.id.deletetPassword);
    loginButton= (Button) findViewById(R.id.loginButton);
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initData() {


    }



    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initEvent() {
    userNameText.addTextChangedListener(this);
    passwordText.addTextChangedListener(this);
    userNameDelete.setOnClickListener(this);
    passwordDelete.setOnClickListener(this);
    loginButton.setOnClickListener(this);
    }

    //系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteUserName: userNameText.setText("");break;
            case R.id.deletetPassword:passwordText.setText("");break;
            case R.id.loginButton:
                if (StringUtil.isPhone(userNameText.getText().toString())==false){
                    showShortToast("请输入正确的手机号码");break;
                }

                HttpRequest.login(userNameText.getText().toString(),passwordText.getText().toString(),0,this);
            default:
                break;
        }
    }


    /**
     * 重写编辑框改变之前的方法
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    /**
     * 重写编辑框改变之后的方法
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {
        if (StringUtil.isNotEmpty(userNameText.getText().toString(),true)&&StringUtil.isNotEmpty(passwordText.getText(),true)){
            loginButton.setEnabled(true);
        }else{
            loginButton.setEnabled(false);
        }
        if (StringUtil.isNotEmpty(userNameText.getText(),true)){
            userNameDelete.setVisibility(View.VISIBLE);
        }else{
            userNameDelete.setVisibility(View.GONE);
        }
        if (StringUtil.isNotEmpty(passwordText.getText(),true)){
            passwordDelete.setVisibility(View.VISIBLE);
        }else{
            passwordDelete.setVisibility(View.GONE);
        }
    }

    /**
     * 重写编辑框改变之前的方法
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }


    //系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public void onDragBottom(boolean rightToLeft) {
     /*   if (rightToLeft) {
            toActivity(WebViewActivity.createIntent(context, "博客", Constant.APP_OFFICIAL_BLOG));

            ivAboutGesture.setImageResource(R.drawable.gesture_right);
            return;
        }

        if (SettingUtil.isFirstStart) {
            runThread(TAG + "onDragBottom", new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "onDragBottom  >> SettingUtil.putBoolean(context, SettingUtil.KEY_IS_FIRST_IN, false);");
                    SettingUtil.putBoolean(SettingUtil.KEY_IS_FIRST_START, false);
                }
            });
        }*/

        finish();
    }



    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public void onHttpResponse(int requestCode, String resultJson, Exception e) {
    switch (requestCode){
        case 0:
            //showShortToast(resultJson);
            try {
                JSONObject json=new JSONObject(resultJson);
                boolean isTrue=json.getBoolean("result");
                if(isTrue){
                    showShortToast("登录成功");

                    startActivity(MainActivity.createIntent(context).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    overridePendingTransition(R.anim.bottom_push_in, R.anim.hold);

                    enterAnim = exitAnim = R.anim.null_anim;
                    finish();
                }else {
                    showShortToast("帐号或密码错误");
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        default:break;
    }
    }







    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


}
