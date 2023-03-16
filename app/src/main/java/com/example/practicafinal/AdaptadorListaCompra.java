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

public class AdaptadorListaCompra extends RecyclerView.Adapter<AdaptadorListaCompra.ViewHolder> {

    private List<String> aListaProductos;

    private List<String> aListaCompra;

    private List<Integer> acantidadProductos;
    private LayoutInflater aInflater;

    public AdaptadorListaCompra(Context context, List<String> lista) {
        this.aListaProductos = lista;
        this.aInflater = LayoutInflater.from(context);
    }

    public AdaptadorListaCompra(Context context, List<String> listaProducto, List<String> listaCompra, List<Integer> cantidadProducto) {
        this.aListaProductos = listaProducto;
        this.aListaCompra = listaCompra;
        this.acantidadProductos = cantidadProducto;
        this.aInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AdaptadorListaCompra.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = aInflater.inflate(R.layout.rv_lista_compra, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorListaCompra.ViewHolder holder, int position) {
        String aux = aListaCompra.get(position);
        holder.setNombreItem(aux);
        aux = String.valueOf(acantidadProductos.get(position));
        holder.setCantidad(aux);

    }

    @Override
    public int getItemCount() {
        return aListaCompra.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombreItem;
        private TextView cantidad;
        private Button btnMas;
        private Button btnMenos;

        public ViewHolder(View itemView) {
            super(itemView);
            nombreItem = itemView.findViewById(R.id.tvNombreProducto);
            cantidad = itemView.findViewById(R.id.tvCantidad);
            btnMas = itemView.findViewById(R.id.botonSum);
            btnMenos = itemView.findViewById(R.id.botonRes);
            btnMas.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    sumar();
                }
            });

            btnMenos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    restar();
                }
            });
        }

        public String getNombreItem() {
            return nombreItem.getText().toString();
        }

        public void setNombreItem(String nombreItem) {
            this.nombreItem.setText(nombreItem);
        }

        public String getCantidad() {
            return cantidad.getText().toString();
        }

        public void setCantidad(String cantidad) {
            this.cantidad.setText(cantidad);
        }

        public void sumar() {
            int cantidadProducto = ListaCompra.sumar(nombreItem);
            setCantidad(String.valueOf(cantidadProducto));
        }
        public void restar() {
            int cantidadProducto = ListaCompra.restar(nombreItem);
            setCantidad(String.valueOf(cantidadProducto));
        }

    }

}

