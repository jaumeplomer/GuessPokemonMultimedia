package com.example.guesspokemon.BaseDades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import androidx.annotation.Nullable;

public class AuxiliarBBDD extends SQLiteOpenHelper {

    //BASE DE DADES DECLARADA
    public static final String TAG = "BBDD";
    public static final String BD_NOM = "GuessPokemonDB";
    public static final String BD_TAULA_JUGADOR = "jugadors";
    public static final String BD_TAULA_POKEMON = "pokemons";
    public static final int VERSIO = 1;

    //TAULA JUGADOR DECLARADA
    public static final String CLAU_ID_JUGADOR = "id";
    public static final String CLAU_NOM_JUGADOR = "nom";
    public static final String CLAU_FOTO = "foto";
    public static final String CLAU_REL_CANCO = "idCan";

    public static final String BD_CREATE_JUGADOR = "create table " + BD_TAULA_JUGADOR + "( " + CLAU_ID_JUGADOR + " integer primary key autoincrement, " +
            CLAU_NOM_JUGADOR + " TEXT NOT NULL, " + CLAU_FOTO + " BLOB);";

    //TAULA POKEMON DECLARADA
    public static final String CLAU_ID_POKEMON = "id";
    public static final String CLAU_NOM_POKEMON = "nom";
    public static final String CLAU_TIPO_POKEMON = "tipo";
    public static final String CLAU_FOTO_TIPO = "foto_tipo";
    public static final String CLAU_FOTO_POKE = "foto_poke";

    public static final String BD_CREATE_POKEMON = "create table " + BD_TAULA_POKEMON + "( " + CLAU_ID_POKEMON + " integer primary key autoincrement, " +
            CLAU_NOM_POKEMON + " TEXT NOT NULL, " + CLAU_TIPO_POKEMON + " TEXT, " + CLAU_FOTO_TIPO + " BLOB, " +  CLAU_FOTO_POKE + " BLOB);";


    public AuxiliarBBDD(@Nullable Context context) {
        super(context, BD_NOM, null, VERSIO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BD_CREATE_JUGADOR);
        db.execSQL(BD_CREATE_POKEMON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("AuxiliarDDBB: ", "On Upgrade");
        db.execSQL("DROP TABLE IF EXISTS " + BD_TAULA_JUGADOR);
        db.execSQL("DROP TABLE IF EXISTS " + BD_TAULA_POKEMON);
        onCreate(db);
    }
}
