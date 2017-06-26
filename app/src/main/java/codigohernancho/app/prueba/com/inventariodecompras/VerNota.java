package codigohernancho.app.prueba.com.inventariodecompras;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.TextView;
import android.widget.Toast;
public class VerNota extends AppCompatActivity {

    String title,content;
    TextView TITLE,CONTENT;
    AdaptadorBD DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_nota);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = this.getIntent().getExtras();

        title = bundle.getString("title");
        content = bundle.getString("content");

        TITLE = (TextView)findViewById(R.id.textView_Titulo);
        CONTENT = (TextView)findViewById(R.id.textView_Content);
        TITLE.setText(title);
        CONTENT.setText(content);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ver_nota, menu);
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
            case R.id.EDITAR:
                    actividad("edit");
                return true;
            case R.id.BORRAR:
                    alert();
                return true;
            case R.id.SALIR:
                    actividad("delete");
                //finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public void actividad(String f){
        if (f.equals("edit")){
            String type ="edit";
            Intent intent = new Intent(VerNota.this,AgregarNota.class);
            intent.putExtra("type",type);
            intent.putExtra("title",title);
            intent.putExtra("content",content);
            startActivity(intent);
        }else{
            if (f.equals("delete")){
                CookieSyncManager.createInstance(this);
                CookieManager cookiesManager = CookieManager.getInstance();
                cookiesManager.removeAllCookie();
                Intent intent = new Intent(VerNota.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

        }
    }

    //Generamos un cuadro de dialogo simple
    private void alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Mensaje de confirmacion");
                builder.setMessage("¿Desea eliminar la nota?");
                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(VerNota.this, "Nota eliminada", Toast.LENGTH_SHORT).show();
                        delete();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);

                Dialog dialog = builder.create();
                dialog.show();

    }

    private void delete(){
        DB = new AdaptadorBD(this);
        DB.deleteNote(title);
        actividad("delete");
    }

}
