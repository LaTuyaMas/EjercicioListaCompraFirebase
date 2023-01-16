package com.example.firebase.ejerciciolistacomprafirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebase.ejerciciolistacomprafirebase.databinding.ActivityCreateBinding;
import com.example.firebase.ejerciciolistacomprafirebase.modelos.Producto;

public class CreateActivity extends AppCompatActivity {

    private ActivityCreateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAnyadirCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Producto producto;
                if ( (producto =  productoEstaBien()) != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("PRODUCTO", producto);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    Toast.makeText(CreateActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Producto productoEstaBien(){

        if (binding.txtNombreCreate.getText().toString().isEmpty()
                || binding.txtCantidadCreate.getText().toString().isEmpty()
                || binding.txtPrecioCreate.getText().toString().isEmpty())
            return null;

        String nombre = binding.txtNombreCreate.getText().toString();
        int cantidad = Integer.parseInt(binding.txtCantidadCreate.getText().toString());
        float precio = Float.parseFloat(binding.txtPrecioCreate.getText().toString());
        return new Producto(nombre, cantidad, precio);
    }
}