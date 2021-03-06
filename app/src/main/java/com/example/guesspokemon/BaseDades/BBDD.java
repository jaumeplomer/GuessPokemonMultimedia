package com.example.guesspokemon.BaseDades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.guesspokemon.Jugador.Jugador;
import com.example.guesspokemon.Pokemon.Pokemon;

import java.io.PipedOutputStream;
import java.util.ArrayList;

public class BBDD {

    private final Context context;
    private AuxiliarBBDD auxiliar;
    private SQLiteDatabase baseDeDades;

    private String[] totesColumnesJugador = {AuxiliarBBDD.CLAU_ID_JUGADOR, AuxiliarBBDD.CLAU_NOM_JUGADOR, AuxiliarBBDD.CLAU_FOTO};
    private String[] totesColumnesPokemon = {AuxiliarBBDD.CLAU_ID_POKEMON, AuxiliarBBDD.CLAU_NOM_POKEMON, AuxiliarBBDD.CLAU_TIPO_POKEMON, AuxiliarBBDD.CLAU_FOTO_TIPO, AuxiliarBBDD.CLAU_FOTO_POKE};

    public BBDD(Context context) {
        this.context = context;
        auxiliar = new AuxiliarBBDD(context);
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
        long insertId = baseDeDades.insert(AuxiliarBBDD.BD_TAULA_JUGADOR, null, valors);
        jugador.setId(insertId);
        return jugador;
    }

    //getAllJugadors
    public ArrayList<Jugador> getJugadors() {
        ArrayList<Jugador> jugadors = new ArrayList<>();
        Cursor cursor = baseDeDades.query(AuxiliarBBDD.BD_TAULA_JUGADOR, totesColumnesJugador, null, null, null, null, AuxiliarBBDD.CLAU_NOM_JUGADOR + " ASC" );
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

    private Jugador cursorToJugador(Cursor cursor) {
        Jugador jugador = new Jugador();
        jugador.setId(cursor.getLong(0));
        jugador.setNom(cursor.getString(1));
        jugador.setFoto(cursor.getBlob(2));
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

    //BOrrar jugador
    public boolean borraJugador(long IDFila) {
        return baseDeDades.delete(AuxiliarBBDD.BD_TAULA_JUGADOR, AuxiliarBBDD.CLAU_ID_JUGADOR + " = " + IDFila, null) > 0;
    }

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

    public ArrayList<Pokemon> getPokemons() {
        ArrayList<Pokemon> pokemons = new ArrayList<>();
        Cursor cursor = baseDeDades.query(AuxiliarBBDD.BD_TAULA_POKEMON, totesColumnesPokemon, null, null, null, null, AuxiliarBBDD.CLAU_ID_POKEMON+ " ASC" );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Pokemon poke = cursorToPokemon(cursor);
            pokemons.add(poke);
            cursor.moveToNext();
        }
        cursor.close();
        return pokemons;
    }

    //ConsultarJugador

    private Pokemon cursorToPokemon(Cursor cursor) {
        Pokemon pokemon = new Pokemon();
        pokemon.setId(cursor.getLong(0));
        pokemon.setNom(cursor.getString(1));
        pokemon.setTipo(cursor.getString(2));
        pokemon.setFoto_tipo(cursor.getBlob(3));
        pokemon.setFoto_poke(cursor.getBlob(4));
        return pokemon;
    }

}
