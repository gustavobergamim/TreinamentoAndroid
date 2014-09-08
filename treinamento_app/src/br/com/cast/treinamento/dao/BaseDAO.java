package br.com.cast.treinamento.dao;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.cast.treinamento.dao.mapping.BaseMap;
import br.com.cast.treinamento.dao.mapping.ContatoMap;
import br.com.cast.treinamento.dao.mapping.ITableDef;
import br.com.cast.treinamento.domain.IEntidade;

public abstract class BaseDAO<T extends IEntidade> extends SQLiteOpenHelper implements IDao<T> {

    public static final String TAG_ERROR = "DAO";

    public static final String DB_NAME = "treinamento_android";
    public static final int DB_VERSION = 1;

    private final ITableDef[] tableDefs = { new ContatoMap() };

    private final BaseMap<T> map;

    public BaseDAO(Context context, BaseMap<T> map) {
        super(context, DB_NAME, null, DB_VERSION);
        this.map = map;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (ITableDef tableDef : tableDefs) {
            db.execSQL(tableDef.createTableCommand());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (ITableDef tableDef : tableDefs) {
            db.execSQL(tableDef.dropTableCommand());
        }
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public List<T> listar() {
        List<T> resultado = new LinkedList<T>();

        try {

            SQLiteDatabase db = this.getWritableDatabase();

            Cursor cursor = db.rawQuery(map.selectCommand(), null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    T entidade = map.fillEntity(cursor);
                    resultado.add(entidade);
                } while (cursor.moveToNext());
            }

        } catch (SQLException ex) {
            Log.e(TAG_ERROR, ex.getMessage());
            throw ex;
        } finally {
            close();
        }
        return resultado;
    }

    @Override
    public void inserir(T entidade) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = map.createParameters(entidade);

            db.beginTransaction();
            db.insert(map.getTableName(), null, values);
            db.setTransactionSuccessful();

        } catch (SQLException ex) {
            Log.e(TAG_ERROR, ex.getMessage());
            throw ex;
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    @Override
    public void alterar(T entidade) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = map.createParameters(entidade);

            db.beginTransaction();
            db.update(map.getTableName(), values, map.getWhereById(),
                    new String[] { String.valueOf(entidade.getId()) });
            db.setTransactionSuccessful();

        } catch (SQLException ex) {
            Log.e(TAG_ERROR, ex.getMessage());
            throw ex;
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    @Override
    public void excluir(T entidade) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            db.delete(map.getTableName(), map.getWhereById(),
                    new String[] { String.valueOf(entidade.getId()) });
            db.setTransactionSuccessful();
        } catch (SQLException ex) {
            Log.e(TAG_ERROR, ex.getMessage());
            throw ex;
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public T recuperar(Long id) {
        T entidade = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(map.getTableName(), map.getColumns(), map.getWhereById(),
                    new String[] { String.valueOf(id) }, null, null, null, null);

            if (cursor == null || !cursor.moveToFirst())
                return null;

            entidade = map.fillEntity(cursor);
        } catch (SQLException ex) {
            Log.e(TAG_ERROR, ex.getMessage());
            throw ex;
        } finally {
            close();
        }
        return entidade;
    }
}
