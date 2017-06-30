package codigohernancho.app.prueba.com.inventariodecompras.modelo;

import java.io.Serializable;

/**
 * Created by dayanamartinez on 27-06-17.
 */

public class ProductoInventario implements Serializable {

    private String id, nombre, marca,lugar_Compra,fecha_vencimiento,fecha_ingreso,cantidad,categoria,precio = "";

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setLugar_Compra(String lugar_Compra) {
        this.lugar_Compra = lugar_Compra;
    }

    public void setFecha_vencimiento(String fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMarca() {
        return marca;
    }

    public String getLugar_Compra() {
        return lugar_Compra;
    }

    public String getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getPrecio() {
        return precio;
    }
}
