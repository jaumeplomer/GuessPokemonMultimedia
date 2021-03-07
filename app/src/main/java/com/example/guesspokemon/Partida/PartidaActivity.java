package com.example.guesspokemon.Partida;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

        llistaPokemons = database.getPokemons();

        if (llistaPokemons.size() == 0)
        {
            Toast.makeText(this,"PRIMER ESTODIA!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), LlistaPokemonActivity.class);
            startActivity(i);
        }

        database.tanca();
    }
}