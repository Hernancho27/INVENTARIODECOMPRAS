package codigohernancho.app.prueba.com.inventariodecompras;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


import codigohernancho.app.prueba.com.inventariodecompras.modelo.ProductoInventario;

public class DetalleInformesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_informes);

        Bundle extras = getIntent().getExtras();
        ProductoInventario producto = (ProductoInventario) extras.get("detlleProductos");

        TextView textViewid=(TextView) findViewById(R.id.id);
        textViewid.setText("Id:  "+ producto.getId());

        TextView textViewnombre=(TextView) findViewById(R.id.nombre);
        textViewnombre.setText("Nombre:  "+producto.getNombre());

        TextView textViewmarca=(TextView) findViewById(R.id.marca);
        textViewmarca.setText("Marca:  "+producto.getMarca());

        TextView textViewlugar=(TextView) findViewById(R.id.lugar_compra);
        textViewlugar.setText("Lugar Compra:  "+producto.getLugar_Compra());

        TextView textViewfechavencimiento=(TextView) findViewById(R.id.fecha_vencimiento);
        textViewfechavencimiento.setText("Fecha Vencimiento:  "+producto.getFecha_vencimiento());

        TextView textViewfechaingreso=(TextView) findViewById(R.id.fecha_ingreso);
        textViewfechaingreso.setText("Fecha Ingreso:  "+producto.getFecha_ingreso());

        TextView textViewcantidad=(TextView) findViewById(R.id.cantidad);
        textViewcantidad.setText("Cantidad:  "+producto.getCantidad());

        TextView textViewcategoria=(TextView) findViewById(R.id.categoria);
        textViewcategoria.setText("Categoria:  "+producto.getCategoria());

        TextView textViewprecio=(TextView) findViewById(R.id.precio);
        textViewprecio.setText("Precio:  "+producto.getPrecio());
    }
}
