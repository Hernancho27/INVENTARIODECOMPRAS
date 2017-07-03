package codigohernancho.app.prueba.com.inventariodecompras.gui.entradas;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;

import com.bumptech.glide.Glide;

import codigohernancho.app.prueba.com.inventariodecompras.R;
import codigohernancho.app.prueba.com.inventariodecompras.modelo.Entrada;
import codigohernancho.app.prueba.com.inventariodecompras.BaseDatos.EntradasSqliteHelper;

public class modificarEntrada extends AppCompatActivity {
    EditText nombre_producto;
    EditText cantidad_producto;
    EntradasSqliteHelper u;
    int IdProducto;
    long IdEntrada;
    String mensaje;
    FloatingActionButton fab;
    FloatingActionButton fab_eliminar;
    private ImageView imagenProducto;

    protected void onCreate(Bundle savedInstanceState) {

        //Aqui se hace el retrieve de la base de datos tomando un valor que viene en el intent anterior

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_entrada);
        u = new EntradasSqliteHelper(this);



        imagenProducto = (ImageView) findViewById(R.id.img_producto);
        nombre_producto = (EditText) findViewById(R.id.txt_nombre_producto);
        cantidad_producto = (EditText) findViewById(R.id.txt_cantidad_producto);
        nombre_producto.setEnabled(false);

        try {
            Entrada e = new Entrada();
            Long id = getIntent().getExtras().getLong("entrada_id");
            e.setId(id.longValue());
            Cursor c = u.encontrarEntradaPorId(e);
            IdProducto = c.getInt(c.getColumnIndexOrThrow("producto_id"));
            IdEntrada = id.longValue();
            nombre_producto.setText(c.getString(c.getColumnIndexOrThrow("nombre")));
            cantidad_producto.setText(c.getString(c.getColumnIndexOrThrow("cantidad")));
            Glide.with(this)
                    .load(Uri.parse("file:///android_asset/" + ""))
                    .centerCrop()
                    .into(imagenProducto);
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "ERROR LISTADO "+ex, Toast.LENGTH_LONG ).show();
        }



        fab = (FloatingActionButton) findViewById(R.id.FAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(modificarEntrada.this, listadoEntrada.class);

                try
                {
                    Entrada e = new Entrada();
                    e.setCantidadAAdicionar(Integer.parseInt(cantidad_producto.getText().toString()));
                    e.setIdProducto(IdProducto);
                    e.setId(IdEntrada);
                    if (u.modificarEntrada(e))
                    {
                        startActivity(intent);
                    }
                    else
                    {
                        mensaje("No se logrà modificar la entrada: No se encuentra el registro");
                    }
                }
                catch (Exception ex)
                {
                    //Toast.makeText(modificarEntrada.this, "Se ha producido un Error: \n Se deben ingresar valores numericos \n No se logrò modificar el registro", Toast.LENGTH_LONG ).show();
                    mensaje("No se logrà modificar la entrada: Se ingresò un valor no numèrico");
                    //Toast.makeText(modificarEntrada.this, "Se ha producido un error al mdificar el registro", Toast.LENGTH_LONG ).show();
                }
                //Snackbar.make(view, "Se presionó el FAB", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });



        fab_eliminar = (FloatingActionButton) findViewById(R.id.FAB1);
        fab_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmacion();
                //Snackbar.make(view, "Se presionó el FAB", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

    }





    public void mensaje(String msg){

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setTitle(msg);
        //dlgAlert.setCancelable(false);

        dlgAlert.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        noModificado();
                    }
                });


        //dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }



    public void noModificado()
    {
        try
        {
            Intent intent = new Intent(modificarEntrada.this, listadoEntrada.class);
            startActivity(intent);
        }
        catch (Exception ex)
        {
            Toast.makeText(modificarEntrada.this, "Se ha producido un ERROR: \n Se deben ingresar valores numericos \n No se logrò modificar el registro", Toast.LENGTH_LONG ).show();
        }
    }






    public void confirmacion(){

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setTitle("Desea eliminar el registro actual?");
        dlgAlert.setCancelable(false);

        //dlgAlert.setMessage(elimina_input.getText());

        dlgAlert.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Aceptar();
                    }
                });

        dlgAlert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Cancelar();
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    public void Aceptar()
    {
        try
        {
            Intent intent = new Intent(modificarEntrada.this, listadoEntrada.class);
            Entrada e = new Entrada();
            e.setId(IdEntrada);
            e.setEstado(0);
            if (u.eliminarEntrada(e))
            {
                startActivity(intent);
            }
            /*if (u.eliminarEntradaLogico(e))
            {
                startActivity(intent);
            }*/
        }
        catch (Exception ex)
        {
            Toast.makeText(modificarEntrada.this, "Se ha producido un ERROR: \n Se deben ingresar valores numericos \n No se logrò modificar el registro", Toast.LENGTH_LONG ).show();
            //Toast.makeText(modificarEntrada.this, "Se ha producido un error al mdificar el registro", Toast.LENGTH_LONG ).show();
        }
    }

    public void Cancelar()
    {
        finish();
    }


    public void crearEntrada_clicked(View view)
    {
        try
        {
            Intent i = new Intent(this, registrarEntrada.class);
            startActivity(i);
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "ERROR CREAR ENTRADA "+ex, Toast.LENGTH_LONG ).show();
        }

    }

}
