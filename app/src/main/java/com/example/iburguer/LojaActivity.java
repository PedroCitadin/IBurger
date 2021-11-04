package com.example.iburguer;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iburguer.entity.Hamburgueria;
import com.example.iburguer.entity.Produto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LojaActivity extends AppCompatActivity {
    private TextView txtNomeLoja;
    Hamburgueria hamburgueria;
    ListView lista_produtos;
    List<Produto> lista_p = new ArrayList<Produto>();
    DatabaseReference reference, rfProdutos;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseUser user = mAuth.getCurrentUser();
    ListaProdutoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loja);
        hamburgueria = new Hamburgueria();
        lista_produtos = findViewById(R.id.lista_produtos);
        txtNomeLoja = findViewById(R.id.txtNomeLoja);
        hamburgueria.setId(getIntent().getStringExtra("idHamburgueria"));
        reference = FirebaseDatabase.getInstance().getReference("hamburguerias").child(hamburgueria.getId());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                  hamburgueria = snapshot.getValue(Hamburgueria.class);
                  txtNomeLoja.setText(hamburgueria.getNome());
                  hamburgueria.setId(snapshot.getKey());
                  rfProdutos = FirebaseDatabase.getInstance().getReference("produtos");
                  rfProdutos.orderByChild("hamburgueriaId").equalTo(hamburgueria.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot snapshot) {
                          for (DataSnapshot obj : snapshot.getChildren()){
                              Produto p = obj.getValue(Produto.class);
                              p.setId(obj.getKey());
                              lista_p.add(p);
                          }
                          adapter = new ListaProdutoAdapter(LojaActivity.this, lista_p);
                          lista_produtos.setAdapter(adapter);
                      }

                      @Override
                      public void onCancelled(@NonNull DatabaseError error) {

                      }
                  });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}
