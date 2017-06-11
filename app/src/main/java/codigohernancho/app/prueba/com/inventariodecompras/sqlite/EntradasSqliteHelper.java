package codigohernancho.app.prueba.com.inventariodecompras.sqlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import codigohernancho.app.prueba.com.inventariodecompras.modelo.Entrada;

import static android.widget.Toast.LENGTH_SHORT;


public class EntradasSqliteHelper extends SQLiteOpenHelper{


/*prueba de datos comenrariosdf*/
    public EntradasSqliteHelper(Context context) {
        super(context, "inventario.sqlite", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*ESTE METODO SE EJECUTA AUTOMATICAMENTE SI LA BASE DE DATOS NO EXISTE */
        db.execSQL("CREATE TABLE registrarEntradas (id INTEGER PRIMARY KEY AUTOINCREMENT, idProducto INTEGER, cantidadTotalAnterior INTEGER, cantidadTotalActual INTEGER, fechaRegistro DATETIME)");
        db.execSQL("CREATE TABLE Productos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombreProducto VARCHAR(20), marca VARCHAR(20), unidad VARCHAR(20), descripcion VARCHAR(100), stock_minimo INTEGER, stock_maximo INTEGER, cantidad INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /**ESTE METODO ACTUALIZA LA VERSION DE LA BASE DE DATOS ... ENTONCES QUEDA POR DEFINIR SI SE CONSTRUYE DE
         * NUEVO LA TABLA Y SE REALIZA UN BACKUP DE LOS DATOS O SI DEFINITIVAMENTE SE ELIMINA Y SE CREA NUEVAMENTE*/
        db.execSQL("DROP TABLE IF EXISTS registrarEntradas");
        db.execSQL("DROP TABLE IF EXISTS Productos");
        db.execSQL("CREATE TABLE registrarEntradas (id INTEGER PRIMARY KEY AUTOINCREMENT, idProducto INTEGER, cantidadTotalAnterior INTEGER, cantidadTotalActual INTEGER, fechaRegistro DATETIME);");
        db.execSQL("CREATE TABLE Productos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombreProducto VARCHAR(20), marca VARCHAR(20), unidad VARCHAR(20), descripcion VARCHAR(100), stock_minimo INTEGER, stock_maximo INTEGER, cantidad INTEGER);");
        onCreate(db);
    }

    //public void createProducto(Entrada e)
    public void createProducto()
    {
        try
        {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombreProducto", "Cafe");
            values.put("marca", "Nescafe");
            values.put("unidad", "Libra");
            values.put("descripcion", "cafe importado");
            values.put("stock_minimo", 1);
            values.put("stock_maximo", 5);
            values.put("cantidad", 0);




            ContentValues values1 = new ContentValues();
            values1.put("nombreProducto", "Detergente");
            values1.put("marca", "Ariel");
            values1.put("unidad", "Kilo");
            values1.put("descripcion", "detergente para lavar ropa");
            values1.put("stock_minimo", 1);
            values1.put("stock_maximo", 6);
            values1.put("cantidad", 0);



            ContentValues values2 = new ContentValues();
            values2.put("nombreProducto", "jabon liquido");
            values2.put("marca", "Salvo");
            values2.put("unidad", "botella");
            values2.put("descripcion", "lavaloza");
            values2.put("stock_minimo", 1);
            values2.put("stock_maximo", 4);
            values2.put("cantidad", 0);


            db.insert("Productos", null, values);
            db.insert("Productos", null, values1);
            db.insert("Productos", null, values2);
            db.close();
        }
        catch (SQLiteException ex)
        {

        }

    }


    public void crearEntrada(Entrada e)
    {
        try
        {
            int totalEntrada = e.getCantidadActual() + e.getCantidadAAdicionar();
            Date fechaActual = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues valoresDeRegistro = new ContentValues();
            valoresDeRegistro.put("idProducto", e.getIdProducto());
            valoresDeRegistro.put("cantidadTotalAnterior", e.getCantidadActual());
            valoresDeRegistro.put("cantidadTotalActual", totalEntrada);
            valoresDeRegistro.put("fechaRegistro", formato.format(fechaActual));
            db.insert("registrarEntradas", null, valoresDeRegistro);
            db.close();
        }
        catch (SQLiteException ex)
        {

        }

    }

    public Cursor encontrarProductoPorId(int idEntrada, String nombre){
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


    public Cursor encontrarEntradaPorId(int idProducto, String codigoProducto){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM registrarEntradas WHERE (1=1)";
        if (idProducto > 0)
        {
            query += " AND (id = " + idProducto + ") ";
        }

        if (!codigoProducto.equals(""))
        {
            query += "AND (idProducto LIKE '%"+codigoProducto + "%') ";
        }
        query += ";";
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }

        return c;
    }



    public void modificarEntrada(Entrada e)
    {

        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int totalEntrada = e.getCantidadActual() + e.getCantidadAAdicionar();

        ContentValues valores = new ContentValues();
        valores.put("idProducto", e.getIdProducto());
        valores.put("cantidadTotalAnterior", e.getCantidadActual());
        valores.put("cantidadTotalActual", totalEntrada);
        valores.put("fechaRegistro", formato.format(fechaActual));
        SQLiteDatabase db = getWritableDatabase();
        db.update("registrarEntradas", valores, "id" + "= ?", new String[] { String.valueOf(e.getId())});
        db.close();


    }

    public void eliminarEntrada()
    {

    }
}

