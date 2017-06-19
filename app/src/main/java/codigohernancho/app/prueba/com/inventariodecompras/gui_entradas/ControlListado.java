package codigohernancho.app.prueba.com.inventariodecompras.gui_entradas;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import codigohernancho.app.prueba.com.inventariodecompras.R;

public class ControlListado extends CursorAdapter
{

    public ControlListado (Context context, Cursor cursor)
{
    super(context, cursor, 0);
}
    @Override

    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        final LayoutInflater inflater = LayoutInflater.from(context);
        return LayoutInflater.from(context).inflate(R.layout.activity_listado_entrada, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView idtxt = (TextView) view.findViewById(R.id.id_txt);
        TextView apellidotxt = (TextView) view.findViewById(R.id.apellido_txt);
        TextView nombretxt = (TextView) view.findViewById(R.id.nombre_txt);



        int txtid = cursor.getInt(1);
        String txtapellido = cursor.getString(2);
        String txtnombre = cursor.getString(3);

        idtxt.setText(String.valueOf(txtid));
        apellidotxt.setText(txtapellido);
        nombretxt.setText(txtnombre);

    }
}
