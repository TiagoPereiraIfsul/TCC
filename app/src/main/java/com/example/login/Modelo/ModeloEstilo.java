package com.example.login.Modelo;

import java.io.Serializable;

public class ModeloEstilo implements Serializable {

    private String id;
    private String nome;
    private String barba;
    private String sobrancelha;
    private String cabelo;
    private String categoria;
    private String barbeiro;
    private String foto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBarba() {
        return barba;
    }

    public void setBarba(String barba) {
        this.barba = barba;
    }

    public String getSobrancelha() {
        return sobrancelha;
    }

    public void setSobrancelha(String sobrancelha) {
        this.sobrancelha = sobrancelha;
    }

    public String getCabelo() {
        return cabelo;
    }

    public void setCabelo(String cabelo) {
        this.cabelo = cabelo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getBarbeiro() {
        return barbeiro;
    }

    public void setBarbeiro(String barbeiro) {
        this.barbeiro = barbeiro;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
