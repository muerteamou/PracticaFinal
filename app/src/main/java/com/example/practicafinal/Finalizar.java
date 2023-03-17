package com.example.practicafinal;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Finalizar extends AppCompatActivity {
    public static SQLiteDatabase db;

    AdaptadorFinalizar adaptador;

    public static ArrayList<String> listaProductos = new ArrayList<>();
    public static ArrayList<String> listaCompra = new ArrayList<>();
    public static ArrayList<Integer> cantidadProductos = new ArrayList<>();

    private ImageButton btnEnd;

    protected void onCreate(Bundle SavedInstaceState) {
        super.onCreate(SavedInstaceState);
        setContentView(R.layout.finalizar_compra);


        RecyclerView recyclerView = findViewById(R.id.rv_lista_finalizar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = openOrCreateDatabase("practicafinal", Context.MODE_PRIVATE, null);



        adaptador = new AdaptadorFinalizar(this, listaProductos, listaCompra, cantidadProductos, db);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), 1);
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(adaptador);
        mostrarListaCompra();

        btnEnd = findViewById(R.id.btnEnd);
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listaProductos = new Intent(Finalizar.this, MainActivity.class);
                startActivity(listaProductos);
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
    public void mostrarListaCompra(){
        listaCompra.clear();
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
    }

    public void onClickShowAlert(View view){
        AlertDialog.Builder myAlertBuild = new AlertDialog.Builder(Finalizar.this);
        myAlertBuild.setTitle("Alerta");
        myAlertBuild.setMessage("Presiona OK para borrar los ensayos, o Cancel para volver");
        myAlertBuild.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Ensayos borrados", Toast.LENGTH_SHORT).show();

            }
        });
        myAlertBuild.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
            }
        });
        myAlertBuild.show();
    }
}

