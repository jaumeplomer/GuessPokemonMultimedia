package com.example.guesspokemon.BaseDades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.guesspokemon.Canco.Canco;
import com.example.guesspokemon.Jugador.Jugador;
import com.example.guesspokemon.Partida.Partida;
import com.example.guesspokemon.Pokemon.Pokemon;

import java.io.PipedOutputStream;
import java.net.JarURLConnection;
import java.security.PublicKey;
import java.util.ArrayList;

public class BBDD {

    private final Context ct;
    private AuxiliarBBDD auxiliar;
    private SQLiteDatabase baseDeDades;

    private String[] totesColumnesJugador = {AuxiliarBBDD.CLAU_ID_JUGADOR, AuxiliarBBDD.CLAU_NOM_JUGADOR, AuxiliarBBDD.CLAU_FOTO, AuxiliarBBDD.CLAU_REL_CANCO};
    private String[] totesColumnesPokemon = {AuxiliarBBDD.CLAU_ID_POKEMON, AuxiliarBBDD.CLAU_NOM_POKEMON, AuxiliarBBDD.CLAU_TIPO_POKEMON, AuxiliarBBDD.CLAU_FOTO_TIPO, AuxiliarBBDD.CLAU_FOTO_POKE};
    private String[] totesColumnesCancons = {AuxiliarBBDD.CLAU_ID_CANCO, AuxiliarBBDD.CLAU_NOM_CANCO, AuxiliarBBDD.CLAU_PREF_CANC};
    private String[] totesColumnesPartides = {AuxiliarBBDD.CLAU_ID_PARTIDA, AuxiliarBBDD.CLAU_PUNTUACIO, AuxiliarBBDD.CLAU_REL_JUGADOR};

    public BBDD(Context context) {
        this.ct = context;
        auxiliar = new AuxiliarBBDD(ct);
    }

    public void obre() throws SQLException {
        baseDeDades = auxiliar.getWritableDatabase();
    }

    public void tanca() {
        auxiliar.close();
    }

    //insert
    public Jugador creaJugador(Jugador jugador) {

        ContentValues valors = new ContentValues();
        valors.put(AuxiliarBBDD.CLAU_NOM_JUGADOR, jugador.getNom());
        valors.put(AuxiliarBBDD.CLAU_FOTO, jugador.getFoto());
        valors.put(AuxiliarBBDD.CLAU_REL_CANCO, jugador.getIdCanco());
        long insertId = baseDeDades.insert(AuxiliarBBDD.BD_TAULA_JUGADOR, null, valors);
        jugador.setId(insertId);
        return jugador;
    }

