package com.example.iburguer.utils;

import android.widget.EditText;

import androidx.annotation.NonNull;

import java.util.List;

public class InputUtil {

    public static void clearFields(@NonNull List<EditText> fields){
        for(EditText field : fields){
            if (field != null) {
                field.setText(null);
            }
        }
    }
}
