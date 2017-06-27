package codigohernancho.app.prueba.com.inventariodecompras.gui.entradas;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import codigohernancho.app.prueba.com.inventariodecompras.R;

public class inicioEntradas extends AppCompatActivity{

    Button nuevaEntrada;
    Button verEntradas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_entradas);
    }


    public void nuevaEntrada_clicked(View view){
        try
        {
            Intent intent = new Intent(this, registrarEntrada.class);
            startActivity(intent);
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "ERROR NUEVA ENTRADA "+ex, Toast.LENGTH_LONG ).show();
        }

    }



    public void verEntradas_clicked(View view){
        try
        {
            Intent intent = new Intent(this, listadoEntrada.class);
            startActivity(intent);
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "ERROR VER ENTRADAS "+ex, Toast.LENGTH_LONG ).show();
        }

    }



}
