package com.iwxyi.letsremember.Users;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iwxyi.letsremember.Globals.App;
import com.iwxyi.letsremember.Globals.Paths;
import com.iwxyi.letsremember.Globals.User;
import com.iwxyi.letsremember.R;
import com.iwxyi.letsremember.Utils.ConnectUtil;
import com.iwxyi.letsremember.Utils.StringCallback;
import com.iwxyi.letsremember.Utils.StringUtil;

public class PersonActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mNicknameTv;
    private TextView mUsernameTv;
    private TextView mPasswordTv;
    private TextView mMobileTv;
    private TextView mEmailTv;
    private TextView mIntegralTv;
    private Button mLogoutBtn;
    private FloatingActionButton mFab;
    private TextView mTermsTv;
    private TextView mCountTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        initView();
        initData();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initView() {
        mNicknameTv = (TextView) findViewById(R.id.tv_nickname);
        mNicknameTv.setOnClickListener(this);
        mUsernameTv = (TextView) findViewById(R.id.tv_username);
        mUsernameTv.setOnClickListener(this);
        mPasswordTv = (TextView) findViewById(R.id.tv_password);
        mPasswordTv.setOnClickListener(this);
        mMobileTv = (TextView) findViewById(R.id.tv_mobile);
        mMobileTv.setOnClickListener(this);
        mEmailTv = (TextView) findViewById(R.id.tv_email);
        mEmailTv.setOnClickListener(this);
        mIntegralTv = (TextView) findViewById(R.id.tv_integral);
        mIntegralTv.setOnClickListener(this);
        mLogoutBtn = (Button) findViewById(R.id.btn_logout);
        mLogoutBtn.setOnClickListener(this);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(this);
        mTermsTv = (TextView) findViewById(R.id.tv_terms);
        mCountTv = (TextView) findViewById(R.id.tv_count);
    }

    private void initData() {
        mNicknameTv.setText(User.nickname);
        mUsernameTv.setText(User.username);
        mPasswordTv.setText(User.password);
        mMobileTv.setText(User.mobile);
        mEmailTv.setText(User.email);
        mIntegralTv.setText("" + User.integral);
        int firstOpen = App.getInt("firstOpen");
        int now = App.getTimestamp();
        int delta = now - firstOpen; // ???
        int day = delta / 3600 / 24;
        int hour = delta % (3600 * 24) / 3600;
        int minute = delta % 3600 / 60;
        String usedDay = String.format("%d???%d??????%d??????", day, hour, minute);
        mTermsTv.setText(usedDay);
        int num = App.getInt("reciteShort") + App.getInt("reciteMiddle") + App.getInt("reciteLong");
        mCountTv.setText("?????????" + num + "???");
        if(!User.isLogin()){
            mLogoutBtn.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_nickname:
                inputDialog("nickname", "??????????????????", User.nickname);
                break;
            case R.id.tv_username:
                App.toast("?????????????????????");
                break;
            case R.id.tv_password:
                inputDialog("password", "??????????????????", User.password);
                break;
            case R.id.tv_mobile:
                inputDialog("mobile", "???????????????", User.mobile);
                break;
            case R.id.tv_email:
                inputDialog("email", "????????????", User.email);
                break;
            case R.id.btn_logout:
                AlertDialog dialog = new AlertDialog.Builder(PersonActivity.this)
                        .setTitle("??????")
                        .setMessage("???????????????????????????\n??????????????????????????????????????????????????????????????????")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                User.user_id = "0";
                                User.username = User.password = User.nickname = User.mobile = User.email = "";
                                App.setVal("user_id", 0);
                                App.setVal("password", "");
                                PersonActivity.this.finish();
                            }
                        })
                        .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                break;
            case R.id.fab:
                //Snackbar.make(findViewById(R.id.fab), "?????????????????????482582886@qq.com", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                startActivity(new Intent(PersonActivity.this, SettingsActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * ???????????????
     *
     * @param aim   ??????
     * @param title ??????
     * @param def   ?????????
     * @return ??????????????????
     */
    private String inputDialog(final String aim, String title, String def) {
        final String[] result = new String[1];
        LayoutInflater factory = LayoutInflater.from(PersonActivity.this);//?????????
        final View view = factory.inflate(R.layout.edit_box, null);//???????????????final???
        final EditText edit = (EditText) view.findViewById(R.id.editText);//?????????????????????
        edit.setText(def);
        new AlertDialog.Builder(PersonActivity.this)
                .setTitle(title)//???????????????
                .setView(view)
                .setPositiveButton("??????",//????????????????????????
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                result[0] = edit.getText().toString();
                                onInputDialog(aim, edit.getText().toString());
                            }
                        })
                .setNegativeButton("??????", null)
                .create().show();
        return result[0];
    }

    /**
     * ?????????????????????
     *
     * @param aim ??????
     * @param s   ?????????
     */
    private void onInputDialog(String aim, String s) {

        switch (aim) {
            case "nickname":
                if (!canMatch(s, "\\S+")) {
                    App.toast("????????????????????????");
                    return;
                }
                mNicknameTv.setText(User.nickname = s);
                break;
            case "username":
                if (!canMatch(s, "\\S+")) {
                    App.toast("????????????????????????");
                    return;
                }
                mUsernameTv.setText(User.username = s);
                break;
            case "password":
                if (!canMatch(s, "\\S+")) {
                    App.toast("?????????????????????");
                    return;
                }
                mPasswordTv.setText(User.password = s);
                break;
            case "mobile":
                if (!canMatch(s, "\\+?\\d{5,15}")) {
                    App.toast("????????????????????????");
                    return;
                }
                mMobileTv.setText(User.mobile = s);
                break;
            case "email":
                if (!canMatch(s, "[\\w\\.]+@[\\.\\w]+")) {
                    App.toast("?????????????????????");
                    return;
                }
                mEmailTv.setText(User.email = s);
                break;
        }

        updateContent(aim, s);
    }

    /**
     * ??????????????????
     *
     * @param key ?????????
     * @param val ??????
     */
    private void updateContent(final String key, final String val) {
        String path = Paths.getNetPath("updateUserInfo");
        String[] params = new String[]{"user_id", User.id(), key, val};
        ConnectUtil.Get(path, params, new StringCallback() {
            @Override
            public void onFinish(String content) {
                content = StringUtil.getXml(content, "result");
                if (content.equals("OK") || content.equals("1")) {
                    Snackbar.make(findViewById(R.id.fab), "????????????", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (!content.isEmpty()) {
                    Snackbar.make(findViewById(R.id.fab), "????????????:" + StringUtil.getXml(content, "result"), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    boolean canMatch(String str, String pat) {
        return StringUtil.canMatch(str, pat);
    }
}
