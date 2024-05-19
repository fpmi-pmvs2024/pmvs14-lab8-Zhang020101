package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;
    private SharedPreferences sharedPreferences;
    private SharedPreferences Number;
    private int number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);


        //获取sharedPreferences       //" "里面的内容一样,不然找不到
        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        Number = getSharedPreferences("number",MODE_PRIVATE);

        //点击注册
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册页面
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        //点击登录按钮
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=et_username.getText().toString();
                String password=et_password.getText().toString();

                if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this,"请输入用户名和密码",Toast.LENGTH_SHORT).show();
                }else{
                    String name = sharedPreferences.getString("username",null);
                    String pwd = sharedPreferences.getString("password",null);

                    if(username.equals(name)&&password.equals(pwd)){
//                        SharedPreferences.Editor edit = Number.edit();
//                        number++;
//                        edit.putInt("number",number);
//
//                        edit.commit();
                        // 在登录按钮的 onClick 方法内部
                        SharedPreferences.Editor edit = Number.edit();
                        number = Number.getInt("number", 0); // 从 SharedPreferences 获取当前的 number 值
                        number++; // number 值加一
                        edit.putInt("number", number); // 将增加后的 number 值保存回 SharedPreferences
                        edit.apply(); // 应用更改


                        //登陆成功
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }else{
                        //用户名或密码错误
                        Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
