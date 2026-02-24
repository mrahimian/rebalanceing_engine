import org.example.portfolio.models.PortfolioRecord;
import org.example.target.models.TargetModelRecord;
import org.example.trade.DefaultTradeComputer;
import org.example.trade.TradeComputer;
import org.example.trade.models.Trade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RebalanceEngineTest {
    private final TradeComputer engine = new DefaultTradeComputer();

    @Test
    void shouldRebalanceCorrectly() {
        Map<String, PortfolioRecord> portfolioMap = Map.of(
                "AAPL", new PortfolioRecord("AAPL", 120, 190.5),
                "TSLA", new PortfolioRecord("TSLA", 50, 720.2),
                "MSFT", new PortfolioRecord("MSFT", 80, 330.9),
                "NFLX", new PortfolioRecord("NFLX", 10, 490),
                "GOOGLE", new PortfolioRecord("GOOGLE", 40, 510)
        );

        Set<TargetModelRecord> targetModel = Set.of(
                new TargetModelRecord("AAPL", 25),
                new TargetModelRecord("MSFT", 20),
                new TargetModelRecord("GOOGLE", 55)
        );

        var trades = engine.compute(portfolioMap, targetModel);

        for (Trade trade : trades) {
            assertTrue(trades.contains(trade));
        }

        Trade google = trades.stream()
                .filter(t -> t.getTicker().equals("GOOGLE"))
                .findFirst()
                .orElseThrow();

        assertEquals(Trade.Side.BUY, google.getSide());
        assertEquals(79.32, google.getTradeShares());
        assertEquals(40453.1, google.getTradeValue());

        Trade aapl = trades.stream()
                .filter(t -> t.getTicker().equals("AAPL"))
                .findFirst()
                .orElseThrow();

        assertEquals(Trade.Side.BUY, aapl.getSide());
        assertEquals(25.2, aapl.getTradeShares());
        assertEquals(4800.5, aapl.getTradeValue());

        Trade tsla = trades.stream()
                .filter(t -> t.getTicker().equals("TSLA"))
                .findFirst()
                .orElseThrow();

        assertEquals(Trade.Side.SELL, tsla.getSide());
        assertEquals(50.0, tsla.getTradeShares());
        assertEquals(36010, tsla.getTradeValue());

        Trade nflx = trades.stream()
                .filter(t -> t.getTicker().equals("NFLX"))
                .findFirst()
                .orElseThrow();

        assertEquals(Trade.Side.SELL, nflx.getSide());
        assertEquals(10.0, nflx.getTradeShares());
        assertEquals(4900.0, nflx.getTradeValue());

        Trade msft = trades.stream()
                .filter(t -> t.getTicker().equals("MSFT"))
                .findFirst()
                .orElseThrow();

        assertEquals(Trade.Side.SELL, msft.getSide());
        assertEquals(13.13, msft.getTradeShares());
        assertEquals(4343.6, msft.getTradeValue());


    }
}
