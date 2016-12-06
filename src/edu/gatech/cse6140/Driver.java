package edu.gatech.cse6140;

import org.apache.commons.cli.*;

import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.io.InputOutputHandler;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;
import edu.gatech.cse6140.tsp.solvers.BranchAndBoundSolver;
import edu.gatech.cse6140.tsp.solvers.local_search.SimulatedAnnealingSolver;
import edu.gatech.cse6140.tsp.solvers.TravelingSalesmanProblemSolver;
import edu.gatech.cse6140.tsp.solvers.heuristic.FarthestInsertionSolver;
import edu.gatech.cse6140.tsp.solvers.heuristic.MinimumSpanningTreeApproximateSolver;
import edu.gatech.cse6140.tsp.solvers.heuristic.NearestNeighborApproximateSolver;
import edu.gatech.cse6140.tsp.solvers.local_search.DeterministicHillClimbingSolver;
import edu.gatech.cse6140.tsp.solvers.local_search.RandomizedHillClimbingSolver;

import java.util.HashMap;
import java.util.StringJoiner;

public class Driver {
    public static void main(String[] args) {
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

        Option inputDebug = new Option("d", "debug", false, "debug mode");
        inputDebug.setRequired(false);
        options.addOption(inputDebug);

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

        String filename = cmd.getOptionValue("inst");
        String algorithm = cmd.getOptionValue("alg");
        Integer cutoffTimeInSeconds = Integer.parseInt(cmd.getOptionValue("time"));
        Long seed = Long.parseLong(cmd.getOptionValue(
                "seed", ((Long)(System.currentTimeMillis())).toString())
        );

        String instance = filename;
        if (instance.endsWith(".tsp"))
            instance = instance.substring(0, instance.length() - 4);

        InputOutputHandler inputHandler = new InputOutputHandler("../data");
        InputOutputHandler outputHandler = new InputOutputHandler("../output");

        Graph graph = new Graph(inputHandler.getNodesFromTSPFile(filename));

        HashMap<String, TravelingSalesmanProblemSolver> algorithms = new HashMap<>();
        algorithms.put("BnB", new BranchAndBoundSolver(graph));
        algorithms.put("MSTApprox", new MinimumSpanningTreeApproximateSolver(graph));
        algorithms.put("Heur", new NearestNeighborApproximateSolver(graph));
        algorithms.put("Heur1", new MinimumSpanningTreeApproximateSolver(graph));
        algorithms.put("Heur2", new FarthestInsertionSolver(graph));
        algorithms.put("Heur3", new NearestNeighborApproximateSolver(graph));
        algorithms.put("LS1", new RandomizedHillClimbingSolver(graph).setRandomSeed(seed));
        algorithms.put("LS2", new SimulatedAnnealingSolver(graph).setRandomSeed(seed));
        algorithms.put("LS3", new DeterministicHillClimbingSolver(graph));


        TravelingSalesmanProblemSolver solver = algorithms.get(algorithm);

        TravelingSalesmanTour tour = solver.solve(cutoffTimeInSeconds);

        StringJoiner solutionFileNameJoiner = new StringJoiner("_");
        StringJoiner traceFileNameJoiner = new StringJoiner("_");

        solutionFileNameJoiner.add(instance);
        traceFileNameJoiner.add(instance);

        solutionFileNameJoiner.add(algorithm);
        traceFileNameJoiner.add(algorithm);

        solutionFileNameJoiner.add(cutoffTimeInSeconds.toString());
        traceFileNameJoiner.add(cutoffTimeInSeconds.toString());

        if (algorithm.equals("LS1") || algorithm.equals("LS2")) {
            solutionFileNameJoiner.add(seed.toString());
            traceFileNameJoiner.add(seed.toString());
        }

        if (cmd.hasOption("debug")) {
            solutionFileNameJoiner.add(((Long)System.currentTimeMillis()).toString());
            traceFileNameJoiner.add(((Long)System.currentTimeMillis()).toString());
        }

        outputHandler.putTravelingSalesmanTourInSolFile(tour, solutionFileNameJoiner.toString());
        outputHandler.putTraceInTraceFile(solver.getTrace(), traceFileNameJoiner.toString());
    }
}
