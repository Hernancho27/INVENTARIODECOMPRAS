package codigohernancho.app.prueba.com.inventariodecompras.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import codigohernancho.app.prueba.com.inventariodecompras.R;
import codigohernancho.app.prueba.com.inventariodecompras.modelo.ProductoInventario;

/**
 * Created by dayanamartinez on 27-06-17.
 */

public class AdapterProductos extends BaseAdapter {

    private Context context;
    private ArrayList<ProductoInventario> productoInventarios;

    public AdapterProductos(Context cont, ArrayList<ProductoInventario> productoInventarios) {

        this.context = cont;
        this.productoInventarios = productoInventarios;

    }
    @Override
    public int getCount() {
        return productoInventarios.size();
    }

    @Override
    public Object getItem(int position) {
        return productoInventarios.get(position);
    }

    public void notifyDataChanged(){
        super.notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.adapter_informes, null);

        TextView nombre =  (TextView) view.findViewById(R.id.nombre);
        nombre.setText(productoInventarios.get(position).getNombre());
        TextView marca =  (TextView) view.findViewById(R.id.marca);
        marca.setText(productoInventarios.get(position).getMarca());
        return view;
    }

}