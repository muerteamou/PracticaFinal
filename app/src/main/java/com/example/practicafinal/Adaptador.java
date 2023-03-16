package com.example.practicafinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {

    private List<String> aListaProductos;

    private List<String> aListaCompra;

    private List<Integer> acantidadProductos;
    private LayoutInflater aInflater;

    public Adaptador(Context context, List<String> lista) {
        this.aListaProductos = lista;
        this.aInflater = LayoutInflater.from(context);
    }

    public Adaptador(Context context, List<String> listaProducto, List<String> listaCompra, List<Integer> cantidadProducto) {
        this.aListaProductos = listaProducto;
        this.aListaCompra = listaCompra;
        this.acantidadProductos = cantidadProducto;
        this.aInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Adaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = aInflater.inflate(R.layout.rv_lista_items, parent, false);

        return new ViewHolder(view);
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
                Anyadir.anyadirListaCompra(nombreProducto);
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

        private Button btnAdd;


        public ViewHolder(View itemView) {
            super(itemView);
            nombreItem = itemView.findViewById(R.id.tvNombreProducto);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }


        public String getItem() {
            return nombreItem.getText().toString();
        }


        public void setItem(String item) {
            this.nombreItem.setText(item);
        }


    }
}