package com.example.portal3;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {

    public static List<String[]> readCSV(Context context, String fileName) {
        List<String[]> data = new ArrayList<>();

        try {
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = reader.readLine()) != null) {
                data.add(parseCSVLine(line));
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    private static String[] parseCSVLine(String line) {
        List<String> tokens = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '\"') {
                inQuotes = !inQuotes; // Toggle quote mode
            } else if (c == ',' && !inQuotes) {
                tokens.add(current.toString().trim());
                current.setLength(0); // Reset StringBuilder
            } else {
                current.append(c);
            }
        }

        // Add last token
        tokens.add(current.toString().trim());

        return tokens.toArray(new String[0]);
    }
}
