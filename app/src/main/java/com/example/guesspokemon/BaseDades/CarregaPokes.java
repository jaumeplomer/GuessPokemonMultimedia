package com.example.guesspokemon.BaseDades;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;

import com.example.guesspokemon.Pokemon.Pokemon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class CarregaPokes {

    public BBDD database;

    public void carrega(Context context)
    {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        database = new BBDD(context);
        database.obre();

        String jsonCosas = addItemsFromJSON(context, "pokedex.json");
        Log.w("DATA: ", jsonCosas);

        try
        {
            JSONObject pokemonesJSON = new JSONObject(jsonCosas);
            JSONArray pokeArray = pokemonesJSON.getJSONArray("pokemon");
            for (int i = 0; i < pokeArray.length(); i++)
            {
                JSONObject pokemon = pokeArray.getJSONObject(i);
                String nom = pokemon.getString("nom");

                String urlFoto = pokemon.getString("foto_poke");
                URL url = new URL(urlFoto);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] fotoPokemon = stream.toByteArray();
                bmp.recycle();

                String urlFotoTipo = pokemon.getString("foto_tipo");
                URL url2 = new URL(urlFotoTipo);
                Bitmap bmp2 = BitmapFactory.decodeStream(url2.openConnection().getInputStream());
                ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                bmp2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                byte[] fotoTipo = stream2.toByteArray();
                bmp2.recycle();

                Pokemon poke = new Pokemon();
                poke.setNom(nom);
                poke.setFoto_poke(fotoPokemon);
                poke.setFoto_tipo(fotoTipo);

                database.creaPokemon(poke);
            }

        } catch (JSONException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String addItemsFromJSON(Context context, String file) {

        String jsonString;

        try {
            InputStream is = context.getAssets().open(file);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jsonString;
    }
}
