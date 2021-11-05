package com.example.iburguer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.iburguer.entity.Endereco;
import com.example.iburguer.entity.Hamburgueria;
import com.example.iburguer.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PrincipalFragment extends Fragment {
    private String idEnderecoPadrao, enderecoPadrao;
    private String nomeCliente;
    private ListView lista_hamburguerias;
    private TextView textClienteNome, lbl_endereco_atual;
    private SharedPreferences sp;
    DatabaseReference reference, rfHamburguerias;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseUser user = mAuth.getCurrentUser();
    private List<Hamburgueria> lista_ham= new ArrayList<Hamburgueria>();
    ArrayAdapter<Hamburgueria> adapter;
    public PrincipalFragment() {}

    public static PrincipalFragment newInstance(Bundle clienteData) {
        PrincipalFragment fragment = new PrincipalFragment();
        fragment.setArguments(clienteData);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nomeCliente = getArguments().getString(Constants.NOME_CLIENTE);

        }
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        idEnderecoPadrao = sp.getString("padrao", "nulo");

    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_principal, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        lista_hamburguerias = view.findViewById(R.id.lista_hamburguerias);
        textClienteNome = view.findViewById(R.id.textClienteNome);
        lbl_endereco_atual = view.findViewById(R.id.lbl_endereco_atual);

        if(!idEnderecoPadrao.equals("nulo")){
            reference = FirebaseDatabase.getInstance().getReference("enderecos").child(idEnderecoPadrao);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Endereco end = snapshot.getValue(Endereco.class);
                    end.setId(snapshot.getKey());
                    enderecoPadrao = end.toString();



                    lbl_endereco_atual.setText(enderecoPadrao);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("The read failed: " + error.getCode());
                }
            });
        }else{
            lbl_endereco_atual.setText("vazio");
        }
        rfHamburguerias = FirebaseDatabase.getInstance().getReference("hamburguerias");
        rfHamburguerias.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot obj: snapshot.getChildren()){
                    Hamburgueria ham = obj.getValue(Hamburgueria.class);
                    ham.setId(obj.getKey());
                    lista_ham.add(ham);


                }
                adapter = new ArrayAdapter<Hamburgueria>(getActivity(), android.R.layout.simple_list_item_1, lista_ham);
                lista_hamburguerias.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        textClienteNome.setText(nomeCliente);

        lista_hamburguerias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Hamburgueria ham = (Hamburgueria) lista_hamburguerias.getItemAtPosition(position);
                Intent it = new Intent(getActivity(), LojaActivity.class);
                it.putExtra("idHamburgueria", ham.getId());
                startActivity(it);
            }
        });
    }



}