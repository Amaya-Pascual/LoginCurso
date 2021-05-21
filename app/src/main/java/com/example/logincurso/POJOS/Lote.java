package com.example.logincurso.POJOS;

public class Lote {
    int idLote;
    int refLote;
    float salida;
    String descripcion;
    String imgLote;

    public Lote(int idLote, int refLote, float salida, String descripcion, String imgLote) {
        this.idLote = idLote;
        this.refLote = refLote;
        this.salida = salida;
        this.descripcion = descripcion;
        this.imgLote = imgLote;
    }

    public Lote() {
    }

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public int getRefLote() {
        return refLote;
    }

    public void setRefLote(int refLote) {
        this.refLote = refLote;
    }

    public float getSalida() {
        return salida;
    }

    public void setSalida(float salida) {
        this.salida = salida;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImgLote() {
        return imgLote;
    }

    public void setImgLote(String imgLote) {
        this.imgLote = imgLote;
    }

    @Override
    public String toString() {
        return "Lote{" +
                "idLote=" + idLote +
                ", refLote=" + refLote +
                ", salida=" + salida +
                ", descripcion='" + descripcion + '\'' +
                ", imgLote='" + imgLote + '\'' +
                '}';
    }
}