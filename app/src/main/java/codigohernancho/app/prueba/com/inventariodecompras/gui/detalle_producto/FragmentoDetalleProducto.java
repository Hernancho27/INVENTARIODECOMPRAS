package codigohernancho.app.prueba.com.inventariodecompras.gui.detalle_producto;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import codigohernancho.app.prueba.com.inventariodecompras.BaseDatos.DataBaseManager;
import codigohernancho.app.prueba.com.inventariodecompras.DrawerMenu;
import codigohernancho.app.prueba.com.inventariodecompras.R;
import codigohernancho.app.prueba.com.inventariodecompras.gui.agregar_editar_producto.CrearProducto;
import codigohernancho.app.prueba.com.inventariodecompras.gui.productos.Productos;
import codigohernancho.app.prueba.com.inventariodecompras.gui.productos.FragmentoProductos;
import codigohernancho.app.prueba.com.inventariodecompras.sqlite.Producto;

/**
 * Created by urreal on 05/06/2017.
 */

public class FragmentoDetalleProducto extends Fragment {
    private static final String ARG_PRODUCTO_ID = "productoId";


    private String mProductoId;
    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mCodigo;
    private TextView mCantidad;
    private TextView mImagen;
    private TextView mDescripcion;
    private DataBaseManager manager;



    public FragmentoDetalleProducto() {
        // Required empty public constructor
    }

    public static FragmentoDetalleProducto newInstance(String productoId) {
        FragmentoDetalleProducto fragment = new FragmentoDetalleProducto();
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

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragmento_detalle_producto, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        mCodigo = (TextView) root.findViewById(R.id.tv_codigo);
        mCantidad = (TextView) root.findViewById(R.id.tv_cantidad);
        mImagen = (TextView) root.findViewById(R.id.tv_imagen);
        mDescripcion = (TextView) root.findViewById(R.id.tv_descripcion);
        manager = new DataBaseManager(getActivity());

        loadProducto();

        return root;
    }

    private void loadProducto() {
        new GetProductoByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteProductoTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FragmentoProductos.REQUEST_UPDATE_DELETE_PRODUCT) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showProducto(Producto producto){
        mCollapsingView.setTitle(producto.getNombre());
        Glide.with(this)
                .load(Uri.parse("file://" + producto.getImg_prod()))
                .centerCrop()
                .into(mAvatar);

        mCodigo.setText(producto.getCod().toString());
        mCantidad.setText(producto.getCant().toString());
        mDescripcion.setText(producto.getDescripcion());
        mImagen.setText(producto.getImg_prod());
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), CrearProducto.class);
        intent.putExtra(Productos.EXTRA_PRODUCTO_ID, mProductoId);
        startActivityForResult(intent, FragmentoProductos.REQUEST_UPDATE_DELETE_PRODUCT);
    }

    private void showProductosScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        Intent intent = new Intent(getActivity(), DrawerMenu.class);
        startActivityForResult(intent, FragmentoProductos.REQUEST_UPDATE_DELETE_PRODUCT);
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al eliminar abogado", Toast.LENGTH_SHORT).show();
    }

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
            }
        }

    }

    private class DeleteProductoTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return manager.deleteProducto(mProductoId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showProductosScreen(integer > 0);
        }

    }



}
