package com.example.login.Modelo;

import java.io.Serializable;

public class ModeloPerfilEdit implements Serializable {
    private Long _id;
    private String fotoeditar;
    private String foto2;
    private String foto3;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getFotoeditar() {
        return fotoeditar;
    }

    public void setFotoeditar(String fotoeditar) {
        this.fotoeditar = fotoeditar;
    }
}
