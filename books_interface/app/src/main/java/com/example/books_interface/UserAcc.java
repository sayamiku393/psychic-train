package com.example.books_interface;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserAcc extends AppCompatActivity {
    private String login;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_account);
        TextView username = findViewById(R.id.user_name);
        Bundle arguments = getIntent().getExtras();
        login = arguments.get("key").toString();
        username.setText(login);

        ArrayList<Book> books = new ArrayList<Book>();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.create_db();
        db = databaseHelper.open();
        userCursor = db.rawQuery("select " +
                DatabaseHelper.COLUMN_AUTHOR + ", " +
                DatabaseHelper.COLUMN_TITLE + " from " +
                DatabaseHelper.TABLE_BOOK + " inner join " +
                DatabaseHelper.TABLE_LOGZ + " on " +
                DatabaseHelper.TABLE_BOOK + "." + DatabaseHelper.COLUMN_ID_B + " = " +
                DatabaseHelper.COLUMN_ID_BOOK + " inner join " +
                DatabaseHelper.TABLE_USER + " on " +
                DatabaseHelper.TABLE_USER + "." + DatabaseHelper.COLUMN_ID_U + " = " +
                DatabaseHelper.COLUMN_ID_USER + " where " +
                DatabaseHelper.COLUMN_LOGIN + " = '" + login + "'", null);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        userCursor.close();
    }
}


