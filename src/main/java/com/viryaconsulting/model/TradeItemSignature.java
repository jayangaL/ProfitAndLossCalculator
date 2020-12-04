package com.viryaconsulting.model;

import com.google.common.base.Objects;

public class TradeItemSignature {

    private String traderId;
    private String stockSymbol;

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

    public TradeItemSignature(TradeTransactionSignature tradeTransactionSignature) {
        this.traderId = tradeTransactionSignature.getTraderId();
        this.stockSymbol = tradeTransactionSignature.getStockSymbol();
    }

    public TradeItemSignature(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeItemSignature that = (TradeItemSignature) o;
        return Objects.equal(traderId, that.traderId) &&
                Objects.equal(stockSymbol, that.stockSymbol);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(traderId, stockSymbol);
    }

    @Override
    public String toString() {
        return "TradeItemSignature{" +
                "traderId='" + traderId + '\'' +
                ", stockSymbol='" + stockSymbol + '\'' +
                '}';
    }
}
