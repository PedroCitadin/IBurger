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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContaFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseUser user = mAuth.getCurrentUser();
    DatabaseReference reference;
    private Button btnSairConta;
    private TextView lblNomePessoa;
    public ContaFragment() {
    }

    public static ContaFragment newInstance(String param1, String param2) {
        ContaFragment fragment = new ContaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Handler handler = new Handler();

        new Thread() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        lblNomePessoa = (TextView) getActivity().findViewById(R.id.lblNomePessoa);

                        btnSairConta = (Button) getActivity().findViewById(R.id.btnSairConta);
                        btnSairConta.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mAuth.signOut();
                                //Shared.put(getActivity().getApplicationContext(), Shared.KEY_MANTER_LOGADO, false);
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                            }
                        });
                    }
                });
            }
        }.start();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        readDataFromCliente();
        return inflater.inflate(R.layout.fragment_conta, container, false);

    }

    private void readDataFromCliente() {
        reference = FirebaseDatabase.getInstance().getReference("clientes");
        reference.child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if(task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        lblNomePessoa.setText(String.valueOf(dataSnapshot.child(Constants.NOME_CLIENTE).getValue()));
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