package com.example.guesspokemon;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guesspokemon.BaseDades.BBDD;
import com.example.guesspokemon.Jugador.AdaptadorJugador;
import com.example.guesspokemon.Jugador.Jugador;
import com.example.guesspokemon.Pokemon.LlistaPokemonActivity;
import com.example.guesspokemon.Pokemon.Pokemon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public BBDD database;
    public RecyclerView rv;
    public ArrayList<Jugador> llista_jugadors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new BBDD(this);
        database.obre();

        rv = findViewById(R.id.recyclerViewJugadors);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Jugador jugador = new Jugador();
        jugador.setNom("Jaume");
        Pokemon poke = new Pokemon();
        poke.setNom("Burbasu");
        poke.setTipo("Planta");

        database.creaJugador(jugador);
        database.creaPokemon(poke);

        Jugador jugador1 = new Jugador();
        jugador1.setNom("MiquelAngel");

        database.creaJugador(jugador1);

        //database.tanca();

        llista_jugadors = database.getJugadors();

        rv.setHasFixedSize(true);
        rv.setLayoutManager( new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        AdaptadorJugador adapter = new AdaptadorJugador(this, llista_jugadors, null);
        rv.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.pokemons) {
            startActivity(new Intent(getApplicationContext(), LlistaPokemonActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}