package codigohernancho.app.prueba.com.inventariodecompras.BaseDatos;


import java.util.ArrayList;

import codigohernancho.app.prueba.com.inventariodecompras.modelo.ProductoInventario;

public interface DataBaseInterface {

	public abstract ArrayList<ProductoInventario> consultaProductosVencidos();
    public abstract ArrayList<ProductoInventario> consultaProductosConsumidosPedido();
	public abstract ArrayList<ProductoInventario> consultaProductosInventario();
    public abstract ArrayList<ProductoInventario> consultaMayorConsumo();
    public abstract ArrayList<ProductoInventario> consultaMenorConsumo();


    	
}
