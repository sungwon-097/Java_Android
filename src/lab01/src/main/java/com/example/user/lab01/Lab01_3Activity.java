package com.example.user.lab01;

import static android.graphics.drawable.Drawable.createFromPath;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Lab01_3Activity extends AppCompatActivity implements View.OnClickListener{

    private TextView textView;
    private Button bt1;
    private Button bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         bt1 = new Button(this);
         bt2 = new Button(this);
        textView = new TextView(this);

        bt1.setText(R.string.visibleTrue);
        bt2.setText(R.string.visibleFalse);
        textView.setText(R.string.app_name);

        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(Color.RED);
        textView.setVisibility(View.VISIBLE);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams bt1LP= new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        LinearLayout.LayoutParams bt2LP= new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        LinearLayout.LayoutParams tvLP= new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearLayout.addView(bt1, bt1LP);
        linearLayout.addView(textView, tvLP);
        linearLayout.addView(bt2, bt2LP);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);

        setContentView(linearLayout);
    }

    @Override
    public void onClick(View view) {
        if(view == bt1){
            textView.setVisibility(View.VISIBLE);
        }else{
            textView.setVisibility(View.INVISIBLE);
        }
//        if (textView.getVisibility() == View.VISIBLE)
//            textView.setVisibility(View.INVISIBLE);
//        else
//            textView.setVisibility(View.VISIBLE);
    }

//    @Override
//    public void onClick(View view) {
//        if(view.getVisibility() == View.INVISIBLE)
//            view.setVisibility(View.VISIBLE);
//        else
//            view.setVisibility(View.INVISIBLE);
//    }
}