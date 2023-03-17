package com.example.practicafinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Esta clase es la pantalla principal de la aplicación, donde se muestran todos los músicos almacenados en la base de datos.
 * Contiene un menú en la parte superior derecha para acceder a las distintas pantallas de la aplicación.
 *
 * @author Alejandro Piñero Medinilla
 */

public class MainActivity extends AppCompatActivity {

    public static SQLiteDatabase db;
    Adaptador adaptador;


    public static ArrayList<String> listaProductos = new ArrayList<>();
    public static ArrayList<String> listaCompra = new ArrayList<>();
    public static ArrayList<Integer> cantidadProductos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //deleteDatabase("practicafinal");
        db = openOrCreateDatabase("practicafinal", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS listaproductos(producto VARCHAR UNIQUE)");
        db.execSQL("CREATE TABLE IF NOT EXISTS listacompra(producto VARCHAR UNIQUE, cantidad int)");

        if (listaProductos.isEmpty()) {
            mostrar();
        }
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new Adaptador(this, listaProductos, listaCompra, cantidadProductos, db);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), 1);
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(adaptador);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.ListaProductos:
                Intent i1 = new Intent(this, MainActivity.class);
                startActivity(i1);
                break;
            case R.id.Anyadir:
                Intent i2 = new Intent(this, Anyadir.class);
                startActivity(i2);
                break;
            case R.id.ListaCompra:
                Intent i3 = new Intent(this, ListaCompra.class);
                startActivity(i3);
                break;
            default:
                break;
        }
        return true;
    }



    public static void mostrar() {
        listaProductos.clear();

        String result = "";
        Cursor c = db.rawQuery("SELECT * FROM listaproductos", null);
        if (c.getCount() == 0) {
            result = "No hay datos en la bd";

        } else {
            while (c.moveToNext()) {
                listaProductos.add(c.getString(0));
            }
        }
        c.close();
    }

    public void mostrarToast(String producto){
        Toast.makeText(getApplicationContext(), producto + " añadido con éxito", Toast.LENGTH_SHORT).show();
    }



}