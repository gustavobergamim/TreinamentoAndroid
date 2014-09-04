package br.com.cast.treinamento.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class BaseDAO extends SQLiteOpenHelper {

    public static final String DB_NAME = "treinamento_android";
    public static final int DB_VERSION = 1;

    public BaseDAO(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ITbContatoCommands.CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ITbContatoCommands.DROP);
    }
}
