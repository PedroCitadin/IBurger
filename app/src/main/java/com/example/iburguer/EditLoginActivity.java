package com.example.iburguer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iburguer.entity.Cliente;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditLoginActivity extends AppCompatActivity {
    private EditText editarEmail, editarNovaSenha, editarConfirmarSenha;
    private Button btnEditSalvarLogin, btnEditCancelarLogin;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase rootNode;
    DatabaseReference reference,  clienteReference;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_login);
        editarEmail = findViewById(R.id.editarEmail);

        editarNovaSenha = findViewById(R.id.editarNovaSenha);
        editarConfirmarSenha = findViewById(R.id.editarConfirmarSenha);
        btnEditSalvarLogin = findViewById(R.id.btnEditSalvarLogin);
        btnEditCancelarLogin = findViewById(R.id.btnEditCancelarLogin);
        reference = FirebaseDatabase.getInstance().getReference("clientes").child(mAuth.getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cliente = snapshot.getValue(Cliente.class);
                editarEmail.setText(cliente.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        btnEditSalvarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editarNovaSenha.getText().toString().isEmpty()){
                    cliente.setEmail(editarEmail.getText().toString());
                    clienteReference = FirebaseDatabase.getInstance().getReference("clientes").child(mAuth.getCurrentUser().getUid());
                    clienteReference.updateChildren(Cliente.retornaMapEmail(cliente));
                    mAuth.getCurrentUser().updateEmail(editarEmail.getText().toString());
                    Toast.makeText(EditLoginActivity.this, "Dados alterados com sucesso!", Toast.LENGTH_SHORT).show();
                }else{
                    cliente.setEmail(editarEmail.getText().toString());
                    clienteReference = FirebaseDatabase.getInstance().getReference("clientes").child(mAuth.getCurrentUser().getUid());
                    clienteReference.updateChildren(Cliente.retornaMapEmail(cliente));
                    mAuth.getCurrentUser().updateEmail(editarEmail.getText().toString());
                    if (editarNovaSenha.getText().toString().equals(editarConfirmarSenha.getText().toString())){
                        mAuth.getCurrentUser().updatePassword(editarNovaSenha.getText().toString());
                    }else{
                        Toast.makeText(EditLoginActivity.this, "Senhas não correspondem", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(EditLoginActivity.this, "Dados alterados com sucesso!", Toast.LENGTH_SHORT).show();
                }
                finish();

            }
        });




        btnEditCancelarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
