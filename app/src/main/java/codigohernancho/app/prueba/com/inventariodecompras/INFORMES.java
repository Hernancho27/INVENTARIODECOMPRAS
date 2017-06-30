package codigohernancho.app.prueba.com.inventariodecompras;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import codigohernancho.app.prueba.com.inventariodecompras.BaseDatos.DBAdapter;
import codigohernancho.app.prueba.com.inventariodecompras.modelo.ProductoInventario;

public class INFORMES extends AppCompatActivity {
    private DBAdapter dao = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informes);

        dao = new DBAdapter(this);
    }





    public void mostrarProductosVencidos(View v){
        ArrayList<ProductoInventario>productos= dao.consultaProductosVencidos();
        Intent intent = new Intent(this, ResultadoInformes.class);
        intent.putExtra("productos", productos);
        startActivity(intent);
    }

    public void mostrarInventarioGeneral(View v){
        ArrayList<ProductoInventario>productos= dao.consultaProductosInventario();
        Intent intent = new Intent(this, ResultadoInformes.class);
        intent.putExtra("productos", productos);
        startActivity(intent);
    }

    public void mostrarConsumidosPedido(View v){
        ArrayList<ProductoInventario>productos= dao.consultaProductosConsumidosPedido();
        Intent intent = new Intent(this, ResultadoInformes.class);
        intent.putExtra("productos", productos);
        startActivity(intent);
    }

    public void mostrarMayorConsumo(View v){
        ArrayList<ProductoInventario>productos= dao.consultaMayorConsumo();
        Intent intent = new Intent(this, ResultadoInformes.class);
        intent.putExtra("productos", productos);
        startActivity(intent);
    }

    public void mostrarMenorConsumo(View v){
        ArrayList<ProductoInventario>productos= dao.consultaMenorConsumo();
        Intent intent = new Intent(this, ResultadoInformes.class);
        intent.putExtra("productos", productos);
        startActivity(intent);
    }

}
