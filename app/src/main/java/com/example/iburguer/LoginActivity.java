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
    private SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        ckbManter = findViewById(R.id.ckbManter);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        checkIfUserIsLoggedIn(currentUser);
        if(sp.getBoolean("manter", false)){

            signIn(sp.getString("user", ""), sp.getString("password", ""));

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
                    Shared.put(LoginActivity.this, Shared.KEY_NOME_USUARIO, user.getEmail());
                    // Passando o user para a outra tela: https://stackoverflow.com/questions/4999991/what-is-a-bundle-in-an-android-application
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("user", " " + user.getEmail());
                    //updateUI(user);
                    if(ckbManter.isChecked()){
                        editor = sp.edit();
                        editor.putBoolean("manter", true);
                        editor.putString("user", user.getEmail());
                        editor.putString("password", password);
                        editor.apply();
                    }
                    startActivity(intent);
                } else {

                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Credenciais inválidas",
                            Toast.LENGTH_SHORT).show();
                    //updateUI(null);
                }
            });
    }

    private void checkIfUserIsLoggedIn(FirebaseUser currentUser){
        if(currentUser != null){
            currentUser.reload();
        }
    }
}