package com.abhilash.hackathon.helper;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReaderHelper {
    public static List<String> getCSVFileNames(Context context, String folder) {
        List<String> fileNames = new ArrayList<>();
        try {
            String[] files = context.getAssets().list(folder);
            if (files != null) {
                for (String file : files) {
                    if (file.endsWith(".csv")) {
                        fileNames.add(file);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileNames;
    }

    public static List<String[]> readCSV(Context context, String filePath) {
        List<String[]> data = new ArrayList<>();
        try {
            InputStream is = context.getAssets().open(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split("\t");
                data.add(values);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    public static List<Float> extractYearlyData(List<String[]> data, String countryName) {
        List<Float> yearlyData = new ArrayList<>();
        boolean countryFound = false;

        for (String[] row : data) {
            // Debugging to print row data
            System.out.println("Reading row: " + java.util.Arrays.toString(row));

            if (row.length > 1 && row[0].trim().equalsIgnoreCase(countryName)) {
                countryFound = true;

                // Loop through columns starting from index 1 (year data)
                for (int i = 1; i < row.length; i++) {
                    try {
                        // Check if the value can be parsed as a float
                        float value = Float.parseFloat(row[i].trim());
                        yearlyData.add(value);
                        System.out.println("Parsed value at column " + i + ": " + value);
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing value at column " + i + ": " + row[i]);
                        yearlyData.add(0f); // Add 0 if data is not parseable
                    }
                }
                break;
            }
        }

        if (!countryFound) {
            System.out.println("Country " + countryName + " not found in the data.");
        }
        return yearlyData;
    }

}
