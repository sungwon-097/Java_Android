package com.example.user.lab07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Context myContext;

    Button setButton1, setButton2, getButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("test", MODE_PRIVATE); // test 이름의 기본모드 설정, 만약 test key값이 있다면 해당 값을 불러옴.

        setButton1 = findViewById(R.id.b1);
        setButton2 = findViewById(R.id.b2);
        getButton = findViewById(R.id.b3);

        setButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
                editor.putString("text", "test data 1"); // key,value 형식으로 저장
                editor.apply();    //최종 커밋. 커밋을 해야 저장이 된다.}
            }
        });

        setButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
                editor.putString("text", "test data 2"); // key,value 형식으로 저장
                editor.apply();    //최종 커밋. 커밋을 해야 저장이 된다.}
            }
        });

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText = sharedPreferences.getString("text","1");
                showToast(inputText);
            }
        });
    }

    private void showToast(String msg){
        Toast toast = Toast.makeText(this, "The value is " + msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}