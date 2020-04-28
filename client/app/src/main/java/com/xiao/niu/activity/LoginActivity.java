package com.xiao.niu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.xiao.niu.R;
import com.xiao.niu.RetrofitConfig.ServiceFactory;
import com.xiao.niu.ServiceAPI.ConfigURL;
import com.xiao.niu.bean.LoginBean;
import com.xiao.niu.utils.ACache;
import com.xiao.niu.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.user_name)
    EditText userName;
    @Bind(R.id.pas_word)
    EditText pasWord;
    @Bind(R.id.checkbox)
    CheckBox checkbox;
    @Bind(R.id.login_but)
    Button loginBut;
ACache aCache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        aCache=ACache.get(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        pasWord.setSelection(pasWord.getText().length());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.checkbox, R.id.login_but})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.checkbox:

                if (checkbox.isChecked()) {
                    //选择状态 显示明文--设置为可见的密码
                    pasWord.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    //光标在最后面
                    Editable etable = pasWord.getText();
                    Selection.setSelection(etable, pasWord.length());
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    pasWord.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //光标在最后面
                    Editable etable = pasWord.getText();
                    Selection.setSelection(etable, pasWord.length());
                }


                break;
            case R.id.login_but:

                String name = userName.getText().toString();
                String pwd = pasWord.getText().toString();
                if(TextUtils.isEmpty(name)||TextUtils.isEmpty(pwd)){
                    ToastUtil.showToast(this,"账号或者用户名不能为空" );
                    return;
                }

                setLogin(name,pwd);

//                Intent intent = new Intent(new Intent(this, Main1Activity.class));
//                startActivity(intent);

                break;
        }
    }

    private void setLogin(String name,String pwd){

        String url= ConfigURL.API_URL+"login?account="+name+"&password="+pwd;


        ServiceFactory.getBusinessApi().login(url).enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {

                int code = response.code();
                int status = response.body().getStatus();
                if(status!=2000){
                    ToastUtil.showToast(LoginActivity.this, response.body().getMsg());
                    return;
                }
                LoginBean body = response.body();

                aCache.put("Token", body.getData().getToken());
                Intent intent = new Intent(new Intent(LoginActivity.this, Main1Activity.class));
                startActivity(intent);


            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable throwable) {
                String message = throwable.getMessage();

            }
        });




    }
}
