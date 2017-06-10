package objetos;

public class Entrada {
    int cantidadActual;
    String nombreProducto;
    int cantidadAdicionada;
    int totalCantidad;

    public Entrada(){}


    public Entrada(String nom, int cantAdd)
    {
        this.nombreProducto = nom;
        this.cantidadAdicionada = cantAdd;
    }

    public int getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(int cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidadAdicionada() {
        return cantidadAdicionada;
    }

    public void setCantidadAdicionada(int cantidadAdicionada) {
        this.cantidadAdicionada = cantidadAdicionada;
    }

    public int getTotalCantidad() {
        return totalCantidad;
    }

    public void setTotalCantidad(int cantidadAc, int cantidadAd) {
        this.totalCantidad = cantidadAc + cantidadAd;
    }
}
