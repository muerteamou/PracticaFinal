package com.example.practicafinal;

import static com.example.practicafinal.MainActivity.cantidadProductos;
import static com.example.practicafinal.MainActivity.listaCompra;
import static com.example.practicafinal.MainActivity.mostrar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorFinalizar extends RecyclerView.Adapter<AdaptadorFinalizar.ViewHolder> {

    private List<String> aListaProductos;

    private List<String> aListaCompra;

    private List<Integer> acantidadProductos;
    private LayoutInflater aInflater;
    private SQLiteDatabase db;

    private Context context;

    public AdaptadorFinalizar(Context context, List<String> lista) {
        this.aListaProductos = lista;
        this.aInflater = LayoutInflater.from(context);
    }

    public AdaptadorFinalizar(Context context, List<String> listaProducto, List<String> listaCompra, List<Integer> cantidadProducto, SQLiteDatabase db) {
        this.aListaProductos = listaProducto;
        this.aListaCompra = listaCompra;
        this.acantidadProductos = cantidadProducto;
        this.db = db;
        this.aInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AdaptadorFinalizar.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = aInflater.inflate(R.layout.rv_lista_finalizar, parent, false);

        return new ViewHolder(view);
    }

    public void anyadirListaProducto(String producto) {

        try {
            db.execSQL("INSERT INTO listaproductos values ('" + producto + "');");
        } catch (Exception e) {
            Toast.makeText(context, "Ya has añadido " + producto + " a la lista de productos", Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminarProductoListaCompra(String producto) {
        try {
            db.execSQL("DELETE FROM listacompra WHERE producto LIKE " + "'" + producto + "'");
        } catch (Exception e) {
        }
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorFinalizar.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String aux = aListaCompra.get(position);
        holder.setItem(aux);
        aux = String.valueOf(acantidadProductos.get(position));
        holder.setCantidad(aux);
        holder.btnFinalizar.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   onClickShowAlert(v, holder, position);

               }
           }
        );


    }

    @Override
    public int getItemCount() {
        return aListaCompra.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombreItem;

        private TextView cantidadItem;

        private ImageButton btnFinalizar;


        public ViewHolder(View itemView) {
            super(itemView);
            nombreItem = itemView.findViewById(R.id.tvListaProducto);
            cantidadItem = itemView.findViewById(R.id.tvCantidadNum);
            btnFinalizar = itemView.findViewById(R.id.btnComprado);

        }


        public String getItem() {
            return nombreItem.getText().toString();
        }


        public void setItem(String item) {
            this.nombreItem.setText(item);
        }

        public String getCantidad() {
            return cantidadItem.getText().toString();
        }

        public void setCantidad(String cantidad) {
            this.cantidadItem.setText(cantidad);
        }

    }

    public void onClickShowAlert(View view, AdaptadorFinalizar.ViewHolder holder, int position){

        String aux = aListaCompra.get(position);
        holder.setItem(aux);
        aux = String.valueOf(acantidadProductos.get(position));
        holder.setCantidad(aux);


        AlertDialog.Builder myAlertBuild = new AlertDialog.Builder(context);
        myAlertBuild.setTitle("Confirmación");
        myAlertBuild.setMessage("Presiona OK para confirmar que has añadido " + aListaCompra.get(holder.getAdapterPosition())+ " al carro de la compra o Cancel para cancelar");
        myAlertBuild.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String nombreProducto = aListaCompra.get(holder.getAdapterPosition());
                aListaCompra.remove(holder.getAdapterPosition());
                Finalizar main = new Finalizar();
                anyadirListaProducto(nombreProducto);
                eliminarProductoListaCompra(nombreProducto);
                notifyItemRemoved(holder.getAdapterPosition());
                mostrar();
                main.mostrarListaCompra();
                Toast.makeText(context, nombreProducto + " Añadido al carro de la compra.", Toast.LENGTH_SHORT).show();

            }
        });
        myAlertBuild.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show();

            }
        });
        myAlertBuild.show();
    }


}