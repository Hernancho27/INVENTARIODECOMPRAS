package codigohernancho.app.prueba.com.inventariodecompras.BaseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hernancho on 22/06/2017.
 */

public class DBHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "DBInventario_3.sqlite";
    public static final int DB_SCHEME_VERSION = 1;



    public DBHelper(Context context) {

        super(context, DB_NAME, null, DB_SCHEME_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(codigohernancho.app.prueba.com.inventariodecompras.BaseDatos.DataBaseManager.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
