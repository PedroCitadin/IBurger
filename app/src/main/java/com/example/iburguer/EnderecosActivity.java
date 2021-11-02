package com.example.iburguer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EnderecosActivity extends AppCompatActivity {
    private FloatingActionButton btnAddEndereco;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enderecos);
        btnAddEndereco = findViewById(R.id.btnAddEndereco);

        btnAddEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnderecosActivity.this, NovoEnderecoActivity.class));
            }
        });
    }
}
