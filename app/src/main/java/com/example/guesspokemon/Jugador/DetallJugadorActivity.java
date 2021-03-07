package com.example.guesspokemon.Jugador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guesspokemon.BaseDades.BBDD;
import com.example.guesspokemon.Partida.Partida;
import com.example.guesspokemon.Partida.PartidaActivity;
import com.example.guesspokemon.R;

public class DetallJugadorActivity extends AppCompatActivity implements AdaptadorJugador.OnItemSelectedListener {


    public long idJugador;
    public int idJugador2;
    public Jugador jugador;
    public BBDD database;
    public TextView textMostraNom, textMostraPuntuacio;
    public ImageView fotoEntrenador;
    public Button buttonJuga;
    public Partida partida;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detall_jugador);
        Bundle extras = getIntent().getExtras();
        idJugador = extras.getLong("idJugador");
        idJugador2 = (int)idJugador;

        database = new BBDD(this.getApplicationContext());
        database.obre();

        textMostraNom = findViewById(R.id.textViewDetallJugadorMostraNom);
        textMostraPuntuacio = findViewById(R.id.textViewDetallJugadorMostraPuntuacio);
        fotoEntrenador = findViewById(R.id.imageViewDetallEntrenador);
        buttonJuga = findViewById(R.id.buttonJugar);

        jugador = database.obtenirJugador(idJugador);

        if (database.getPartida(idJugador2).size() == 0)
        {
            partida = new Partida();
            partida.setPuntuacio(0);
            partida.setIdJugador(idJugador2);
            database.creaPartida(partida);
        }
        partida = new Partida();
        partida = database.obtenirPartidaMillor(idJugador2);

        textMostraNom.setText(jugador.getNom());
        textMostraPuntuacio.setText("Maxima puntuacio: " + partida.getPuntuacio());

        byte[] fotoPlayer = jugador.getFoto();
        if(fotoPlayer != null)
        {
            Bitmap fotoMap;
            fotoMap = BitmapFactory.decodeByteArray(fotoPlayer,0,fotoPlayer.length);
            fotoEntrenador.setImageBitmap(fotoMap);
        }

        buttonJuga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PartidaActivity.class);
                intent.putExtra("idJugador", jugador.getId());
                startActivity(intent);
            }
        });

        Toast.makeText(this, "jugador: " + idJugador, Toast.LENGTH_SHORT).show();
    }

}