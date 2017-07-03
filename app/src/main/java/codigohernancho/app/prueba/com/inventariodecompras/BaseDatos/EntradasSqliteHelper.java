package codigohernancho.app.prueba.com.inventariodecompras.BaseDatos;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import codigohernancho.app.prueba.com.inventariodecompras.sqlite.Entrada;


public class EntradasSqliteHelper extends SQLiteOpenHelper{


/*prueba de datos comenrariosdf*/
    public EntradasSqliteHelper(Context context) {
        super(context, "inventario.sqlite", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*ESTE METODO SE EJECUTA AUTOMATICAMENTE SI LA BASE DE DATOS NO EXISTE */
        /*db.execSQL("CREATE TABLE registrarEntradas (id INTEGER PRIMARY KEY AUTOINCREMENT, idProducto INTEGER, cantidadTotalAnterior INTEGER, cantidadTotalActual INTEGER, fechaRegistro DATETIME)");
        * db.execSQL("CREATE TABLE Productos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombreProducto VARCHAR(20), marca VARCHAR(20), unidad VARCHAR(20), descripcion VARCHAR(100), stock_minimo INTEGER, stock_maximo INTEGER, cantidad INTEGER);");*/
        db.execSQL("CREATE TABLE Entradas (entrada_id INTEGER PRIMARY KEY AUTOINCREMENT, producto_id INTEGER, cantidad_entrada INTEGER, fecha DATETIME, estado INTEGER, FOREIGN KEY(producto_id) REFERENCES Productos(producto_id));");
        db.execSQL("CREATE TABLE Productos (producto_id INTEGER PRIMARY KEY AUTOINCREMENT, nombre VARCHAR(20), descripcion VARCHAR(100), fecha_creacion DATETIME, cantidad INTEGER, estado INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /**ESTE METODO ACTUALIZA LA VERSION DE LA BASE DE DATOS ... ENTONCES QUEDA POR DEFINIR SI SE CONSTRUYE DE
         * NUEVO LA TABLA Y SE REALIZA UN BACKUP DE LOS DATOS O SI DEFINITIVAMENTE SE ELIMINA Y SE CREA NUEVAMENTE*/
        db.execSQL("DROP TABLE IF EXISTS Entradas");
        db.execSQL("DROP TABLE IF EXISTS Productos");
        /*db.execSQL("CREATE TABLE registrarEntradas (id INTEGER PRIMARY KEY AUTOINCREMENT, idProducto INTEGER, cantidadTotalAnterior INTEGER, cantidadTotalActual INTEGER, fechaRegistro DATETIME);");
        db.execSQL("CREATE TABLE Productos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombreProducto VARCHAR(20), marca VARCHAR(20), unidad VARCHAR(20), descripcion VARCHAR(100), stock_minimo INTEGER, stock_maximo INTEGER, cantidad INTEGER);");*/
        db.execSQL("CREATE TABLE Entradas (entrada_id INTEGER PRIMARY KEY AUTOINCREMENT, producto_id INTEGER, cantidad_entrada INTEGER, fecha DATETIME, estado INTEGER, FOREIGN KEY(producto_id) REFERENCES Productos(producto_id));");
        db.execSQL("CREATE TABLE Productos (producto_id INTEGER PRIMARY KEY AUTOINCREMENT, nombre VARCHAR(20), descripcion VARCHAR(100), fecha_creacion DATETIME, cantidad INTEGER, estado INTEGER);");
        onCreate(db);
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
            valoresDeRegistro.put("producto_id", e.getIdProducto());
            valoresDeRegistro.put("cantidad_entrada", totalEntrada);
            valoresDeRegistro.put("fecha", formato.format(fechaActual));
            valoresDeRegistro.put("estado", 1);
            db.insert("Entradas", null, valoresDeRegistro);
            db.close();
        }
        catch (SQLiteException ex)
        {
            Toast.makeText(null, "ERROR CREAR registrarEntrada "+ex, Toast.LENGTH_LONG ).show();
        }

    }

    public Cursor encontrarProductoPorId(int idEntrada, String nombre){
        try
        {
            SQLiteDatabase db = getWritableDatabase();
            String query = "SELECT * FROM Productos WHERE (1=1)";
            if (idEntrada > 0)
            {
                query += " AND (producto_id = " + idEntrada + ") ";
            }

            if (!nombre.equals(""))
            {
                query += "AND (nombre LIKE '%"+nombre + "%') ";
            }
            query += ";";
            Cursor c = db.rawQuery(query, null);

            if (c != null) {
                c.moveToFirst();
            }

            return c;
        }
        catch (Exception ex)
        {

        }
        return  null;

    }


    public Cursor encontrarEntradaPorId(Entrada e){
        try
        {
            SQLiteDatabase db = getWritableDatabase();
            String query = "SELECT e.entrada_id as _id, e.producto_id, p.nombre as nombre, e.cantidad_entrada as cantidad FROM Productos as p INNER JOIN Entradas as e ON e.producto_id = p.producto_id WHERE (1=1)";
            if (e.getId() > 0)
            {
                query += " AND (e.entrada_id = " + e.getId() + ") ";
            }
            query += ";";
            Cursor c = db.rawQuery(query, null);

            if (c != null) {
                c.moveToFirst();
            }

            return c;

        }
        catch (Exception ex)
        {

        }
        return null;
    }



    public boolean modificarEntrada(Entrada e)
    {

        try
        {
            Date fechaActual = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int totalEntrada = e.getCantidadActual() + e.getCantidadAAdicionar();

            ContentValues valores = new ContentValues();
            valores.put("producto_id", e.getIdProducto());
            valores.put("cantidad_entrada", e.getCantidadAAdicionar());
            valores.put("fecha", formato.format(fechaActual));
            valores.put("estado", 1);
            SQLiteDatabase db = getWritableDatabase();
            int entradas = db.update("Entradas", valores, "entrada_id" + "= ?", new String[]{String.valueOf(e.getId())});
            db.close();
            if (entradas > 0)
            {
                return true;
            }
        }
        catch (Exception ex)
        {

        }

        return false;
    }


    public Cursor listarEntradas()
    {
        try
        {
            SQLiteDatabase db = getReadableDatabase();
            String query = ("SELECT e.entrada_id as _id, p.nombre, e.cantidad_entrada FROM Productos as p INNER JOIN Entradas as e ON e.producto_id = p.producto_id WHERE 1 ORDER BY e.entrada_id;");
            Cursor c = db.rawQuery(query, null);

            if (c != null) {
                c.moveToFirst();
            }

            return c;
        }
        catch (Exception ex)
        {

        }
        return  null;
    }


    public boolean eliminarEntrada(Entrada e)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();
            //db.execSQL("DELETE FROM Entradas WHERE entrada_id = " + e.getId() + ";");
            int entradas = db.delete("Entradas", "entrada_id" + "= ?", new String[]{String.valueOf(e.getId())});
            db.close();
            if (entradas > 0)
            {
                return true;
            }
        }
        catch (Exception ex)
        {

        }
        return false;
    }


    public boolean eliminarEntradaLogico(Entrada e)
    {
        try
        {
            ContentValues valores = new ContentValues();
            valores.put("estado", e.getEstado());
            SQLiteDatabase db = getWritableDatabase();
            int entradas = db.update("Entradas", valores, "estado" + "= ?", new String[]{String.valueOf(e.getEstado())});
            db.close();
            if (entradas > 0)
            {
                return true;
            }
        }
        catch (Exception ex)
        {

        }
        return false;
    }

}

