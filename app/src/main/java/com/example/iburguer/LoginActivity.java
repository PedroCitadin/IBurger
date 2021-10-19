package com.example.iburguer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iburguer.utils.InputUtil;
import com.example.iburguer.utils.Shared;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private EditText editLoginEmail, editLoginSenha;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private CheckBox ckbManter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ckbManter = findViewById(R.id.ckbManter);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        checkIfUserIsLoggedIn(currentUser);

        if(Shared.getBoolean(LoginActivity.this, Shared.KEY_MANTER_LOGADO, false)){
            Toast.makeText(LoginActivity.this, "Entrando na conta salva...",
                    Toast.LENGTH_SHORT).show();
            signIn(Shared.getString(LoginActivity.this, Shared.KEY_EMAIL_USUARIO, ""),
                    Shared.getString(LoginActivity.this, Shared.KEY_SENHA_USUARIO, ""));
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
                    checkIfUserIsLoggedIn(user);

                    // Substituir pelo nome do usuário gravado no banco, ligado ao email logado
                    Shared.put(LoginActivity.this, Shared.KEY_NOME_USUARIO, user.getEmail());

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    if(ckbManter.isChecked()){
                        Shared.put(LoginActivity.this, Shared.KEY_MANTER_LOGADO, true);
                        Shared.put(LoginActivity.this, Shared.KEY_EMAIL_USUARIO, email);
                        Shared.put(LoginActivity.this, Shared.KEY_SENHA_USUARIO, password);
                    }
                    startActivity(intent);
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Credenciais inválidas",
                            Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void checkIfUserIsLoggedIn(FirebaseUser currentUser){
        if(currentUser != null){
            currentUser.reload();
        }
    }
}