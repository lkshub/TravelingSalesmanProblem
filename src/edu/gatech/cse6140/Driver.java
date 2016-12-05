package edu.gatech.cse6140;

import org.apache.commons.cli.*;


public class Driver {
    public static void main(String[] args) {
        int numArgs = args.length;

        Options options = new Options();

        Option inputInstance = new Option("i", "inst", true, "instance filename");
        inputInstance.setRequired(true);
        options.addOption(inputInstance);

        Option inputAlgorithm = new Option("a", "alg", true, "algorithm");
        inputAlgorithm.setRequired(true);
        options.addOption(inputAlgorithm);

        Option inputTime = new Option("t", "time", true, "cutoff time in seconds");
        inputTime.setRequired(true);
        options.addOption(inputTime);

        Option inputSeed = new Option("s", "seed", true, "random seed");
        inputSeed.setRequired(false);
        options.addOption(inputSeed);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("CSE 6140 - Team Z Code Driver", options);

            System.exit(1);
            return;
        }

        for (String arg: args)
            System.out.println(arg);
    }
}
