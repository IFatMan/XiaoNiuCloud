package com.ubisys.uav.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;

/**
 * author : 郑振楠
 * date   : 2020/4/27
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ImmersionBar.with(this).init();

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //当模式为singleTop和SingleInstance会回调到这里
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    /**
     * 跳转界面并关闭当前界面
     *
     * @param clazz 目标Activity
     */
    protected void startThenKill(Class<?> clazz) {
        startThenKill(clazz, null);
    }

    /**
     * @param clazz  目标Activity
     * @param bundle 数据
     */
    protected void startThenKill(Class<?> clazz, Bundle bundle) {

        finish();
    }


    //获取布局
    protected abstract int getLayoutId();

    //初始化布局和监听
    protected abstract void initView();


    protected abstract void initData();

    protected abstract void initEvent();
}