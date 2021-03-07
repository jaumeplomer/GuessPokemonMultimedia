package com.example.guesspokemon.Partida;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
    public int contador = 0;

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

        /*llistaPokemons = database.getPokemons();

        if (llistaPokemons.size() == 0)
        {
            Toast.makeText(this,"PRIMER ESTODIA!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), LlistaPokemonActivity.class);
            startActivity(i);
        }

        Collections.shuffle(llistaPokemons);
        database.tanca();

        int tamanyLlista = llistaPokemons.size();


        for (contador = 0; contador < tamanyLlista; contador++)
        {
            puntuacio.setText("Ronda: "+ contador);
            Pokemon pokemon = llistaPokemons.get(contador);

            byte[] foto = pokemon.getFoto_poke();
            Bitmap fotoMap;
            fotoMap = BitmapFactory.decodeByteArray(foto,0,foto.length);
            fotoPokemon.setImageBitmap(fotoMap);
            nomPoke.setText(pokemon.getNom());
            boto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Hola", Toast.LENGTH_SHORT).show();
                    //contador++;
                }
            });;
        }*/

    }
}