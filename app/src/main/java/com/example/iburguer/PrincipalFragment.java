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

public class PrincipalFragment extends Fragment {

    private String nomeCliente;
    private TextView textClienteNome;

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

        testeLoja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LojaActivity.class));
            }
        });
        textClienteNome.setText(nomeCliente);
    }

}