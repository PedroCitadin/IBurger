package com.example.iburguer;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iburguer.Model.Items_pedido;
import com.example.iburguer.Model.Pedido;
import com.example.iburguer.entity.Endereco;
import com.example.iburguer.entity.Hamburgueria;
import com.example.iburguer.entity.Produto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PedidoActivity extends AppCompatActivity {
    private TextView txtValorTotalPedido, txtEnderecoEntregaPedido, txtFormaPagamento, txtSituacaoPedido, lblNomeHamburgueria;
    private Button btnConfirmarRecebimentoPedido;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase rootNode;
    private ImageButton btnVoltarPedido;
    DatabaseReference reference,  enderecoReference, produtoReference, hamburgueriaReference;
    private ListView pedidoListaProdutos;
    ListaProdutoPedidoAdapter adapter;
    private Pedido ped;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        txtValorTotalPedido = findViewById(R.id.txtValorTotalPedido);
        txtEnderecoEntregaPedido = findViewById(R.id.txtEnderecoEntregaPedido);
        txtFormaPagamento = findViewById(R.id.txtFormaPagamento);
        txtSituacaoPedido = findViewById(R.id.txtSituacaoPedido);
        pedidoListaProdutos = findViewById(R.id.pedidoListaProdutos);
        btnVoltarPedido = findViewById(R.id.btnVoltarPedido);
        lblNomeHamburgueria = findViewById(R.id.lblNomeHamburgueria);
        btnConfirmarRecebimentoPedido = findViewById(R.id.btnConfirmarRecebimentoPedido);
        reference = FirebaseDatabase.getInstance().getReference("pedidos").child(getIntent().getStringExtra("idpedido"));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ped = snapshot.getValue(Pedido.class);
                ped.setId(snapshot.getKey());

                txtValorTotalPedido.setText("R$ "+ped.getValorTotal());
                txtFormaPagamento.setText(ped.getPagamento());
                txtSituacaoPedido.setText(com.example.iburguer.entity.Pedido.converteSituacao(ped));
                if (ped.getSituacao().equalsIgnoreCase("ENVIADO")){
                    btnConfirmarRecebimentoPedido.setVisibility(View.VISIBLE);
                }
                enderecoReference = FirebaseDatabase.getInstance().getReference("enderecos").child(ped.getEnderecoId());
                enderecoReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot esnapshot) {
                        Endereco end = esnapshot.getValue(Endereco.class);
                        end.setId(esnapshot.getKey());

                        txtEnderecoEntregaPedido.setText(end.toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                List<com.example.iburguer.entity.Items_pedido> produtos = new ArrayList<com.example.iburguer.entity.Items_pedido>();
                for (Items_pedido it: ped.getItensPedido()){
                    produtoReference = FirebaseDatabase.getInstance().getReference("produtos").child(it.getProdutoId());
                    produtoReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Produto prod = snapshot.getValue(Produto.class);
                            prod.setId(snapshot.getKey());
                            com.example.iburguer.entity.Items_pedido item = com.example.iburguer.entity.Items_pedido.modelToStructure(it);
                            item.setProduto(prod);
                            produtos.add(item);
                            adapter = new ListaProdutoPedidoAdapter(PedidoActivity.this, produtos);
                            pedidoListaProdutos.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                hamburgueriaReference = FirebaseDatabase.getInstance().getReference("hamburguerias").child(ped.getHamburgueriaId());
                hamburgueriaReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot hsnapshot) {
                        Hamburgueria ham = hsnapshot.getValue(Hamburgueria.class);
                        ham.setId(hsnapshot.getKey());
                        lblNomeHamburgueria.setText(ham.getNome());
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

        btnVoltarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnConfirmarRecebimentoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ped.setSituacao("FINALIZADO");
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("pedidos").child(ped.getId());
                reference.updateChildren(Pedido.retornaMap(ped));
                finish();
            }
        });

    }



}
