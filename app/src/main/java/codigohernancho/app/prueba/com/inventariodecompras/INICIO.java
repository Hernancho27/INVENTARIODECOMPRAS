package codigohernancho.app.prueba.com.inventariodecompras;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class INICIO extends AppCompatActivity implements View.OnClickListener{

    Button boton1;
    Button boton2;
    Button boton3;
    Button boton4;
    Button boton5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        boton1 = (Button)findViewById(R.id.boton1);
        boton1.setOnClickListener(this);
        boton2 = (Button)findViewById(R.id.boton2);
        boton2.setOnClickListener(this);
        boton3 = (Button)findViewById(R.id.boton3);
        boton3.setOnClickListener(this);
        boton4 = (Button)findViewById(R.id.boton4);
        boton4.setOnClickListener(this);
        boton5 = (Button)findViewById(R.id.boton5);
        boton5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.boton1:
                Intent intent1 = new Intent(INICIO.this,CrearLista.class);
                startActivity(intent1);
                break;
            case R.id.boton2:
                Intent intent2 = new Intent(INICIO.this,ENTRADAS.class);
                startActivity(intent2);
                break;
            case R.id.boton3:
                Intent intent3 = new Intent(INICIO.this,SALIDAS.class);
                startActivity(intent3);
                break;
            case R.id.boton4:
                Intent intent4 = new Intent(INICIO.this,ALERTAS.class);
                startActivity(intent4);
                break;
            case R.id.boton5:
                Intent intent5 = new Intent(INICIO.this,LISTA.class);
                startActivity(intent5);
                break;
        }

    }
}
