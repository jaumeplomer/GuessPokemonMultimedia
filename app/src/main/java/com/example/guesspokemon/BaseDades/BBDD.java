package com.example.guesspokemon.BaseDades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.guesspokemon.Jugador.Jugador;

import java.util.ArrayList;

public class BBDD {

    private final Context context;
    private AuxiliarBBDD auxiliar;
    private SQLiteDatabase baseDeDades;

    private String[] totesColumnesJugador = {AuxiliarBBDD.CLAU_ID_JUGADOR, AuxiliarBBDD.CLAU_NOM, AuxiliarBBDD.CLAU_FOTO};

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
        valors.put(AuxiliarBBDD.CLAU_NOM, jugador.getNom());
        valors.put(AuxiliarBBDD.CLAU_FOTO, jugador.getFoto());
        long insertId = baseDeDades.insert(AuxiliarBBDD.BD_TAULA_JUGADOR, null, valors);
        jugador.setId(insertId);
        return jugador;
    }

    //getAllJugadors
    public ArrayList<Jugador> getJugadors() {
        ArrayList<Jugador> jugadors = new ArrayList<>();
        Cursor cursor = baseDeDades.query(AuxiliarBBDD.BD_TAULA_JUGADOR, totesColumnesJugador, null, null, null, null, AuxiliarBBDD.CLAU_NOM + " ASC" );
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
}
