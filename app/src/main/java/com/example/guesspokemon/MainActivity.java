package com.example.guesspokemon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guesspokemon.BaseDades.BBDD;
import com.example.guesspokemon.BaseDades.CarregaPokes;
import com.example.guesspokemon.Canco.Canco;
import com.example.guesspokemon.CrearJugadors.GeneraJugador;
import com.example.guesspokemon.Jugador.AdaptadorJugador;
import com.example.guesspokemon.Jugador.DetallJugadorActivity;
import com.example.guesspokemon.Jugador.Jugador;
import com.example.guesspokemon.Pokemon.LlistaPokemonActivity;
import com.example.guesspokemon.Pokemon.Pokemon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.math.BigInteger;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public BBDD database;
    public RecyclerView rv;
    public ArrayList<Jugador> llista_jugadors;
    public ArrayList<Canco> llista_cancons;
    public ArrayList<Long> id_jugador_borrar;
    public AdaptadorJugador adapter;
    private int ADD_CODE = 1;
    private int DETAIL_CODE_JUGADOR = 2;
    private CarregaPokes carregaPokes;
    private static boolean varBool;
    public SharedPreferences prefs;
    private Paint paint = new Paint();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("Fitxer", MODE_PRIVATE);
        varBool = prefs.getBoolean("varBool", true);

        id_jugador_borrar = new ArrayList<>();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GeneraJugador.class);
                startActivityForResult(i, ADD_CODE);
            }
        });

        if (varBool == true)
        {
            carregaPokes = new CarregaPokes();
            carregaPokes.carrega(this);
            varBool = false;
        }

        llistaJugadors();

    }

    @Override
    protected void onStop() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("varBool", varBool);
        editor.commit();
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        llistaJugadors();
    }


    public void llistaJugadors()
    {
        database = new BBDD(this);
        database.obre();

        llista_jugadors = database.getJugadors();
        llista_cancons = database.getCancons();
        database.tanca();

        actualitzaLlista();
    }

    private void actualitzaLlista() {

        rv = findViewById(R.id.recyclerViewJugadors);
        rv.setHasFixedSize(true);
        rv.setLayoutManager( new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
         adapter = new AdaptadorJugador(this, llista_jugadors, new AdaptadorJugador.OnItemClickListener() {
            @Override
            public void onItemClick(Jugador jugador) {
                Intent intent = new Intent(getApplicationContext(), DetallJugadorActivity.class);
                intent.putExtra("idJugador", jugador.getId());
                startActivityForResult(intent, DETAIL_CODE_JUGADOR);
            }
        });
        rv.setAdapter(adapter);
        enableSwipe();
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

    private void enableSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT){
                    final Jugador jugadorBorra = llista_jugadors.get(position);
                    final int deletedPosition = position;

                    id_jugador_borrar.add(jugadorBorra.getId());
                    adapter.eliminaJugador(position);


                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), " Jugador borrat, donde te sentastee?", Snackbar.LENGTH_LONG);
                    snackbar.setAction("Recupera'm!", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            adapter.retornaJugador(jugadorBorra, deletedPosition);
                            id_jugador_borrar.remove(id_jugador_borrar.size()-1);
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();

                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;
                    paint.setColor(Color.parseColor("#D32F2F"));
                    RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                    c.drawRect(background,paint);
                    icon = BitmapFactory.decodeResource(getResources(), R.drawable.basura);
                    RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                    c.drawBitmap(icon,null,icon_dest,paint);
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rv);
    }
}