package org.example.target.models;

public class TargetModelRecord {
    String ticker;
    int targetPct;

    public TargetModelRecord(String ticker, int targetPct) {
        this.ticker = ticker;
        this.targetPct = targetPct;
    }

    public String getTicker() {
        return ticker;
    }

    public int getTargetPct() {
        return targetPct;
    }
}
