package com.example.user.lab01;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class Lab01_4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab014);

        TextView textView = findViewById(R.id.fontView);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "xmas.ttf");
        textView.setTypeface(typeface);

        CheckBox checkBox = findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    compoundButton.setText("is Checked");
                else
                    compoundButton.setText("is unChecked");
            }
        });
    }
}