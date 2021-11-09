package com.example.iburguer;

import static java.util.Collections.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.iburguer.Model.Pedido;
import com.example.iburguer.entity.Hamburgueria;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PedidosFragment extends Fragment {
    private ListView lista_pedidos;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseUser user = mAuth.getCurrentUser();
    private List<com.example.iburguer.Model.Pedido> lista_ped= new ArrayList<Pedido>();
    ArrayAdapter<com.example.iburguer.Model.Pedido> adapter;
    DatabaseReference reference;


    public PedidosFragment() {}

    public static PedidosFragment newInstance(Bundle clienteData) {
        PedidosFragment fragment = new PedidosFragment();
        fragment.setArguments(clienteData);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // setar pedidos vindo do cliente
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedidos, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        lista_pedidos = view.findViewById(R.id.lista_pedidos);
        reference = FirebaseDatabase.getInstance().getReference("pedidos");
        reference.orderByChild("clienteId").equalTo(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lista_ped.clear();
                for (DataSnapshot obj : snapshot.getChildren()){
                    Pedido ped = obj.getValue(Pedido.class);
                    ped.setId(obj.getKey());
                    lista_ped.add(ped);
                }
                Collections.reverse(lista_ped);

                if (getActivity()!=null) {
                    adapter = new ArrayAdapter<Pedido>(getActivity(), android.R.layout.simple_list_item_1, lista_ped);
                    lista_pedidos.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    lista_pedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Pedido ped = (Pedido) lista_pedidos.getItemAtPosition(position);
            Intent intent = new Intent(getActivity(), PedidoActivity.class);
            intent.putExtra("idpedido", ped.getId());
            startActivity(intent);
        }
    });
    }
}