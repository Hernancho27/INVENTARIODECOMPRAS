package codigohernancho.app.prueba.com.inventariodecompras.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.UUID;

import codigohernancho.app.prueba.com.inventariodecompras.sqlite.ContratoInventario.ProductoEntrada;

/**
 * Created by urreal on 26/05/2017.
 */

public class Producto {

    public String id;
    public String cod;
    public String fecha;
    public Integer cant;
    public String img_prod;
    public String estado;
    public String nombre;
    public String descripcion;

    public Producto(String cod, String fecha, Integer cant, String img_prod, String estado, String nombre, String descripcion) {
        this.id = UUID.randomUUID().toString();
        this.cod = cod;
        this.fecha = fecha;
        this.cant = cant;
        this.img_prod = img_prod;
        this.estado = estado;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public  Producto(Cursor cursor){

        id=cursor.getString(cursor.getColumnIndex(ProductoEntrada.CN_ID));
        cod=cursor.getString(cursor.getColumnIndex(ProductoEntrada.CN_CODIGO));
        fecha=cursor.getString(cursor.getColumnIndex(ProductoEntrada.CN_FECHA_CREACION));
        cant=cursor.getInt(cursor.getColumnIndex(ProductoEntrada.CN_CANTIDAD));
        img_prod=cursor.getString(cursor.getColumnIndex(ProductoEntrada.CN_IMG_PROD));
        estado=cursor.getString(cursor.getColumnIndex(ProductoEntrada.CN_ESTADO));
        nombre=cursor.getString(cursor.getColumnIndex(ProductoEntrada.CN_NAME));
        descripcion=cursor.getString(cursor.getColumnIndex(ProductoEntrada.CN_DESCRIPCION));
    }

    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(ProductoEntrada.CN_ID, id);
        values.put(ProductoEntrada.CN_CODIGO, cod);
        values.put(ProductoEntrada.CN_FECHA_CREACION, fecha);
        values.put(ProductoEntrada.CN_CANTIDAD, cant);
        values.put(ProductoEntrada.CN_IMG_PROD, img_prod);
        values.put(ProductoEntrada.CN_ESTADO, estado);
        values.put(ProductoEntrada.CN_NAME, nombre);
        values.put(ProductoEntrada.CN_DESCRIPCION, descripcion);
        return values;
    }

    public  String getId(){return id;}
    public  String getCod(){return cod;}
    public  String getFecha() {return fecha;}
    public  Integer getCant() {return  cant;}
    public  String getImg_prod() {return img_prod;}
    public  String getEstado() {return estado;}
    public  String getNombre(){return nombre;}
    public  String getDescripcion() {return descripcion;}

}