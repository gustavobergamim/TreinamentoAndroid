package br.com.cast.treinamento.dao.mapping;

public interface ITableDef {

    String getTableName();

    String[] getColumns();

    String createTableCommand();

    String dropTableCommand();
}
