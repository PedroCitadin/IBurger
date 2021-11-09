package com.example.iburguer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iburguer.dialog.EnderecoDialog;
import com.example.iburguer.entity.Endereco;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class EnderecosActivity extends AppCompatActivity {
    private FloatingActionButton btnAddEndereco;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase rootNode;
    private List<Endereco> listaEnd = new ArrayList<Endereco>();
    DatabaseReference reference;
    private ListView lista_enderecos;
    ArrayAdapter<Endereco> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enderecos);
        lista_enderecos = findViewById(R.id.lista_enderecos);
        btnAddEndereco = findViewById(R.id.btnAddEndereco);
        reference = FirebaseDatabase.getInstance().getReference("enderecos");
        reference.orderByChild("clienteId").equalTo(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaEnd.clear();
                for(DataSnapshot obj: dataSnapshot.getChildren()){
                    Endereco end = obj.getValue(Endereco.class);
                    end.setId(obj.getKey());
                    listaEnd.add(end);


                }
                adapter = new ArrayAdapter<Endereco>(EnderecosActivity.this, android.R.layout.simple_list_item_1, listaEnd);
                lista_enderecos.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        lista_enderecos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Endereco end = (Endereco) lista_enderecos.getItemAtPosition(position);
                EnderecoDialog dialog = new EnderecoDialog(EnderecosActivity.this);
                dialog.show(end, adapter, lista_enderecos, position);

                return true;
            }
        });

        btnAddEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnderecosActivity.this, NovoEnderecoActivity.class));
            }
        });
    }
}
