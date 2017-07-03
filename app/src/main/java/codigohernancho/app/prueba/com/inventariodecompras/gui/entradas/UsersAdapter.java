package codigohernancho.app.prueba.com.inventariodecompras.gui.entradas;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.sql.Array;
import java.util.ArrayList;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import codigohernancho.app.prueba.com.inventariodecompras.R;
import codigohernancho.app.prueba.com.inventariodecompras.modelo.Entrada;

public class UsersAdapter extends ArrayAdapter<Entrada>
{
    private Context context;
    private ArrayList<Entrada> listado = null;
    private int layoutResourceId;

    public UsersAdapter(Context context, int resource, ArrayList<Entrada> entradas) {
        super(context, resource, entradas);
        this.layoutResourceId= resource;
        this.context = context;
        this.listado = (ArrayList<Entrada>) entradas;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        HeaderHolder holder = null;
    try
    {
            if(row == null)
            {
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new HeaderHolder();
                holder.id = (TextView) row.findViewById(R.id.id_txt);
                holder.nombre = (TextView)row.findViewById(R.id.nombre_txt);
                holder.cantidad = (TextView)row.findViewById(R.id.cantidad_txt);

                row.setTag(holder);
            }
            else
            {
                holder = (HeaderHolder) row.getTag();
            }

            Entrada item = listado.get(position);
            holder.id.setText(item.getId()+"");
            holder.nombre.setText(item.getNombre());
            holder.cantidad.setText(item.getCantidadTotal()+"");

            return row;

        }
        catch (Exception ex)
        {
            Toast.makeText(null, "ERROR Useradpter "+ex, Toast.LENGTH_LONG ).show();
            return null;
        }

    }



    private class HeaderHolder {
        public TextView id;
        public TextView nombre;
        public TextView cantidad;

    }


}
