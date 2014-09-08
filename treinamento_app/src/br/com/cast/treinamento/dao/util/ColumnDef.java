package br.com.cast.treinamento.dao.util;

public class ColumnDef {

    private String name;
    private DbType type;
    private boolean nullable;
    private boolean unique;

    public String getName() {
        return name;
    }

    public DbType getType() {
        return type;
    }

    public boolean isNullable() {
        return nullable;
    }

    public boolean isUnique() {
        return unique;
    }

    public ColumnDef(String name, DbType type) {
        this(name, type, true);
    }

    public ColumnDef(String name, DbType type, boolean nullable) {
        this(name, type, nullable, false);
    }
    
    public ColumnDef(String name, DbType type, boolean nullable, boolean unique) {
        this.name = name;
        this.type = type;
        this.nullable = nullable;
        this.unique = unique;
    }

    @Override
    public String toString() {
        return getName() + " " + getType() + (isNullable() ? "" : "NOT ")
                + " NULL";
    }

    public String toUnique() {
        return getName() + " " + getType() + " UNIQUE"
                + (isNullable() ? " NOT " : "") + " NULL";
    }

    public String toPrimaryKey() {
        return getName() + " " + getType() + " PRIMARY KEY";
    }
}
