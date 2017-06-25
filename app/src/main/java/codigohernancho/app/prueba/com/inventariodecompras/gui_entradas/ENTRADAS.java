package codigohernancho.app.prueba.com.inventariodecompras.gui_entradas;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;
import android.widget.ListView;
import android.content.Intent;

import codigohernancho.app.prueba.com.inventariodecompras.R;
import codigohernancho.app.prueba.com.inventariodecompras.sqlite.EntradasSqliteHelper;
import codigohernancho.app.prueba.com.inventariodecompras.modelo.Entrada;

public class ENTRADAS extends AppCompatActivity {

    EditText codigoABuscar;
    EditText nombreProductoABuscar;
    EditText cantidadARegistrar;

    EditText nombreProductoEncontrado;
    EditText marcaProductoEncontrado;
    EditText cantidadProductoEncontrado;
    EditText unidadProductoEncontrado;
    EditText descripcionProductoEncontrado;
    EditText stockMinimoProductoEncontrado;
    EditText stockMaximoProductoEncontrado;

    Button adicionar;
    EntradasSqliteHelper u;
    int idProductoEncontrado;
    int cantidadActualProductoEncontrado;


    public ENTRADAS()
    {
//        createProducto();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_entrada);
        u = new EntradasSqliteHelper(this);

        codigoABuscar = (EditText) findViewById(R.id.txtcodigoABuscar);
        nombreProductoABuscar = (EditText) findViewById(R.id.txtnombreProductoABuscar);
        cantidadARegistrar = (EditText) findViewById(R.id.txtCantidadAAdicionar);


        stockMaximoProductoEncontrado = (EditText) findViewById(R.id.txtstockActualProductoEncontrado);
        stockMaximoProductoEncontrado.setEnabled(false);

        nombreProductoEncontrado = (EditText) findViewById(R.id.txtnombreProductoEncontrado);
        nombreProductoEncontrado.setEnabled(false);

        marcaProductoEncontrado = (EditText) findViewById(R.id.txtMarcaProductoEncontrado);
        marcaProductoEncontrado.setEnabled(false);

        cantidadProductoEncontrado = (EditText) findViewById(R.id.txtCantidadProductoEncontrado);
        cantidadProductoEncontrado.setEnabled(false);

        unidadProductoEncontrado = (EditText) findViewById(R.id.txtunidadProductoEncontrado);
        unidadProductoEncontrado.setEnabled(false);

        descripcionProductoEncontrado = (EditText) findViewById(R.id.txtdescripcionProductoEncontrado);
        descripcionProductoEncontrado.setEnabled(false);

        stockMinimoProductoEncontrado = (EditText) findViewById(R.id.txtstockMinProductoEncontrado);
        stockMinimoProductoEncontrado.setEnabled(false);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_entradas, menu);
        return  true;
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
                String stockMa= stockMaximoProductoEncontrado.getText().toString();
                String cant= cantidadARegistrar.getText().toString();
                String cod= codigoABuscar.getText().toString();
                String nomprod = nombreProductoEncontrado.getText().toString();
                String marc= marcaProductoEncontrado.getText().toString();
                String unid= unidadProductoEncontrado.getText().toString();
                String descr= descripcionProductoEncontrado.getText().toString();
                String sockMi= stockMinimoProductoEncontrado.getText().toString();

                Toast.makeText(this, "El usuario es: "+nomprod+" "+marc+" "+unid, Toast.LENGTH_LONG ).show();

