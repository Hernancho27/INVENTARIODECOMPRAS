package codigohernancho.app.prueba.com.inventariodecompras;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;import codigohernancho.app.prueba.com.inventariodecompras.R;

public class AgregarNota extends AppCompatActivity {
    Button Add;
    EditText TITLE, CONTENT;
    String type, getTitle;
    AdaptadorBD DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_nota);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Add = (Button) findViewById(R.id.button_Add);
        TITLE = (EditText) findViewById(R.id.editText_Titulo);
        CONTENT = (EditText) findViewById(R.id.editText_Nota);
        Bundle bundle = this.getIntent().getExtras();
        String content;
        getTitle = bundle.getString("title");
        content = bundle.getString("content");
        type = bundle.getString("type");

        if (type.equals("add")) {
            Add.setText("Agregar Nota");
        } else {
            if (type.equals("edit")) {
                TITLE.setText(getTitle);
                CONTENT.setText(content);
                Add.setText("Actualizar Nota");
            }
        }

        Add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addUpdateNotes();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_agregar_nota, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.SALIR:
                CookieSyncManager.createInstance(this);
                CookieManager cookiesManager = CookieManager.getInstance();
                cookiesManager.removeAllCookie();
                Intent intent = new Intent(AgregarNota.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addUpdateNotes() {
        DB = new AdaptadorBD(this);
        String title, content, msj;
        title = TITLE.getText().toString();
        content = CONTENT.getText().toString();
        if (type.equals("add")) {
            if (title.equals("")) {
                msj = "Ingrese un titulo";
                TITLE.requestFocus();
                Mensaje(msj);
            }else{
                if (content.equals("")) {
                    msj = "Ingrese la nota";
                    CONTENT.requestFocus();
                    Mensaje(msj);
                }else{
                    Cursor c = DB.getNote(title);
                    String gettitle = "";
                    // Aseguramos que exista al menos un registro
                    if (c.moveToFirst()){
                        //Recorremos el cursor hasta que no haya mas registros
                        do{
                            gettitle = c.getString(1);
                        }while (c.moveToNext());
                    }
                    if (gettitle.equals(title)){
                        msj = "El titulo de la nota ya existe";
                        TITLE.requestFocus();
                        Mensaje(msj);
                    }else {
                        DB.addNote(title,content);
                        actividad(title,content);
                    }
                }
            }
        }else{
            if(type.equals("edit")){
                Add.setText("Acnutalizar Nota");
                if(title.equals("")){
                    msj = "Ingrese un titulo";
                    TITLE.requestFocus();
                    Mensaje(msj);
                }else{
                    if(content.equals("")){
                        msj = "Ingrese la nota";
                        CONTENT.requestFocus();
                        Mensaje(msj);
                    }else {
                        DB.updateNote(title,content,getTitle);
                        actividad(title,content);
                    }
                }
            }
        }
    }
    public  void Mensaje(String msj){
        Toast toast = Toast.makeText(this, msj, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }
    public void actividad(String title,String content){
        Intent intent = new Intent(AgregarNota.this,VerNota.class);
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        startActivity(intent);
    }

}
