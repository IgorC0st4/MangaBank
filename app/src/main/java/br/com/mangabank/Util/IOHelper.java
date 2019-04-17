package br.com.mangabank.Util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class IOHelper {

    public static boolean escreverArquivo(String strJSON){
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "mangas.txt");

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(strJSON.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            Log.e("ERRO ARQUIVO", e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String lerArquivo(String input){
        File file = new File(Environment.getExternalStorageDirectory(), input);
        StringBuilder builder = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String linha;
            while ((linha = reader.readLine()) != null){
                builder.append(linha);
            }
            reader.close();
        }catch (Exception e){
            Log.e("ERRO LER ARQUIVO", e.getMessage());
            e.printStackTrace();
        }
        return builder.toString();
    }
}
