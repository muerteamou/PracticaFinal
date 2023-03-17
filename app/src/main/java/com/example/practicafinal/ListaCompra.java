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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaCompra extends AppCompatActivity {
    public static SQLiteDatabase db;
    AdaptadorListaCompra adaptador;

    ArrayList<String> listaProductos = new ArrayList<>();
    ArrayList<String> listaCompra = new ArrayList<>();
    ArrayList<Integer> cantidadProductos = new ArrayList<>();

    private ImageButton btnBack;
    private ImageButton btnNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_compra);


        RecyclerView recyclerView = findViewById(R.id.rv_lista_compra);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = openOrCreateDatabase("practicafinal", Context.MODE_PRIVATE, null);

        String result = "";
        Cursor c = db.rawQuery("SELECT * FROM listacompra", null);
        if (c.getCount() == 0) {
            result = "No hay datos en la bd";
        } else {
            while (c.moveToNext()) {
                listaCompra.add(c.getString(0));
                cantidadProductos.add(c.getInt(1));
            }
        }
        c.close();
        adaptador = new AdaptadorListaCompra(this, listaProductos, listaCompra, cantidadProductos);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), 1);
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(adaptador);


        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listaProductos = new Intent(ListaCompra.this, MainActivity.class);
                startActivity(listaProductos);
            }
        });

        btnNext = findViewById(R.id.btnFinalizar);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listaCompra = new Intent(ListaCompra.this, Finalizar.class);
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

    public static int sumar(TextView producto) {
        String textProducto = producto.getText().toString();
        int numProducto = 0;

        Cursor c = db.rawQuery("SELECT cantidad FROM listacompra WHERE producto = " + "'" + textProducto + "'", null);
        while (c.moveToNext()) {
            numProducto = c.getInt(0);
        }
        numProducto++;
        db.execSQL("UPDATE listacompra SET cantidad = " + numProducto + " WHERE producto = " + "'" + textProducto + "'");
        return numProducto;
    }

    public static int restar(TextView producto) {
        String textProducto = producto.getText().toString();
        int numProducto = 0;

        Cursor c = db.rawQuery("SELECT cantidad FROM listacompra WHERE producto = " + "'" + textProducto + "'", null);
        while (c.moveToNext()) {
            numProducto = c.getInt(0);
        }
        numProducto--;
        db.execSQL("UPDATE listacompra SET cantidad = " + numProducto + " WHERE producto = " + "'" + textProducto + "'");
        return numProducto;
    }


}

