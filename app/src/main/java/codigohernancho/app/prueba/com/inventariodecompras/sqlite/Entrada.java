package codigohernancho.app.prueba.com.inventariodecompras.sqlite;

public class Entrada {
    long id;
    int idProducto;
    String nombre;
    int cantidadActual;
    int cantidadAAdicionar;
    int cantidadTotal;
    int estado;

    public Entrada(){}

    public Entrada(int idProd, int cantAct, int nuevaCant)
    {
        this.idProducto = idProd;
        this.cantidadActual = cantAct;
        this.cantidadAAdicionar =nuevaCant;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(int cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public int getCantidadAAdicionar() {
        return cantidadAAdicionar;
    }

    public void setCantidadAAdicionar(int cantidadAAdicionar) {
        this.cantidadAAdicionar = cantidadAAdicionar;
    }

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
