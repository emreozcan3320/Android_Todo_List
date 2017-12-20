package com.kadiremre.firstapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.kadiremre.firstapp.db.TaskContract;
import com.kadiremre.firstapp.db.TaskDbHelper;

import java.util.Calendar;

public class SecondActivity extends AppCompatActivity {

    private TaskDbHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        /* DATE PICKER */

        final TextView textView = (TextView) findViewById(R.id.textView);
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);

        Calendar takvim = Calendar.getInstance();
        int yil = takvim.get(Calendar.YEAR);
        int ay = takvim.get(Calendar.MONTH);
        int gun = takvim.get(Calendar.DAY_OF_MONTH);
        final String[] tarih = new String[1];

        datePicker.init(yil, ay, gun, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int mounth, int day) {
                tarih[0] = day+"-"+(mounth+1)+"-"+year;
                textView.setText(tarih[0]);
            }
        });

        /* END OF DATE PICKER */

        mHelper = new TaskDbHelper(this);

        final EditText editText = (EditText) findViewById(R.id.editText);
        Button save_button = (Button) findViewById(R.id.save_button);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = editText.getText().toString();

                // Gets the data repository in write mode
                SQLiteDatabase db = mHelper.getWritableDatabase();

                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();

                values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
                values.put(TaskContract.TaskEntry.COL_TASK_DATE, tarih[0]);

                // Ikiside calisiyor biri ilki stackoverflow'dan ikincisi documentation'dan
                /*db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                        null,
                        values,
                        SQLiteDatabase.CONFLICT_REPLACE);*/
                db.insert(TaskContract.TaskEntry.TABLE,null,values);

                db.close();
                Intent homepage = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(homepage);
            }
        });


    }

}
