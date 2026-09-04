package com.emprende.inti_agenda;

public class Pedido {
    private int id;
    private String titulo;
    private String cliente;
    private String descripcion;
    private String pago;
    private String fechaEntrega;
    private String categoria;

    public Pedido(int id, String titulo, String cliente, String descripcion, String pago, String fechaEntrega, String categoria) {
        this.id = id;
        this.titulo = titulo;
        this.cliente = cliente;
        this.descripcion = descripcion;
        this.pago = pago;
        this.fechaEntrega = fechaEntrega;
        this.categoria = categoria;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getCliente() { return cliente; }
    public String getDescripcion() { return descripcion; }
    public String getPago() { return pago; }
    public String getFechaEntrega() { return fechaEntrega; }
    public String getCategoria() { return categoria; }
}