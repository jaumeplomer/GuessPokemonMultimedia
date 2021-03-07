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

    public ArrayList<Pokemon> pkm;
    public RecyclerView mRecyclerView;
    public AdaptadorPokemon adaptador;
    public BBDD database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llista_pokemon);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        database = new BBDD(getApplicationContext());
        database.obre();

        String jsonCosas = addItemsFromJSON(getApplicationContext(), "pokedex.json");
        Log.w("DATA: ", jsonCosas);

        try
        {
            JSONObject pokemonesJSON = new JSONObject(jsonCosas);
            JSONArray pokeArray = pokemonesJSON.getJSONArray("pokemon");
            for (int i = 0; i < pokeArray.length(); i++)
            {
                JSONObject pokemon = pokeArray.getJSONObject(i);
                String nom = pokemon.getString("nom");

                String urlFoto = pokemon.getString("foto_poke");
                URL url = new URL(urlFoto);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] fotoPokemon = stream.toByteArray();
                bmp.recycle();

                String urlFotoTipo = pokemon.getString("foto_tipo");
                URL url2 = new URL(urlFotoTipo);
                Bitmap bmp2 = BitmapFactory.decodeStream(url2.openConnection().getInputStream());
                ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                bmp2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                byte[] fotoTipo = stream2.toByteArray();
                bmp2.recycle();

                Pokemon poke = new Pokemon();
                poke.setNom(nom);
                poke.setFoto_poke(fotoPokemon);
                poke.setFoto_tipo(fotoTipo);

                database.creaPokemon(poke);
            }

        } catch (JSONException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        pkm = database.getPokemons();
        database.tanca();

        mRecyclerView = findViewById(R.id.recyclerViewPokemons);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        adaptador = new AdaptadorPokemon(this, pkm,null);

        mRecyclerView.setAdapter(adaptador);

    }

    private String addItemsFromJSON(Context context, String file) {

        String jsonString;

        try {
            InputStream is = context.getAssets().open(file);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jsonString;
    }



}