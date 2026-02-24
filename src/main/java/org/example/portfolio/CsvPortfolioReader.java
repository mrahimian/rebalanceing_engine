package org.example.portfolio;

import org.example.portfolio.models.PortfolioRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CsvPortfolioReader implements PortfolioReader {
    private String filePath;

    public CsvPortfolioReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Map<String, PortfolioRecord> read() throws FileNotFoundException {
        var map = new HashMap<String, PortfolioRecord>();
        var file = new File(filePath);
        try (Scanner inputStream = new Scanner(file)) {
            System.out.println("Reading portfolio from " + file.getName() + " ...");
                    inputStream.next();
            while (inputStream.hasNext()) {
                String data = inputStream.next();
                String[] values = data.split(",");

                String ticker = values[0];
                double shares = Double.parseDouble(values[1]);
                double price = Double.parseDouble(values[2]);
                var portfolio = new PortfolioRecord(ticker, shares, price);
                map.put(ticker, portfolio);
            }

            System.out.println("Successfully read portfolio from " + file.getName());
            return map;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            throw e;
        }
    }
}
