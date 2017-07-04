package codigohernancho.app.prueba.com.inventariodecompras;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import codigohernancho.app.prueba.com.inventariodecompras.BaseDatos.DataBaseManager;
import codigohernancho.app.prueba.com.inventariodecompras.gui.detalle_producto.DetalleProducto;
import codigohernancho.app.prueba.com.inventariodecompras.gui.productos.Productos;
import codigohernancho.app.prueba.com.inventariodecompras.gui.productos.ProductosCursorAdapter;

public class SALIDAS extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_UPDATE_DELETE_PRODUCT = 2;
    public static final String EXTRA_PRODUCTO_ID = "extra_producto_id";
    private DataBaseManager manager;
    private Cursor cursor;
    private ListView lista;
    private SimpleCursorAdapter adapter;
    private ProductosCursorAdapter mProductosAdapter;
    private TextView tv;
    private Button bt;

    Button leerCodigo;
    EditText codNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salidas);

        manager = new DataBaseManager(this);
        lista = (ListView) findViewById(R.id.product_list);
        tv= (TextView) findViewById(R.id.codbarras);
        bt= (Button) findViewById(R.id.button1);
        mProductosAdapter = new ProductosCursorAdapter(getBaseContext(), null);

        bt.setOnClickListener(this);
                /*ejemploscod, fecha, cant, img_prod, estado, nombre,descripcion*/

        String[] from = new String[]{manager.CN_NAME, manager.CN_IMG_PROD, manager.CN_CODIGO};
        int [] to = new int[] {R.id.tv_name, R.id.iv_avatar, R.id.tv_codigo};

        cursor = manager.cargarCursorInventario();
        adapter = new SimpleCursorAdapter(this,R.layout.lista_item_producto,cursor,from,to);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) adapter.getItem(i);
                String currentProductoId = currentItem.getString(
                        currentItem.getColumnIndex(manager.CN_ID));

                showDetailScreen(currentProductoId);
            }
        });

        leerCodigo = (Button) findViewById(R.id.button2);
        leerCodigo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Context context = getApplicationContext();
                CharSequence text = "Enfoque la cámara hacia un Código de Barras";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Intent intent1 = new Intent(SALIDAS.this, ESCANEAR.class);
                startActivity(intent1);
            }
        });

        codNombre = (EditText) findViewById(R.id.codbarras);

        Intent intent=getIntent();//Traemos el codigo de la activity escanear.
        Bundle extras=intent.getExtras();

        if(extras!=null){
            //Validamos que el codigo no venga vacio.
            String codigo=extras.getString("CODIGO");
            codNombre.setText(codigo);
            Cursor c =manager.buscarCodigo(tv.getText().toString());


            if(c.moveToFirst() == false){
                //Revisamos si existe el producto
                Context context = getApplicationContext();
                CharSequence text = "No existe el producto";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }else{
                adapter.changeCursor(c);
            }
        }
    }

    private void showDetailScreen(String productoId) {
        Intent intent = new Intent(getBaseContext(), DetalleProducto.class);
        intent.putExtra(Productos.EXTRA_PRODUCTO_ID, productoId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_PRODUCT);
    }

    @Override
    public void onClick(View view) {
        int busqueda = 0;
        Cursor c = manager.buscarNombre(tv.getText().toString());
        Cursor c2 = manager.buscarCodigo(tv.getText().toString());
        if (tv.getText().toString().equals("")) {
            Context context = getApplicationContext();
            CharSequence text = "Inserte un Nombre o Codigo";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            Cursor Reset = manager.cargarCursorInventario();
            adapter.changeCursor(Reset);
            //adapter = new SimpleCursorAdapter(this,R.layout.lista_item_producto,cursor,from,to);
            //lista.setAdapter(adapter);
        } else {
            if (c.moveToFirst() == false) {
                //Revisamos si existe el producto
                Context context = getApplicationContext();
                CharSequence text = "Busqueda por codigo";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                busqueda ++;}
            else{   adapter.changeCursor(c);
            }
            if (c2.moveToFirst() == false) {
                //Revisamos si existe el producto
                Context context2 = getApplicationContext();
                CharSequence text2 = "Busqueda por nombre";
                int duration2 = Toast.LENGTH_SHORT;
                Toast toast2 = Toast.makeText(context2, text2, duration2);
                toast2.show();
                busqueda ++;
            }else{
                adapter.changeCursor(c2);
            }
        } if(busqueda == 2){
            Context context = getApplicationContext();
            CharSequence text = "No se encontro el producto";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            Cursor Reset = manager.cargarCursorInventario();
            adapter.changeCursor(Reset);
        }

    }
}
