package com.example.practicafinal;

import static com.example.practicafinal.MainActivity.mostrar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {

    private List<String> aListaProductos;

    private List<String> aListaCompra;

    private List<Integer> acantidadProductos;
    private LayoutInflater aInflater;
    private SQLiteDatabase db;

    private Context context;

    public Adaptador(Context context, List<String> lista) {
        this.aListaProductos = lista;
        this.aInflater = LayoutInflater.from(context);
    }

    public Adaptador(Context context, List<String> listaProducto, List<String> listaCompra, List<Integer> cantidadProducto, SQLiteDatabase db) {
        this.aListaProductos = listaProducto;
        this.aListaCompra = listaCompra;
        this.acantidadProductos = cantidadProducto;
        this.db = db;
        this.aInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public Adaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = aInflater.inflate(R.layout.rv_lista_items, parent, false);

        return new ViewHolder(view);
    }

    public void anyadirListaCompra(String producto) {

        try {
            db.execSQL("INSERT INTO listacompra values ('" + producto + "', '" + "1" + "' );");
            Toast.makeText(context, producto + " añadido a la lista de la compra.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Ya has añadido " + producto + " a la lista de productos", Toast.LENGTH_SHORT).show();
        }
        try {
            db.execSQL("DELETE FROM listaproductos WHERE producto LIKE " + "'" + producto + "'");
            mostrar();
        } catch (Exception e) {
        }
    }

    public void eliminarProductoLista(String producto) {
        try {
            db.execSQL("DELETE FROM listaproductos WHERE producto LIKE " + "'" + producto + "'");
            Toast.makeText(context, producto + " eliminado", Toast.LENGTH_SHORT).show();
            mostrar();
        } catch (Exception e) {
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.ViewHolder holder, int position) {
        String aux = aListaProductos.get(position);
        holder.setItem(aux);
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreProducto = aListaProductos.get(holder.getAdapterPosition());
                aListaProductos.remove(holder.getAdapterPosition());
                MainActivity main = new MainActivity();
                anyadirListaCompra(nombreProducto);
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreProducto = aListaProductos.get(holder.getAdapterPosition());
                aListaProductos.remove(holder.getAdapterPosition());
                MainActivity main = new MainActivity();
                eliminarProductoLista(nombreProducto);
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return aListaProductos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombreItem;

        private ImageButton btnAdd;
        private ImageButton btnDel;


        public ViewHolder(View itemView) {
            super(itemView);
            nombreItem = itemView.findViewById(R.id.tvNombreProducto);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            btnDel = itemView.findViewById(R.id.btnDel);
        }


        public String getItem() {
            return nombreItem.getText().toString();
        }


        public void setItem(String item) {
            this.nombreItem.setText(item);
        }


    }


}