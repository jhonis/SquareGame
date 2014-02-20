package br.com.jgames.squaregame.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.*;
import android.util.Log;

public class PartidaDAO extends SQLiteOpenHelper {
    private static final String NOME_BD = "squaregamey.db";
    private static final int VERSAO_BD = 1;
    private static final String LOG_TAG = "squareGame";
    private final Context contexto;

    public PartidaDAO(Context context) {
        super(context, NOME_BD, null, VERSAO_BD);
        this.contexto = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL("CREATE TABLE sq_partida ( id INTEGER PRIMARY KEY AUTOINCREMENT, tempo INTEGER, movimentos INTEGER, sequencia TEXT) ");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.e("Erro ao criar as tabelas e testar os dados", e.toString());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Atualizando a base de dados da versão " + oldVersion + " para " + newVersion + ", que destruirá todos os dados antigos");
        db.beginTransaction();
        try {
            db.execSQL("DROP TABLE IF EXISTS sq_partida ");
            onCreate(db);
        } catch (SQLException e) {
            Log.e("Erro ao atualizar as tabelas e testar os dados", e.toString());
            throw e;
        } finally {
            db.endTransaction();
        }
    }

    public CursorPartida buscarPartida() {
        String sql = "SELECT * FROM sq_partida ORDER BY id DESC";
        SQLiteDatabase bd = getReadableDatabase();
        CursorPartida cc = (CursorPartida) bd.rawQueryWithFactory(new CursorPartida.Factory(), sql, null, null);
        cc.moveToFirst();
        return cc;
    }

    public long inserirPartida(Long tempo, Integer movimentos, String sequencia) {
        SQLiteDatabase db = getReadableDatabase();
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put("tempo", tempo);
            initialValues.put("movimentos", movimentos);
            initialValues.put("sequencia", sequencia);
            return db.insert("sq_partida", null, initialValues);
        } finally {
            db.close();
        }
    }

    public void atualizarPartida(Long tempo, Integer movimentos, String sequencia) {
        CursorPartida partida = buscarPartida();
        SQLiteDatabase db = getReadableDatabase();
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put("tempo", tempo);
            initialValues.put("movimentos", movimentos);
            initialValues.put("sequencia", sequencia);
            db.update("sq_partida", initialValues, "id = ?", new String[]{partida.getId().toString()});
        } finally {
            db.close();
        }
    }

    public static class CursorPartida extends SQLiteCursor {

        private CursorPartida(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
            super(db, driver, editTable, query);
        }

        private static class Factory implements SQLiteDatabase.CursorFactory {
            @Override
            public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
                return new CursorPartida(db, driver, editTable, query);
            }
        }

        public Long getId() {
            return getLong(getColumnIndex("id"));
        }

        public Long getTempo() {
            return getLong(getColumnIndex("tempo"));
        }

        public Integer getMovimentos() {
            return getInt(getColumnIndex("movimentos"));
        }

        public String getSequencia() {
            return getString(getColumnIndex("sequencia"));
        }
    }
}
