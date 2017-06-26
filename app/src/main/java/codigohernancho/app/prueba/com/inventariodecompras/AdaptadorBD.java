package codigohernancho.app.prueba.com.inventariodecompras;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hernancho on 23/06/2017.
 */

public class AdaptadorBD extends SQLiteOpenHelper{
    // Strings finales que contienen las cadenas de lo que se quedara en la BD
    public static final String TABLE_ID = "_idNote";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    //Nombre de la base de datos , de la tabla de la base de datos a utilizar.
    private static final String DATBASE = "Note.db";
    private static final String TABLE = "notes";
    public AdaptadorBD(Context context) {
        super(context, DATBASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE "+ TABLE +" ("+
            TABLE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            TITLE +" TEXT,"+ CONTENT +" TEXT)");
    }
    //Metodo usado en el caso de que haga falta actualizar
    //la version de la base de datos

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE);
        onCreate(db);
    }
    public void addNote (String title,String content){
        ContentValues valores = new ContentValues();
        valores.put(TITLE,title);
        valores.put(CONTENT,content);
        this.getWritableDatabase().insert(TABLE,null,valores);
    }
    // Mediante este emtodo se devuelve una nota.
    public Cursor getNote(String condition){
        String columnas[]={TABLE_ID,TITLE,CONTENT};
        String[] args = new String[] {condition};
        Cursor c = this.getReadableDatabase().query(TABLE, columnas, TITLE+"=?", args, null, null, null);
        return  c;
    }

    public void deleteNote(String condition){
        String args[]={condition};
        this.getWritableDatabase().delete(TABLE,TITLE +"=?", args);
    }

    public void updateNote(String title,String content,String condition){
        String args[]={condition};
        ContentValues valores = new ContentValues();
        valores.put(TITLE,title);
        valores.put(CONTENT,content);
        this.getWritableDatabase().update(TABLE,valores,TITLE +"=?", args);
    }

    //Mediante este metodo devolvemos todas la notas
    public  Cursor getNotes(){
        String columnas[]={TABLE_ID,TITLE,CONTENT};
        Cursor c = this.getReadableDatabase().query(TABLE, columnas, null, null, null, null, null);
        return c;
        }

    public void deleteNotes(){
        this.getWritableDatabase().delete(TABLE,null,null);
    }
}