    //getAllJugadors
    public ArrayList<Jugador> getJugadors() {
        ArrayList<Jugador> jugadors = new ArrayList<>();
        Cursor cursor = baseDeDades.query(AuxiliarBBDD.BD_TAULA_JUGADOR, totesColumnesJugador, null, null, null, null, AuxiliarBBDD.CLAU_ID_JUGADOR + " ASC" );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Jugador jugador = cursorToJugador(cursor);
            jugadors.add(jugador);
            cursor.moveToNext();
        }
        cursor.close();
        return jugadors;
    }

    //ConsultarJugador
    public ArrayList<Jugador> consultaJugador(String regex) {
        ArrayList<Jugador> jugadors = new ArrayList<>();
        Cursor cursor = baseDeDades.query(true, AuxiliarBBDD.BD_TAULA_JUGADOR, totesColumnesJugador, AuxiliarBBDD.CLAU_NOM_JUGADOR + " LIKE ?", new String[] {regex+"%"}, null, null, AuxiliarBBDD.CLAU_NOM_JUGADOR + " ASC", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Jugador jugador = cursorToJugador(cursor);
            jugadors.add(jugador);
            cursor.moveToNext();
        }
        cursor.close();
        return jugadors;
    }

    private Jugador cursorToJugador(Cursor cursor) {
        Jugador jugador = new Jugador();
        jugador.setId(cursor.getLong(0));
        jugador.setNom(cursor.getString(1));
        jugador.setFoto(cursor.getBlob(2));
        jugador.setIdCanco(cursor.getInt(3));
        return jugador;
    }

    //Retornar un jugador
    public Jugador obtenirJugador(long IdFila) throws SQLException {
        Cursor cursor = baseDeDades.query(true, AuxiliarBBDD.BD_TAULA_JUGADOR, totesColumnesJugador, AuxiliarBBDD.CLAU_ID_JUGADOR + " = " + IdFila, null, null, null, null, null);
        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        return cursorToJugador(cursor);
    }

    //actualitzar jugador
    public boolean actualitzaJugador(long idFila, Jugador jugador) {
        ContentValues valors = new ContentValues();
        valors.put(AuxiliarBBDD.CLAU_NOM_JUGADOR, jugador.getNom());
        valors.put(AuxiliarBBDD.CLAU_FOTO, jugador.getFoto());
        valors.put(AuxiliarBBDD.CLAU_REL_CANCO, jugador.getIdCanco());
        return baseDeDades.update(AuxiliarBBDD.BD_TAULA_JUGADOR, valors, AuxiliarBBDD.CLAU_ID_JUGADOR + " = " + idFila, null ) > 0;
    }

    //BOrrar jugador
    public boolean borraJugador(long IDFila) {
        return baseDeDades.delete(AuxiliarBBDD.BD_TAULA_JUGADOR, AuxiliarBBDD.CLAU_ID_JUGADOR + " = " + IDFila, null) > 0;
    }

    //insert
    public Pokemon creaPokemon(Pokemon pokemon) {

        ContentValues valors = new ContentValues();
        valors.put(AuxiliarBBDD.CLAU_NOM_POKEMON, pokemon.getNom());
        valors.put(AuxiliarBBDD.CLAU_TIPO_POKEMON, pokemon.getTipo());
        valors.put(AuxiliarBBDD.CLAU_FOTO_TIPO, pokemon.getFoto_tipo());
        valors.put(AuxiliarBBDD.CLAU_FOTO_POKE, pokemon.getFoto_poke());

        long insertId = baseDeDades.insert(AuxiliarBBDD.BD_TAULA_POKEMON, null, valors);
        pokemon.setId(insertId);
        return pokemon;
    }

    //Llista Pokemons
    public ArrayList<Pokemon> getPokemons() {
        ArrayList<Pokemon> pokemons = new ArrayList<>();
        Cursor cursor = baseDeDades.query(AuxiliarBBDD.BD_TAULA_POKEMON, totesColumnesPokemon, null, null, null, null, AuxiliarBBDD.CLAU_ID_POKEMON + " ASC" );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Pokemon poke = cursorToPokemon(cursor);
            pokemons.add(poke);
            cursor.moveToNext();
        }
        cursor.close();
        return pokemons;
    }

    //Obtenir Pokemon
    public Pokemon obtenirPokemon(long IdFila) throws SQLException {
        Cursor cursor = baseDeDades.query(true, AuxiliarBBDD.BD_TAULA_POKEMON, totesColumnesPokemon, AuxiliarBBDD.CLAU_ID_POKEMON + " = " + IdFila, null, null, null, null, null);
        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        return cursorToPokemon(cursor);
    }

    private Pokemon cursorToPokemon(Cursor cursor) {
        Pokemon pokemon = new Pokemon();
        pokemon.setId(cursor.getLong(0));
        pokemon.setNom(cursor.getString(1));
        pokemon.setTipo(cursor.getString(2));
        pokemon.setFoto_tipo(cursor.getBlob(3));
        pokemon.setFoto_poke(cursor.getBlob(4));
        return pokemon;
    }

    public boolean esborraPokemons(long IDFila) {
        return baseDeDades.delete(AuxiliarBBDD.BD_TAULA_POKEMON, AuxiliarBBDD.CLAU_ID_POKEMON + " = " + IDFila, null) > 0;
    }


    public Canco creaCanco(Canco canco) {

        ContentValues valors = new ContentValues();
        valors.put(AuxiliarBBDD.CLAU_ID_CANCO, canco.getId());
        valors.put(AuxiliarBBDD.CLAU_NOM_CANCO, canco.getNom());
        valors.put(AuxiliarBBDD.CLAU_PREF_CANC, canco.getPreferencia());
        long insertId = baseDeDades.insert(AuxiliarBBDD.BD_TAULA_CANCO, null, valors);
        canco.setId(insertId);
        return canco;
    }

    //getAllJugadors
    public ArrayList<Canco> getCancons() {
        ArrayList<Canco> cancons = new ArrayList<>();
        Cursor cursor = baseDeDades.query(AuxiliarBBDD.BD_TAULA_CANCO, totesColumnesCancons, null, null, null, null, AuxiliarBBDD.CLAU_ID_CANCO + " ASC" );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Canco canco = cursorToCanco(cursor);
            cancons.add(canco);
            cursor.moveToNext();
        }
        cursor.close();
        return cancons;
    }

    private Canco cursorToCanco(Cursor cursor) {
        Canco canco = new Canco();
        canco.setId(cursor.getInt(0));
        canco.setNom(cursor.getString(1));
        canco.setPreferencia((cursor.getInt(2)));
        return canco;
    }

    public Canco obtenirCanco(long IDFila) throws SQLException {
        Cursor cursor = baseDeDades.query(true, AuxiliarBBDD.BD_TAULA_CANCO, totesColumnesCancons,AuxiliarBBDD.CLAU_ID_CANCO + " = " + IDFila, null, null, null, null, null);
        if(cursor != null) {
            cursor.moveToFirst();
        }
        return cursorToCanco(cursor);
    }

    public boolean actualitzaCanco(long IDFila, Canco canco) {
        ContentValues valors = new ContentValues();
        valors.put(AuxiliarBBDD.CLAU_NOM_CANCO, canco.getNom());
        valors.put(AuxiliarBBDD.CLAU_PREF_CANC, canco.getPreferencia());
        return baseDeDades.update(AuxiliarBBDD.BD_TAULA_CANCO, valors, AuxiliarBBDD.CLAU_ID_CANCO + " = " + IDFila, null) > 0;
    }

    public boolean esborraCanco(long IDFila) {
        return baseDeDades.delete(AuxiliarBBDD.BD_TAULA_CANCO, AuxiliarBBDD.CLAU_ID_CANCO + " = " + IDFila, null) > 0;
    }


    public Partida creaPartida(Partida partida) {

        ContentValues valors = new ContentValues();
        valors.put(AuxiliarBBDD.CLAU_PUNTUACIO, partida.getPuntuacio());
        valors.put(AuxiliarBBDD.CLAU_REL_JUGADOR, partida.getIdJugador());

        long insertId = baseDeDades.insert(AuxiliarBBDD.BD_TAULA_PARTIDA, null, valors);
        partida.setId(insertId);
        return partida;
    }

    public ArrayList<Partida> getPartida(int idJugador) {
        ArrayList<Partida> partides = new ArrayList<>();
        Cursor cursor = baseDeDades.query(AuxiliarBBDD.BD_TAULA_PARTIDA, totesColumnesPartides, AuxiliarBBDD.CLAU_REL_JUGADOR + " = " + idJugador, null, null, null, AuxiliarBBDD.CLAU_PUNTUACIO + " DESC" );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Partida partida = cursorToPartida(cursor);
            partides.add(partida);
            cursor.moveToNext();
        }
        cursor.close();
        return partides;
    }

    private Partida cursorToPartida(Cursor cursor) {
        Partida partida = new Partida();
        partida.setId(cursor.getLong(0));
        partida.setPuntuacio(cursor.getInt(1));
        partida.setIdJugador(cursor.getInt(2));
        return partida;
    }

    public Partida obtenirPartidaMillor(long idJugador) throws SQLException {
        Cursor cursor = baseDeDades.query(true, AuxiliarBBDD.BD_TAULA_PARTIDA, totesColumnesPartides, AuxiliarBBDD.CLAU_REL_JUGADOR + " = " + idJugador, null, null, null, AuxiliarBBDD.CLAU_PUNTUACIO + " DESC", null);
        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        return cursorToPartida(cursor);
    }

}
