package com.example.user.lab01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button bt1 = new Button(this);
        bt1.setText(R.string.bt1);

        Button bt2 = new Button(this);
        bt2.setText(R.string.bt2);

        LinearLayout linearLayout = new LinearLayout(this);

        linearLayout.addView(bt1);
        linearLayout.addView(bt2);

        setContentView(linearLayout);
    }
}