package org.example.portfolio;

import org.example.portfolio.models.PortfolioRecord;

import java.io.IOException;
import java.util.Map;

public interface PortfolioReader {

    Map<String, PortfolioRecord> read() throws IOException;
}
