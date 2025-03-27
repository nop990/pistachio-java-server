package com.nop990.pistachio.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;

public class Utility {
    public static String readFile(String fileName) throws FileNotFoundException {
        try {
            File file = new File(fileName);
            StringBuilder content = new StringBuilder();

            try (FileReader reader = new FileReader(file)) {
                int ch;
                while ((ch = reader.read()) != -1) {
                    content.append((char) ch);
                }
                return new String(content);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new FileNotFoundException("File not found");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading file");
        }
    }

    public static void writeFile(String fileName, String content) {
        try {
            File file = new File(fileName);
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void validateCsv(String content) {
        try (CSVReader csvReader = new CSVReader(new StringReader(content))) {
            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                if (nextLine.length != 2) {
                    throw new IllegalArgumentException("CSV does not have exactly 2 columns");
                }
            }
        } catch (IOException | CsvValidationException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
