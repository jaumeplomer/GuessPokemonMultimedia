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
        rv = findViewById(R.id.recyclerView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(view.getContext(), LlistaPokemonActivity.class);
               startActivity(i);
            }
        });

        Jugador jugador = new Jugador();
        jugador.setNom("Jaume");
        database.creaJugador(jugador);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}