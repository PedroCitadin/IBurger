package com.example.iburguer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iburguer.utils.InputUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class CadastroActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";

    private Button btnCancelar, btnFinalizarCadastro;
    private EditText editNomeCompleto, editCPF, editEmail, editTelefone, editSenha, editConfirmarSenha;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnFinalizarCadastro = findViewById(R.id.btnFinalizarCadastro);

        editNomeCompleto = findViewById(R.id.editNomeCompleto);
        editCPF = findViewById(R.id.editCPF);
        editEmail = findViewById(R.id.editEmail);
        editTelefone = findViewById(R.id.editTelefone);
        editSenha = findViewById(R.id.editSenha);
        editConfirmarSenha = findViewById(R.id.editConfirmarSenha);

        btnFinalizarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSenhasValidas(editSenha.getText().toString(), editConfirmarSenha.getText().toString())){
                    createAccount(editEmail.getText().toString(), editSenha.getText().toString());
                    InputUtil.clearFields(Arrays.asList(editNomeCompleto, editCPF, editEmail, editTelefone, editSenha, editConfirmarSenha));
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

    private boolean isSenhasValidas(String senha, String confirmarSenha){
        if(senha == null || confirmarSenha == null){
            Toast.makeText(CadastroActivity.this, "As duas senhas devem ser informadas", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(senha == "" || confirmarSenha == ""){
            Toast.makeText(CadastroActivity.this, "As duas senhas devem ser informadas", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(senha != confirmarSenha){
            Toast.makeText(CadastroActivity.this, "As senhas informadas são diferentes!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void createAccount(String email, String senha){
        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            //updateUI(user) ATUALIZAR TELA;
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CadastroActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            //updateUI(null) ATUALIZAR TELA;
                        }
                    }
                });
    }
}