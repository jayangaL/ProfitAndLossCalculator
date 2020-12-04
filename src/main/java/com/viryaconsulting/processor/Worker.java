package com.viryaconsulting.processor;

import com.viryaconsulting.model.Trade;
import com.viryaconsulting.model.TradeTransactionResult;
import com.viryaconsulting.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Worker {

    private final TradeLoader tradeLoader;
    private final PNLCalculator pnlCalculator;
    private final ResultExporter resultExporter;

    public Worker(TradeLoader tradeLoader, PNLCalculator pnlCalculator, ResultExporter resultExporter) {
        this.tradeLoader = tradeLoader;
        this.pnlCalculator = pnlCalculator;
        this.resultExporter = resultExporter;
    }

    public File process(String filename) throws URISyntaxException, IOException {
        List<Trade> trades = tradeLoader.loadTrades(ResourceUtils.getFileFromResource(filename));
        List<TradeTransactionResult> tradeTransactionResults = pnlCalculator.generateTransactionResultList(trades);
        return resultExporter.exportDat(tradeTransactionResults);
    }

}
