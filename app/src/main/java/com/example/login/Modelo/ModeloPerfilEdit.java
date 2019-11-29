package com.example.login.Modelo;

import java.io.Serializable;

public class ModeloPerfilEdit implements Serializable {
    private String _id;
    private String fotoeditar;
    private String nome;
    private String foto2;
    private String foto3;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFotoeditar() {
        return fotoeditar;
    }

    public void setFotoeditar(String fotoeditar) {
        this.fotoeditar = fotoeditar;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFoto2() {
        return foto2;
    }

    public void setFoto2(String foto2) {
        this.foto2 = foto2;
    }

    public String getFoto3() {
        return foto3;
    }

    public void setFoto3(String foto3) {
        this.foto3 = foto3;
    }
}
