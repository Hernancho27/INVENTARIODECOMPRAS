package codigohernancho.app.prueba.com.inventariodecompras;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ENTRADAS extends AppCompatActivity {

    EditText stockMax;
    EditText cantidad;
    EditText codigo;
    EditText nombreProducto;
    EditText marca;
    EditText unidad;
    EditText descripcion;
    EditText stockMin;
    Button adicionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entradas);

        stockMax = (EditText) findViewById(R.id.txtstockMax);


        cantidad = (EditText) findViewById(R.id.txtcantidad);

        codigo = (EditText) findViewById(R.id.txtcodigo);

        nombreProducto = (EditText) findViewById(R.id.txtnombre);

        marca = (EditText) findViewById(R.id.txtmarca);

        unidad = (EditText) findViewById(R.id.txtunidad);

        descripcion = (EditText) findViewById(R.id.txtdescripcion);

        stockMin = (EditText) findViewById(R.id.txtstockMin);

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
                String stockMa= stockMax.getText().toString();
                String cant= cantidad.getText().toString();
                String cod= codigo.getText().toString();
                String nomprod = nombreProducto.getText().toString();
                String marc= marca.getText().toString();
                String unid= unidad.getText().toString();
                String descr= descripcion.getText().toString();
                String sockMi= stockMin.getText().toString();

                Toast.makeText(this, "El usuario es: "+nomprod+" "+marc+" "+unid, Toast.LENGTH_LONG ).show();

                return  true;
            }
            default:
            {
                return  super.onOptionsItemSelected(item);
            }
        }


    }


    public void limpiarCajas()
    {
        stockMax.setText("");
        cantidad.setText("");
        codigo.setText("");
        nombreProducto.setText("");
        marca.setText("");
        unidad.setText("");
        descripcion.setText("");
        stockMin.setText("");
    }


}
