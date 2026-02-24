package org.example;

import org.example.portfolio.CsvPortfolioReader;
import org.example.portfolio.PortfolioReader;
import org.example.target.CsvModelReader;
import org.example.target.ModelReader;
import org.example.trade.CsvTradeWriter;
import org.example.trade.DefaultTradeComputer;
import org.example.trade.TradeComputer;
import org.example.trade.TradeWriter;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {
    private final PortfolioReader portfolioReader = new CsvPortfolioReader("./portfolio.csv");
    private final ModelReader modelReader = new CsvModelReader("./target.csv");
    private final TradeWriter tradeWriter = new CsvTradeWriter();
    private final TradeComputer tradeComputer = new DefaultTradeComputer();
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    public void start() throws IOException, ExecutionException, InterruptedException {
        //Reading from files concurrently
        var portfolioFuture = executorService.submit(portfolioReader::read);
        var targetModelFuture = executorService.submit(modelReader::read);

        var trades = tradeComputer.compute(portfolioFuture.get(), targetModelFuture.get());
        tradeWriter.write(trades);
    }
}
