package com.example.firebase.ejerciciolistacomprafirebase;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;

import com.example.firebase.ejerciciolistacomprafirebase.adapters.listaAdapter;
import com.example.firebase.ejerciciolistacomprafirebase.modelos.Producto;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebase.ejerciciolistacomprafirebase.databinding.ActivityMainBinding;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private FirebaseDatabase database;

    private ArrayList<Producto> productos;

    private listaAdapter adapter;
    private RecyclerView.LayoutManager lm;

    private NumberFormat numberFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance("https://ejerciciolistacomprafirebase-default-rtdb.europe-west1.firebasedatabase.app/");

        productos = new ArrayList<>();
        numberFormat = NumberFormat.getCurrencyInstance();

        int columnas;
        columnas = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 1 : 2;

        adapter = new listaAdapter(MainActivity.this, productos, R.layout.lista_model_view);
        lm = new GridLayoutManager(MainActivity.this, columnas);
        binding.contentMain.contenedor.setAdapter(adapter);
        binding.contentMain.contenedor.setLayoutManager(lm);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createToDo();
            }
        });
    }

    private androidx.appcompat.app.AlertDialog createToDo() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Crear Producto");
        builder.setCancelable(false);

        View alertView = LayoutInflater.from(MainActivity.this). inflate(R.layout.activity_create, null);
        TextView txtNombre = alertView.findViewById(R.id.txtNombreCreate);
        TextView txtCantidad = alertView.findViewById(R.id.txtCantidadCreate);
        TextView txtPrecio = alertView.findViewById(R.id.txtPrecioCreate);
        Button btnCrear = alertView.findViewById(R.id.btnAnyadirCreate);

        txtNombre.setEnabled(false);
        btnCrear.setVisibility(View.GONE);

        builder.setView(alertView);

        builder.setNegativeButton("CANCELAR", null);
        builder.setPositiveButton("CREAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!txtNombre.getText().toString().isEmpty() && !txtCantidad.getText().toString().isEmpty() && !txtPrecio.getText().toString().isEmpty()){
                    Producto producto = new Producto();
                    producto.setNombre(txtNombre.getText().toString());
                    producto.setCantidad(Integer.parseInt(txtCantidad.getText().toString()));
                    producto.setPrecio(Float.parseFloat(txtPrecio.getText().toString()));
                    productos.add(0, producto);
                    adapter.notifyItemInserted(0);
                }
                else {
                    Toast.makeText(MainActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return builder.create();
    }
}