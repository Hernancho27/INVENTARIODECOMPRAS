package codigohernancho.app.prueba.com.inventariodecompras.BaseDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import codigohernancho.app.prueba.com.inventariodecompras.modelo.ProductoInventario;


/**
 * Created by  on 27-06-17.
 */

public class BDHelperProductos extends SQLiteOpenHelper {

    private static BDHelperProductos _dbHelper;
    private Context context;
    private SQLiteDatabase myDataBase;
    private static String DB_PATH = "/data/data/codigohernancho.app.prueba.com.inventariodecompras/databases/";
    private static String DB_NAME = "bdinformes.db";
    private static final String TAG = "HelperDB";
    //public final static String DATABASE_TABLE = "Agendas";

    private BDHelperProductos(Context contexto) {
        super(contexto, "informes", null, 1);
        this.context = contexto;
        try {
            createDataBase();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }

    public static BDHelperProductos getInstance(Context context) {
        if (_dbHelper == null) {
            _dbHelper = new BDHelperProductos(context);
        }
        return _dbHelper;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     *
     */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (!dbExist) {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            Log.e(TAG, "Base de datos no existe + " + e);
        }

        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     *
     */
    private void copyDataBase() throws IOException {
        InputStream myInput = context.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void open() {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    public SQLiteDatabase getDataBase() {

        String myPath = DB_PATH + DB_NAME;
        return SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Entro OnCreate Open Helper");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public ArrayList<ProductoInventario> mostrarProductos (SQLiteDatabase db){
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
    }
}

