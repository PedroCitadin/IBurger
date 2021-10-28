package com.example.iburguer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.iburguer.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PrincipalFragment extends Fragment {

    private Bundle extras;
    private TextView textClienteNome;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseUser user = mAuth.getCurrentUser();
    DatabaseReference reference;

    public PrincipalFragment() {}

    public PrincipalFragment(Bundle extras) {
        this.extras = extras;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_principal, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        Handler handler = new Handler();
        new Thread() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        Button testeLoja = (Button) getActivity().findViewById(R.id.lojateste);
                        testeLoja.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(getActivity(), LojaActivity.class));
                            }
                        });
                    }
                });
            }
        }.start();
        textClienteNome = view.findViewById(R.id.textClienteNome);
        readDataFromCliente();
    }

    private void readDataFromCliente() {
        reference = FirebaseDatabase.getInstance().getReference("clientes");
        reference.child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if(task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        textClienteNome.setText(String.valueOf(dataSnapshot.child(Constants.NOME_CLIENTE).getValue()));
                    } else {
                        Toast.makeText(getContext(), "Falha na leitura de dados: usuário não existe!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Falha ao ler dados do banco!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}