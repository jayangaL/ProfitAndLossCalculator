package com.viryaconsulting.processor;

import com.viryaconsulting.exception.InvalidArgumentException;
import com.viryaconsulting.exception.InvalidNumberOfArgumentsException;
import com.viryaconsulting.exception.NullTradeInputException;
import com.viryaconsulting.model.Trade;
import com.viryaconsulting.util.ValidationUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.viryaconsulting.util.Constants.*;
import static com.viryaconsulting.util.ResourceUtils.getLinesFromFile;
import static com.viryaconsulting.util.ResourceUtils.getParametersFromLine;

public class TradeLoader {

    public List<Trade> loadTrades(File file) throws IOException {
        String[] lines = getLinesFromFile(file);
        return loadTrades(lines);

    }

    public List<Trade> loadTrades(String[] lines) {
        List<Trade> trades = new ArrayList<>();
        int i = 0;
        for (String line : lines) {
            Trade trade = createTrade(line);
            trade.setIndex(++i);
            trades.add(trade);
        }
        return trades;
    }

    public Trade createTrade(String line) {
        String[] params = getParametersFromLine(line);
        return createTrade(params);
    }

    public Trade createTrade(String[] params) {
        validateTrade(params);
        Trade trade = new Trade();
        trade.setTraderId(params[0].trim());
        trade.setStockSymbol(params[1].trim());
        trade.setQuantity(Double.parseDouble(params[2].trim()));
        trade.setBuyOrSell(params[3].charAt(0));
        trade.setPrice(Double.parseDouble(params[4]));
        return trade;
    }

    public boolean validateTrade(String[] params) {
        if (params == null)
            throw new NullTradeInputException();
        if (params.length != 5)
            throw new InvalidNumberOfArgumentsException(params.length, Arrays.toString(params));
        if (!ValidationUtils.isNonEmptyString(params[0]))
            throw new InvalidArgumentException(1, TRADE_ID);
        if (!ValidationUtils.isNonEmptyString(params[1]))
            throw new InvalidArgumentException(2, STOCK_SYMBOL);
        if (!ValidationUtils.isNonEmptyString(params[2])) {
            throw new InvalidArgumentException(3, QUANTITY);
        } else if (!ValidationUtils.isNumber(params[2])) {
            throw new InvalidArgumentException(3, NUMBER, QUANTITY, params[2]);
        }
        if (!ValidationUtils.isNonEmptyString(params[3])) {
            throw new InvalidArgumentException(4, BUY_OR_SALE);
        } else if (!ValidationUtils.isBuyOrSale(params[3])) {
            throw new InvalidArgumentException(4, BUY_OR_SALE, BUY_OR_SALE_VALID, params[3]);
        }
        if (!ValidationUtils.isNonEmptyString(params[4])) {
            throw new InvalidArgumentException(5, PRICE);
        } else if (!ValidationUtils.isPositiveNumber(params[4])) {
            throw new InvalidArgumentException(5, PRICE, NUMBER, params[4]);
        }

        return true;
    }
}
