package com.example.myapplication;


public class Contato{
    private String nome;
    private String telefone;
    private String email;

    public Contato(String nome, String telefone, String email){
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public String getNome(){
        return nome;
    }
		public String getTelefone(){
        return telefone;
    }
		public String getEmail(){
        return email;
    }
	
	
    public void setNome(String nome){
        this.nome= nome;
    }
    public void setTelefone(String telefone){
        this.telefone= telefone;
    }
		public void setEmail(String email){
        this.email= email;
    }
		
		@Override
    public String toString(){
        return nome;
    }
}
