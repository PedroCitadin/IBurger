package com.example.iburguer.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.iburguer.Model.Pedido;
import com.example.iburguer.R;
import com.example.iburguer.entity.Cliente;
import com.example.iburguer.entity.Endereco;
import com.example.iburguer.entity.Hamburgueria;
import com.example.iburguer.entity.Items_pedido;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NovoPedidoDialog {
    private final Activity activity;
    private AlertDialog dialog;
    private SharedPreferences sp;
    SharedPreferences.Editor editor;
    private ListView pedidoListaProdutos;
    private TextView txtValorTotal, txtEnderecoEntrega;
    private RadioGroup radioOpPagamento;
    private Button btnConfirmarPedido;
    private ImageButton btnVoltarPedido;
    private FirebaseDatabase rootNode;
    Hamburgueria hamburgueria;

    Cliente cliente;
    Endereco endereco;
    List<Items_pedido> carrinho;
    DatabaseReference reference, pedidoReference;
    private String idEnderecoPadrao, enderecoPadrao;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseUser user = mAuth.getCurrentUser();

    private Float valorTotal;
    public NovoPedidoDialog(Activity activity) {
        this.activity = activity;
        sp = PreferenceManager.getDefaultSharedPreferences(activity);
        idEnderecoPadrao = sp.getString("padrao", "nulo");

    }

    public void show(Hamburgueria ham, List<Items_pedido> carr, ExtendedFloatingActionButton btnPedido){

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view =  inflater.inflate(R.layout.dialog_novo_pedido, null);
        valorTotal = Float.parseFloat("0");
        btnVoltarPedido = view.findViewById(R.id.btnVoltarPedido);
        pedidoListaProdutos = view.findViewById(R.id.pedidoListaProdutos);
        txtValorTotal = view.findViewById(R.id.txtValorTotal);
        txtEnderecoEntrega = view.findViewById(R.id.txtEnderecoEntrega);
        radioOpPagamento = view.findViewById(R.id.radioOpPagamento);
        btnConfirmarPedido = view.findViewById(R.id.btnConfirmarPedido);
        hamburgueria = ham;
        carrinho = carr;
        ArrayAdapter<Items_pedido> adapter = new ArrayAdapter<Items_pedido>(activity, android.R.layout.simple_list_item_1, carrinho);
        pedidoListaProdutos.setAdapter(adapter);
        for (Items_pedido it:carrinho){
            valorTotal += it.getProduto().getValor()*it.getQuantidade();
        }
        if(!idEnderecoPadrao.equals("nulo")){
            reference = FirebaseDatabase.getInstance().getReference("enderecos").child(idEnderecoPadrao);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    endereco = snapshot.getValue(Endereco.class);
                    endereco.setId(snapshot.getKey());
                    enderecoPadrao = endereco.toString();



                    txtEnderecoEntrega.setText(enderecoPadrao);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("The read failed: " + error.getCode());
                }
            });
        }else{
            txtEnderecoEntrega.setText("vazio");
        }
        txtValorTotal.setText("R$ "+valorTotal);
        btnVoltarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnConfirmarPedido.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                List<com.example.iburguer.Model.Items_pedido> lista = new ArrayList<com.example.iburguer.Model.Items_pedido>();
                for (Items_pedido it: carrinho){
                    com.example.iburguer.Model.Items_pedido it2 = new com.example.iburguer.Model.Items_pedido(it.getObservacao(), it.getProduto().getId(), it.getQuantidade());
                    lista.add(it2);
                }
                String pagamento;
                if (radioOpPagamento.getCheckedRadioButtonId()==1){
                    pagamento = "DINHEIRO";
                }else{
                    pagamento = "CARTÃO";
                }
                Pedido pedido = new Pedido(mAuth.getCurrentUser().getUid(), endereco.getId(), hamburgueria.getId(), "AGUARDANDO APROVACAO", pagamento, valorTotal, lista);
                postPedido(pedido);
                carr.clear();
                btnPedido.setVisibility(View.INVISIBLE);
                dialog.cancel();
            }
        });


        builder.setView(view);
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    private void postPedido(Pedido pedido){
        rootNode = FirebaseDatabase.getInstance();
        pedidoReference = rootNode.getReference("pedidos").push();
        pedidoReference.setValue(pedido);
    }
}
