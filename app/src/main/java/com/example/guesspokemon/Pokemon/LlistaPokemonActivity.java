package com.example.guesspokemon.Pokemon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LlistaPokemonActivity extends AppCompatActivity {
    ArrayList<Pokemon> pkm;
    RecyclerView mRecyclerView;
    AdaptadorPokemon adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llista_pokemon);

        mRecyclerView = findViewById(R.id.recyclerViewPokemons);

        pkm = new ArrayList<>();
        //pkm = getLlista();

        String jsonCosas = addItemsFromJSON(getApplicationContext(), "pokedex.json");
        Log.w("DATA: ", jsonCosas);

        try
        {
            JSONObject pokemonesJSON = new JSONObject(jsonCosas);

        } catch (JSONException e) {
            e.printStackTrace();
        }


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