package com.example.myapplication;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NorthAmericaFlagQuizActivity extends Activity {

    private ImageView flagImageView;
    private Button option1Button, option2Button, option3Button;
    private TextView questionTextView;
    private int currentQuestionIndex;
    private String[] countries; // 国家名称数组
    private int[] flags; // 国旗图片资源数组
    private String[] answers; // 正确答案数组
    private TextView flagText;
    private int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge_flag_quiz);

        // 初始化视图组件
        flagImageView = (ImageView) findViewById(R.id.flagImageView);
        option1Button = (Button) findViewById(R.id.option1Button);
        option2Button = (Button) findViewById(R.id.option2Button);
        option3Button = (Button) findViewById(R.id.option3Button);
        questionTextView = (TextView) findViewById(R.id.questionTextView);
        flagText = (TextView) findViewById(R.id.flag);

        // 初始化数据
        initializeData();

        // 设置问题和选项
        setQuestion(currentQuestionIndex);
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
    }

    private void initializeData() {
        // 假设这里有一些国家名称和对应的国旗资源
        countries = new String[]{"Канада", "Мексика", "США","Коста-Рика","Гондурас","Гватемала","Панама","Куба","Гаити","Ямайка"};
        flags = new int[]{R.drawable.quiz_canada, R.drawable.quiz_mexico, R.drawable.quiz_usa, R.drawable.quiz_costa_rica, R.drawable.quiz_honduras, R.drawable.quiz_guatemala, R.drawable.quiz_panama, R.drawable.quiz_cuba, R.drawable.quiz_haiti, R.drawable.quiz_jamaica};
        answers = countries;//new String[]{"加拿大", "墨西哥", "美国","哥斯达黎加","洪都拉斯","危地马拉","巴拿马","古巴","海地","牙买加"};
        currentQuestionIndex = 0;
    }
    private void setQuestion(int questionIndex) {
        // 设置国旗图片
        flagImageView.setImageResource(flags[questionIndex]);
        // 设置问题
        questionTextView.setText("Какой стране принадлежит этот флаг？");

        // 创建一个包含所有国家名称的列表
        List<String> allCountries = new ArrayList<>(Arrays.asList(countries));

        // 从列表中移除正确答案
        allCountries.remove(answers[questionIndex]);

        // 随机打乱剩余的国家列表
        Collections.shuffle(allCountries);

        // 创建选项列表并添加正确答案
        List<String> options = new ArrayList<>();
        options.add(answers[questionIndex]);

        // 添加两个随机的错误答案
        options.add(allCountries.get(0));
        options.add(allCountries.get(1));

        // 再次打乱选项，以确保选项顺序是随机的
        Collections.shuffle(options);

        // 设置按钮上的答案选项
        option1Button.setText(options.get(0));
        option2Button.setText(options.get(1));
        option3Button.setText(options.get(2));

        // 设置按钮的点击事件
        option1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(options.get(0));
            }
        });

        option2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(options.get(1));
            }
        });

        option3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(options.get(2));
            }
        });
    }


    private void checkAnswer(String selectedAnswer) {
        if (selectedAnswer.equals(answers[currentQuestionIndex])) {
            Toast.makeText(this, "правильный！", Toast.LENGTH_SHORT).show();
            // 更新问题索引并设置下一个问题
            if (currentQuestionIndex < countries.length - 1) {
                currentQuestionIndex++;
                setQuestion(currentQuestionIndex);
            } else {
                // 游戏结束
                Toast.makeText(this, "игра закончена！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "ошибка！", Toast.LENGTH_SHORT).show();
            flag++;
            String str = Integer.toString(10 - flag);
            flagText.setText(str);
            if (flag == 10) {
                Toast.makeText(this, "игра закончена！", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 这里的代码会在5秒后执行
                        finish();
                    }
                }, 5000); // 延迟5秒

            }
        }
    }
}
