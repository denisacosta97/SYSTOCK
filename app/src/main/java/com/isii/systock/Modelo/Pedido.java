package com.isii.systock.Modelo;

import java.util.List;

public class Pedido {

    private String nroPedido;
    private Cliente cliente;
    private List<Producto> productos;
    private String medioPago;
    private boolean estadoEntrega;
    private float montoTotal;

    public Pedido(String nroPedido, Cliente cliente, List<Producto> productos, String medioPago, boolean estadoEntrega, float montoTotal) {
        this.nroPedido = nroPedido;
        this.cliente = cliente;
        this.productos = productos;
        this.medioPago = medioPago;
        this.estadoEntrega = estadoEntrega;
        this.montoTotal = montoTotal;
    }

    public String getNroPedido() {
        return nroPedido;
    }

    public void setNroPedido(String nroPedido) {
        this.nroPedido = nroPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public boolean isEstadoEntrega() {
        return estadoEntrega;
    }

    public void setEstadoEntrega(boolean estadoEntrega) {
        this.estadoEntrega = estadoEntrega;
    }

    public float getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(float montoTotal) {
        this.montoTotal = montoTotal;
    }

    public void agregarProducto(Producto producto){
        productos.add(producto);
    }
}
