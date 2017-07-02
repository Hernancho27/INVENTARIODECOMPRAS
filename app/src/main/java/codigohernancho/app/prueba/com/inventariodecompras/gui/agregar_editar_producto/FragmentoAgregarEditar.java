package codigohernancho.app.prueba.com.inventariodecompras.gui.agregar_editar_producto;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.util.Date;

import codigohernancho.app.prueba.com.inventariodecompras.BaseDatos.DataBaseManager;
import codigohernancho.app.prueba.com.inventariodecompras.R;
import codigohernancho.app.prueba.com.inventariodecompras.sqlite.Producto;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;


/**
 * Created by urreal on 05/06/2017.
 */

public class FragmentoAgregarEditar extends Fragment {

    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";
    private static final String ARG_PRODUCTO_ID = "arg_producto_id";
    private String mProductoId;
    private String mPath ;
    private String imgFromGallery;
    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    //private OperacionesBaseDatos mOperacionesBaseDatos;
    private DataBaseManager manager;

    private FloatingActionButton mSaveButton;
    private FloatingActionButton mAddImgButton;
    private TextInputEditText mNombreField;
    private TextInputEditText mCodigoField;
    private TextInputEditText mCantidadField;
    private TextInputEditText mDescripcionField;
    private TextInputEditText mImagenField;
    private TextInputLayout mNombreLabel;
    private TextInputLayout mCodigoLabel;
    private TextInputLayout mCantidadLabel;
    private TextInputLayout mDescripcionLabel;
    private TextInputLayout mImagenLabel;
    private RelativeLayout mRlView;

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
        mAddImgButton = (FloatingActionButton) getActivity().findViewById(R.id.fabimg);
        mNombreField = (TextInputEditText) root.findViewById(R.id.et_nombre);
        mCodigoField = (TextInputEditText) root.findViewById(R.id.et_codigo);
        mCantidadField = (TextInputEditText) root.findViewById(R.id.et_cantidad);
        mDescripcionField = (TextInputEditText) root.findViewById(R.id.et_descripcion);
        mImagenField = (TextInputEditText) root.findViewById(R.id.et_img_prod);
        mNombreLabel = (TextInputLayout) root.findViewById(R.id.til_nombre);
        mCodigoLabel = (TextInputLayout) root.findViewById(R.id.til_codigo);
        mCantidadLabel = (TextInputLayout) root.findViewById(R.id.til_cantidad);
        mDescripcionLabel = (TextInputLayout) root.findViewById(R.id.til_descripcion);
        mImagenLabel = (TextInputLayout) root.findViewById(R.id.til_img_prod);
        mRlView = (RelativeLayout) root.findViewById(R.id.relLayAddImg);
        mImagenField.setEnabled(false);

        if(mayRequestStoragePermission())
            mAddImgButton.setEnabled(true);
        else
            mAddImgButton.setEnabled(false);

        // Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditProducto();
            }
        });
        mAddImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOptions();
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

    private boolean mayRequestStoragePermission(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;
        if ((getActivity().checkSelfPermission(WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)&&
                (getActivity().checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED))
            return true;
        if ((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))||(shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(mRlView,"Los permisos son necesarios para utilizar la aplicacion",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener(){
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA}, MY_PERMISSIONS);
                }
            });
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA}, MY_PERMISSIONS);
        }
        return false;
    }

    private void loadProducto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            new GetProductoByIdTask().execute();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(getContext(),
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage","Scanned "+path+":");
                                    Log.i("ExternalStorage","-> Uri = "+ uri );
                                }
                            });
                    Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                    mImagenField.setText(mPath);

                    break;

                case SELECT_PICTURE:
                    Uri path = data.getData();
                    imgFromGallery = RealPathUtil.getRealPath(getContext(),path).toString();
                    mImagenField.setText(imgFromGallery);
                    break;
            }
        }
    }
    private void addEditProducto() {
        boolean error = false;

        String nombre = mNombreField.getText().toString();
        String codigo = mCodigoField.getText().toString();
        String cantidad = mCantidadField.getText().toString();
        String descripcion = mDescripcionField.getText().toString();
        String fecha = new Date().toString();
        String imagen = mImagenField.getText().toString();

        if (TextUtils.isEmpty(nombre)) {
            mNombreField.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(codigo)) {
            mCodigoField.setError(getString(R.string.field_error));
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
        Producto producto = new Producto(codigo, fecha, 1, imagen, "activo",nombre, descripcion);

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
        mCodigoField.setText(producto.getCod());
        mCantidadField.setText(producto.getCant().toString());
        mDescripcionField.setText(producto.getDescripcion());
        mImagenField.setText(producto.getImg_prod());
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
    private void showOptions(){
        final CharSequence[] option = {"Tomar foto","Elegir de Galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(super.getContext());
        builder.setTitle("Elegir una Opcion");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (option[which] == "Tomar foto"){
                    openCamera();
                }else if (option[which] == "Elegir de Galeria"){
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //intent.setType("image/*");
                    //Uri path = intent.getData();
                    //imgFromGallery = RealPathUtil.getRealPath(getContext(),path);
                    startActivityForResult(intent, SELECT_PICTURE);
                }else{
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }
    private void openCamera(){
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        Boolean isDirectoryCreated = file.exists();
        if (!isDirectoryCreated){
            isDirectoryCreated = file.mkdirs();
        }

        if (isDirectoryCreated){
            Long timestamp = System.currentTimeMillis()/1000;
            String imageName  = timestamp.toString()+".jpg";

            mPath = Environment.getExternalStorageDirectory()+File.separator+MEDIA_DIRECTORY
                    +File.separator+imageName;
            File newFile=new File(mPath);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            intent.putExtra("imagen",mPath);
            startActivityForResult(intent,PHOTO_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode ==  MY_PERMISSIONS){
            if (grantResults.length ==2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getActivity(),"Permisos aceptados", Toast.LENGTH_SHORT).show();
                mAddImgButton.setEnabled(true);
            }
        }else{
            showExplanation();
        }

    }
    private void showExplanation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package",getActivity().getPackageName(), null );
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getActivity().finish();
            }
        });
        builder.show();

    }

}
