package com.example.iburguer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iburguer.entity.Cliente;
import com.example.iburguer.utils.MaskEditUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PessoaisActivity extends AppCompatActivity {
    private EditText editarNome, editarSobrenome, editarCPF, editarTelefone;
    private Button btnEditSalvarEndereco, btnEditCancelar;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase rootNode;
    DatabaseReference reference,  clienteReference;
    private Cliente cliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoais);
        editarNome = findViewById(R.id.editarNome);
        editarSobrenome = findViewById(R.id.editarSobrenome);
        editarCPF = findViewById(R.id.editarCPF);
        editarCPF.addTextChangedListener(MaskEditUtil.mask(editarCPF, MaskEditUtil.FORMAT_CPF));

        editarTelefone = findViewById(R.id.editarTelefone);
        editarTelefone.addTextChangedListener(MaskEditUtil.mask(editarTelefone, MaskEditUtil.FORMAT_FONE));
        btnEditSalvarEndereco = findViewById(R.id.btnEditSalvarEndereco);
        btnEditCancelar = findViewById(R.id.btnEditCancelar);

        reference = FirebaseDatabase.getInstance().getReference("clientes").child(mAuth.getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cliente = snapshot.getValue(Cliente.class);

                editarNome.setText(cliente.getNome());
                editarSobrenome.setText(cliente.getSobrenome());
                editarCPF.setText(cliente.getCpf());
                editarTelefone.setText(cliente.getCpf());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnEditSalvarEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cliente.setNome(editarNome.getText().toString());
                cliente.setSobrenome(editarSobrenome.getText().toString());
                cliente.setCpf(editarCPF.getText().toString());
                cliente.setTelefone(editarTelefone.getText().toString());


                rootNode = FirebaseDatabase.getInstance();
                clienteReference = rootNode.getReference("clientes").child(mAuth.getCurrentUser().getUid());
                clienteReference.updateChildren(Cliente.retornaMap(cliente));

            }
        });
        btnEditCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
