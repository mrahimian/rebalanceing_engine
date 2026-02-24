package org.example.target.models;

public class TargetModelRecord {
    String ticker;
    double targetPct;

    public TargetModelRecord(String ticker, double targetPct) {
        this.ticker = ticker;
        this.targetPct = targetPct;
    }

    public String getTicker() {
        return ticker;
    }

    public double getTargetPct() {
        return targetPct;
    }
}
