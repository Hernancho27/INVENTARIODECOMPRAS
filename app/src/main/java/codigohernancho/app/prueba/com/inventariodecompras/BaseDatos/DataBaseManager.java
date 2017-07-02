package codigohernancho.app.prueba.com.inventariodecompras.BaseDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import codigohernancho.app.prueba.com.inventariodecompras.sqlite.Producto;

/**
 * Created by Hernancho on 22/06/2017.
 */

public class DataBaseManager {


    public static final String TABLE_NAME = "Inventario";

    public static final String CN_ID = "_id";
    public static final String CN_CODIGO = "cod";
    public static final String CN_FECHA_CREACION = "fecha";
    public static final String CN_CANTIDAD = "cant";
    public static final String CN_IMG_PROD = "img_prod";
    public static final String CN_ESTADO = "estado";
    public static final String CN_NAME = "nombre";
    public static final String CN_DESCRIPCION = "descripcion";

    public static final String CREATE_TABLE = " create table " +TABLE_NAME+ " ("
            + CN_ID + " integer primary key autoincrement,"
            + CN_CODIGO + " text not null,"
            + CN_FECHA_CREACION + " text not null,"
            + CN_CANTIDAD + " integer,"
            + CN_IMG_PROD + " text not null,"
            + CN_ESTADO + " text not null,"
            + CN_NAME + " text not null,"
            + CN_DESCRIPCION + " text);";

    private DBHelper helper;
    private SQLiteDatabase db;


    public DataBaseManager(Context context) {

        helper = new DBHelper(context);
        db = helper.getWritableDatabase();

        //db.insert();

        //db.execSQL();

        //db.rawQuery();

    }
    public Cursor getProductoById(String productoId) {
        Cursor c = helper.getReadableDatabase().query(
                TABLE_NAME,
                null,
                CN_ID + " LIKE ?",
                new String[]{productoId},
                null,
                null,
                null);
        return c;
    }
    public int deleteProducto(String productoId) {
        return helper.getWritableDatabase().delete(
                TABLE_NAME,
                CN_ID + " LIKE ?",
                new String[]{productoId});
    }

    public long saveProducto(Producto producto) {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        return sqLiteDatabase.insert(
                TABLE_NAME,
                null,
                generarContentValues(producto.getCod(),producto.getFecha(), producto.getCant(), producto.getImg_prod(), producto.getEstado(), producto.getNombre(), producto.getDescripcion()));

    }

    public int updateProducto(Producto producto, String productoId) {
        return helper.getWritableDatabase().update(
                TABLE_NAME,
                generarContentValues(producto.getCod(),producto.getFecha(), producto.getCant(), producto.getImg_prod(), producto.getEstado(), producto.getNombre(), producto.getDescripcion()),
                CN_ID + " LIKE ?",
                new String[]{productoId}
        );
    }

    public ContentValues generarContentValues(String cod, String fecha, Integer cant, String img_prod, String estado, String nombre, String descripcion){
        ContentValues valores = new ContentValues();
        valores.put(CN_CODIGO, cod);
        valores.put(CN_FECHA_CREACION, fecha);
        valores.put(CN_CANTIDAD, cant);
        valores.put(CN_IMG_PROD, img_prod);
        valores.put(CN_ESTADO, estado);
        valores.put(CN_NAME, nombre);
        valores.put(CN_DESCRIPCION, descripcion);
        return valores;
    }

    public void insertar(String cod, String fecha, Integer cant, String img_prod, String estado, String nombre, String descripcion){
        db.insert(TABLE_NAME, null,generarContentValues(cod, fecha, cant, img_prod, estado, nombre,descripcion));
    }
    public void insertar2 (String codigo, String nombre, String descripcion){
        db.execSQL("insert into "+TABLE_NAME+" values (null,'"+codigo+"',"+nombre+","+descripcion+")");
    }

    public void eliminar (String nombre) {
        db.delete(TABLE_NAME, CN_NAME + "=?", new String[]{nombre});
    }

    public void eliminarMultiple(String nom1, String nom2){
        db.delete(TABLE_NAME,CN_NAME +"IN (?,?)", new String[]{nom1,nom2});
    }

    public void modificarTelefono (String cod, String fecha, Integer cant, String img_prod, String estado, String nombre, String descripcion){
        db.update(TABLE_NAME,generarContentValues(cod, fecha, cant, img_prod, estado, nombre,descripcion),CN_NAME +"=?", new String[]{nombre});
    }

    public Cursor cargarCursorInventario(){
        String[] columnas = new String[]{CN_ID,CN_CODIGO,CN_FECHA_CREACION,CN_CANTIDAD,CN_IMG_PROD,CN_NAME,CN_ESTADO,CN_DESCRIPCION};
        return db.query(TABLE_NAME,columnas,null,null,null,null,null);
    }

    public Cursor buscarNombre(String nombre) {
        String[] columnas = new String[]{CN_ID,CN_CODIGO,CN_FECHA_CREACION,CN_CANTIDAD, CN_IMG_PROD, CN_ESTADO, CN_NAME,CN_DESCRIPCION};

        /*Retardo para simular demora en la busqueda*/
        /*try{
            Thread.sleep(7000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }*/
        return db.query(TABLE_NAME,columnas,CN_NAME+ "=?", new  String[]{nombre},null,null,null);
    }
    public Cursor getAllProductos() {
        return helper.getReadableDatabase()
                .query(
                        TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor buscarCodigo(String codigo) {
        String[] columnas = new String[]{CN_ID,CN_CODIGO,CN_FECHA_CREACION,CN_CANTIDAD, CN_IMG_PROD, CN_ESTADO, CN_NAME,CN_DESCRIPCION};
        return db.query(TABLE_NAME,columnas,CN_CODIGO+ "=?", new  String[]{codigo}, null,null,null);
    }

}