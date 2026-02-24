package org.example.trade;

import org.example.portfolio.models.PortfolioRecord;
import org.example.target.models.TargetModelRecord;
import org.example.trade.models.Trade;

import java.util.Map;
import java.util.Set;

public interface TradeComputer {

    Set<Trade> compute(Map<String, PortfolioRecord> portfolio, Set<TargetModelRecord> targetModel);
}
