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
import com.example.guesspokemon.Partida.PartidaActivity;
import com.example.guesspokemon.R;

public class DetallJugadorActivity extends AppCompatActivity implements AdaptadorJugador.OnItemSelectedListener {

    public MediaPlayer mediaPlayer;
    public long idJugador;
    public Jugador jugador;
    public BBDD database;
    public TextView textMostraNom, textMostraPuntuacio;
    public ImageView fotoEntrenador;
    public Button buttonJuga;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detall_jugador);
        Bundle extras = getIntent().getExtras();
        idJugador = extras.getLong("idJugador");

        database = new BBDD(this.getApplicationContext());
        database.obre();

        textMostraNom = findViewById(R.id.textViewDetallJugadorMostraNom);
        textMostraPuntuacio = findViewById(R.id.textViewDetallJugadorMostraPuntuacio);
        fotoEntrenador = findViewById(R.id.imageViewDetallEntrenador);
        buttonJuga = findViewById(R.id.buttonJugar);

        jugador = database.obtenirJugador(idJugador);
        textMostraNom.setText(jugador.getNom());

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

        /*Toast.makeText(this, "jugador: " + idJugador, Toast.LENGTH_SHORT).show();

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
        }*/
    }

  /* @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }*/
}