package com.iwxyi.letsremember.Users;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText mUsernameTv;
    private EditText mPasswordTv;
    private EditText mNicknameTv;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        ActionBar actionBar = this.getActionBar();
        if (actionBar != null) {
            actionBar.setTitle("用户注册");
        }

        mUsernameTv = (EditText) findViewById(R.id.tv_username);
        mPasswordTv = (EditText) findViewById(R.id.tv_password);
        mNicknameTv = (EditText) findViewById(R.id.tv_nickname);
    }

    public void toRegister(View view) throws JSONException {
        final String username = mUsernameTv.getText().toString();
        final String password = mPasswordTv.getText().toString();
        final String nickname = mNicknameTv.getText().toString();
        if (username.isEmpty() || password.isEmpty() || nickname.isEmpty()) {
            Toast.makeText(this, "请填写完整信息", Toast.LENGTH_SHORT).show();
            return ;
        }

        progressDialog = ProgressDialog.show(this, "请稍等", "正在注册", true, false);
        //String[] param = {"username", username, "password", password, "nickname", nickname};
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("username",username);
        jsonParam.put("password",password);
        jsonParam.put("name",username);
        jsonParam.put("phone","");
        jsonParam.put("email","");
        ConnectUtil.Post(Paths.getNetPath("/api/users"), jsonParam.toString(), new StringCallback(){
            @Override
            public void onFinish(String content) {
               if("500".equals(content)){
                    Toast.makeText(RegisterActivity.this, "invalid data", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                App.setVal("username", User.username = username);
                App.setVal("password", User.password = password);
                App.setVal("nickname", User.nickname = nickname);

                setResult(Def.code_register);
                finish();
            }
        });

    }
}
