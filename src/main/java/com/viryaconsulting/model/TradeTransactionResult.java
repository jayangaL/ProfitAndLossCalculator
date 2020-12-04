package com.viryaconsulting.model;

import com.google.common.base.Objects;

public class TradeTransactionResult {

    private String traderId;
    private String stockSymbol;
    private double pnl;
    private double residual;

    public String getTraderId() {
        return traderId;
    }

    public void setTraderId(String traderId) {
        this.traderId = traderId;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public double getPnl() {
        return pnl;
    }

    public void setPnl(double pnl) {
        this.pnl = pnl;
    }

    public double getResidual() {
        return residual;
    }

    public void setResidual(double residual) {
        this.residual = residual;
    }

    public TradeTransactionResult(TradeItemSignature tradeItemSignature) {
        this.traderId = tradeItemSignature.getTraderId();
        this.stockSymbol = tradeItemSignature.getStockSymbol();
    }

    public TradeTransactionResult() {
    }

    @Override
    public String toString() {
        return "TradeTransactionResult{" +
                "traderId='" + traderId + '\'' +
                ", stockSymbol='" + stockSymbol + '\'' +
                ", pnl=" + pnl +
                ", residual=" + residual +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeTransactionResult that = (TradeTransactionResult) o;
        return Double.compare(that.pnl, pnl) == 0 &&
                Double.compare(that.residual, residual) == 0 &&
                Objects.equal(traderId, that.traderId) &&
                Objects.equal(stockSymbol, that.stockSymbol);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(traderId, stockSymbol, pnl, residual);
    }
}
