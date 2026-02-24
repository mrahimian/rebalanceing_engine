package org.example;

import org.agrona.collections.ObjectHashSet;
import org.example.portfolio.models.PortfolioRecord;
import org.example.target.models.TargetModelRecord;
import org.example.trade.DefaultTradeComputer;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class PerformanceTest {
    public static void main(String[] args) {
        int modelSize = 200;
        double equalPct = 100.0 / modelSize;

        List<String> allTickers = new ArrayList<>();
        for (int i = 0; i < modelSize; i++) {
            allTickers.add("T" + i);
        }

        Set<TargetModelRecord> targetModel = new HashSet<>();
        for (int i = 0; i < modelSize; i++) {
            targetModel.add(new TargetModelRecord(allTickers.get(i), equalPct));
        }

        System.out.println("Target model size: " + targetModel.size());

        int portfolioCount = 1000;
        int minPortfolioSize = 50;

        List<Map<String, PortfolioRecord>> portfolios = new ArrayList<>();
        Random random = new Random(42);

        for (int p = 0; p < portfolioCount; p++) {
            Map<String, PortfolioRecord> portfolio = new HashMap<>();

            int portfolioSize = minPortfolioSize + random.nextInt(51);
            Collections.shuffle(allTickers, random);

            for (int i = 0; i < portfolioSize; i++) {
                String ticker = allTickers.get(i);
                double shares = 10;
                double price = 120;
                portfolio.put(ticker, new PortfolioRecord(ticker, shares, price));
            }

            portfolios.add(portfolio);
        }

        System.out.println("Generated " + portfolios.size() + " portfolios.\n\n");

        var tradeComputer = new DefaultTradeComputer();

        long startTime = System.currentTimeMillis();
        int totalPositions = 0;

        for (Map<String, PortfolioRecord> portfolio : portfolios) {
            tradeComputer.compute(portfolio, targetModel);
            totalPositions += portfolio.size();
        }

        long endTime = System.currentTimeMillis();

        double totalTimeSec = (endTime - startTime) / 1000.0;
        double avgPortfolioLatencyMs = ((endTime - startTime) * 1.0) / portfolios.size();
        double throughput = totalPositions / totalTimeSec;

        System.out.println("====== Rebalance Benchmark Metrics ======");
        System.out.printf("Total processing time: %.3f sec%n", totalTimeSec);
        System.out.println("Positions processed: " + totalPositions);
        System.out.printf("Throughput: %.2f positions/sec%n", throughput);
        System.out.printf("Average per-portfolio latency: %.3f ms%n", avgPortfolioLatencyMs);
    }
}
