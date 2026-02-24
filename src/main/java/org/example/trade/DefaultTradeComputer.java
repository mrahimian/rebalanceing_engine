package org.example.trade;

import org.agrona.collections.ObjectHashSet;
import org.example.portfolio.models.PortfolioRecord;
import org.example.target.models.TargetModelRecord;
import org.example.trade.models.Trade;

import java.util.Map;
import java.util.Set;

import static org.example.trade.models.Trade.Side.BUY;
import static org.example.trade.models.Trade.Side.SELL;

public class DefaultTradeComputer implements TradeComputer {

    @Override
    public Set<Trade> compute(Map<String, PortfolioRecord> portfolio, Set<TargetModelRecord> targetModel) {
        var portfolioTotalValue = portfolio.values()
                .stream()
                .mapToDouble(portfolioRecord -> portfolioRecord.getShares() * portfolioRecord.getPrice())
                .sum();

        Set<Trade> trades = new ObjectHashSet<>();

        targetModel.forEach(model -> {
            if (portfolio.containsKey(model.getTicker())) {
                var value = portfolio.get(model.getTicker());

                var tickerValueAfterTrading = portfolioTotalValue * model.getTargetPct() / 100;
                var tickerShareAfterTrading = tickerValueAfterTrading / value.getPrice();

                var sharesToBuyOrSell = value.getShares() - tickerShareAfterTrading;
                var side = sharesToBuyOrSell > 0 ? SELL : BUY;
                trades.add(new Trade(model.getTicker(), side, truncateToTwoDecimalPlaces(Math.abs(sharesToBuyOrSell)),
                        truncateToTwoDecimalPlaces(Math.abs(sharesToBuyOrSell) * value.getPrice())));
            }
        });

        portfolio.keySet().forEach(key -> {
            if (!trades.contains(new Trade(key))) {
                var value = portfolio.get(key);
                trades.add(new Trade(key, SELL, truncateToTwoDecimalPlaces(value.getShares()),
                        truncateToTwoDecimalPlaces(value.getPrice() * value.getShares())));
            }
        });

        return trades;
    }

    private double truncateToTwoDecimalPlaces(double value) {
        var formatted = String.format("%.2f", value);
        return Double.parseDouble(formatted);
    }
}
