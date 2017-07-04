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
        super(context, "DBInventario_5.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*ESTE METODO SE EJECUTA AUTOMATICAMENTE SI LA BASE DE DATOS NO EXISTE */
        db.execSQL("CREATE TABLE Entradas (_id INTEGER PRIMARY KEY AUTOINCREMENT, cod TEXT, fecha TEXT, cant INTEGER, img_prod TEXT, estado TEXT, nombre INTEGER, descripcion TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /**ESTE METODO ACTUALIZA LA VERSION DE LA BASE DE DATOS ... ENTONCES QUEDA POR DEFINIR SI SE CONSTRUYE DE
         * NUEVO LA TABLA Y SE REALIZA UN BACKUP DE LOS DATOS O SI DEFINITIVAMENTE SE ELIMINA Y SE CREA NUEVAMENTE*/
        db.execSQL("DROP TABLE IF EXISTS Entradas");
        db.execSQL("CREATE TABLE Entradas (_id INTEGER PRIMARY KEY AUTOINCREMENT, cod TEXT, fecha TEXT, cant INTEGER, img_prod TEXT, estado TEXT, nombre INTEGER, descripcion TEXT);");
        onCreate(db);
    }


    public boolean crearEntrada(Entrada e)
    {
        try
        {
            int totalEntrada = e.getCantidadActual() + e.getCantidadAAdicionar();
            Date fechaActual = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues valoresDeRegistro = new ContentValues();
            valoresDeRegistro.put("cod", e.getIdProducto());
            valoresDeRegistro.put("nombre", e.getNombre());
            valoresDeRegistro.put("cant", totalEntrada);
            valoresDeRegistro.put("img_prod", e.getRutaImagen());
            valoresDeRegistro.put("fecha", formato.format(fechaActual));
            valoresDeRegistro.put("estado", "activo");
            long registrado = db.insertOrThrow("Entradas", null, valoresDeRegistro);
            db.close();
            if (registrado > 0)
            {
                return true;
            }

        }
        catch (SQLiteException ex)
        {
            Toast.makeText(null, "ERROR CREAR registrarEntrada "+ex, Toast.LENGTH_LONG ).show();
            return false;
        }
        return false;
    }

    public Cursor encontrarProductoPorId(String idProducto, String nombre){
        Cursor c;
        try
        {
            SQLiteDatabase db = getWritableDatabase();
            String query = "SELECT * FROM Crear WHERE (1=1)";
            if (!idProducto.equals(""))
            {
                query += " AND (cod = '" + idProducto + "') ";
            }

            if (!nombre.equals(""))
            {
                query += "AND (nombre LIKE '%"+nombre + "%') ";
            }
            query += " AND (estado = 'activo');";
            c = db.rawQuery(query, null);

        }
        catch (Exception ex)
        {
            Toast.makeText(null, "ERROR CREAR buscar producto "+ex, Toast.LENGTH_LONG ).show();
            return  null;
        }
        return c;

    }


    public Cursor encontrarEntradaPorId(Entrada e){
        try
        {
            SQLiteDatabase db = getWritableDatabase();
            String query = "SELECT e._id as _id, e.cod, p.nombre as nombre, e.cant as cantidad, p.img_prod FROM Crear as p INNER JOIN Entradas as e ON e.cod = p.cod WHERE (1=1)";
            if (e.getId() > 0)
            {
                query += " AND (e._id = " + e.getId() + ") ";
            }
            query += " AND (e.estado = 'activo');";
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
            valores.put("cod", e.getIdProducto());
            valores.put("cant", e.getCantidadAAdicionar());
            valores.put("fecha", formato.format(fechaActual));
            valores.put("estado", "activo");
            SQLiteDatabase db = getWritableDatabase();
            int entradas = db.update("Entradas", valores, "_id" + "= ?", new String[]{String.valueOf(e.getId())});
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
            String query = ("SELECT e._id, p.nombre, e.cant FROM Crear as p INNER JOIN Entradas as e ON e.cod = p.cod WHERE 1 ORDER BY e._id;");
            Cursor c = db.rawQuery(query, null);

            /*if (c != null) {
                c.moveToFirst();
            }*/

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
            int entradas = db.delete("Entradas", "_id" + "= ?", new String[]{String.valueOf(e.getId())});
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