                return  true;
            }
            default:
            {
                return  super.onOptionsItemSelected(item);
            }
        }


    }



    public void guardarProducto_clicked(View view){
        try
        {
            Cursor cursor=null;
            u.createProducto();
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "ERROR GUARDAR "+ex, Toast.LENGTH_LONG ).show();
        }


    }

    public void guardarEntrada_clicked(View view){
        try
        {
            Cursor cursor=null;
            u.crearEntrada(new Entrada(idProductoEncontrado, cantidadActualProductoEncontrado, Integer.parseInt(cantidadARegistrar.getText().toString())));
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "ERROR GUARDAR "+ex, Toast.LENGTH_LONG ).show();
        }


    }

    public void buscarProducto_clicked(View view){
        int codigo =0;
        String nombre ="";
        try
        {
            if(!codigoABuscar.getText().toString().equals(""))
            {
                codigo = Integer.parseInt( codigoABuscar.getText().toString() );
            }
            if(!nombreProductoABuscar.getText().toString().equals(""))
            {
                nombre = nombreProductoABuscar.getText().toString();
            }
            Cursor c = u.encontrarProductoPorId(codigo, nombre);

            idProductoEncontrado = Integer.parseInt( c.getString(c.getColumnIndexOrThrow("id")) );
            nombreProductoEncontrado.setText(c.getString(c.getColumnIndexOrThrow("nombreProducto")));
            cantidadActualProductoEncontrado = Integer.parseInt( c.getString(c.getColumnIndexOrThrow("cantidad") ) );
            cantidadProductoEncontrado.setText(cantidadActualProductoEncontrado+"");
            marcaProductoEncontrado.setText( c.getString(c.getColumnIndexOrThrow("marca")) );
            unidadProductoEncontrado.setText( c.getString(c.getColumnIndexOrThrow("unidad")) );
            descripcionProductoEncontrado.setText( c.getString(c.getColumnIndexOrThrow("descripcion")) );
            stockMinimoProductoEncontrado.setText(c.getString(c.getColumnIndexOrThrow("stock_minimo")));
            stockMaximoProductoEncontrado.setText(c.getString(c.getColumnIndexOrThrow("stock_maximo")));

        }
        catch (Exception ex)
        {
            Toast.makeText(this, "ERROR BUSCAR "+ex, Toast.LENGTH_LONG ).show();

        }

    }

    public void buscarEntrada_clicked(View view){
        try
        {
            int id = Integer.parseInt( codigoABuscar.getText().toString() );
            Cursor c = u.encontrarEntradaPorId(id, nombreProductoABuscar.getText().toString());

            idProductoEncontrado = Integer.parseInt( c.getString(c.getColumnIndexOrThrow("id")) );
            nombreProductoEncontrado.setText(c.getString(c.getColumnIndexOrThrow("nombreProducto")));
            cantidadActualProductoEncontrado = Integer.parseInt( c.getString(c.getColumnIndexOrThrow("cantidad") ) );
            descripcionProductoEncontrado.setText( c.getString(c.getColumnIndexOrThrow("descripcion")) );
            stockMinimoProductoEncontrado.setText(c.getString(c.getColumnIndexOrThrow("stock_minimo")));
            stockMaximoProductoEncontrado.setText(c.getString(c.getColumnIndexOrThrow("stock_maximo")));
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "ERROR BUSCAR "+ex, Toast.LENGTH_LONG ).show();
        }

    }


    public void listar_clicked(View view)
    {
        try
        {
            Intent i = new Intent(this, listadoEntradas.class);
            startActivity(i);
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "ERROR BUSCAR "+ex, Toast.LENGTH_LONG ).show();
        }

    }
    public void modificar_clicked(View view){

        finish();
    }

    public void limpiar_clicked(View view){
       limpiarCajas();
    }


    public void confirmacion(){

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("El Proceso de realizo correctamente exitosamente!");
        dlgAlert.setTitle("Agregar Entrada");
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss the dialog
                    }
                });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    public void limpiarCajas()
    {
        stockMaximoProductoEncontrado.setText("");
        cantidadARegistrar.setText("");
        codigoABuscar.setText("");
        nombreProductoEncontrado.setText("");
        marcaProductoEncontrado.setText("");
        unidadProductoEncontrado.setText("");
        descripcionProductoEncontrado.setText("");
        stockMinimoProductoEncontrado.setText("");
    }


}
