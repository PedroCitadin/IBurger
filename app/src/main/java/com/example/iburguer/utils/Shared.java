package com.example.iburguer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.util.Map;

public class Shared {

    public static final String KEY_NOME_USUARIO = "NOME_USUARIO";
    public static final String KEY_EMAIL_USUARIO = "EMAIL_USUARIO";
    public static final String KEY_SENHA_USUARIO = "SENHA_USUARIO";
    public static final String KEY_MANTER_LOGADO = "MANTER_LOGADO";

    private static SharedPreferences preferences;

    public static final void put(final Context activity, final String key, final Object value){
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = preferences.edit();

        if (value instanceof String) {
            editor.putString(key, (String) value);
        }
        else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        }
        else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        }
        else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        }
        else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }
        else {
            Toast.makeText(activity, "Tipo de dados inválido!", Toast.LENGTH_SHORT).show();
            return;
        }
        editor.apply();
    }

    public static final String getString(final Context activity, final String key, final String defaultValue) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return preferences.getString(key, defaultValue);
    }

    public static final Float getFloat(final Context activity, final String key, final Float defaultValue) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return preferences.getFloat(key, defaultValue);
    }

    public static final Integer getInt(final Context activity, final String key, final Integer defaultValue) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return preferences.getInt(key, defaultValue);
    }

    public static final Boolean getBoolean(final Context activity, final String key, final Boolean defaultValue) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return preferences.getBoolean(key, defaultValue);
    }

    public static final Long getLong(final Context activity, final String key, final Long defaultValue) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return preferences.getLong(key, defaultValue);
    }

    public static final Map<String, ?> getAll(final Context activity, final String key, final Object value) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return preferences.getAll();
    }
}
