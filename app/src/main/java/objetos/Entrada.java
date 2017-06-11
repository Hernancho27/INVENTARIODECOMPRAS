package objetos;

public class Entrada {
    int id;
    int idProducto;
    int cantidadActual;
    int cantidadAAdicionar;
    int cantidadTotal;

    public Entrada(){}

    public Entrada (int idProd, int cantAc, int cantA_Add)
    {
        this.idProducto= idProd;
        this.cantidadActual=cantAc;
        this.cantidadAAdicionar=cantA_Add;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
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
}
