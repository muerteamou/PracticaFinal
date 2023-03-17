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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static SQLiteDatabase db;
    static Adaptador adaptador;

    ImageButton btnMostrarLCompra;

    EditText etNombre;

    public static ArrayList<String> listaProductos = new ArrayList<>();
    public static ArrayList<String> listaCompra = new ArrayList<>();
    public static ArrayList<Integer> cantidadProductos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = findViewById(R.id.etNombre);

        //deleteDatabase("practicafinal");
        db = openOrCreateDatabase("practicafinal", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS listaproductos(producto VARCHAR UNIQUE)");
        db.execSQL("CREATE TABLE IF NOT EXISTS listacompra(producto VARCHAR UNIQUE, cantidad int)");


        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new Adaptador(this, listaProductos, listaCompra, cantidadProductos, db);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), 1);
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(adaptador);
        if (listaProductos.isEmpty()) {
            mostrar();
        }

        btnMostrarLCompra = findViewById(R.id.btnMostrarListaCompra);
        btnMostrarLCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listaCompra = new Intent(MainActivity.this, ListaCompra.class);
                startActivity(listaCompra);
            }
        });
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

            case R.id.ListaCompra:
                Intent i2 = new Intent(this, ListaCompra.class);
                startActivity(i2);
                break;
            case R.id.FinalizarCompra:
                Intent i3 = new Intent(this, Finalizar.class);
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
        adaptador.notifyDataSetChanged();
    }

    public void anyadirListaProducto(View view) {
        String item = etNombre.getText().toString();
        String mensaje = getResources().getString(R.string.error_campo_vacio);
        if (item.isEmpty())
            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
        else {
            try {
                db.execSQL("INSERT INTO listaproductos values ('" + item + "');");

                Toast.makeText(getApplicationContext(), item + " añadido con éxito", Toast.LENGTH_SHORT).show();
                etNombre.setText("");
                mostrar();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Ya has añadido " + item + " a la lista de productos", Toast.LENGTH_SHORT).show();
            }
        }
    }



}