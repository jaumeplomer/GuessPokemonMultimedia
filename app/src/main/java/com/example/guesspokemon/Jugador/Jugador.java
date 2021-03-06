package com.example.guesspokemon.Jugador;

//POJO
public class Jugador {

    public long id;
    public String nom;
    public byte[] foto;
    public long idCanco;

    public Jugador() {
    }

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

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public long getIdCanco() {
        return idCanco;
    }

    public void setIdCanco(long idCanco) {
        this.idCanco = idCanco;
    }
}
