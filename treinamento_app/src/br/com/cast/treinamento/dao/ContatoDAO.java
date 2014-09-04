package br.com.cast.treinamento.dao;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.cast.treinamento.domain.Contato;

public class ContatoDAO extends BaseDAO implements IDao<Contato> {

    public ContatoDAO(Context context) {
        super(context);
    }

    public List<Contato> listar() {
        List<Contato> contatos = new LinkedList<Contato>();

        try {
            SQLiteDatabase db = this.getWritableDatabase();

            Cursor cursor = db.rawQuery(ITbContatoCommands.SELECT, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Contato contato = preencherContato(cursor);
                    contatos.add(contato);
                } while (cursor.moveToNext());
            }

        } catch (SQLException ex) {
            Log.e(TAG_ERROR, ex.getMessage());
            throw ex;
        } finally {
            close();
        }
        return contatos;
    }

    public void inserir(Contato contato) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = criarParametros(contato);

            db.beginTransaction();
            db.insert(ITbContatoCommands.TABLE, null, values);
            db.setTransactionSuccessful();

        } catch (SQLException ex) {
            Log.e(TAG_ERROR, ex.getMessage());
            throw ex;
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void alterar(Contato contato) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = criarParametros(contato);

            db.beginTransaction();
            db.update(ITbContatoCommands.TABLE, values,
                    ITbContatoCommands.WHERE_ID,
                    new String[] { String.valueOf(contato.getId()) });
            db.setTransactionSuccessful();

        } catch (SQLException ex) {
            Log.e(TAG_ERROR, ex.getMessage());
            throw ex;
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void excluir(Contato contato) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            db.delete(ITbContatoCommands.TABLE, ITbContatoCommands.WHERE_ID,
                    new String[] { String.valueOf(contato.getId()) });
            db.setTransactionSuccessful();
        } catch (SQLException ex) {
            Log.e(TAG_ERROR, ex.getMessage());
            throw ex;
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public Contato recuperar(Long id) {
        Contato contato = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db
                    .query(ITbContatoCommands.TABLE,
                            ITbContatoCommands.COLUMNS,
                            ITbContatoCommands.WHERE_ID,
                            new String[] { String.valueOf(id) }, null, null,
                            null, null);

            if (cursor == null || !cursor.moveToFirst())
                return null;

            contato = preencherContato(cursor);
        } catch (SQLException ex) {
            Log.e(TAG_ERROR, ex.getMessage());
            throw ex;
        } finally {
            close();
        }
        return contato;
    }

    private Contato preencherContato(Cursor cursor) {
        Contato contato = new Contato();
        contato.setId(Long.valueOf(cursor.getLong(0)));
        contato.setNome(cursor.getString(1));
        contato.setEndereco(cursor.getString(2));
        contato.setSite(cursor.getString(3));
        contato.setTelefone(cursor.getString(4));
        contato.setAvaliacao(cursor.getFloat(5));
        return contato;
    }

    private ContentValues criarParametros(Contato contato) {
        ContentValues values = new ContentValues();
        values.put(ITbContatoCommands.COLUMN_NOME, contato.getNome());
        values.put(ITbContatoCommands.COLUMN_ENDERECO, contato.getEndereco());
        values.put(ITbContatoCommands.COLUMN_SITE, contato.getSite());
        values.put(ITbContatoCommands.COLUMN_TELEFONE, contato.getTelefone());
        values.put(ITbContatoCommands.COLUMN_AVALIACAO, contato.getAvaliacao());
        return values;
    }
}
