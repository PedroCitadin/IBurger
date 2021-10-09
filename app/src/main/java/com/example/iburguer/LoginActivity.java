package com.example.iburguer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iburguer.utils.InputUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private EditText editLoginEmail, editLoginSenha;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){ // checa se o usuário atual está logado
            // alguma ação (reload)
            currentUser.reload();
        }

        Button btnCadastro = findViewById(R.id.btnCadastrar);
        btnCadastro.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, CadastroActivity.class)));

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(view -> {
            editLoginEmail = findViewById(R.id.editLoginEmail);
            editLoginSenha = findViewById(R.id.editLoginSenha);
            try {
                signIn(editLoginEmail.getText().toString(), editLoginSenha.getText().toString());
                Toast.makeText(LoginActivity.this, "Carregando...",
                        Toast.LENGTH_SHORT).show();
                InputUtil.clearFields(Arrays.asList(editLoginEmail, editLoginSenha));
            } catch(IllegalArgumentException exception) {
                Toast.makeText(LoginActivity.this, "Nenhum campo pode estar vazio!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {

                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();

                    // Passando o user para a outra tela: https://stackoverflow.com/questions/4999991/what-is-a-bundle-in-an-android-application
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("user", user.getDisplayName());
                    //updateUI(user);
                    startActivity(intent);
                } else {

                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Credenciais inválidas",
                            Toast.LENGTH_SHORT).show();
                    //updateUI(null);
                }
            });
    }
}