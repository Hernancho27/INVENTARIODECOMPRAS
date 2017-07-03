package codigohernancho.app.prueba.com.inventariodecompras.BaseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hernancho on 22/06/2017.
 */

public class DBHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "DBInventario_5.sqlite";
    public static final int DB_SCHEME_VERSION = 1;



    public DBHelper(Context context) {

        super(context, DB_NAME, null, DB_SCHEME_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*String cod = "019057";
        String fecha = "20170701" ;
        Integer cant = 3;
        String img_prod = "img.jpg" ;
        String estado = "activo";
        String nombre ="Maiz";
        String descripcion = "Maiz tierno en harina ";*/
        db.execSQL(codigohernancho.app.prueba.com.inventariodecompras.BaseDatos.DataBaseManager.CREATE_TABLE);
        db.execSQL(codigohernancho.app.prueba.com.inventariodecompras.BaseDatos.DataBaseManager.CREATE_TABLE2);
        db.execSQL(codigohernancho.app.prueba.com.inventariodecompras.BaseDatos.DataBaseManager.CREATE_TABLE3);
        db.execSQL(codigohernancho.app.prueba.com.inventariodecompras.BaseDatos.DataBaseManager.CREATE_TABLE4);
        //db.execSQL("insert into Inventario values ( '1', '"+cod+"', '"+fecha+"', "+cant.toString()+", '"+img_prod+"', '"+estado+"', '"+nombre+"', '"+descripcion+"'); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
