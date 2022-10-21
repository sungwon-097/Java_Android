package com.example.user.lab07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    SharedPreferences activityPreferences = getPreferences(Context.MODE_PRIVATE);
    // activity의 이름을 파일명으로 사용하며 하나의 액티비티만을 위한 공간으로 사용
    SharedPreferences contextPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
    // context단위로 앱 내의 다른 액티비티나 컴포넌트가 데이터 공유 가능.
    SharedPreferences packagePreferences = PreferenceManager.getDefaultSharedPreferences(this);
    // com.example.test_preferences와 같이 앱의 패키지명을 파일명으로 사용하고 앱 내의 다른 액티비티나 컴포넌트가 데이터 공유 가능.

    // ContextMode는 외부 앱과의 공유 정도에 따라 MODE_PRIVATE,MODE_WORLD_READABLE, MODE_WORLD_WRITEABLE를 가짐.

    SharedPreferences.Editor editor = activityPreferences.edit();

    // Write
    public SharedPreferences.Editor getEditor() {
        editor.putString("data1", "information of data1");
        editor.putInt("data2", 100);
        editor.commit();
        // put Boolean,Float, Long 모두 지원
        return editor;
    }

    // Read
    String data1 = activityPreferences.getString("data1", "none");
    int data2 = activityPreferences.getInt("data2", 0);
}