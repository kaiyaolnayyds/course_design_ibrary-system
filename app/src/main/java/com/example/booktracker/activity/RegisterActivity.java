package com.example.booktracker.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booktracker.R;
import com.example.booktracker.db.BusinessResult;
import com.example.booktracker.db.UserDB;
import com.example.booktracker.entity.User;
import com.example.booktracker.utils.MD5Utils;


public class RegisterActivity extends AppCompatActivity {
    private ImageView ivBack;//返回按钮
    private Button btn_register;//注册按钮
    //用户名，密码，再次输入的密码的控件
    private EditText etUsername, etPassword, etPasswordAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置页面布局 ,注册界面
        setContentView(R.layout.activity_register);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init() {
        ivBack = findViewById(R.id.iv_back);
        //从activity_register.xml 页面中获取对应的UI控件
        btn_register = findViewById(R.id.btn_register);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etPasswordAgain = findViewById(R.id.et_password_again);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回键
                RegisterActivity.this.finish();
            }
        });
        //注册按钮
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String passwordAgain = etPasswordAgain.getText().toString();
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);

                BusinessResult<User> result = UserDB.register(user, passwordAgain);
                Toast.makeText(RegisterActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                if (result.isSuccess()) {
                    // 注册成功，返回登录界面，并传递用户信息
                    Intent intent = new Intent();
                    intent.putExtra("user", user);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

}