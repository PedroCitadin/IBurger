package com.example.iburguer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.iburguer.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;

public class ContaFragment extends Fragment {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private String nomeCliente;
    private Button btnSairConta;
    private TextView textClienteNome;

    public ContaFragment() {
    }

    public static ContaFragment newInstance(Bundle clienteData) {
        ContaFragment fragment = new ContaFragment();
        fragment.setArguments(clienteData);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nomeCliente = getArguments().getString(Constants.NOME_CLIENTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conta, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        textClienteNome = view.findViewById(R.id.textClienteNome);
        btnSairConta = view.findViewById(R.id.btnSairConta);

        textClienteNome.setText(nomeCliente);
        btnSairConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }
}