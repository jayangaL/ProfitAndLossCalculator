package com.viryaconsulting.processor;

import com.viryaconsulting.model.TradeTransactionResult;
import com.viryaconsulting.util.ResourceUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;

public class ResultExporter {

    private static DecimalFormat df2 = new DecimalFormat("#.##");


    public File exportDat(List<TradeTransactionResult> tradeTransactionResults) throws IOException {
        File tempFile = ResourceUtils.createTemporyFile("result-", ".dat");
        return exportDat(tradeTransactionResults, tempFile);
    }

    public File exportDat(List<TradeTransactionResult> tradeTransactionResults, File file) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(file));

        for (TradeTransactionResult tradeTransactionResult : tradeTransactionResults) {
            pw.write(tradeTransactionResult.getTraderId());
            System.out.print(tradeTransactionResult.getTraderId());
            pw.write(",");
            System.out.print(",");
            pw.write(tradeTransactionResult.getStockSymbol());
            System.out.print(tradeTransactionResult.getStockSymbol());
            pw.write(",");
            System.out.print(",");
            pw.write(df2.format(tradeTransactionResult.getPnl()));
            System.out.print(df2.format(tradeTransactionResult.getPnl()));
            pw.write(",");
            System.out.print(",");
            pw.write(df2.format(tradeTransactionResult.getResidual()));
            System.out.print(df2.format(tradeTransactionResult.getResidual()));
            pw.write("\n");
            System.out.println();
        }

        pw.close();
        System.out.println(file.toPath());
        return file;
    }
}
