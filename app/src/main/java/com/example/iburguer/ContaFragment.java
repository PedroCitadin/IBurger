package com.example.iburguer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

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
    private Button btnSairConta;

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
        return inflater.inflate(R.layout.fragment_conta, container, false);
    }
}