package br.com.mangabank.Util;

import android.text.TextUtils;

public class Utils {
    public static boolean isCampoVazio(String valor) {
        return (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
    }
}
