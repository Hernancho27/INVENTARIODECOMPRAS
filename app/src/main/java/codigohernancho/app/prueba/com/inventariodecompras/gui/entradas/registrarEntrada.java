package codigohernancho.app.prueba.com.inventariodecompras.gui.entradas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import codigohernancho.app.prueba.com.inventariodecompras.R;
import codigohernancho.app.prueba.com.inventariodecompras.sqlite.Entrada;
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
    ImageView imagen;

    Button adicionar;
    Button limpiarCantidad;
    Button limpiarTodo;
    EntradasSqliteHelper u;
    String idProductoEncontrado;
    int cantidadActualProductoEncontrado;
    String ruta;


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
        cantidadARegistrar.setEnabled(false);
        imagen = (ImageView) findViewById(R.id.imagenProducto);

        adicionar = (Button) findViewById(R.id.btnGuardarEntrada);
        adicionar.setEnabled(false);

        limpiarCantidad = (Button) findViewById(R.id.btnLimpiarCantidad);
        limpiarCantidad.setEnabled(false);



        nombreProductoEncontrado = (EditText) findViewById(R.id.txtnombreProductoEncontrado);
        nombreProductoEncontrado.setEnabled(false);
        cantidadProductoEncontrado = (EditText) findViewById(R.id.txtCantidadProductoEncontrado);
        cantidadProductoEncontrado.setEnabled(false);
        descripcionProductoEncontrado = (EditText) findViewById(R.id.txtdescripcionProductoEncontrado);
        descripcionProductoEncontrado.setEnabled(false);


    }

    

    public void guardarEntrada_clicked(View view){
        try
        {

            if (Integer.parseInt(cantidadARegistrar.getText().toString()) <= 0)
            {
                Cursor cursor=null;
                Entrada nuevaEntrada = new Entrada();
                nuevaEntrada.setIdProducto(idProductoEncontrado);
                nuevaEntrada.setCantidadActual(cantidadActualProductoEncontrado);
                nuevaEntrada.setCantidadAAdicionar(Integer.parseInt(cantidadARegistrar.getText().toString()));
                nuevaEntrada.setNombre(nombreProductoEncontrado.getText().toString());
                nuevaEntrada.setRutaImagen(ruta);
                if (u.crearEntrada(nuevaEntrada))
                {
                    Intent intent = new Intent(registrarEntrada.this, listadoEntrada.class);
                    startActivity(intent);
                }
            }
            else
            {
                confirmacion("Debe Ingresar un valor mayor a cero", "Validacion");
            }

        }
        catch (Exception ex)
        {
            //Toast.makeText(this, "ERROR GUARDAR LA ENTRADA \n Debe ingresar un valor numérico. ", Toast.LENGTH_LONG ).show();
            confirmacion("Debe ingresar un valor o el valor ingresado no es numèrico", "Validacion");
        }


    }




    public void confirmacion(String msg, String title){

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(msg);
        dlgAlert.setTitle(title);
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss the dialog
                    }
                });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }


    public void buscarProducto_clicked(View view){
        String codigo ="";
        String nombre ="";
        try
        {
            if(!codigoABuscar.getText().toString().equals(""))
            {
                codigo = codigoABuscar.getText().toString();
            }
            if(!nombreProductoABuscar.getText().toString().equals(""))
            {
                nombre = nombreProductoABuscar.getText().toString();
            }
            Cursor c = u.encontrarProductoPorId(codigo, nombre);
            adicionar.setEnabled(true);
            cantidadARegistrar.setEnabled(true);
            limpiarCantidad.setEnabled(true);
            idProductoEncontrado =  c.getString(c.getColumnIndexOrThrow("cod")) ;
            nombreProductoEncontrado.setText(c.getString(c.getColumnIndexOrThrow("nombre")));
            descripcionProductoEncontrado.setText( c.getString(c.getColumnIndexOrThrow("descripcion")) );
            cantidadActualProductoEncontrado = Integer.parseInt( c.getString(c.getColumnIndexOrThrow("cant") ) );
            cantidadProductoEncontrado.setText(cantidadActualProductoEncontrado+"");
            ruta = c.getString(c.getColumnIndexOrThrow("img_prod"));
            Glide.with(this)
                    .load(Uri.parse("file://" + c.getString(c.getColumnIndexOrThrow("img_prod"))))
                    .centerCrop()
                    .into(imagen);
            InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(codigoABuscar.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(nombreProductoABuscar.getWindowToken(), 0);


        }
        catch (Exception ex)
        {
            Toast.makeText(this, "ERROR BUSCAR "+ex, Toast.LENGTH_LONG ).show();

        }

    }





    public void limpiar_clicked(View view){
       limpiarCajas();
    }




    public void limpiarCajas()
    {

        codigoABuscar.setText("");
        nombreProductoABuscar.setText("");
        cantidadARegistrar.setText("");
        nombreProductoEncontrado.setText("");
        cantidadProductoEncontrado.setText("");
        descripcionProductoEncontrado.setText("");


    }


}
