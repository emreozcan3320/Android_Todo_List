package com.kadiremre.firstapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.kadiremre.firstapp.db.TaskContract;
import com.kadiremre.firstapp.db.TaskDbHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ArrayAdapter<String> mAdapter;

    private TaskDbHelper mHelper;
    private ListView mTaskListView;

    int year_x,month_x,day_x;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new TaskDbHelper(this);
            mTaskListView = (ListView) findViewById(R.id.list_todo);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
        * main_menu.xml i menu ye işliyor
        */
        getMenuInflater().inflate(R.menu.main_menu, menu);
        updateUI();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_add_task){
            Intent myIntent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(myIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();

        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());

        // Aldigim string ten taski ayikliyorum
        String[] parts = task.split("\n");
        String [] mainStr = parts[1].toString().split(":");
        final String eskiKelime = mainStr[1].toString().trim();

        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(
                TaskContract.TaskEntry.TABLE,

                // Define 'where' part of query.
                TaskContract.TaskEntry.COL_TASK_TITLE + " = ?",

                // Specify arguments which we get it above
                new String[]{eskiKelime});

        db.close();
        updateUI();
    }

    public void updateTask(View view){
        View parent = (View) view.getParent();

        final SQLiteDatabase db = mHelper.getWritableDatabase();

        //Listeden task title li aliyorum
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        final String task = String.valueOf(taskTextView.getText());

        // Aldigim string ten taski ayikliyorum
        String[] parts = task.split("\n");
        String [] mainStr = parts[1].toString().split(":");
        final String eskiKelime = mainStr[1].toString().trim();

        // Ayiklanmis tas'i edit text'e atiyorum
        final EditText taskEditText = new EditText(this);
        taskEditText.setText(eskiKelime);


        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Update the : " + eskiKelime)
                .setMessage("")
                .setView(taskEditText)
                .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String updateStr = String.valueOf(taskEditText.getText());

                        /*
                        * When you need to modify a subset of your database values,
                        * use the update() method.
                        * Updating the table combines the content values syntax of insert()
                        * with the where syntax of delete().
                        * */
                        ContentValues values = new ContentValues();
                        values.put(TaskContract.TaskEntry.COL_TASK_TITLE,updateStr);

                        // Which row to update, based on the title
                        String selection = TaskContract.TaskEntry.COL_TASK_TITLE + " LIKE ?";
                        String[] selectionArgs = {eskiKelime};
                        db.update(
                                TaskContract.TaskEntry.TABLE,
                                values,
                                selection,
                                selectionArgs);
                        db.close();
                        updateUI();
                    }
                })
                .setNegativeButton("CANCEL", null)
                .create();
        dialog.show();
    }

   public void changeDate(View view){
       View parent = (View) view.getParent();

       final SQLiteDatabase db = mHelper.getWritableDatabase();

       //Listeden task title li aliyorum
       TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
       final String task = String.valueOf(taskTextView.getText());

       // Aldigim string ten taski ayikliyorum
       String[] parts = task.split("\n");
       String [] mainStr = parts[2].toString().split(":");
       final String eskiTarih= mainStr[1].toString().trim();

       //Log.d("***************", "****************==> " + eskiTarih);

       // Ayiklanmis tas'i edit text'e atiyorum
       final EditText taskEditText = new EditText(this);
       taskEditText.setText(eskiTarih);


       AlertDialog dialog = new AlertDialog.Builder(this)
               .setTitle("Uptade the : " + eskiTarih)
               .setMessage("Please follow the format at below !")
               .setView(taskEditText)
               .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       String updateStr = String.valueOf(taskEditText.getText());

                        /*
                        * When you need to modify a subset of your database values,
                        * use the update() method.
                        * Updating the table combines the content values syntax of insert()
                        * with the where syntax of delete().
                        * */
                       ContentValues values = new ContentValues();
                       values.put(TaskContract.TaskEntry.COL_TASK_DATE,updateStr);

                       // Which row to update, based on the title
                       String selection = TaskContract.TaskEntry.COL_TASK_DATE+ " LIKE ?";
                       String[] selectionArgs = {eskiTarih};
                       db.update(
                               TaskContract.TaskEntry.TABLE,
                               values,
                               selection,
                               selectionArgs);
                       db.close();
                       updateUI();
                   }
               })
               .setNegativeButton("Cancel", null)
               .create();
       dialog.show();

    }


    private void updateUI(){

        /*
        * To read from a database, use the query() method,
        * passing it your selection criteria and desired columns.
        * The method combines elements of insert() and update(),
        * except the column list defines the data you want to fetch,
        * rather than the data to insert.
        * The results of the query are returned to you in a Cursor object.
        * */

        SQLiteDatabase db = mHelper.getReadableDatabase();

        Cursor cursor = db.query(
                // The table to query
                TaskContract.TaskEntry.TABLE,

                // The columns to return
                new String[]{
                        TaskContract.TaskEntry._ID,
                        TaskContract.TaskEntry.COL_TASK_TITLE,
                        TaskContract.TaskEntry.COL_TASK_DATE
                },

                // The columns for the WHERE clause
                null,

                // The values for the WHERE clause
                null,

                // don't group the rows
                null,

                // don't filter by row groups
                null,

                // The sort order
                null);

        /*
        * To look at a row in the cursor,
        * use one of the Cursor move methods,
        * which you must always call before you begin reading values.
        * Since the cursor starts at position -1,
        * calling moveToNext() places the "read position" on the first entry in the results
        * and returns whether or not the cursor is already past the last entry in the result set.
        */

        ArrayList<String> taskList = new ArrayList<>();

        while (cursor.moveToNext()) {

            /*
            * For each row, you can read a column's value by calling one of the Cursor get methods,
            * such as getString() or getLong(). For each of the get methods,
            * you must pass the index position of the column you desire,
            * which you can get by calling getColumnIndex() or getColumnIndexOrThrow().
            */

            int id = cursor.getInt(0);
            String tsk = cursor.getString(1);
            String dt = cursor.getString(2);
            taskList.add("id : "+ id + "\nTodo : " + tsk + "\nDeadline : " + dt );

        }

        // When finished iterating through results, call close() on the cursor to release its resources
        cursor.close();

        /*
        * Elimizdeki SQLite dan donen data'yi
        * taskList'e atiyabilmek icin Adapte ederek atiyoruz.
        * */

      if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(
                    this,
                    R.layout.item_todo,
                    R.id.task_title,
                    taskList);
            mTaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
        db.close();
    }


}

