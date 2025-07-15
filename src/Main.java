import model.Location;
import model.Order;
import service.HaversineCalculator;
import service.IDistanceCalculator;
import service.IRoutePlanner;
import service.LoggingDistanceCalculator;
import service.RoutePlannerFactory;

import java.util.List;

public class Main {
    public static void main(String [] args) {
        Location aman = new Location("Aman", 12.9352, 77.6146);
        Location r1 = new Location("R1", 12.9366, 77.6101);
        Location r2 = new Location("R2", 12.9371, 77.6200);
        Location c1 = new Location("C1", 12.9385, 77.6155);
        Location c2 = new Location("C2", 12.9390, 77.6162);

        Order o1 = new Order("O1", r1, c1, 0.25);
        Order o2 = new Order("O2", r2, c2, 0.20);

        IDistanceCalculator calculator = new LoggingDistanceCalculator(new HaversineCalculator());
        IRoutePlanner planner = RoutePlannerFactory.getPlanner("time", calculator);
        IRoutePlanner.RouteResult result = planner.findOptimalRoute(aman, List.of(o1, o2));

        System.out.println("Best Route: " + result.path);
        System.out.printf("Total Time: %.2f minutes%n", result.totalTime * 60);
    }
}
