package com.viryaconsulting.processor;

import com.viryaconsulting.model.*;
import com.viryaconsulting.util.ValidationUtils;

import java.util.*;

public class PNLCalculator {

    public Map<TradeTransactionSignature, Queue<Trade>> buildTradeQueueMap(List<Trade> trades) {
        Map<TradeTransactionSignature, Queue<Trade>> tradeSignatureQueueMap = new HashMap<>();

        for (Trade trade : trades) {
            TradeTransactionSignature tradeTransactionSignature = new TradeTransactionSignature(trade);
            Queue<Trade> queue = tradeSignatureQueueMap.getOrDefault(tradeTransactionSignature, new LinkedList<Trade>());
            queue.add(trade);
            tradeSignatureQueueMap.put(tradeTransactionSignature, queue);
        }

        return tradeSignatureQueueMap;

    }

    public List<TradeTransactionTree> buildTradeTransactionTreeList(Map<TradeTransactionSignature, Queue<Trade>>  tradeSignatureQueueMap) {

        List<TradeTransactionTree> tradeTransactionTrees = new ArrayList<>();

        tradeSignatureQueueMap.keySet().forEach(k -> {
            TradeTransactionTree tradeTransactionTree = new TradeTransactionTree(new TradeItemSignature(k));
            boolean contains = tradeTransactionTrees.contains(tradeTransactionTree);
            if (contains) {
                tradeTransactionTree = tradeTransactionTrees.get(tradeTransactionTrees.indexOf(tradeTransactionTree));
            } else if (ValidationUtils.isBuyOrSale(String.valueOf(k.getBuyOrSell()))) {
                tradeTransactionTrees.add(tradeTransactionTree);
            } else {
                System.out.println("Skip Trade Invalid Buy or Sale " + k.toString());
            }
            if (k.getBuyOrSell() == 'S') {
                tradeTransactionTree.setSaleQueue(tradeSignatureQueueMap.get(k));
            } else {
                tradeTransactionTree.setBuyQueue(tradeSignatureQueueMap.get(k));
            }
        });

        return tradeTransactionTrees;
    }

    public List<TradeTransactionResult> buildTradeTransactionResultList(List<TradeTransactionTree> tradeTransactionTrees) {
        List<TradeTransactionResult> tradeTransactionResults = new ArrayList<>();
        for (TradeTransactionTree tradeTransactionTree : tradeTransactionTrees) {
            tradeTransactionResults.add(buildTradeTransactionResult(tradeTransactionTree));
        }
        return tradeTransactionResults;
    }

    public TradeTransactionResult buildTradeTransactionResult(TradeTransactionTree tradeTransactionTree) {
        TradeTransactionResult tradeTransactionResult = new TradeTransactionResult(tradeTransactionTree.getTradeTransactionSignature());
        Queue<Trade> buyQueue = tradeTransactionTree.getBuyQueue();
        Queue<Trade> saleQueue = tradeTransactionTree.getSaleQueue();

        double onHandQuantity = 0.0;
        double onHandItemCost = 0.0;
        double pnl = 0.0;

        for (Trade saleTrade : saleQueue) {
            double saleTradePrice = saleTrade.getPrice();
            double saleTradeQuantity = saleTrade.getQuantity();
            if (onHandQuantity == 0 && !buyQueue.isEmpty()) {
                Trade buyTrade = buyQueue.poll();
                //PNL = (sell_trade.saleTradeQuantity x sell_trade.saleTradePrice)
                // - (buy_trade.saleTradeQuantity x buy_trade.saleTradePrice)
                onHandItemCost = buyTrade.getPrice();
                onHandQuantity = buyTrade.getQuantity();
            }

            while (saleTradeQuantity != 0 && onHandQuantity != 0.0) {

                if (saleTradeQuantity > onHandQuantity) {
                    pnl += (onHandQuantity * saleTradePrice) - (onHandQuantity * onHandItemCost);
                    saleTradeQuantity -= onHandQuantity;
                    onHandQuantity = 0.0;
                    if (!buyQueue.isEmpty()) {
                        Trade buyTrade = buyQueue.poll();
                        //PNL = (sell_trade.saleTradeQuantity x sell_trade.saleTradePrice)
                        // - (buy_trade.saleTradeQuantity x buy_trade.saleTradePrice)
                        onHandItemCost = buyTrade.getPrice();
                        onHandQuantity = buyTrade.getQuantity();
                    }
                } else if (saleTradeQuantity < onHandQuantity) {
                    pnl += (saleTradeQuantity * saleTradePrice) - (saleTradeQuantity * onHandItemCost);
                    onHandQuantity -= saleTradeQuantity;
                    saleTradeQuantity = 0.0;
                } else {
                    pnl += (saleTradeQuantity * saleTradePrice) - (onHandQuantity * onHandItemCost);
                    onHandItemCost = 0.0;
                    onHandQuantity = 0.0;
                    saleTradeQuantity = 0.0;
                }
            }
        }

        if (!buyQueue.isEmpty()) {
            for (Trade buyTrade : buyQueue) {
                onHandQuantity += buyTrade.getQuantity();
            }
        }

        tradeTransactionResult.setPnl(pnl);
        tradeTransactionResult.setResidual(onHandQuantity);

        return tradeTransactionResult;
    }

    public List<TradeTransactionResult> generateTransactionResultList(List<Trade> trades) {
        Map<TradeTransactionSignature, Queue<Trade>> buildTradeQueueMap = buildTradeQueueMap(trades);
        List<TradeTransactionTree> tradeTransactionTrees = buildTradeTransactionTreeList(buildTradeQueueMap);
        return buildTradeTransactionResultList(tradeTransactionTrees);

    }
}
