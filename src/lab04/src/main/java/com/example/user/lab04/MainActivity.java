package com.example.user.lab04;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    TextView bellTextView, labelTextView;
    CheckBox repeatCheckBox, vibrateCheckBox;
    Switch switchView;

    float initX = 0.0f;
    Long initTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bellTextView = findViewById(R.id.bell_name);
        bellTextView.setOnClickListener(this);

        labelTextView = findViewById(R.id.label);
        labelTextView.setOnClickListener(this);

        repeatCheckBox = findViewById(R.id.repeatCheck);
        repeatCheckBox.setOnCheckedChangeListener(this);

        vibrateCheckBox = findViewById(R.id.vibrate);
        vibrateCheckBox.setOnCheckedChangeListener(this);

        switchView = findViewById(R.id.onOff);
        switchView.setOnCheckedChangeListener(this);
    }

    private void showToast(String message){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onClick(View view) {
        if (view == bellTextView)
            showToast("bell text click event...");
        else
            showToast("label text click event...");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == repeatCheckBox)
            showToast("repeatCheckBox" + isChecked);
        else if (buttonView == vibrateCheckBox)
            showToast("vibrateCheckBox" + isChecked);
        else if (buttonView == switchView)
            showToast("switchView" + isChecked);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                initX = event.getRawX();
                return true;
            case MotionEvent.ACTION_UP:
                float diffX = initX - event.getRawX();
                if (diffX > 30)
                    showToast("slide left");
                else if (diffX < -30)
                    showToast("slide right");
                else
                    break;
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (System.currentTimeMillis() - initTime > 3000){
            showToast("Exit? okd " +(System.currentTimeMillis() - initTime));
            initTime = System.currentTimeMillis();
                Log.d(TAG, "onKeyDown: ");
                onBackPressed();
            }
            else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {

        if ((System.currentTimeMillis() - initTime) > 3000){
            showToast("Exit? obp " +(System.currentTimeMillis() - initTime));
            initTime = System.currentTimeMillis();
            Log.d(TAG, "onBackPressed: ");
        }
        else
            finish();
    }
}