package org.example.target;

import org.agrona.collections.ObjectHashSet;
import org.example.target.models.TargetModelRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;

public class CsvModelReader implements ModelReader {
    private String filePath;

    public CsvModelReader(String filepath) {
        this.filePath = filepath;
    }

    @Override
    public Set<TargetModelRecord> read() throws FileNotFoundException {
        var set = new ObjectHashSet<TargetModelRecord>();
        var file = new File(filePath);
        try (Scanner inputStream = new Scanner(file)) {
            System.out.println("Reading portfolio from " + file.getName() + " ...");
            inputStream.next();
            while (inputStream.hasNext()) {
                String data = inputStream.next();
                String[] values = data.split(",");

                String ticker = values[0];
                int targetPct = Integer.parseInt(values[1]);
                var targetModel = new TargetModelRecord(ticker, targetPct);
                set.add(targetModel);
            }

            System.out.println("Successfully read portfolio from " + file.getName());
            return set;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            throw e;
        }
    }
}
