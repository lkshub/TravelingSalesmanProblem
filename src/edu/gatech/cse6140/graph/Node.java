package edu.gatech.cse6140.graph;

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

    private Integer id;
    private String label;
    private Coordinates coordinates;

    public Node(Integer id, String label, Double x, Double y) {
        this.id = id;
        this.label = label;
        this.coordinates = new Coordinates(x, y);
    }

    public Integer getId() { return id; }

    public String getLabel() {return  label;}

    public Double getXCoordinate() { return coordinates.getX(); }

    public Double getYCoordinate() { return coordinates.getY(); }

    public Double calculateDistanceFromXYCoordinates(Double x, Double y) {
        Double distance = 0.0;

        distance += Math.pow(this.getXCoordinate() - x, 2);
        distance += Math.pow(this.getYCoordinate() - y, 2);

        distance = Math.sqrt(distance);

        return distance;
    }

    public Double calculateDistanceFromNode(Node otherNode) {
        return calculateDistanceFromXYCoordinates(
                otherNode.getXCoordinate(),
                otherNode.getYCoordinate()
        );
    }
}
