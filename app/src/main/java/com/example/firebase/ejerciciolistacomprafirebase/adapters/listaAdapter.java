package com.example.firebase.ejerciciolistacomprafirebase.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.ejerciciolistacomprafirebase.R;
import com.example.firebase.ejerciciolistacomprafirebase.modelos.Producto;

import java.text.NumberFormat;
import java.util.ArrayList;

public class listaAdapter extends RecyclerView.Adapter<listaAdapter.ListaVH>{

    private Context context;
    private ArrayList<Producto> objects;
    private int resources;

    private NumberFormat numberFormat;

    public listaAdapter(Context context, ArrayList<Producto> objects, int cardLayout){
        this.context = context;
        this.objects = objects;
        this.resources = cardLayout;
        numberFormat = NumberFormat.getCurrencyInstance();
    }

    @NonNull
    @Override
    public ListaVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ProductoView = LayoutInflater.from(context).inflate(resources, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        ProductoView.setLayoutParams(layoutParams);
        return new ListaVH(ProductoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaVH holder, int position) {
        Producto p = objects.get(position);
        holder.lblNombre.setText(p.getNombre());
        holder.lblCantidad.setText(String.valueOf(p.getCantidad()));
        holder.lblPrecio.setText(numberFormat.format(p.getPrecio()));

        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarProducto(p, holder.getAdapterPosition()).show();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editToDo(p, holder.getAdapterPosition()).show();
            }
        });
    }
    // Retornar la cantidad de elementos que hay que instanciar
    @Override
    public int getItemCount() {
        return objects.size();
    }

    private androidx.appcompat.app.AlertDialog editToDo(Producto producto, int position) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setTitle("Editar Producto");
        builder.setCancelable(false);

        View alertView = LayoutInflater.from(context). inflate(R.layout.activity_create, null);
        TextView txtNombre = alertView.findViewById(R.id.txtNombreCreate);
        TextView txtCantidad = alertView.findViewById(R.id.txtCantidadCreate);
        TextView txtPrecio = alertView.findViewById(R.id.txtPrecioCreate);
        Button btnCrear = alertView.findViewById(R.id.btnAnyadirCreate);

        txtNombre.setEnabled(false);
        btnCrear.setVisibility(View.GONE);

        builder.setView(alertView);

        txtNombre.setText(producto.getNombre());
        txtCantidad.setText(String.valueOf(producto.getCantidad()));
        txtPrecio.setText(String.valueOf(producto.getPrecio()));

        builder.setNegativeButton("CANCELAR", null);
        builder.setPositiveButton("EDITAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!txtNombre.getText().toString().isEmpty() && !txtCantidad.getText().toString().isEmpty() && !txtPrecio.getText().toString().isEmpty()){
                    producto.setNombre(txtNombre.getText().toString());
                    producto.setCantidad(Integer.parseInt(txtCantidad.getText().toString()));
                    producto.setPrecio(Float.parseFloat(txtPrecio.getText().toString()));
                    notifyItemChanged(position);
                }
                else {
                    Toast.makeText(context, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return builder.create();
    }

    private android.app.AlertDialog eliminarProducto(Producto producto, int position){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

        builder.setCancelable(false);
        TextView mensaje = new TextView(context);
        mensaje.setText("¿ESTAS SEGURO QUE QUIERES ELIMINAR?");
        mensaje.setTextSize(20);
        mensaje.setTextColor(Color.RED);
        mensaje.setPadding(50,100,50,100);
        builder.setView(mensaje);

        builder.setNegativeButton("NO", null);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                objects.remove(producto);
                notifyItemRemoved(position);
            }
        });
        return builder.create();
    }

    public class ListaVH extends RecyclerView.ViewHolder{

        TextView lblNombre, lblCantidad, lblPrecio;
        ImageButton btnEliminar;
        public ListaVH(@NonNull View itemView) {
            super(itemView);
            lblNombre = itemView.findViewById(R.id.lblNombreViewModel);
            lblCantidad = itemView.findViewById(R.id.lblCantidadViewModel);
            lblPrecio = itemView.findViewById(R.id.lblPrecioViewModel);
            btnEliminar = itemView.findViewById(R.id.btnEliminarViewModel);
        }
    }
}
