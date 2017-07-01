package codigohernancho.app.prueba.com.inventariodecompras;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import codigohernancho.app.prueba.com.inventariodecompras.BaseDatos.DBHelper;
import codigohernancho.app.prueba.com.inventariodecompras.BaseDatos.DataBaseManager;
import codigohernancho.app.prueba.com.inventariodecompras.gui.agregar_editar_producto.ActividadAgregarEditar;
import codigohernancho.app.prueba.com.inventariodecompras.gui.detalle_producto.ActividadDetalleProducto;
import codigohernancho.app.prueba.com.inventariodecompras.gui.entradas.inicioEntradas;
import codigohernancho.app.prueba.com.inventariodecompras.gui.productos.ActividadProductos;
import codigohernancho.app.prueba.com.inventariodecompras.gui.productos.ProductosCursorAdapter;


public class DrawerMenu
        extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    public static final int REQUEST_UPDATE_DELETE_PRODUCT = 2;
    private DataBaseManager manager;
    private Cursor cursor;
    private ListView lista;
    private SimpleCursorAdapter adapter;
    private ProductosCursorAdapter mProductosAdapter;
    private TextView tv;
    private Button bt;

    public static final String EXTRA_PRODUCTO_ID = "extra_producto_id";

    Button leerCodigo;
    EditText codNombre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_menu);

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        manager = new DataBaseManager(this);
        lista = (ListView) findViewById(R.id.product_list);
        tv= (TextView) findViewById(R.id.codbarras);
        bt= (Button) findViewById(R.id.button1);
        mProductosAdapter = new ProductosCursorAdapter(getBaseContext(), null);

        bt.setOnClickListener(this);
                /*ejemploscod, fecha, cant, img_prod, estado, nombre,descripcion*/

        String[] from = new String[]{manager.CN_NAME, manager.CN_IMG_PROD};
        int [] to = new int[] {R.id.tv_name, R.id.iv_avatar};

        cursor = manager.cargarCursorContactos();
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



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        leerCodigo = (Button) findViewById(R.id.button2);
        leerCodigo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Context context = getApplicationContext();
                CharSequence text = "Enfoque la cámara hacia un Código de Barras";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Intent intent1 = new Intent(DrawerMenu.this,ESCANEAR.class);
                startActivity(intent1);
            }
                                });

        codNombre = (EditText) findViewById(R.id.codbarras);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
        Intent intent = new Intent(getBaseContext(), ActividadDetalleProducto.class);
        intent.putExtra(ActividadProductos.EXTRA_PRODUCTO_ID, productoId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_PRODUCT);
    }
    private void showSuccessfullSavedMessage() {
        Toast.makeText(getBaseContext(),
                "Producto guardado correctamente", Toast.LENGTH_SHORT).show();
    }
    private void loadProductos() {
        new ProductoLoadTask().execute();
    }

    private class ProductoLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return manager.getAllProductos();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                adapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case ActividadAgregarEditar.REQUEST_ADD_PRODUCTO:
                    showSuccessfullSavedMessage();
                    loadProductos();
                    break;
                case REQUEST_UPDATE_DELETE_PRODUCT:
                    loadProductos();
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.crear) {
                Intent intent1 = new Intent(DrawerMenu.this,ActividadAgregarEditar.class);
                startActivityForResult(intent1, ActividadAgregarEditar.REQUEST_ADD_PRODUCTO);
            // Handle the camera action
        } else
            if (id == R.id.entradas) {
                Intent intent1 = new Intent(DrawerMenu.this,inicioEntradas.class);
                startActivity(intent1);

        } else if (id == R.id.salidas) {
                Intent intent1 = new Intent(DrawerMenu.this,SALIDAS.class);
                startActivity(intent1);

        } else if (id == R.id.alertas) {
                Intent intent1 = new Intent(DrawerMenu.this,ALERTAS.class);
                startActivity(intent1);

        } else if (id == R.id.informes) {
                Intent intent1 = new Intent(DrawerMenu.this,INFORMES.class);
                startActivity(intent1);

        } else if (id == R.id.bloc) {
                Intent intent1 = new Intent(DrawerMenu.this, BLOCNOTAS.class);
                startActivity(intent1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View view) {

        Cursor c = manager.buscarNombre(tv.getText().toString());
        Cursor c2 = manager.buscarCodigo(tv.getText().toString());
        if (tv.getText().toString().equals("")) {
            Context context = getApplicationContext();
            CharSequence text = "Inserte un Nombre o Codigo";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            if (c.moveToFirst() == false) {
                //Revisamos si existe el producto
                Context context = getApplicationContext();
                CharSequence text = "Busqueda por codigo";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();}
            else{   adapter.changeCursor(c);}
            if (c2.moveToFirst() == false) {
                //Revisamos si existe el producto
                Context context2 = getApplicationContext();
                CharSequence text2 = "Busqueda por nombre";
                int duration2 = Toast.LENGTH_SHORT;
                Toast toast2 = Toast.makeText(context2, text2, duration2);
                toast2.show();
            }else{
                adapter.changeCursor(c2);
            }
        }
    }



}
