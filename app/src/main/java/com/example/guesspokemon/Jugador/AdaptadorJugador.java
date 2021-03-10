package com.example.guesspokemon.Jugador;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guesspokemon.BaseDades.BBDD;
import com.example.guesspokemon.Canco.Canco;
import com.example.guesspokemon.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class AdaptadorJugador extends RecyclerView.Adapter<AdaptadorJugador.JugadorViewHolder> {

    private final ArrayList<Jugador> jugadors;

    private Context context;

    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Jugador jugador);
    }

    public AdaptadorJugador(Context context, ArrayList<Jugador> jugadors, OnItemClickListener listener) {
        this.jugadors = jugadors;
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

        holder.bindJugador(jugadors.get(position), listener, this.context);

    }

    @Override
    public int getItemCount() {
        return jugadors.size();
    }

    public interface OnItemSelectedListener {
    }

    public void eliminaJugador(int position)
    {
        jugadors.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, jugadors.size());
    }

    public void retornaJugador(Jugador jugador, int position)
    {
        jugadors.add(position, jugador);
        notifyItemInserted(position);
    }

    public static class JugadorViewHolder extends RecyclerView.ViewHolder {

        public TextView textNom, textCanc;
        public ImageView fotoImage;

        public JugadorViewHolder(View itemView) {
            super(itemView);
            textNom = itemView.findViewById(R.id.textViewNom);
            textCanc = itemView.findViewById(R.id.textViewCanco);
            fotoImage = itemView.findViewById(R.id.fotoJugador);
        }

        public void bindJugador(Jugador jugador, OnItemClickListener listener, Context context) {

             BBDD database = new BBDD(context);
             database.obre();
             ArrayList<Canco> cancons = database.getCancons();


            textNom.setText(jugador.getNom());

            byte[] foto = jugador.getFoto();
            if (foto != null)
            {
                Bitmap imatge_bitmap;
                imatge_bitmap = BitmapFactory.decodeByteArray(foto, 0, foto.length);
                fotoImage.setImageBitmap(imatge_bitmap);
            }

            for (int i = 0; i< cancons.size(); i++)
            {
                if (cancons.get(i).getId() == jugador.getIdCanco())
                {
                    textCanc.setText(cancons.get(i).getNom());
                }
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(jugador);
                }
            });

        }
    }

}
