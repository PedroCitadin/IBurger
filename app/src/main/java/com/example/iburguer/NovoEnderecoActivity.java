package com.example.iburguer;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iburguer.entity.Endereco;
import com.example.iburguer.entity.EnderecoCliente;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NovoEnderecoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText editRua, editBairro, editNumero, editCep, editCidade, editComplemento;
    private Button btnSalvarEndereco, btnCancelar;
    private Spinner spnEstado;
    private String rua, bairro, numero, cep, cidade, estado, complemento;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase rootNode;
    DatabaseReference enderecoReference, clienteReference;

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
        btnSalvarEndereco = findViewById(R.id.btnSalvarEndereco);
        btnCancelar = findViewById(R.id.btnCancelar);
        spnEstado = findViewById(R.id.spnEstado);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.estados, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEstado.setAdapter(adapter);
        spnEstado.setOnItemSelectedListener(this);

        btnSalvarEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rua = editRua.getText().toString();
                bairro = editBairro.getText().toString();
                numero = editNumero.getText().toString();
                cep = editCep.getText().toString();
                cidade = editCidade.getText().toString();
                complemento = editComplemento.getText().toString();

                Endereco endereco = new Endereco(rua, numero, bairro, cep, cidade, estado, complemento, mAuth.getCurrentUser().getUid());
                postEndereco(endereco);
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
        this.estado = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void postEndereco(Endereco endereco){
        String enderecoId;
        EnderecoCliente enderecoCliente;

        rootNode = FirebaseDatabase.getInstance();
        enderecoReference = rootNode.getReference("enderecos").push();
        clienteReference = rootNode.getReference("clientes").child(endereco.getClienteId()).child("enderecos").push();

        enderecoId = enderecoReference.getKey();
        enderecoCliente = new EnderecoCliente(endereco.getRua(), endereco.getCidade(), endereco.getNumero(), enderecoId);

        enderecoReference.setValue(endereco);
        clienteReference.setValue(enderecoCliente);
    }

}