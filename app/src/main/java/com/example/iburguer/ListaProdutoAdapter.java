package com.example.iburguer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.iburguer.entity.Produto;

import java.util.List;

public class ListaProdutoAdapter extends BaseAdapter {
    private Context contexto;
    private List<Produto> lista;

    public ListaProdutoAdapter(Context contexto, List<Produto> lista) {
        this.contexto = contexto;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(contexto, R.layout.item_produto, null);
        TextView lblNomeEntretenimento = (TextView) v.findViewById(R.id.lblNomeProduto);
        TextView lblValorEntretenimento = (TextView) v.findViewById(R.id.lblValorProduto);
        lblNomeEntretenimento.setText(lista.get(position).getNome());
        lblValorEntretenimento.setText("R$ "+lista.get(position).getValor());
        v.setTag(lista.get(position).getId());
        return v;
    }
}
