package com.example.booktracker.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.booktracker.R;
import com.example.booktracker.db.BusinessResult;
import com.example.booktracker.db.UserDB;
import com.example.booktracker.entity.User;
import com.example.booktracker.utils.CurrentUserUtils;

public class LoginActivity extends AppCompatActivity {
    private TextView tvRegister;//返回键,显示的注册
    private Button btnLogin;//登录按钮
    private EditText etUsername, etPassword;//编辑框

    /**
     * 记录密码复选框
     */
    private CheckBox cbRemember;

    /**
     * 界面跳转回调
     */
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //当注册成功返回时，接收返回的用户名和密码
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            // 注册成功
                            User user =(User) result.getData().getSerializableExtra("user");
                            etUsername.setText(user.getUsername());
                            etPassword.setText(user.getPassword());
                        }
                    }
                }
        );

        init();
    }

    //获取界面控件
    private void init() {
        //从activity_login.xml中获取的
        tvRegister = findViewById(R.id.tv_register);
        btnLogin = findViewById(R.id.btn_login);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        cbRemember = findViewById(R.id.cb_remember);
        // 获取当前用户
        User currentUser = CurrentUserUtils.getCurrentUser();
        if(currentUser.getRemember()){
            // 如果用户选择了记住密码，则填充用户名和密码
            etUsername.setText(currentUser.getUsername());
            etPassword.setText(currentUser.getPassword());
            cbRemember.setChecked(true);
        }else{
            // 否则只填充用户名
            etUsername.setText(currentUser.getUsername());
            cbRemember.setChecked(false);
        }
        cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // 记住密码
                User user = CurrentUserUtils.getCurrentUser();
                user.setRemember(b);
                CurrentUserUtils.setCurrentUser(user);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                BusinessResult<User> login = UserDB.login(user);
                if (login.isSuccess()) {
                    // 登录成功
                    login.getData().setRemember(cbRemember.isChecked());
                    CurrentUserUtils.setCurrentUser(login.getData());
                    Intent intent = new Intent(LoginActivity.this, NavActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // 登录失败
                    Toast.makeText(LoginActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                activityResultLauncher.launch(intent);
            }
        });
    }
}
