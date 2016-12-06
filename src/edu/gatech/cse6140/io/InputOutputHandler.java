package edu.gatech.cse6140.io;

import edu.gatech.cse6140.graph.Node;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class InputOutputHandler {
    private String basePath;

    public InputOutputHandler(String basePath) {
        this.basePath = basePath;
    }

    public ArrayList<Node> getNodesFromTSPFile(String fileReference) {
        ArrayList<Node> nodes = new ArrayList<>();

        try {
            if (!fileReference.endsWith(".tsp"))
                fileReference = fileReference.concat(".tsp");

            InputStream in = new FileInputStream(new File(basePath + "/" + fileReference));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            for (int i = 0; i < 5; i++) {
                reader.readLine();
            }

            String line;

            int n = 0;

            while (!(line = reader.readLine()).equals("EOF")) {
                String[] d = line.split(" ");

                nodes.add(new Node(n++, d[0], Double.parseDouble(d[1]), Double.parseDouble(d[2])));
            }

            reader.close(); in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nodes;
    }

    public void putTravelingSalesmanTourInSolFile(TravelingSalesmanTour tour, String fileReference) {
        ArrayList<Node> nodes = tour.getTour();

        nodes.add(nodes.get(0));

        try {
            if (!fileReference.endsWith(".sol"))
                fileReference = fileReference.concat(".sol");

            PrintWriter out = new PrintWriter(basePath + "/" + fileReference);

            out.println(tour.getTourCost());

            for (int i = 1; i < nodes.size(); i++) {
                Node sourceNode = nodes.get(i - 1);
                Node targetNode = nodes.get(i);

                out.println(sourceNode.getId()
                        +" "+targetNode.getId()
                        +" "+targetNode.calculateDistanceFromNode(sourceNode)
                );
            }

            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void putTraceInTraceFile(Trace trace, String fileReference) {
        ArrayList<Double> traceTimes = trace.getTraceTimes();
        ArrayList<Integer> traceValues = trace.getTraceValues();

        try {
            if (!fileReference.endsWith(".trace"))
                fileReference = fileReference.concat(".trace");

            DecimalFormat df = new DecimalFormat("0.000");

            PrintWriter out = new PrintWriter(basePath + "/" + fileReference);

            for (int i = 0; i < traceTimes.size(); i++)
                out.println(df.format(traceTimes.get(i)) + ", " + traceValues.get(i));

            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}