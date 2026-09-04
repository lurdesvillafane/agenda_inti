package com.emprende.inti_agenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDSQLite extends SQLiteOpenHelper {

        // Cambiamos 'date' por 'TEXT' para evitar problemas de formato de fecha
        private static final String SQL_CREATE = "CREATE TABLE pedidos(" +
                "idPedido INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombrePedido TEXT," +
                "descripcion TEXT," +
                "fechadesde TEXT," +
                "fechahasta TEXT," +
                "pago TEXT)";

        public BDSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
                super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL(SQL_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
                // Si necesitas modificar la tabla en el futuro, borrarías la vieja y crearías la nueva:
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS pedidos");
                onCreate(sqLiteDatabase);
        }
}
