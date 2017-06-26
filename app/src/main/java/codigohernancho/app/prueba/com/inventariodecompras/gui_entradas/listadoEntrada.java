package codigohernancho.app.prueba.com.inventariodecompras.gui_entradas;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Intent;
import android.widget.Toast;

import codigohernancho.app.prueba.com.inventariodecompras.R;

import codigohernancho.app.prueba.com.inventariodecompras.BaseDatos.EntradasSqliteHelper;

public class listadoEntrada extends AppCompatActivity {


    EntradasSqliteHelper u;
    ListView lvlitems;
    Cursor cursor;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listado_entrada);
        u = new EntradasSqliteHelper(this);
        try {
            cursor = u.listarEntradas();
            lvlitems = (ListView) findViewById(R.id.lvlitems);
            lvlitems.setTextFilterEnabled(true);
            final ControlListado todoAdapter = new ControlListado(this, cursor);
            lvlitems.setAdapter(todoAdapter);
        }

        catch (Exception ex)
        {
            /*Snackbar.make(new View(), "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();*/
            Toast.makeText(this, "ERROR LISTADO "+ex, Toast.LENGTH_LONG ).show();
        }


            lvlitems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(listadoEntrada.this, modificarEntrada.class);
                    //intent.putExtra("entrada_id",id);
                    Bundle bundle = new Bundle();
                    bundle.putLong("entrada_id", id);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });


    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //getMenuInflater().inflate(R.menu.menu_entradas, menu);
        //return  true;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_entradas, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
            {
                this.finish();
                return true;
            }

            case R.id.action_add:
            {
                Toast.makeText(this, "El usuario es: "+1+" "+1+" "+1, Toast.LENGTH_LONG ).show();
                return  true;
            }
            default:
            {
                return  super.onOptionsItemSelected(item);
            }
        }


    }



}
