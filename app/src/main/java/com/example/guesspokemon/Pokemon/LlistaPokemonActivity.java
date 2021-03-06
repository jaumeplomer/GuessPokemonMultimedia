package com.example.guesspokemon.Pokemon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.guesspokemon.R;

import java.util.ArrayList;

public class LlistaPokemonActivity extends AppCompatActivity {
    ArrayList<Pokemon> pkm;
    RecyclerView mRecyclerView;
    AdaptadorPokemon adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llista_pokemon);

        mRecyclerView = findViewById(R.id.recyclerViewPokemons);

        pkm = getLlista();
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        adaptador = new AdaptadorPokemon(this, pkm,null);

        mRecyclerView.setAdapter(adaptador);
    }

    public ArrayList<Pokemon> getLlista()
    {
        ArrayList<Pokemon> torna = new ArrayList<>();
        Pokemon p = new Pokemon();
        p.setId(0);
        p.setNom("Burbasú");
        torna.add(p);
        p = new Pokemon();
        p.setId(1);
        p.setNom("Escuirtle");
        torna.add(p);
        //p.foto_poke(R.drawable.);
        //p.tipo();
        return torna;
    }
}