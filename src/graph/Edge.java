package graph;

public class Edge {
    private final GraphNode from;
    private final GraphNode to;
    private final double cost;

    public Edge(GraphNode from, GraphNode to, double cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

    public GraphNode getFrom() { return from; }
    public GraphNode getTo() { return to; }
    public double getCost() { return cost; }
}
