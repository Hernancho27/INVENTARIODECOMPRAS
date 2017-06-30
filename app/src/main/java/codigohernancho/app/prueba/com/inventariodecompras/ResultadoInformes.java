package codigohernancho.app.prueba.com.inventariodecompras;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import codigohernancho.app.prueba.com.inventariodecompras.modelo.ProductoInventario;
import codigohernancho.app.prueba.com.inventariodecompras.sqlite.AdapterProductos;

import static android.R.id.input;


/**
 * Created by  on 27-06-17.
 */

public class ResultadoInformes extends AppCompatActivity {
    public  ArrayList<ProductoInventario> productoInventarios;

    private Uri uri= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_informes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        productoInventarios = (ArrayList<ProductoInventario>) extras.get("productos");

        ListView listView = (ListView)findViewById(R.id.lista_productos);
        listView.setAdapter(new AdapterProductos(getApplicationContext(), productoInventarios));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Intent inten = new Intent(ResultadoInformes.this, DetalleInformesActivity.class);

                ProductoInventario producto= productoInventarios.get(position);
                inten.putExtra("detlleProductos", producto);
                startActivity(inten);
    }
});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_informes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.csv:
                try {
                    if(validarPermisos()){
                    GenerarCSV.generarInforme(this,productoInventarios);
                    GenerarCSV.mostrarReporte(this);
                    uri = Uri.parse("file://" + GenerarCSV.expFile);
                    Toast.makeText(this,"Informe generado correctamente",Toast.LENGTH_LONG).show();}
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.email:
                enviarEmail();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean validarPermisos() {

            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    Log.v("","Permission is granted");
                    return true;
                } else {

                    Log.v("","Permission is revoked");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    return false;
                }
            }
            else { //permission is automatically granted on sdk<23 upon installation
                Log.v("","Permission is granted");
                return true;
            }

    }

    private void enviarEmail() {

        if(uri!=null){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","Freddyrxn@hotmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Informes Inventarios");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Adjunto envío archivo");

        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));}
        else{
            Toast.makeText(this,"Debe generar primero el archivo ",Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v("","Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }
}