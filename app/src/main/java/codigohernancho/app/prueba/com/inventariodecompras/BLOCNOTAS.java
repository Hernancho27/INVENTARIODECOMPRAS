package codigohernancho.app.prueba.com.inventariodecompras;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BLOCNOTAS extends AppCompatActivity {

    ListView lista;
    TextView textLista;
    AdaptadorBD DB;
    List<String> itmen = null;
    String getTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocnotas);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textLista = (TextView)findViewById(R.id.textView_Lista);
        lista = (ListView)findViewById(R.id.listView_Lista);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getTitle = (String) lista.getItemAtPosition(position);
                alert("list");
            }
        });


        showNotes();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            case R.id.ADD:
                actividad("add");
                return true;
            case R.id.DELETE:
                alert("deletes");
                return true;
            case R.id.EXIT:
                //finish();
                Intent intent1 = new Intent(BLOCNOTAS.this, DrawerMenu.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private  void showNotes(){
        DB = new  AdaptadorBD(this);
        Cursor c = DB.getNotes();
        itmen = new ArrayList<String>();
        String title = "";
        //Aseguramos que existe al menos un registro
        if (c.moveToFirst() == false){
            //si el cursor esta vacio
            textLista.setText("No hay notas");
        }else {
            //Recorremos el cursor hasta que no haya mas registros
            do{
                title = c.getString(1);

                itmen.add(title);
            }while(c.moveToNext());
        }
        //se crea un adaptador de tipo array
        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, itmen);
        lista.setAdapter(adaptador);
    }

    public String getNote(){
        String type ="",content = "";

        DB = new AdaptadorBD(this);
        Cursor c = DB.getNote(getTitle);
        //Aseguramos que existe al menos un registro
        if (c.moveToFirst())  {
            //Recorremos el cursor hasta que no haya mas registros
            do{
                content = c.getString(2);

            }while(c.moveToNext());
        }
        return content;
    }

    public void  actividad(String act){
        String type = "",content = "";
        if (act.equals("add")){
            type = "add";
            Intent intent = new Intent(BLOCNOTAS.this,AgregarNota.class);
            intent.putExtra("type", type);
            startActivity(intent);
        }else {
            if (act.equals("edit")){
                type = "edit";
                content = getNote();
                Intent intent = new Intent(BLOCNOTAS.this,AgregarNota.class);
                intent.putExtra("type",type);
                intent.putExtra("title",getTitle);
                intent.putExtra("content",content);
                startActivity(intent);

            }else{
                if(act.equals("see")){
                    content = getNote();
                    Intent intent = new Intent(BLOCNOTAS.this,VerNota.class);
                    intent.putExtra("title",getTitle);
                    intent.putExtra("content",content);
                    startActivity(intent);
                }
            }
        }
    }

    //Generamos un cuadro de dialogo simple
    private void alert(String f){
        //AlertDialog alerta;
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        if(f.equals("list")){
        alerta.setTitle("Titulo de la Nota: " +getTitle);
        alerta.setMessage("¿Que accion desea realizar?");
            alerta.setPositiveButton("Ver", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                actividad("see");
                }
            });
            alerta.setNeutralButton("Eliminar", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    delete("delete");
                    Intent intent = getIntent();
                    startActivity(intent);
                }
            });
            alerta.setNegativeButton("Editar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    actividad("edit");
                }
            });
        }else{
            if (f.equals("deletes")){
                alerta.setTitle("Mensaje de confirmacion");
                alerta.setMessage("¿Que accion desea realizar");
                alerta.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
                alerta.setNeutralButton("Eliminar Notas", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete("deletes");
                        Intent intent = getIntent();
                        startActivity(intent);
                    }
                });
            }
        }
        Dialog dialog = alerta.create();
        dialog.show();
    }

    private void delete(String f){
        DB = new AdaptadorBD(this);
        if(f.equals("delete")){
            DB.deleteNote(getTitle);
        }else {
            if(f.equals("deletes")){
                DB.deleteNotes();
            }
        }
    }
}
