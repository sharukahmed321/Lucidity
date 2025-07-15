package service;

public class RoutePlannerFactory {
    private RoutePlannerFactory() {}
    public static IRoutePlanner getPlanner(String strategy, IDistanceCalculator calculator) {
        return switch (strategy.toLowerCase()) {
            case "time" -> new GraphRoutePlanner(calculator);
            // case "distance" -> new DijkstraPlanner(calculator);
            default -> throw new IllegalArgumentException("Unknown strategy: " + strategy);
        };
    }
}