package codigohernancho.app.prueba.com.inventariodecompras.gui_entradas;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import codigohernancho.app.prueba.com.inventariodecompras.R;
import codigohernancho.app.prueba.com.inventariodecompras.sqlite.EntradasSqliteHelper;

public class listadoEntradas extends AppCompatActivity {


    EntradasSqliteHelper u;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listado_entrada);
        u = new EntradasSqliteHelper(this);
        cargarDatos();
    }
    public void listar_clicked(View view)
    {
        Intent i = new Intent(this, listadoEntradas.class);
        startActivity(i);
    }

    public void cargarDatos()
    {
        try
        {
            Cursor cursor = u.listarEntradas();
            ListView lvlitems = (ListView) findViewById(R.id.lvlitems);
            lvlitems.setTextFilterEnabled(true);
            final ControlListado todoAdapter = new ControlListado(this, cursor);
            lvlitems.setAdapter(todoAdapter);

        }
        catch (Exception ex)
        {
            Toast.makeText(this, "ERROR GUARDAR "+ex, Toast.LENGTH_LONG ).show();
        }
    }
}
