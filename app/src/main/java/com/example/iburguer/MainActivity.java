package com.example.iburguer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.iburguer.entity.Endereco;
import com.example.iburguer.entity.EnderecoCliente;
import com.example.iburguer.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseUser user = mAuth.getCurrentUser();
    DatabaseReference reference;
    private SharedPreferences sp;
    String enderecoPadrao;
    String nomeCliente;
    Bundle clienteData = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        BottomNavigationView menu_navegacao = findViewById(R.id.menu_navegacao);
        reference = FirebaseDatabase.getInstance().getReference("clientes");
        sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        reference.child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if(task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();


                        nomeCliente = String.valueOf(dataSnapshot.child(Constants.NOME_CLIENTE).getValue());

                        clienteData.putString(Constants.NOME_CLIENTE, nomeCliente);
                        clienteData.putString(Constants.ENDERECO_PADRAO, sp.getString("padrao", "nulo"));
                        menu_navegacao.setOnNavigationItemSelectedListener(bottomNavMethod);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new PrincipalFragment().newInstance(clienteData)).commit();
                    } else {
                        Toast.makeText(MainActivity.this, "Falha na leitura de dados: usuário não existe!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Falha ao ler dados do banco!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment frag = null;
            switch (item.getItemId()){
                case R.id.page_home:
                    frag = new PrincipalFragment().newInstance(clienteData);
                break;
                case R.id.page_pedidos:
                    frag = new PedidosFragment().newInstance(clienteData);
                    break;
                case R.id.page_conta:
                    frag = new ContaFragment().newInstance(clienteData);
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).commit();
            return true;
        }
    };

    private void storeUserData(Bundle extras){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("NOME_USUARIO", extras.getString("user"));
        editor.apply();
    }
}
