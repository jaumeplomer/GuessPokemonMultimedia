package com.example.guesspokemon.Pokemon;  //POJO public class Pokemon


public class Pokemon {

    long id;
    String nom, tipo;
    byte[] foto_tipo, foto_poke;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public byte[] getFoto_tipo() {
        return foto_tipo;
    }

    public void setFoto_tipo(byte[] foto_tipo) {
        this.foto_tipo = foto_tipo;
    }

    public byte[] getFoto_poke() {
        return foto_poke;
    }

    public void setFoto_poke(byte[] foto_poke) {
        this.foto_poke = foto_poke;
    }
}
