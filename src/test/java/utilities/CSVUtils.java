package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVUtils {

    public static List<Map<String, String>> readCsv(String file) throws IOException {
        List<Map<String, String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/test/resources/excel/"+file+".csv"))) {
            String[] headers = br.readLine().split(","); // Read the header row
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, String> row = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    row.put(headers[i], values[i]);
                }
                records.add(row);
            }
        }
        return records;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(readCsv("TestData"));
    }

}
