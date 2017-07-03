package codigohernancho.app.prueba.com.inventariodecompras.BaseDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import codigohernancho.app.prueba.com.inventariodecompras.modelo.ProductoInventario;


public class DBAdapter implements DataBaseInterface {

    private BDHelperProductos dbHelper;
    private SQLiteDatabase db;
    protected int INGRESO = 1;
    protected int PENDIENTE = 3;
    protected int EN_CURSO = 5;
    protected static final int ENTURNADO = 5;
    protected int DENEGADO = 0;
    protected int FINALIZADO = 7;
    private static final String TAG = "Querys DB";
    private Context context;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Date date;

    public DBAdapter(Context ctx) {
        Log.d(TAG, "Database Helper got");
        try {
            dbHelper = BDHelperProductos.getInstance(ctx);
            context = ctx;
            if (db == null) {
                db = dbHelper.getDataBase();
            }

        } catch (Exception sql) {
            Log.e(TAG, sql.getMessage());
        }
    }







    @Override
    public ArrayList<ProductoInventario>  consultaProductosInventario() {
try {
    Cursor c = db.rawQuery("SELECT * FROM productos", null);
    ArrayList<ProductoInventario> productos = new ArrayList<>();
    if (c.moveToFirst()) {
        //Recorremos el cursor hasta que no haya más registros
        do {

            ProductoInventario producto = new ProductoInventario();
            producto.setId(c.getString(0));
            producto.setNombre(c.getString(1));
            producto.setMarca(c.getString(2));
            producto.setLugar_Compra(c.getString(3));
            producto.setFecha_vencimiento(c.getString(4));
            producto.setFecha_ingreso(c.getString(5));
            producto.setCantidad(c.getString(6));
            producto.setCategoria(c.getString(7));
            producto.setPrecio(c.getString(8));
            productos.add(producto);
        } while (c.moveToNext());
    }
    return productos;


}catch (SQLiteException sql){
    return null;
}


    }

    @Override
    public ArrayList<ProductoInventario> consultaMayorConsumo() {
        try {
            Cursor c = db.rawQuery("SELECT * FROM productos ORDER BY Cantidad DESC LIMIT 1", null);
            ArrayList<ProductoInventario> productos = new ArrayList<>();
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                        ProductoInventario producto = new ProductoInventario();
                        producto.setId(c.getString(0));
                        producto.setNombre(c.getString(1));
                        producto.setMarca(c.getString(2));
                        producto.setLugar_Compra(c.getString(3));
                        producto.setFecha_vencimiento(c.getString(4));
                        producto.setFecha_ingreso(c.getString(5));
                        producto.setCantidad(c.getString(6));
                        producto.setCategoria(c.getString(7));
                        producto.setPrecio(c.getString(8));
                        productos.add(producto);
                } while (c.moveToNext());
            }
            return productos;


        } catch (SQLiteException sql) {
            return null;
        }
    }

    @Override
    public ArrayList<ProductoInventario> consultaMenorConsumo() {
        try {
            Cursor c = db.rawQuery("SELECT * FROM productos where cantidad <> 0 ORDER BY Cantidad ASC LIMIT 1", null);
            ArrayList<ProductoInventario> productos = new ArrayList<>();
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    ProductoInventario producto = new ProductoInventario();
                    producto.setId(c.getString(0));
                    producto.setNombre(c.getString(1));
                    producto.setMarca(c.getString(2));
                    producto.setLugar_Compra(c.getString(3));
                    producto.setFecha_vencimiento(c.getString(4));
                    producto.setFecha_ingreso(c.getString(5));
                    producto.setCantidad(c.getString(6));
                    producto.setCategoria(c.getString(7));
                    producto.setPrecio(c.getString(8));
                    productos.add(producto);
                } while (c.moveToNext());
            }
            return productos;


        } catch (SQLiteException sql) {
            return null;
        }
    }


    public ArrayList<ProductoInventario>  consultaProductosVencidos() {
        Date now = new Date();
        try {
            Cursor c = db.rawQuery("SELECT * FROM productos", null);
            ArrayList<ProductoInventario> productos = new ArrayList<>();
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {

                    Date date = textToDate(c.getString(4));
                    if(date.before(now)){
                    ProductoInventario producto = new ProductoInventario();
                    producto.setId(c.getString(0));
                    producto.setNombre(c.getString(1));
                    producto.setMarca(c.getString(2));
                    producto.setLugar_Compra(c.getString(3));
                    producto.setFecha_vencimiento(c.getString(4));
                    producto.setFecha_ingreso(c.getString(5));
                    producto.setCantidad(c.getString(6));
                    producto.setCategoria(c.getString(7));
                    producto.setPrecio(c.getString(8));
                    productos.add(producto);}
                } while (c.moveToNext());
            }
            return productos;


        } catch (SQLiteException sql) {
            return null;
        }
    }

    @Override
    public ArrayList<ProductoInventario> consultaProductosConsumidosPedido() {
        try {
            Cursor c = db.rawQuery("SELECT * FROM productos", null);
            ArrayList<ProductoInventario> productos = new ArrayList<>();
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    if(Integer.parseInt(c.getString(6))==0){
                        ProductoInventario producto = new ProductoInventario();
                        producto.setId(c.getString(0));
                        producto.setNombre(c.getString(1));
                        producto.setMarca(c.getString(2));
                        producto.setLugar_Compra(c.getString(3));
                        producto.setFecha_vencimiento(c.getString(4));
                        producto.setFecha_ingreso(c.getString(5));
                        producto.setCantidad(c.getString(6));
                        producto.setCategoria(c.getString(7));
                        producto.setPrecio(c.getString(8));
                        productos.add(producto);}
                } while (c.moveToNext());
            }
            return productos;


        } catch (SQLiteException sql) {
            return null;
        }
    }


    private Date textToDate(String dateString){
        Date date = null;
        try {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyyy");
            date = format.parse(dateString);
            Log.i("fecha vencimiento",date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    }