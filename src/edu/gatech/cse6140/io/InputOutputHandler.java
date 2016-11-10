package edu.gatech.cse6140.io;

import edu.gatech.cse6140.graph.Node;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;

import java.io.*;
import java.util.ArrayList;

public class InputOutputHandler {
    private String basePath;

    public InputOutputHandler(String basePath) {
        this.basePath = basePath;
    }

    public ArrayList<Node> getNodesFromTSPFile(String fileName) {
        ArrayList<Node> nodes = new ArrayList<>();

        try {
            InputStream in = new FileInputStream(new File(basePath + "/" + fileName));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            for (Integer i = 0; i < 5; i++) {
                reader.readLine();
            }

            String line;

            Integer n = 0;

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

    public void putTravelingSalesmanTourInTourFile(TravelingSalesmanTour tour, String fileName) {
        ArrayList<Node> nodes = tour.getTour();

        nodes.add(nodes.get(0));

        try {
            PrintWriter out = new PrintWriter(basePath + "/" + fileName);

            out.println(tour.getTourCost());

            for (Integer i = 1; i < nodes.size(); i++) {
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
}