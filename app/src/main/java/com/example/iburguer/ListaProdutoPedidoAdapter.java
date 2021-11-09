package com.example.iburguer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.iburguer.entity.Items_pedido;
import com.example.iburguer.entity.Produto;

import java.util.List;

public class ListaProdutoPedidoAdapter extends BaseAdapter {
    private Context contexto;
    private List<Items_pedido> lista;

    public ListaProdutoPedidoAdapter(Context contexto, List<Items_pedido> lista) {
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
        View v = View.inflate(contexto, R.layout.item_produtocomobs, null);
        TextView lblNomeProdutoPedido = (TextView) v.findViewById(R.id.lblNomeProdutoPedido);
        TextView lblObsProdutoPedido = (TextView) v.findViewById(R.id.lblObsProdutoPedido);
        TextView lblQuantidadeProdutoPedido = (TextView) v.findViewById(R.id.lblQuantidadeProdutoPedido);
        lblNomeProdutoPedido.setText(lista.get(position).getProduto().getNome());
        lblObsProdutoPedido.setText("OBS: "+lista.get(position).getObservacao());
        lblQuantidadeProdutoPedido.setText(""+lista.get(position).getQuantidade());
        v.setTag(lista.get(position).getProduto().getId());
        return v;
    }
}
