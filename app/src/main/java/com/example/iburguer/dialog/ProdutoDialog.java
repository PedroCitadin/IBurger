package com.example.iburguer.dialog;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.iburguer.R;
import com.example.iburguer.entity.Endereco;
import com.example.iburguer.entity.Items_pedido;
import com.example.iburguer.entity.Produto;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDialog {
    private final Activity activity;
    private AlertDialog dialog;

    private Button btnProdutoVoltar, btnAddProduto;
    private TextView txtNomeProduto, txtDescProduto;
    private EditText editObs, editQuantidade;
    private SharedPreferences sp;
    SharedPreferences.Editor editor;


    public ProdutoDialog(Activity activity) {
        this.activity = activity;
        sp = PreferenceManager.getDefaultSharedPreferences(activity);

    }
    public List<Items_pedido> show(List<Items_pedido> lista, Produto prod, ExtendedFloatingActionButton btnPedido){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view =  inflater.inflate(R.layout.dialog_produto, null);
        btnProdutoVoltar = view.findViewById(R.id.btnProdutoVoltar);

        btnAddProduto = view.findViewById(R.id.btnAddProduto);
        txtNomeProduto = view.findViewById(R.id.txtNomeProduto);
        txtDescProduto = view.findViewById(R.id.txtDescProduto);
        editObs = view.findViewById(R.id.editObs);
        editQuantidade = view.findViewById(R.id.editQuantidade);
        txtNomeProduto.setText(prod.getNome());
        txtDescProduto.setText(prod.getDescricao());
        btnProdutoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnAddProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editObs.getText().toString().equals("")){
                    Items_pedido ip = new Items_pedido();
                    ip.setProduto(prod);
                    ip.setObservacao("");
                    ip.setQuantidade(Integer.parseInt(editQuantidade.getText().toString()));
                    lista.add(ip);
                }else{
                    Items_pedido ip = new Items_pedido();
                    ip.setProduto(prod);
                    ip.setObservacao(editObs.getText().toString());
                    ip.setQuantidade(Integer.parseInt(editQuantidade.getText().toString()));
                    lista.add(ip);
                }
                btnPedido.setText("Efetuar Pedido ("+lista.size()+")");
                btnPedido.setVisibility(View.VISIBLE);
                dialog.cancel();

            }
        });

        builder.setView(view);
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
        return lista;

    }


}
