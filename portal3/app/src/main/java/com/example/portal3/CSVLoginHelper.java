package com.example.portal3;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class CSVLoginHelper {
    private HashMap<String, String> credentials;

    public CSVLoginHelper(Context context, int csvResId) {
        credentials = new HashMap<>();
        loadCSV(context, csvResId);
    }

    private void loadCSV(Context context, int resId) {
        try {
            InputStream is = context.getResources().openRawResource(resId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 2) {
                    String email = tokens[0].trim();
                    String password = tokens[1].trim();
                    credentials.put(email, password);
                }
            }

            reader.close();
        } catch (Exception e) {
            Log.e("CSVLoginHelper", "Error reading CSV", e);
        }
    }

    public boolean validateLogin(String email, String password) {
        return credentials.containsKey(email) && credentials.get(email).equals(password);
    }
}
