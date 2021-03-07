package com.example.guesspokemon.Pokemon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.example.guesspokemon.BaseDades.BBDD;
import com.example.guesspokemon.BaseDades.CarregaPokes;
import com.example.guesspokemon.Jugador.AdaptadorJugador;
import com.example.guesspokemon.Jugador.DetallJugadorActivity;
import com.example.guesspokemon.Jugador.Jugador;
import com.example.guesspokemon.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LlistaPokemonActivity extends AppCompatActivity {

    public ArrayList<Pokemon> pkm = new ArrayList<>();
    public RecyclerView mRecyclerView;
    public AdaptadorPokemon adaptador;
    public BBDD database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llista_pokemon);

        database = new BBDD(getApplicationContext());
        database.obre();

        pkm.clear();
        pkm = database.getPokemons();

        mRecyclerView = findViewById(R.id.recyclerViewPokemons);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        adaptador = new AdaptadorPokemon(this, pkm,null);

        mRecyclerView.setAdapter(adaptador);

    }
}