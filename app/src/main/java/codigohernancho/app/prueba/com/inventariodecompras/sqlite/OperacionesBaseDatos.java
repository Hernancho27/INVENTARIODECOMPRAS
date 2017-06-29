package codigohernancho.app.prueba.com.inventariodecompras.sqlite;

/**
 * Created by urreal on 26/05/2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import codigohernancho.app.prueba.com.inventariodecompras.sqlite.ContratoInventario.ProductoEntrada;


public class OperacionesBaseDatos extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Productos.db";

    public OperacionesBaseDatos (Context context){
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ProductoEntrada.TABLE_NAME + " ("
                + ProductoEntrada._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ProductoEntrada.ID + " TEXT NOT NULL,"
                + ProductoEntrada.NOMBRE + " TEXT NOT NULL,"
                + ProductoEntrada.DESCRIPCION + " TEXT NOT NULL,"
                + ProductoEntrada.FECHA_CREACION + " TEXT NOT NULL,"
                + ProductoEntrada.CANTIDAD + " INTEGER,"
                + ProductoEntrada.IMG_PROD + " TEXT NOT NULL,"
                + ProductoEntrada.ESTADO + " INTEGER" + ")");

        // Insertar datos ficticios para prueba inicial
        mockData(db);

    }
    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockProducto(sqLiteDatabase, new Producto("Producto Dummy",
                "Producto para pruebas", "20170601","0","tv.jpg",1));
    }

    public long mockProducto(SQLiteDatabase db, Producto producto) {
        return db.insert(
                ProductoEntrada.TABLE_NAME,
                null,
                producto.toContentValues());
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }
    public long saveProducto(Producto producto) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                ProductoEntrada.TABLE_NAME,
                null,
                producto.toContentValues());

    }

    public Cursor getAllProductos() {
        return getReadableDatabase()
                .query(
                        ProductoEntrada.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getProductoById(String productoId) {
        Cursor c = getReadableDatabase().query(
                ProductoEntrada.TABLE_NAME,
                null,
                ProductoEntrada.ID + " LIKE ?",
                new String[]{productoId},
                null,
                null,
                null);
        return c;
    }

    public int deleteProducto(String productoId) {
        return getWritableDatabase().delete(
                ProductoEntrada.TABLE_NAME,
                ProductoEntrada.ID + " LIKE ?",
                new String[]{productoId});
    }

    public int updateProducto(Producto producto, String productoId) {
        return getWritableDatabase().update(
                ProductoEntrada.TABLE_NAME,
                producto.toContentValues(),
                ProductoEntrada.ID + " LIKE ?",
                new String[]{productoId}
        );
    }
}