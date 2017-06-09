package baseDeDatos;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import objetos.Entrada;


public class EntradasSqliteHelper extends SQLiteOpenHelper{


/*prueba de datos comenrariosdf*/
    public EntradasSqliteHelper(Context context) {
        super(context, "BDInventario.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*ESTE METODO SE EJECUTA AUTOMATICAMENTE SI LA BASE DE DATOS NO EXISTE */
        db.execSQL("CREATE TABLE registrarEntradas (id INTEGER PRIMARY KEY AUTOINCREMENT, idProducto INTEGER, cantidadTotalAnterior INTEGER, cantidadTotalActual INTEGER, fechaRegistro DATETIME)");
        db.execSQL("CREATE TABLE Productos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombreProducto TEXT, cantidad INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /**ESTE METODO ACTUALIZA LA VERSION DE LA BASE DE DATOS ... ENTONCES QUEDA POR DEFINIR SI SE CONSTRUYE DE
         * NUEVO LA TABLA Y SE REALIZA UN BACKUP DE LOS DATOS O SI DEFINITIVAMENTE SE ELIMINA Y SE CREA NUEVAMENTE*/
        db.execSQL("DROP TABLE IF EXIST registrarEntradas;");
        db.execSQL("CREATE TABLE registrarEntradas (id INTEGER PRIMARY KEY AUTOINCREMENT, idProducto INTEGER, cantidadTotalAnterior INTEGER, cantidadTotalActual INTEGER, fechaRegistro DATETIME);");
        db.execSQL("CREATE TABLE Productos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombreProducto TEXT, cantidad INTEGER)");
        onCreate(db);
    }

    public void createProducto(Entrada e)
    {
        try
        {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombreProducto", e.getNombreProducto());
            values.put("cantidad", e.getCantidadAdicionada());
            db.insert("Productos", null, values);
            db.close();
        }
        catch (SQLiteException ex)
        {

        }

    }


    public Cursor entradabyid(int idEntrada, String nombre){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM Productos WHERE (1=1)";
            if (idEntrada > 0)
            {
                query += " AND (id = " + idEntrada + ") ";
            }

            if (!nombre.equals(""))
            {
                query += "AND (nombreProducto LIKE '%"+nombre + "%') ";
            }


            query += ";";
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }

        return c;
    }
}
