package com.example.books_interface;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH; // полный путь к базе данных
    private static String DB_NAME = "bookstore.db";
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE_USER = "user"; // название таблицы в бд
    // названия столбцов
    static final String COLUMN_ID_U = "id";
    static final String COLUMN_LOGIN = "login";
    static final String COLUMN_PASSWORD = "password";

    static final String TABLE_LOGZ = "logz"; // название таблицы в бд
    // названия столбцов
    static final String COLUMN_ID_BOOK = "id_book";
    static final String COLUMN_ID_DOT = "id_dot";
    static final String COLUMN_ID_USER = "id_user";


    static final String TABLE_DOT = "dot"; // название таблицы в бд
    // названия столбцов
    static final String COLUMN_ID_D = "id";
    static final String COLUMN_ADDRESS = "adress";

    static final String TABLE_BOOK = "book"; // название таблицы в бд
    // названия столбцов
    static final String COLUMN_ID_B = "id";
    static final String COLUMN_TITLE = "title";
    static final String COLUMN_AUTHOR = "author";
    private Context myContext;

    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA);
        this.myContext=context;
        DB_PATH = context.getFilesDir().getPath() + DB_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) { }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) { }

    void create_db(){

        File file = new File(DB_PATH);
        if (!file.exists()) {
            //получаем локальную бд как поток
            try(InputStream myInput = myContext.getAssets().open(DB_NAME);
                // Открываем пустую бд
                OutputStream myOutput = new FileOutputStream(DB_PATH)) {

                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
            }
            catch(IOException ex){
                Log.d("DatabaseHelper", ex.getMessage());
            }
        }
    }
    public SQLiteDatabase open()throws SQLException {

        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}