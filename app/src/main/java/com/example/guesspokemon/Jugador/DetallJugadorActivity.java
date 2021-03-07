package com.example.guesspokemon.Jugador;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;

import com.example.guesspokemon.BaseDades.BBDD;
import com.example.guesspokemon.R;

public class DetallJugadorActivity extends AppCompatActivity implements AdaptadorJugador.OnItemSelectedListener {

    public MediaPlayer mediaPlayer;
    public long idJugador;
    public Jugador jugador;
    public BBDD database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detall_jugador);
        Bundle extras = getIntent().getExtras();
        idJugador = extras.getLong("idJugador");

        database = new BBDD(this.getApplicationContext());
        database.obre();

        jugador = database.obtenirJugador(idJugador);
        Toast.makeText(this, "jugador: " + idJugador, Toast.LENGTH_SHORT).show();

        /*int canco = jugador.getIdCanco();
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