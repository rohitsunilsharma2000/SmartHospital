package com.hms.util;


import com.hms.dto.MedicineRequest;
import org.apache.commons.csv.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {

    private static final  String TYPE = "text/csv";
    private static final String[] HEADERS = { "name", "brand", "dosageForm", "strength", "category", "expiryDate", "stockQuantity", "price", "reorderLevel" };

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType()) || file.getOriginalFilename().endsWith(".csv");
    }

    public static List<MedicineRequest> csvToMedicines(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            // Handle BOM characters in the first line
            String firstLine = fileReader.readLine();
            if (firstLine != null && firstLine.startsWith("\uFEFF")) {
                firstLine = firstLine.substring(1); // Remove BOM
            }

            CSVFormat csvFormat = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim();

            try (CSVParser csvParser = new CSVParser(new StringReader(firstLine + "\n" + fileReader.lines().reduce("", (s1, s2) -> s1 + "\n" + s2)), csvFormat)) {

                List<MedicineRequest> medicines = new ArrayList<>();
                Iterable<CSVRecord> csvRecords = csvParser.getRecords();

                for (CSVRecord csvRecord : csvRecords) {
                    MedicineRequest medicine = new MedicineRequest(
                            csvRecord.get("name").trim(),
                            csvRecord.get("brand").trim(),
                            csvRecord.get("dosageForm").trim(),
                            csvRecord.get("strength").trim(),
                            csvRecord.get("category").trim(),
                            LocalDate.parse(csvRecord.get("expiryDate").trim()),
                            Integer.parseInt(csvRecord.get("stockQuantity").trim()),
                            Double.parseDouble(csvRecord.get("price").trim()),
                            Integer.parseInt(csvRecord.get("reorderLevel").trim())
                    );

                    medicines.add(medicine);
                }

                return medicines;
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }
    }
}
