package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FlagQuizActivity extends Activity {
    private Button Asia;
    private Button Europe;
    private Button Africa;
    private Button NorthAmerica;
    private Button SouthAmerica;
    private Button Oceania;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag_quiz);
        Asia = findViewById(R.id.Asia);
        Europe = findViewById(R.id.Europe);
        Africa = findViewById(R.id.Africa);
        NorthAmerica = findViewById(R.id.NorthAmerica);
        SouthAmerica = findViewById(R.id.SouthAmerica);
        Oceania = findViewById(R.id.Oceania);

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
        Asia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册页面
                Intent intent = new Intent(FlagQuizActivity.this, AsiaFlagQuizActivity.class);
                startActivity(intent);
            }
        });
        Europe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册页面
                Intent intent = new Intent(FlagQuizActivity.this, EuropeFlagQuizActivity.class);
                startActivity(intent);
            }
        });
        Africa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册页面
                Intent intent = new Intent(FlagQuizActivity.this, AfricaFlagQuizActivity.class);
                startActivity(intent);
            }
        });
        NorthAmerica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册页面
                Intent intent = new Intent(FlagQuizActivity.this, NorthAmericaFlagQuizActivity.class);
                startActivity(intent);
            }
        });
        SouthAmerica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册页面
                Intent intent = new Intent(FlagQuizActivity.this, SouthAmericaFlagQuizActivity.class);
                startActivity(intent);
            }
        });
        Oceania.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册页面
                Intent intent = new Intent(FlagQuizActivity.this, OceaniaFlagQuizActivity.class);
                startActivity(intent);
            }
        });

    }
}
