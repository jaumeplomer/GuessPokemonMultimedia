package com.example.guesspokemon.Pokemon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guesspokemon.Jugador.Jugador;
import com.example.guesspokemon.R;

import java.util.ArrayList;

public class AdaptadorPokemon extends RecyclerView.Adapter<AdaptadorPokemon.PokemonViewHolder>
{

    private final ArrayList<Pokemon> mPokemon;

    private Context c;

    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Pokemon pokemon);
    }

    public AdaptadorPokemon(Context c, ArrayList<Pokemon> pkm, OnItemClickListener listener) {
        mPokemon = pkm;
        this.c = c;
        this.listener = listener;
    }


    @Override
    public AdaptadorPokemon.PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_llista_pokemon,parent,false);
        PokemonViewHolder pvh = new PokemonViewHolder(view);
        return pvh;
    }

    @Override
    public void onBindViewHolder(AdaptadorPokemon.PokemonViewHolder holder, int position) {
        Pokemon pokemon = mPokemon.get(position);
        holder.mNom.setText(pokemon.getNom());

    }

    public int getItemCount() {
        return mPokemon.size();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {
        TextView mNom;
        ImageView mImatge;
        ImageView mImatgeTipus;
        public PokemonViewHolder(View itemView) {
            super(itemView);
            mNom = itemView.findViewById(R.id.titol);
        }
    }
}


