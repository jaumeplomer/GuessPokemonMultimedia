package com.example.guesspokemon.Partida;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
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
import com.example.guesspokemon.Jugador.Jugador;
import com.example.guesspokemon.Pokemon.LlistaPokemonActivity;
import com.example.guesspokemon.Pokemon.Pokemon;
import com.example.guesspokemon.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PartidaActivity extends AppCompatActivity {

    public MediaPlayer mediaPlayer;
    public Button boto;
    public ImageView fotoPokemon;
    public TextView puntuacio;
    public EditText nomPoke;
    public ArrayList<Pokemon> llistaPokemons;
    public BBDD database;
    public int contador;
    public Partida partida;
    public long idJugador;
    public int idJugador2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);

        Bundle extras = getIntent().getExtras();
        idJugador = extras.getLong("idJugador");
        idJugador2 = (int)idJugador;

        boto = findViewById(R.id.buttonEnvia);
        fotoPokemon = findViewById(R.id.imageViewPoke);
        puntuacio = findViewById(R.id.textViewPuntuacio);
        nomPoke = findViewById(R.id.editTextNomPokemon);

        database = new BBDD(getApplicationContext());
        database.obre();

        partida = new Partida();
        partida.setIdJugador(idJugador2);

        Jugador jugador = database.obtenirJugador(idJugador);
        int canco = jugador.getIdCanco();
        switch (canco)
        {
            case 1:
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tema1);
                mediaPlayer.start();
                break;
            case 2:
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tema2);
                mediaPlayer.start();
                break;
            case 3:
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tema3);
                mediaPlayer.start();
                break;
        }

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

                if (nomUsuari.contentEquals(nomPokemon))
                {
                    if (contador == llistaPokemons.size() - 1)
                    {
                        partida.setPuntuacio(contador + 1);
                        database.creaPartida(partida);
                        finish();
                        Toast.makeText(getApplicationContext(),"La teva puntuacio ha estat " + contador + 1, Toast.LENGTH_LONG).show();
                    }
                    else{
                        byte[] foto = llistaPokemons.get(contador + 1).getFoto_poke();
                        if (foto != null)
                        {
                            Bitmap map;
                            map = BitmapFactory.decodeByteArray(foto,0, foto.length);
                            fotoPokemon.setImageBitmap(map);
                        }

                        nomPoke.setText(null);

                        contador++;
                        puntuacio.setText("Ronda: " + (contador + 1));
                    }
                }
                else {
                    partida.setPuntuacio(contador);
                    database.creaPartida(partida);
                    finish();
                    Toast.makeText(getApplicationContext(),"La teva puntuacio ha estat " + contador, Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }
}