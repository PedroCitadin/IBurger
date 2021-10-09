package com.example.iburguer.utils;

import android.widget.EditText;

import java.util.List;

public class InputUtil {

    public static void clearFields(List<EditText> fields){
        for(EditText field : fields){
            field.setText(null);
        }
    }
}
