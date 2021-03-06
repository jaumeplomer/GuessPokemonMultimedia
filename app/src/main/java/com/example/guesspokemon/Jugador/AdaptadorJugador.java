package com.example.guesspokemon.Jugador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guesspokemon.R;

import java.util.ArrayList;

public class AdaptadorJugador extends RecyclerView.Adapter<AdaptadorJugador.JugadorViewHolder> {

    private final ArrayList<Jugador> mJugadors;

    private Context context;

    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Jugador jugador);
    }

    public AdaptadorJugador(Context context, ArrayList<Jugador> jugadors, OnItemClickListener listener) {
        mJugadors = jugadors;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public AdaptadorJugador.JugadorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_main, parent, false);
        JugadorViewHolder jvh = new JugadorViewHolder(view);
        return jvh;
    }

    @Override
    public void onBindViewHolder(AdaptadorJugador.JugadorViewHolder holder, int position) {
        Jugador jugador = mJugadors.get(position);
        holder.textNom.setText(jugador.getNom());
    }

    @Override
    public int getItemCount() {
        return mJugadors.size();
    }

    public static class JugadorViewHolder extends RecyclerView.ViewHolder {

        public TextView textNom;

        public JugadorViewHolder(View itemView) {
            super(itemView);
            textNom = itemView.findViewById(R.id.textViewNom);
        }
    }

}
