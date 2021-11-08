package com.example.iburguer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iburguer.entity.Cliente;
import com.example.iburguer.utils.InputUtil;
import com.example.iburguer.utils.MaskEditUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class CadastroActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";

    private Button btnCancelar, btnFinalizarCadastro;
    private EditText editNome, editSobrenome, editCPF, editEmail, editTelefone, editSenha, editConfirmarSenha;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnFinalizarCadastro = findViewById(R.id.btnFinalizarCadastro);

        editNome = findViewById(R.id.editNome);
        editSobrenome = findViewById(R.id.editSobrenome);
        editCPF = findViewById(R.id.editCPF);
        editCPF.addTextChangedListener(MaskEditUtil.mask(editCPF, MaskEditUtil.FORMAT_CPF));
        editEmail = findViewById(R.id.editEmail);
        editTelefone = findViewById(R.id.editTelefone);
        editTelefone.addTextChangedListener(MaskEditUtil.mask(editTelefone, MaskEditUtil.FORMAT_FONE));
        editSenha = findViewById(R.id.editSenha);
        editConfirmarSenha = findViewById(R.id.editConfirmarSenha);

        btnFinalizarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSenhasValidas(editSenha.getText().toString(), editConfirmarSenha.getText().toString())){
                    createNewCliente(
                            editNome.getText().toString(),
                            editSobrenome.getText().toString(),
                            editCPF.getText().toString(),
                            editEmail.getText().toString(),
                            editTelefone.getText().toString(),
                            editSenha.getText().toString());

                    InputUtil.clearFields(Arrays.asList(editNome, editCPF, editEmail, editTelefone, editSenha, editConfirmarSenha));
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void createNewCliente(String nome, String sobrenome, String cpf, String email, String telefone, String senha){
        Cliente cliente = new Cliente(nome, sobrenome, cpf, email, telefone);
        createAccount(cliente, senha);
    }

    private void createAccount(Cliente cliente, String senha){
        mAuth.createUserWithEmailAndPassword(cliente.getEmail(), senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(CadastroActivity.this, "Conta criada com sucesso!",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            postCliente(cliente, user.getUid());
                            startActivity(new Intent(CadastroActivity.this, MainActivity.class));
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CadastroActivity.this, "Authentication failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void postCliente(Cliente cliente, String userId){
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("clientes");
        reference.child(userId).setValue(cliente);
    }

    private boolean isSenhasValidas(String senha, String confirmarSenha){
        if(senha.isEmpty() || confirmarSenha.isEmpty()){
            Toast.makeText(CadastroActivity.this, "As duas senhas devem ser informadas", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!senha.equals(confirmarSenha)){
            Toast.makeText(CadastroActivity.this, "As senhas informadas são diferentes!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}