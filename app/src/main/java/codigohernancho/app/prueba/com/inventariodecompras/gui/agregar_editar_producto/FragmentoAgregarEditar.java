package codigohernancho.app.prueba.com.inventariodecompras.gui.agregar_editar_producto;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Date;

import codigohernancho.app.prueba.com.inventariodecompras.BaseDatos.DataBaseManager;
import codigohernancho.app.prueba.com.inventariodecompras.R;
import codigohernancho.app.prueba.com.inventariodecompras.sqlite.Producto;

/**
 * Created by urreal on 05/06/2017.
 */

public class FragmentoAgregarEditar extends Fragment {

    private static final String ARG_PRODUCTO_ID = "arg_producto_id";

    private String mProductoId;

    //private OperacionesBaseDatos mOperacionesBaseDatos;
    private DataBaseManager manager;

    private FloatingActionButton mSaveButton;
    private TextInputEditText mNombreField;
    private TextInputEditText mCantidadField;
    private TextInputEditText mDescripcionField;
    private TextInputLayout mNombreLabel;
    private TextInputLayout mCantidadLabel;
    private TextInputLayout mDescripcionLabel;


    public FragmentoAgregarEditar() {
        // Required empty public constructor
    }

    public static FragmentoAgregarEditar newInstance(String productoId) {
        FragmentoAgregarEditar fragment = new FragmentoAgregarEditar();
        Bundle args = new Bundle();
        args.putString(ARG_PRODUCTO_ID, productoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProductoId = getArguments().getString(ARG_PRODUCTO_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragmento_agregar_editar_producto, container, false);

        // Referencias UI
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mNombreField = (TextInputEditText) root.findViewById(R.id.et_nombre);
        mCantidadField = (TextInputEditText) root.findViewById(R.id.et_cantidad);
        mDescripcionField = (TextInputEditText) root.findViewById(R.id.et_descripcion);
        mNombreLabel = (TextInputLayout) root.findViewById(R.id.til_nombre);
        mCantidadLabel = (TextInputLayout) root.findViewById(R.id.til_cantidad);
        mDescripcionLabel = (TextInputLayout) root.findViewById(R.id.til_descripcion);

        // Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditProducto();
            }
        });

        //mOperacionesBaseDatos = new OperacionesBaseDatos(getActivity());
        manager =  new DataBaseManager(getActivity());
        // Carga de datos
        if (mProductoId != null) {
            loadProducto();
        }

        return root;
    }

    private void loadProducto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            new GetProductoByIdTask().execute();
        }
    }

    private void addEditProducto() {
        boolean error = false;

        String nombre = mNombreField.getText().toString();
        String cantidad = mCantidadField.getText().toString();
        String descripcion = mDescripcionField.getText().toString();
        String fecha = new Date().toString();

        if (TextUtils.isEmpty(nombre)) {
            mNombreField.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(cantidad)) {
            mCantidadField.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(descripcion)) {
            mDescripcionField.setError(getString(R.string.field_error));
            error = true;
        }

        if (error) {
            return;
        }
        Producto producto = new Producto("", fecha, 1, "", "",nombre, descripcion);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            new AddEditProductoTask().execute(producto);
        }

    }

    private void showLawyersScreen(Boolean requery) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }

        getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    private void showProducto(Producto producto) {
        mNombreField.setText(producto.getNombre());
        mCantidadField.setText(producto.getCant().toString());
        mDescripcionField.setText(producto.getDescripcion());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar producto", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private class GetProductoByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return manager.getProductoById(mProductoId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showProducto(new Producto(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    private class AddEditProductoTask extends AsyncTask<Producto, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Producto... productos) {
            if (mProductoId != null) {
                return manager.updateProducto(productos[0], mProductoId) > 0;

            } else {
                return manager.saveProducto(productos[0]) > 0;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            showLawyersScreen(result);
        }

    }

}
