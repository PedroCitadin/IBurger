package com.example.iburguer;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class NovoEnderecoActivity extends AppCompatActivity {
    private EditText editRua, editBairro, editNumero, editCep, editCidade, editComplemento;
    private Spinner spnEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_endereco);
        editRua = findViewById(R.id.editRua);
        editBairro = findViewById(R.id.editBairro);
        editNumero = findViewById(R.id.editNumero);
        editCep = findViewById(R.id.editCep);
        editCidade = findViewById(R.id.editCidade);
        editComplemento = findViewById(R.id.editComplemento);
        spnEstado = findViewById(R.id.spnEstado);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.estados, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEstado.setAdapter(adapter);

    }
}
