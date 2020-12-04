package com.viryaconsulting.model;

import com.google.common.base.Objects;

public class  TradeTransactionSignature {

    private String traderId;
    private String stockSymbol;
    private char buyOrSell;

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

    public char getBuyOrSell() {
        return buyOrSell;
    }

    public void setBuyOrSell(char buyOrSell) {
        this.buyOrSell = buyOrSell;
    }

    public TradeTransactionSignature(Trade trade) {
        this.traderId = trade.getTraderId();
        this.stockSymbol = trade.getStockSymbol();
        this.buyOrSell = trade.getBuyOrSell();
    }

    public TradeTransactionSignature(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeTransactionSignature that = (TradeTransactionSignature) o;
        return buyOrSell == that.buyOrSell &&
                Objects.equal(traderId, that.traderId) &&
                Objects.equal(stockSymbol, that.stockSymbol);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(traderId, stockSymbol, buyOrSell);
    }

    @Override
    public String toString() {
        return "TradeSignature{" +
                "traderId='" + traderId + '\'' +
                ", stockSymbol='" + stockSymbol + '\'' +
                ", buyOrSell=" + buyOrSell +
                '}';
    }
}
