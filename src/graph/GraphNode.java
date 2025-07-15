package graph;

import model.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GraphNode {
    private final Location location;
    private final List<Edge> edges = new ArrayList<>();

    public GraphNode(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof GraphNode other && location.equals(other.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    @Override
    public String toString() {
        return location.toString();
    }
}