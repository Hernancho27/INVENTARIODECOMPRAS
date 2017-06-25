package codigohernancho.app.prueba.com.inventariodecompras.gui_entradas;


import android.database.Cursor;
import android.database.CursorJoiner;
import android.database.CursorJoiner.Result;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Intent;
import android.widget.Toast;

import codigohernancho.app.prueba.com.inventariodecompras.R;
import codigohernancho.app.prueba.com.inventariodecompras.gui_entradas.VerEntrada;
import codigohernancho.app.prueba.com.inventariodecompras.sqlite.EntradasSqliteHelper;

public class listadoEntradas extends AppCompatActivity {


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

        try
        {
            lvlitems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(listadoEntradas.this, VerEntrada.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
            });
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "ERROR ENVIAR ID "+ex, Toast.LENGTH_LONG ).show();
        }

    }


}
