import org.example.portfolio.models.PortfolioRecord;
import org.example.target.models.TargetModelRecord;
import org.example.trade.DefaultTradeComputer;
import org.example.trade.TradeComputer;
import org.example.trade.models.Trade;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

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
                new TargetModelRecord("AAPL",25),
                new TargetModelRecord("MSFT",20),
                new TargetModelRecord("GOOGLE",55)
        );

        engine.compute(portfolioMap, targetModel);

        Set<Trade> trades = Set.of(

        );
    }
