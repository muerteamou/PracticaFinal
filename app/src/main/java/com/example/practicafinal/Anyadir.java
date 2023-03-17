package com.example.practicafinal;


import static com.example.practicafinal.MainActivity.mostrar;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class Anyadir extends AppCompatActivity {
    static SQLiteDatabase db;
    EditText etNombre;

    protected void onCreate(Bundle SavedInstaceState) {
        super.onCreate(SavedInstaceState);
        setContentView(R.layout.add_item);
        db = openOrCreateDatabase("practicafinal", Context.MODE_PRIVATE, null);
        etNombre = findViewById(R.id.etNombre);

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

    public void anyadirListaProducto(View view) {
        try {
            db.execSQL("INSERT INTO listaproductos values ('" + etNombre.getText().toString() + "');");

            Toast.makeText(getApplicationContext(), etNombre.getText().toString() + " añadido con éxito", Toast.LENGTH_SHORT).show();
            etNombre.setText("");
            mostrar();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Ya has añadido " + etNombre.getText().toString() + " a la lista de productos", Toast.LENGTH_SHORT).show();
        }

    }


}

