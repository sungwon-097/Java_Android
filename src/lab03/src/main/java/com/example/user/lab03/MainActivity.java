package com.example.user.lab03;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button alert, list, date, time, custom;
    AlertDialog listDig, alertDig, cusDig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alert = findViewById(R.id.btnAlert);
        list = findViewById(R.id.btnList);
        date = findViewById(R.id.btnDate);
        time = findViewById(R.id.btnTime);
        custom = findViewById(R.id.btmCustom);

        alert.setOnClickListener(this);
        list.setOnClickListener(this);
        date.setOnClickListener(this);
        time.setOnClickListener(this);
        custom.setOnClickListener(this);

    }
    private void showToast(String str){
        Toast toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
        toast.show();
    }

    DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            if (dialogInterface == cusDig && which == DialogInterface.BUTTON_POSITIVE)
                showToast("customDig");
//            else if (dialogInterface == listDig){
//                String[] data = getResources().getStringArray(R.array.dialog);
//                showToast(data[which] + "selected");
//            }
//            else if (dialogInterface == alertDig && which == DialogInterface.BUTTON_POSITIVE)
//                showToast("alert");
        }
    };

    @Override
    public void onClick(View view) {
        if (view == alert) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_launcher_background);
            builder.setTitle("alert");
            builder.setMessage("exit?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    showToast("alert");
                }
            });
            builder.setNegativeButton("NO", null);

            alertDig = builder.create();
            alertDig.show();
        }
        else if (view == list){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("alarm");
            builder.setSingleChoiceItems(R.array.dialog, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    String[] data = getResources().getStringArray(R.array.dialog);
                    showToast(data[which] + "selected");
                }
            });
            builder.setPositiveButton("ok", null);
            builder.setNegativeButton("no", null);

            listDig = builder.create();
            listDig.show();
        }
        else if (view == date){
            Calendar c = Calendar.getInstance();
            int y = c.get(Calendar.YEAR);
            int m = c.get(Calendar.MONTH);
            int d = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                    showToast(yy + "-" + mm+1 + "-" + dd);
                }
            }, y, m, d);
            dialog.show();
        }
        else if (view == time){
            Calendar c = Calendar.getInstance();
            int h = c.get(Calendar.HOUR_OF_DAY);
            int m = c.get(Calendar.MINUTE);

            TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hh, int mm) {
                    showToast(hh+" : "+mm);
                }
            }, h, m, false);
            dialog.show();
        }
        else if (view == custom){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View customDialog = inflater.inflate(R.layout.dialog, null);
            builder.setView(customDialog);
            builder.setPositiveButton("ok", onClickListener);
            builder.setNegativeButton("no", null);

            cusDig = builder.create();
            cusDig.show();
        }
    }
}