package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {


    private EditText et_username;
    private EditText et_password;

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //获取sharedPreferences
        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);


        et_username= findViewById(R.id.et_username);
        et_password= findViewById(R.id.et_password);


        findViewById(R.id.toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回，跳转到登录页面，相当于重新打开一个页面
//                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                startActivity(intent);

                //直接用finish返回(把当前页面销毁掉)
                finish();
            }
        });

        //点击注册
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username= et_username.getText().toString();
                String password= et_password.getText().toString();

                if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this,"用户名或密码为空",Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("username",username);
                    edit.putString("password",password);

                    //一点要提交
                    edit.commit();
                    Toast.makeText(RegisterActivity.this,"注册成功,请登录",Toast.LENGTH_SHORT).show();

                    //
                    finish();
                }
            }
        });
    }
}
