package com.example.iburguer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iburguer.dialog.NovoPedidoDialog;
import com.example.iburguer.dialog.ProdutoDialog;
import com.example.iburguer.entity.Hamburgueria;
import com.example.iburguer.entity.Items_pedido;
import com.example.iburguer.entity.Produto;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LojaActivity extends AppCompatActivity {
    private TextView txtNomeLoja;
    Hamburgueria hamburgueria;
    ListView lista_produtos;
    private ExtendedFloatingActionButton btnPedido;
    List<Produto> lista_p = new ArrayList<Produto>();
    DatabaseReference reference, rfProdutos;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseUser user = mAuth.getCurrentUser();
    ListaProdutoAdapter adapter;
    ArrayList<Items_pedido> carrinho = new ArrayList<Items_pedido>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loja);
        hamburgueria = new Hamburgueria();
        lista_produtos = findViewById(R.id.lista_produtos);
        txtNomeLoja = findViewById(R.id.txtNomeLoja);
        btnPedido = findViewById(R.id.btnPedido);
        if (verificaCarrinho(carrinho)){
            btnPedido.setVisibility(View.VISIBLE);
        }
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
        lista_produtos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto prod = (Produto) lista_produtos.getItemAtPosition(position);
                ProdutoDialog dialog = new ProdutoDialog(LojaActivity.this);
                carrinho = (ArrayList<Items_pedido>) dialog.show(carrinho, prod, btnPedido);
                System.out.println(verificaCarrinho(carrinho));
            }
        });

        btnPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NovoPedidoDialog dialog = new NovoPedidoDialog(LojaActivity.this);
                dialog.show(hamburgueria ,carrinho, btnPedido);
            }
        });
    }


    public boolean verificaCarrinho(List<Items_pedido> li){

        if (li.size()>0){
            return true;
        }else{
            return false;
        }
    }

}
