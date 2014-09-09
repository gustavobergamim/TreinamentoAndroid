package br.com.cast.treinamento.dao.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.database.Cursor;
import br.com.cast.treinamento.dao.util.ColumnDef;
import br.com.cast.treinamento.dao.util.SortDir;
import br.com.cast.treinamento.domain.IEntidade;

public abstract class BaseMap<T extends IEntidade> implements ITableDef {

    public abstract String getTableName();

    public abstract String[] getColumns();

    public abstract void configureColumns();

    public abstract String getWhereById();

    public abstract HashMap<String, SortDir> getSorting();

    public abstract T fillEntity(Cursor cursor);

    public abstract ContentValues createParameters(T entity);

    private ColumnDef pk;
    private List<ColumnDef> columnDefs;

    public String createTableCommand() {
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE ");
        sb.append(getTableName());
        sb.append("( ");
        sb.append(getPk().toPrimaryKey());
        sb.append(", ");
        List<ColumnDef> columns = getColumnDefs();
        for (int index = 0; index < columns.size(); index++) {
            ColumnDef columnDef = columns.get(index);
            sb.append(columnDef.isUnique() ? columnDef.toUnique() : columnDef.toString());
            if (index < columns.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(" );");
        return sb.toString();
    }

    public String dropTableCommand() {
        return String.format("DROP TABLE IF EXISTS %s;", getTableName());
    }

    public String selectCommand() {
        StringBuffer sb = new StringBuffer();
        String[] columns = getColumns();
        for (int index = 0; index < columns.length; index++) {
            sb.append(columns[index]);
            if (index < columns.length - 1) {
                sb.append(", ");
            }
        }
        String command = String.format("SELECT %s FROM %s", sb.toString(), getTableName());
        command += configureSorting();
        return command;
    }

    public String configureSorting() {
        return configureSorting(true);
    }

    public String configureSorting(boolean addPrefix) {
        String orderBy = "";
        HashMap<String, SortDir> sorting = getSorting();
        if (sorting != null && sorting.size() > 0) {
            if (addPrefix) {
                orderBy += " ORDER BY ";
            }
            int index = 0;
            for (Entry<String, SortDir> order : sorting.entrySet()) {
                orderBy += String.format("UPPER(%s) %s", order.getKey(), order.getValue());
                if (index < sorting.size() - 1) {
                    orderBy += ", ";
                }
                index++;
            }
        }
        return orderBy;
    }

    public ColumnDef getPk() {
        return pk;
    }

    public void setPk(ColumnDef pk) {
        this.pk = pk;
    }

    public List<ColumnDef> getColumnDefs() {
        if (columnDefs == null) {
            columnDefs = new ArrayList<ColumnDef>();
        }
        return columnDefs;
    }

    protected BaseMap() {
        configureColumns();
    }
}
