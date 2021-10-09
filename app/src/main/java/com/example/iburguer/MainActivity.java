package com.example.iburguer;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //Bundle extras = getIntent().getExtras();
        //TextView textUserName = findViewById(R.id.textUserName);
        //textUserName.setText(extras.getString("user"));

        BottomNavigationView menu_navegacao = findViewById(R.id.menu_navegacao);
        menu_navegacao.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new PrincipalFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment frag = null;
            switch (item.getItemId()){
                case R.id.page_home:
                    frag = new PrincipalFragment();
                break;
                case R.id.page_pedidos:
                    frag = new PedidosFragment();
                    break;
                case R.id.page_conta:
                    frag = new ContaFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).commit();
            return true;
        }
    };
}
