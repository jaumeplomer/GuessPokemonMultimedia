package com.example.guesspokemon.Partida;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guesspokemon.BaseDades.BBDD;
import com.example.guesspokemon.Pokemon.LlistaPokemonActivity;
import com.example.guesspokemon.Pokemon.Pokemon;
import com.example.guesspokemon.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PartidaActivity extends AppCompatActivity {

    public Button boto;
    public ImageView fotoPokemon;
    public TextView puntuacio;
    public EditText nomPoke;
    public ArrayList<Pokemon> llistaPokemons;
    public BBDD database;
    public int contador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);

        boto = findViewById(R.id.buttonEnvia);
        fotoPokemon = findViewById(R.id.imageViewPoke);
        puntuacio = findViewById(R.id.textViewPuntuacio);
        nomPoke = findViewById(R.id.editTextNomPokemon);

        database = new BBDD(getApplicationContext());
        database.obre();

        llistaPokemons = database.getPokemons();
        Collections.shuffle(llistaPokemons);
        contador = 0;

        byte[] foto = llistaPokemons.get(contador).getFoto_poke();
        if (foto != null)
        {
            Bitmap map;
            map = BitmapFactory.decodeByteArray(foto,0, foto.length);
            fotoPokemon.setImageBitmap(map);
        }
        puntuacio.setText("Ronda: " + (contador + 1));

        Log.w("POKEMON: ", llistaPokemons.get(contador).getNom());

        boto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("POKEMON: ", "He pitjat");
                String nomUsuari;
                String nomPokemon;
                nomUsuari = nomPoke.getText().toString().toLowerCase();
                nomPokemon = llistaPokemons.get(contador).getNom().toLowerCase();

                if (nomUsuari.contentEquals(nomPokemon));
                {
                    byte[] foto = llistaPokemons.get(contador + 1).getFoto_poke();
                    if (foto != null)
                    {
                        Bitmap map;
                        map = BitmapFactory.decodeByteArray(foto,0, foto.length);
                        fotoPokemon.setImageBitmap(map);
                    }
                    puntuacio.setText("Ronda: " + (contador + 1));
                    nomPoke.setText(null);
                    contador++;
                }
            }
        });


    }
}