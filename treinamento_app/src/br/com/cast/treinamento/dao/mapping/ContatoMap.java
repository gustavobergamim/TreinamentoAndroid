package br.com.cast.treinamento.dao.mapping;

import java.util.HashMap;

import android.content.ContentValues;
import android.database.Cursor;
import br.com.cast.treinamento.dao.util.ColumnDef;
import br.com.cast.treinamento.dao.util.DbType;
import br.com.cast.treinamento.dao.util.SortDir;
import br.com.cast.treinamento.domain.Contato;

public class ContatoMap extends BaseMap<Contato> {

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_ENDERECO = "endereco";
    public static final String COLUMN_SITE = "site";
    public static final String COLUMN_TELEFONE = "telefone";
    public static final String COLUMN_AVALIACAO = "avaliacao";

    @Override
    public String getTableName() {
        return "tb_contato";
    }

    @Override
    public String[] getColumns() {
        return new String[] { COLUMN_ID, COLUMN_NOME, COLUMN_ENDERECO, COLUMN_SITE,
                COLUMN_TELEFONE, COLUMN_AVALIACAO };
    }

    @Override
    public String getWhereById() {
        return COLUMN_ID + " = ? ";
    }

    @Override
    public void configureColumns() {
        setPk(new ColumnDef(COLUMN_ID, DbType.INTEGER));
        getColumnDefs().add(new ColumnDef(COLUMN_NOME, DbType.TEXT, false));
        getColumnDefs().add(new ColumnDef(COLUMN_ENDERECO, DbType.TEXT, false));
        getColumnDefs().add(new ColumnDef(COLUMN_SITE, DbType.TEXT, false));
        getColumnDefs().add(new ColumnDef(COLUMN_TELEFONE, DbType.TEXT, false));
        getColumnDefs().add(new ColumnDef(COLUMN_AVALIACAO, DbType.REAL));
    }

    @Override
    public Contato fillEntity(Cursor cursor) {
        Contato contato = new Contato();
        contato.setId(Long.valueOf(cursor.getLong(cursor.getColumnIndex(COLUMN_ID))));
        contato.setNome(cursor.getString(cursor.getColumnIndex(COLUMN_NOME)));
        contato.setEndereco(cursor.getString(cursor.getColumnIndex(COLUMN_ENDERECO)));
        contato.setSite(cursor.getString(cursor.getColumnIndex(COLUMN_SITE)));
        contato.setTelefone(cursor.getString(cursor.getColumnIndex(COLUMN_TELEFONE)));
        if (!cursor.isNull(cursor.getColumnIndex(COLUMN_AVALIACAO))) {
            contato.setAvaliacao(cursor.getFloat(cursor.getColumnIndex(COLUMN_AVALIACAO)));
        }
        return contato;
    }

    @Override
    public ContentValues createParameters(Contato entity) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, entity.getNome());
        values.put(COLUMN_ENDERECO, entity.getEndereco());
        values.put(COLUMN_SITE, entity.getSite());
        values.put(COLUMN_TELEFONE, entity.getTelefone());
        if (entity.getAvaliacao() == null || entity.getAvaliacao().floatValue() == 0) {
            values.putNull(COLUMN_AVALIACAO);
        } else {
            values.put(COLUMN_AVALIACAO, entity.getAvaliacao());
        }
        return values;
    }

    @Override
    public HashMap<String, SortDir> getSorting() {
        HashMap<String, SortDir> orderInfo = new HashMap<>();
        orderInfo.put(COLUMN_NOME, SortDir.DESC);
        return orderInfo;
    }
}
