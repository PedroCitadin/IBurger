package com.example.iburguer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.iburguer.entity.Endereco;
import com.example.iburguer.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PrincipalFragment extends Fragment {
    private String idEnderecoPadrao, enderecoPadrao;
    private String nomeCliente;
    private TextView textClienteNome, lbl_endereco_atual;
    private SharedPreferences sp;
    DatabaseReference reference;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseUser user = mAuth.getCurrentUser();
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
            idEnderecoPadrao = getArguments().getString(Constants.ENDERECO_PADRAO);
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_principal, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        Button testeLoja = (Button) view.findViewById(R.id.lojateste);
        textClienteNome = view.findViewById(R.id.textClienteNome);
        lbl_endereco_atual = view.findViewById(R.id.lbl_endereco_atual);
        testeLoja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LojaActivity.class));
            }
        });
        reference = FirebaseDatabase.getInstance().getReference("enderecos/"+idEnderecoPadrao);
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
        
        textClienteNome.setText(nomeCliente);
    }

}