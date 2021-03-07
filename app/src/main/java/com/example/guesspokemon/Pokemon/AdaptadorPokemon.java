package com.example.guesspokemon.Pokemon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guesspokemon.BaseDades.BBDD;
import com.example.guesspokemon.Jugador.Jugador;
import com.example.guesspokemon.R;

import java.util.ArrayList;

public class AdaptadorPokemon extends RecyclerView.Adapter<AdaptadorPokemon.PokemonViewHolder>
{

    private final ArrayList<Pokemon> pokemons;

    private Context context;

    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Pokemon pokemon);
    }

    public AdaptadorPokemon(Context context, ArrayList<Pokemon> pokemons, OnItemClickListener listener) {
        this.pokemons = pokemons;
        this.context = context;
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

        holder.bindPokemon(pokemons.get(position), listener, this.context);
    }

    public int getItemCount() {
        return pokemons.size();
    }

    public interface OnItemSelectedListener {

    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {
        TextView mNom;
        ImageView mImatge;
        ImageView mImatgeTipus;

        public PokemonViewHolder(View itemView) {
            super(itemView);
            mNom = itemView.findViewById(R.id.titol);
            mImatge = itemView.findViewById(R.id.imageViewPoke);
            mImatgeTipus = itemView.findViewById(R.id.imageViewTipus);
        }

        public void bindPokemon(Pokemon pokemon, OnItemClickListener listener, Context context)
        {
            BBDD database = new BBDD(context);
            database.obre();

            mNom.setText(pokemon.getNom());

            byte[] fotoPoke = pokemon.getFoto_poke();
            if (fotoPoke != null)
            {
                Bitmap imatge_bitmap;
                imatge_bitmap = BitmapFactory.decodeByteArray(fotoPoke, 0, fotoPoke.length);
                mImatge.setImageBitmap(imatge_bitmap);
            }

            byte[] fotoTipo = pokemon.getFoto_tipo();
            if (fotoTipo != null)
            {
                Bitmap imatge_bitmap;
                imatge_bitmap = BitmapFactory.decodeByteArray(fotoTipo, 0, fotoTipo.length);
                mImatgeTipus.setImageBitmap(imatge_bitmap);
            }

        }
    }
}


