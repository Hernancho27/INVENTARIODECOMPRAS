package codigohernancho.app.prueba.com.inventariodecompras.gui_entradas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import codigohernancho.app.prueba.com.inventariodecompras.R;
import codigohernancho.app.prueba.com.inventariodecompras.modelo.Entrada;
import codigohernancho.app.prueba.com.inventariodecompras.BaseDatos.EntradasSqliteHelper;

public class registrarEntrada extends AppCompatActivity {

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


    public registrarEntrada()
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
        stockMaximoProductoEncontrado.setVisibility(View.INVISIBLE);

        nombreProductoEncontrado = (EditText) findViewById(R.id.txtnombreProductoEncontrado);
        nombreProductoEncontrado.setEnabled(false);

        marcaProductoEncontrado = (EditText) findViewById(R.id.txtMarcaProductoEncontrado);
        marcaProductoEncontrado.setEnabled(false);
        marcaProductoEncontrado.setVisibility(View.INVISIBLE);

        cantidadProductoEncontrado = (EditText) findViewById(R.id.txtCantidadProductoEncontrado);
        cantidadProductoEncontrado.setEnabled(false);

        unidadProductoEncontrado = (EditText) findViewById(R.id.txtunidadProductoEncontrado);
        unidadProductoEncontrado.setEnabled(false);
        unidadProductoEncontrado.setVisibility(View.INVISIBLE);


        descripcionProductoEncontrado = (EditText) findViewById(R.id.txtdescripcionProductoEncontrado);
        descripcionProductoEncontrado.setEnabled(false);

        stockMinimoProductoEncontrado = (EditText) findViewById(R.id.txtstockMinProductoEncontrado);
        stockMinimoProductoEncontrado.setEnabled(false);
        stockMinimoProductoEncontrado.setVisibility(View.INVISIBLE);


    }

    

    public void guardarEntrada_clicked(View view){
        try
        {
            Cursor cursor=null;
            u.crearEntrada(new Entrada(idProductoEncontrado, cantidadActualProductoEncontrado, Integer.parseInt(cantidadARegistrar.getText().toString())));
            Intent intent = new Intent(registrarEntrada.this, listadoEntrada.class);
            startActivity(intent);
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

            idProductoEncontrado = Integer.parseInt( c.getString(c.getColumnIndexOrThrow("producto_id")) );
            nombreProductoEncontrado.setText(c.getString(c.getColumnIndexOrThrow("nombre")));
            descripcionProductoEncontrado.setText( c.getString(c.getColumnIndexOrThrow("descripcion")) );
            cantidadActualProductoEncontrado = Integer.parseInt( c.getString(c.getColumnIndexOrThrow("cantidad") ) );
            /*idProductoEncontrado = Integer.parseInt( c.getString(c.getColumnIndexOrThrow("id")) );
            nombreProductoEncontrado.setText(c.getString(c.getColumnIndexOrThrow("nombreProducto")));
            cantidadActualProductoEncontrado = Integer.parseInt( c.getString(c.getColumnIndexOrThrow("cantidad") ) );
            cantidadProductoEncontrado.setText(cantidadActualProductoEncontrado+"");
            marcaProductoEncontrado.setText( c.getString(c.getColumnIndexOrThrow("marca")) );
            unidadProductoEncontrado.setText( c.getString(c.getColumnIndexOrThrow("unidad")) );
            descripcionProductoEncontrado.setText( c.getString(c.getColumnIndexOrThrow("descripcion")) );
            stockMinimoProductoEncontrado.setText(c.getString(c.getColumnIndexOrThrow("stock_minimo")));
            stockMaximoProductoEncontrado.setText(c.getString(c.getColumnIndexOrThrow("stock_maximo")));*/

        }
        catch (Exception ex)
        {
            Toast.makeText(this, "ERROR BUSCAR "+ex, Toast.LENGTH_LONG ).show();

        }

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
