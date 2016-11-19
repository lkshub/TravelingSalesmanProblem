package edu.gatech.cse6140.graph;

import java.util.Comparator;

public class Node {
    private class Coordinates {
        private Double x, y;

        public Coordinates(Double x, Double y) {
            this.x = x;
            this.y = y;
        }

        public Double getX() { return x; }

        public Double getY() { return y; }
    }

    private class NodeDistanceComparator implements Comparator<Node> {
        public int compare(Node node1, Node node2) {
            return Node.this.calculateDistanceFromNode(node1)
                    - Node.this.calculateDistanceFromNode(node2);
        }
    }

    private int id;
    private String label;
    private Coordinates coordinates;
    private Comparator<Node> distanceComparator;

    public Node(int id, String label, Double x, Double y) {
        this.id = id;
        this.label = label;
        this.coordinates = new Coordinates(x, y);
        this.distanceComparator = new NodeDistanceComparator();
    }

    public int getId() { return id; }

    public String getLabel() {return  label;}

    public Double getXCoordinate() { return coordinates.getX(); }

    public Double getYCoordinate() { return coordinates.getY(); }

    public int calculateDistanceFromXYCoordinates(Double x, Double y) {
        Double distance = 0.0;

        distance += Math.pow(this.getXCoordinate() - x, 2);
        distance += Math.pow(this.getYCoordinate() - y, 2);

        distance = Math.sqrt(distance);

        distance += 0.5;

        return distance.intValue();
    }

    public int calculateDistanceFromNode(Node otherNode) {
        return calculateDistanceFromXYCoordinates(
                otherNode.getXCoordinate(),
                otherNode.getYCoordinate()
        );
    }

    public Comparator<Node> getDistanceComparator() { return distanceComparator; }
}
