package br.com.cast.treinamento.dao;

public interface ITbContatoCommands {

    public static final String TABLE = "tb_contato";
    
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_ENDERECO = "endereco";
    public static final String COLUMN_SITE = "site";
    public static final String COLUMN_TELEFONE = "telefone";
    public static final String COLUMN_AVALIACAO = "avaliacao";
    
    public static final String DROP = "DROP TABLE IF EXISTS " + TABLE + ";";

    public static final String CREATE = ""
            + "CREATE TABLE " + TABLE + " ( "
            + COLUMN_ID + " INTEGER PRIMARY KEY "
            + ", " + COLUMN_NOME + " TEXT UNIQUE NOT NULL "
            + ", " + COLUMN_ENDERECO + " TEXT NOT NULL "
            + ", " + COLUMN_SITE + " TEXT NOT NULL "
            + ", " + COLUMN_TELEFONE + " TEXT NOT NULL "
            + ", " + COLUMN_AVALIACAO + " REAL NULL "
            + ");";
    
    public static final String SELECT = ""
            + "SELECT id, nome, endereco, site, telefone, avaliacao "
            + "FROM " + TABLE + " ";
    
    public static final String WHERE_ID = COLUMN_ID + " = ? ";
    
    public static final String[] COLUMNS = { COLUMN_ID, COLUMN_NOME, COLUMN_ENDERECO, COLUMN_SITE,
        COLUMN_TELEFONE, COLUMN_AVALIACAO };
}
