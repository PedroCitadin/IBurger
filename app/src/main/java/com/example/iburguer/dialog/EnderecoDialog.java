package com.example.iburguer.dialog;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;




import com.example.iburguer.R;
import com.example.iburguer.entity.Endereco;

import java.util.List;

public class EnderecoDialog {
    private final Activity activity;
    private AlertDialog dialog;
    private Button btnConfirmarExclusao, btnDefinePadrao;
    private SharedPreferences sp;
    SharedPreferences.Editor editor;


    public EnderecoDialog(Activity activity) {
        this.activity = activity;
        sp = PreferenceManager.getDefaultSharedPreferences(activity);

    }
    public void show(Endereco end, ArrayAdapter<Endereco> adapter, ListView lista){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view =  inflater.inflate(R.layout.dialog_endereco, null);
        builder.setView(view);
        builder.setCancelable(true);
        btnDefinePadrao = view.findViewById(R.id.btnDefinePadrao);
        btnConfirmarExclusao = view.findViewById(R.id.btnConfirmarExclusao);
        btnDefinePadrao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = sp.edit();
                editor.putString("padrao", end.getId());
                editor.apply();

                dialog.cancel();
            }
        });
        btnConfirmarExclusao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                lista.setAdapter(adapter);
                dialog.cancel();

            }
        });
        dialog = builder.create();
        dialog.show();
        ;
    }


}
