package com.viryaconsulting;

import com.viryaconsulting.processor.PNLCalculator;
import com.viryaconsulting.processor.ResultExporter;
import com.viryaconsulting.processor.TradeLoader;
import com.viryaconsulting.processor.Worker;

import java.io.*;
import java.net.URISyntaxException;

import static com.viryaconsulting.util.Constants.*;

public class Main {


    public static void main(String[] args) throws URISyntaxException, IOException {

        Worker worker = new Worker(new TradeLoader(), new PNLCalculator(), new ResultExporter());
        String filename = "trades.dat";
        if (args.length > 0) {
            filename = args[0];
        }
        System.out.println(invoke(worker, filename));
    }

    public static File invoke(Worker worker, String filename) throws IOException, URISyntaxException {
        System.out.println(INVOKE_METHOD_CALLED_PRINT_MSG);
        return worker.process(filename);
    }

}
