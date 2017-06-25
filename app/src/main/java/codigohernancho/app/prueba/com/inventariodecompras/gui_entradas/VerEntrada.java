package codigohernancho.app.prueba.com.inventariodecompras.gui_entradas;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import codigohernancho.app.prueba.com.inventariodecompras.R;
import codigohernancho.app.prueba.com.inventariodecompras.modelo.Entrada;
import codigohernancho.app.prueba.com.inventariodecompras.sqlite.EntradasSqliteHelper;

public class VerEntrada extends AppCompatActivity {
    EditText nombre_producto;
    EditText cantidad_producto;
    EntradasSqliteHelper u;

    protected void onCreate(Bundle savedInstanceState) {

        //Aqui se hace el retrieve de la base de datos tomando un valor que viene en el intent anterior

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_entrada);
        u = new EntradasSqliteHelper(this);


        nombre_producto = (EditText) findViewById(R.id.txt_nombre_producto);
        cantidad_producto = (EditText) findViewById(R.id.txt_cantidad_producto);

        try {
            Entrada unaEntrada = new Entrada();

            Intent i = getIntent(); // gets the previously created intent
            String stringid = i.getExtras().getString("id");

            int id = Integer.parseInt(stringid);
            Cursor c = u.encontrarEntradaPorId(id, "");
            nombre_producto.setText(c.getString(c.getColumnIndexOrThrow("nombre")));
            cantidad_producto.setText(c.getString(c.getColumnIndexOrThrow("cantidad")));
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "ERROR MOSTRAR ID "+ex, Toast.LENGTH_LONG ).show();
        }




    }
}
