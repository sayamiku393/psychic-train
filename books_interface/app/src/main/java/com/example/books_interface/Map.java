package com.example.books_interface;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Map extends AppCompatActivity {
    private String login;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Bundle arguments = getIntent().getExtras();
        login = arguments.get("key").toString();
        ImageButton profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_profile_func();
            }
        });
        ImageButton dot1 = findViewById(R.id.dot1);
        ImageButton dot2 = findViewById(R.id.dot2);
        ImageButton dot3 = findViewById(R.id.dot3);
        ImageButton dot4 = findViewById(R.id.dot4);
        ImageButton dot5 = findViewById(R.id.dot5);


        dot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_dot(1);
            }
        });
        dot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_dot(2);
            }
        });
        dot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_dot(3);
            }
        });
        dot4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_dot(4);
            }
        });
        dot5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_dot(5);
            }
        });
    }

    public void go_to_profile_func() {
        Intent intent_to_profile = new Intent(this, UserAcc.class);
        intent_to_profile.putExtra("key", login);
        startActivity(intent_to_profile);
    }

    public void display_dot(int id) {
        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.create_db();
        db = databaseHelper.open();
        userCursor = db.rawQuery("select " +
                DatabaseHelper.COLUMN_ADDRESS + " from " +
                DatabaseHelper.TABLE_DOT + " where " +
                DatabaseHelper.COLUMN_ID_D + " = " + id, null);
        if (userCursor.getCount() != 0){
            userCursor.moveToFirst();
            int index = userCursor.getColumnIndex(DatabaseHelper.COLUMN_ADDRESS);
            String address = userCursor.getString(index);
            TextView dot_address = findViewById(R.id.book_title);
            dot_address.setText(address);
        }
        ArrayList<Book> books = new ArrayList<Book>();
        userCursor = null;
        userCursor = db.rawQuery("select " +
                DatabaseHelper.COLUMN_AUTHOR + ", " +
                DatabaseHelper.COLUMN_TITLE + " from " +
                DatabaseHelper.TABLE_BOOK + " inner join " +
                DatabaseHelper.TABLE_LOGZ + " on " +
                DatabaseHelper.TABLE_BOOK + "." +
                DatabaseHelper.COLUMN_ID_B + " = " +
                DatabaseHelper.COLUMN_ID_BOOK + " where " +
                DatabaseHelper.COLUMN_ID_D + " = " + id, null);
        if (userCursor.getCount() != 0){
            userCursor.moveToFirst();
            do {
                ListView bookList = findViewById(R.id.history_list);
                BookAdapter adapter = new BookAdapter(this, R.layout.list_item, books);
                bookList.setAdapter(adapter);
                int index = userCursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE);
                int index2 = userCursor.getColumnIndex(DatabaseHelper.COLUMN_AUTHOR);
                String author = userCursor.getString(index2);
                String title = userCursor.getString(index);
                books.add(new Book(title, author));

            } while ( userCursor.moveToNext());
        }
        else {
            TextView no_books = findViewById(R.id.no_books);
            no_books.setVisibility(TextView.VISIBLE);
        }
    }


}