package com.viryaconsulting.model;

import com.google.common.base.Objects;

import java.util.LinkedList;
import java.util.Queue;

public class TradeTransactionTree {
    private TradeItemSignature tradeItemSignature;
    private Queue<Trade> saleQueue;
    private Queue<Trade> buyQueue;

    public TradeItemSignature getTradeItemSignature() {
        return tradeItemSignature;
    }

    public TradeTransactionTree(TradeItemSignature tradeItemSignature) {
        this.tradeItemSignature = tradeItemSignature;
        this.saleQueue = new LinkedList<>();
        this.buyQueue = new LinkedList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeTransactionTree that = (TradeTransactionTree) o;
        return Objects.equal(tradeItemSignature, that.tradeItemSignature);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tradeItemSignature);
    }

    public TradeItemSignature getTradeTransactionSignature() {
        return tradeItemSignature;
    }

    public void setTradeItemSignature(TradeItemSignature tradeItemSignature) {
        this.tradeItemSignature = tradeItemSignature;
    }

    public Queue<Trade> getSaleQueue() {
        return saleQueue;
    }

    public void setSaleQueue(Queue<Trade> saleQueue) {
        this.saleQueue = saleQueue;
    }

    public Queue<Trade> getBuyQueue() {
        return buyQueue;
    }

    public void setBuyQueue(Queue<Trade> buyQueue) {
        this.buyQueue = buyQueue;
    }

    @Override
    public String toString() {
        return "TradeTransactionTree{" +
                "tradeItemSignature=" + tradeItemSignature +
                ", saleQueue=" + saleQueue +
                ", buyQueue=" + buyQueue +
                '}';
    }
}
