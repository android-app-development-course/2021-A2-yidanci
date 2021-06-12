package com.iwxyi.letsremember.Users;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.iwxyi.letsremember.Globals.App;
import com.iwxyi.letsremember.Globals.Def;
import com.iwxyi.letsremember.Globals.Paths;
import com.iwxyi.letsremember.Globals.User;
import com.iwxyi.letsremember.R;
import com.iwxyi.letsremember.Utils.ConnectUtil;
import com.iwxyi.letsremember.Utils.StringCallback;
import com.iwxyi.letsremember.Utils.XmlParser;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsernameTv;
    private EditText mPasswordTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        if (App.getInt("user_id") != 0) {
            try{
                toLogin(null);
            }catch (Exception e){
                Toast.makeText(LoginActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }
    }

    private void initView() {
        ActionBar actionBar = this.getActionBar();
        if (actionBar != null)
            actionBar.setTitle("用户登录");

        mUsernameTv = (EditText) findViewById(R.id.tv_username);
        mPasswordTv = (EditText) findViewById(R.id.tv_password);

        mUsernameTv.setText(App.getVal("username"));
        mPasswordTv.setText(App.getVal("password"));
    }

    public void toLogin(View view) throws JSONException {
        final String username = mUsernameTv.getText().toString();
        final String password = mPasswordTv.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "请输入数据", Toast.LENGTH_SHORT).show();
            return ;
        }
        final ProgressDialog progressDialog = ProgressDialog.show(this, "请稍等", "正在登录", true, false);
        //String[] params = {"username", username, "password", password};
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("username",username);
        jsonParam.put("password",password);
        ConnectUtil.Post(Paths.getNetPath("/api/users"),jsonParam.toString(), new StringCallback(){
            @Override
            public void onFinish(String content) {
                if("401".equals(content)){
                    Toast.makeText(LoginActivity.this, "账号或错误", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }else if("500".equals(content)){
                    Toast.makeText(LoginActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                progressDialog.dismiss();
                try{
                    JSONObject response = new JSONObject(content);
                    System.out.println("id"+response.getString("id"));
                    User.user_id = response.getString("id");
                    User.username = response.getString("username");
                    User.mobile = response.getString("phone");
                    User.email = response.getString("email");
                    User.integral = response.getInt("score");
                    System.out.println("userid:"+User.user_id);
                    App.setVal("username", User.username = username);
                    App.setVal("password", User.password = password);
                    App.setVal("user_id", User.user_id);
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    setResult(Def.code_login);
                    finish();
                }catch (JSONException e){
                    Toast.makeText(LoginActivity.this, "内部错误", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void toRegister(View view) {
       startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), Def.code_register);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Def.code_register) {
            setResult(Def.code_login);
            finish();
        }
    }
}
