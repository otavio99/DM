package com.example.myapplication;


public class Nota{
    private String titulo;
    private String texto;

    public Nota(String titulo, String texto){
        this.titulo = titulo;
        this.texto = texto;
    }

    public String getTitulo(){
        return titulo;
    }
	public String getTexto(){
        return texto;
    }
	
	
    public void setTitulo(String titulo){
        this.titulo= titulo;
    }
    public void setTexto(String texto){
        this.texto= texto;
    }

    @Override
    public String toString(){
        return titulo;
    }
}
