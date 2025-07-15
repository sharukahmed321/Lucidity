package service;

import graph.Edge;
import graph.GraphNode;
import model.Location;
import model.Order;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class GraphRoutePlanner implements IRoutePlanner {
    private final IDistanceCalculator calculator;

    public GraphRoutePlanner(IDistanceCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public RouteResult findOptimalRoute(Location start, List<Order> orders) {
        GraphNode startNode = new GraphNode(start);
        Map<Location, GraphNode> nodeMap = new HashMap<>();
        nodeMap.put(start, startNode);

        for (Order order : orders) {
            nodeMap.putIfAbsent(order.getRestaurant(), new GraphNode(order.getRestaurant()));
            nodeMap.putIfAbsent(order.getConsumer(), new GraphNode(order.getConsumer()));
        }

        List<GraphNode> allNodes = new ArrayList<>(nodeMap.values());

        // Create fully connected graph
        for (GraphNode from : allNodes) {
            for (GraphNode to : allNodes) {
                if (!from.equals(to)) {
                    double cost = calculator.calculateTime(from.getLocation(), to.getLocation());
                    from.addEdge(new Edge(from, to, cost));
                }
            }
        }

        // Build delivery path nodes
        List<GraphNode> deliveryNodes = new ArrayList<>();
        for (Order order : orders) {
            deliveryNodes.add(nodeMap.get(order.getRestaurant()));
            deliveryNodes.add(nodeMap.get(order.getConsumer()));
        }

        List<List<GraphNode>> permutations = new ArrayList<>();
        permute(new ArrayList<>(), deliveryNodes, new boolean[deliveryNodes.size()], permutations);

        double bestTime = Double.MAX_VALUE;
        List<Location> bestPath = null;

        for (List<GraphNode> path : permutations) {
            if (!isValid(path, orders, nodeMap)) continue;

            double time = 0;
            GraphNode current = startNode;

            for (GraphNode next : path) {
                Optional<Edge> edge = current.getEdges().stream()
                        .filter(e -> e.getTo().equals(next))
                        .findFirst();

                if (edge.isEmpty()) continue;

                time += edge.get().getCost();

                for (Order order : orders) {
                    if (next.getLocation().equals(order.getRestaurant())) {
                        time = Math.max(time, order.getPreparationTimeHours());
                    }
                }

                current = next;
            }

            if (time < bestTime) {
                bestTime = time;
                bestPath = path.stream().map(GraphNode::getLocation).toList();
            }
        }

        return new RouteResult(bestPath, bestTime);
    }

    private void permute(List<GraphNode> curr, List<GraphNode> all, boolean[] used, List<List<GraphNode>> result) {
        if (curr.size() == all.size()) {
            result.add(new ArrayList<>(curr));
            return;
        }

        for (int i = 0; i < all.size(); i++) {
            if (!used[i]) {
                used[i] = true;
                curr.add(all.get(i));
                permute(curr, all, used, result);
                curr.removeLast();
                used[i] = false;
            }
        }
    }

    private boolean isValid(List<GraphNode> path, List<Order> orders, Map<Location, GraphNode> nodeMap) {
        Set<GraphNode> visited = new HashSet<>();
        for (GraphNode node : path) {
            for (Order order : orders) {
                GraphNode restaurant = nodeMap.get(order.getRestaurant());
                GraphNode consumer = nodeMap.get(order.getConsumer());
                if (node.equals(consumer) && !visited.contains(restaurant)) {
                    return false;
                }
            }
            visited.add(node);
        }
        return true;
    }
}