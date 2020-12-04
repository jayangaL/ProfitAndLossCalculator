package com.viryaconsulting.model;

public class Trade {
    private int index;
    private String traderId;
    private String stockSymbol;
    private double quantity;
    private char buyOrSell;
    private double price;

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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public char getBuyOrSell() {
        return buyOrSell;
    }

    public void setBuyOrSell(char buyOrSell) {
        this.buyOrSell = buyOrSell;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "traderId='" + traderId + '\'' +
                ", stockSymbol='" + stockSymbol + '\'' +
                ", quantity=" + quantity +
                ", buyOrSell=" + buyOrSell +
                ", price=" + price +
                '}';
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
