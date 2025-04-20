package com.example.books_interface;

import android.widget.SimpleCursorAdapter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.*;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter);

        Button register_button = findViewById(R.id.registerButton);
        Button login_button = findViewById(R.id.loginButton);
        TextView forgot_button = findViewById(R.id.forgotPasswordText);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.create_db();
        register_button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    register_func();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        login_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                login_func();
            }
        });
        forgot_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                forgot_password_func();
            }
        });
    }

    public void register_func() throws ClassNotFoundException, SQLException {

        TextView repeating  = findViewById(R.id.repeating);
        EditText login_edit = findViewById(R.id.login_EditText);
        EditText password_edit = findViewById(R.id.password_EditText);
        String login = String.valueOf(login_edit.getText());
        String password = String.valueOf(password_edit.getText());
        db = databaseHelper.open();
        userCursor = db.rawQuery("select " + DatabaseHelper.COLUMN_LOGIN + " from " +
                DatabaseHelper.TABLE_USER + " where " + DatabaseHelper.COLUMN_LOGIN +
                " = '" + login + "'", null);

        if (userCursor.getCount() == 0){
            db.execSQL("INSERT INTO user(login, password) VALUES ( '" + login + "', '" + password + "');");
            //to main screen
            Intent intent = new Intent(this, Map.class);
            intent.putExtra("key", password);
            startActivity(intent);
        }
        else repeating.setVisibility(TextView.VISIBLE);
    }

    public void login_func() {
        TextView incorrect = findViewById(R.id.incorrect);
        EditText login_edit = findViewById(R.id.login_EditText);
        EditText password_edit = findViewById(R.id.password_EditText);
        String login = String.valueOf(login_edit.getText());
        String password = String.valueOf(password_edit.getText());
        db = databaseHelper.open();
        String foundPassword;
        userCursor = db.rawQuery("select " + DatabaseHelper.COLUMN_PASSWORD + " from " + DatabaseHelper.TABLE_USER + " where " + DatabaseHelper.COLUMN_LOGIN + " = '" + login + "'", null);
        if (userCursor.getCount() > 0) {
            userCursor.moveToFirst();
            int index = userCursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD);
            foundPassword = userCursor.getString(index);
            if (foundPassword.equals(password)){
                //to main screen
                Intent intent = new Intent(this, Map.class);
                intent.putExtra("key", login);
                startActivity(intent);
            }
            else incorrect.setVisibility(TextView.VISIBLE);
        }
        else incorrect.setVisibility(TextView.VISIBLE);
    }

    public void forgot_password_func() {

        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String password = String.valueOf(editText.getText());
                        //обработка
                    }
                });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        userCursor.close();
    }

}