package codigohernancho.app.prueba.com.inventariodecompras.sqlite;

import android.provider.BaseColumns;

/**
 * Created by urreal on 25/05/2017.
 */

public class ContratoInventario{
    public static abstract class ProductoEntrada implements BaseColumns{
        public static final String TABLE_NAME = "Inventario";

        public static final String CN_ID = "_id";
        public static final String CN_CODIGO = "cod";
        public static final String CN_FECHA_CREACION = "fecha";
        public static final String CN_CANTIDAD = "cant";
        public static final String CN_IMG_PROD = "img_prod";
        public static final String CN_ESTADO = "estado";
        public static final String CN_NAME = "nombre";
        public static final String CN_DESCRIPCION = "descripcion";
    }

}

