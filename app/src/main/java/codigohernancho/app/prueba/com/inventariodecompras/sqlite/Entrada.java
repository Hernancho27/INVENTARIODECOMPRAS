package codigohernancho.app.prueba.com.inventariodecompras.sqlite;

public class Entrada {
    long id;
    String idProducto;
    String nombre;
    String rutaImagen;
    int cantidadActual;
    int cantidadAAdicionar;
    int cantidadTotal;
    String estado;

    public Entrada(){}

    public Entrada(String idProd, int cantAct, int nuevaCant, String nomb, String img_path)
    {
        this.idProducto = idProd;
        this.cantidadActual = cantAct;
        this.cantidadAAdicionar =nuevaCant;
        this.nombre = nomb;
        this.rutaImagen = img_path;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
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

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
