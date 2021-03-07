package com.example.guesspokemon.CrearJugadors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.guesspokemon.BaseDades.BBDD;
import com.example.guesspokemon.Canco.Canco;
import com.example.guesspokemon.Jugador.Jugador;
import com.example.guesspokemon.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GeneraJugador extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    private int GALLERY_REQUEST_CODE = 1;
    private int APP_PERMISSION_READ_STORAGE = 1;

    private EditText editNom, editNomBSO, editEnllaçBSO;
    private ArrayList<Jugador> llista_jugador;
    private ArrayList<Canco> llista_canco;
    private BBDD bd;
    private Button btnAfegir, btnTorna;
    private ImageView imageView;
    private Spinner spinner;
    private Bitmap imatge_bitmap;
    private byte[] bitmap;
    private Jugador jugador = null;
    private Canco canco = null;
    //private YouTubePlayerView youTubePlayerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genera_jugador);

        bd = new BBDD(this);
        bd.obre();

        editNom = findViewById(R.id.editTextGeneraJugador);
        spinner = findViewById(R.id.spinner);
        btnTorna = findViewById(R.id.buttonGeneraJugadorTorna);
        btnAfegir = findViewById(R.id.buttonGeneraJugadorOk);
        imageView = findViewById(R.id.imageView);

        spinner.setOnItemSelectedListener(this);
        btnAfegir.setOnClickListener(this);
        btnTorna.setOnClickListener(this);
        //Peta si s'activa
        //imageView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btnAfegir)
        {
            Jugador jugador = generaObjecteJugador();

            if (jugador == null)
                Toast.makeText(this, "Posa nom i tria la teva canço", Toast.LENGTH_SHORT).show();
            else if (bd.creaJugador(jugador).getId() != -1)
            {
                Toast.makeText(this, "Afegit correctament", Toast.LENGTH_SHORT).show();
                bd.tanca();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }else {
                Toast.makeText(this, "Error a l’afegir BBDD", Toast.LENGTH_SHORT).show();
            }
            } else if(v == imageView){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, APP_PERMISSION_READ_STORAGE);
            }
            recullDeGaleria();
        } else if (v == btnTorna){
            finish();
        }
    }

    public Jugador generaObjecteJugador()
    {
        //Aqui ni puta idea
        if(canco != null && !editNom.getText().toString().isEmpty())
        {
            jugador = new Jugador();
            jugador.setNom(editNom.getText().toString());
            jugador.setFoto(bitmap);
            // ? duda
            //jugador.setIdCanco(canco.getId());
        }
        return jugador;
    }

    private void recullDeGaleria(){
        //Cream l'Intent amb l'acció ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Establim tipus d'imatges, per tant només s'acceptaran els tipus imagtge
        intent.setType("image/*");
        //Establim uns tipus de format de fotografia per assegurar-nos d'acceptar només aquest tipus de format jpg i png
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Llançam l'Intent
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_REQUEST_CODE) {//data.getData return the content URI for the selected Image
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();
                //Get the column index of MediaStore.Images.Media.DATA
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                //Gets the String value in the column
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String
                imatge_bitmap = BitmapFactory.decodeFile(imgDecodableString);

                ByteArrayOutputStream blob = new ByteArrayOutputStream();
                imatge_bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /* Ignored for PNGs */, blob);
                bitmap = blob.toByteArray();
                imageView.setImageBitmap(imatge_bitmap);

            }
        }
    }

//Esto es una puta mierda, no me entero
    private void ferSpinnerBSO(){

        llista_canco = bd.getCancons();

        // Spinner Drop down elements
        List<String> string_bso = new ArrayList<String>();
        string_bso.add("Selecciona bso..");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            for (int i = 0; i<llista_canco.size();i++){
                string_bso.add(llista_canco.get(i).getNom());
            }
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterBSO = new ArrayAdapter<>(this, R.layout.spinner, string_bso);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapterBSO);
        spinner.setSelection(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position != 0){
                canco = llista_canco.get(position-1);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}