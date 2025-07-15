package test;

import model.Location;
import model.Order;
import org.junit.Test;
import service.GraphRoutePlanner;
import service.HaversineCalculator;
import service.IDistanceCalculator;
import service.IRoutePlanner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GraphRoutePlannerTest {

    @Test
    public void testOptimalRoute() {
        Location aman = new Location("Aman", 12.9352, 77.6146);
        Location r1 = new Location("R1", 12.9366, 77.6101);
        Location r2 = new Location("R2", 12.9371, 77.6200);
        Location c1 = new Location("C1", 12.9385, 77.6155);
        Location c2 = new Location("C2", 12.9390, 77.6162);

        Order o1 = new Order("O1", r1, c1, 0.25);
        Order o2 = new Order("O2", r2, c2, 0.20);

        IDistanceCalculator calc = new HaversineCalculator();
        IRoutePlanner planner = new GraphRoutePlanner(calc);
        IRoutePlanner.RouteResult result = planner.findOptimalRoute(aman, List.of(o1, o2));

        assertNotNull(result);
        assertEquals(4, result.path.size());
        assertTrue(result.totalTime > 0);
    }

    @Test
    public void testEqualPreparationTimes() {
        Location aman = new Location("Aman", 12.94, 77.60);
        Location r1 = new Location("R1", 12.95, 77.61);
        Location r2 = new Location("R2", 12.96, 77.62);
        Location c1 = new Location("C1", 12.97, 77.63);
        Location c2 = new Location("C2", 12.98, 77.64);

        Order o1 = new Order("O1", r1, c1, 0.2);
        Order o2 = new Order("O2", r2, c2, 0.2);

        IRoutePlanner planner = new GraphRoutePlanner(new HaversineCalculator());
        IRoutePlanner.RouteResult result = planner.findOptimalRoute(aman, List.of(o1, o2));

        assertEquals(4, result.path.size());
        assertTrue(result.totalTime >= 0.2);
    }

    @Test
    public void testLongPreparationAtOneRestaurant() {
        Location aman = new Location("Aman", 12.90, 77.60);
        Location r1 = new Location("R1", 12.91, 77.61);
        Location r2 = new Location("R2", 12.92, 77.62);
        Location c1 = new Location("C1", 12.93, 77.63);
        Location c2 = new Location("C2", 12.94, 77.64);

        Order o1 = new Order("O1", r1, c1, 0.5);  // 30 mins
        Order o2 = new Order("O2", r2, c2, 0.1);  // 6 mins

        IRoutePlanner planner = new GraphRoutePlanner(new HaversineCalculator());
        IRoutePlanner.RouteResult result = planner.findOptimalRoute(aman, List.of(o1, o2));

        assertEquals(4, result.path.size());
        assertTrue(result.totalTime >= 0.5);
    }

    @Test
    public void testZeroPreparationTimes() {
        Location aman = new Location("Aman", 12.91, 77.61);
        Location r1 = new Location("R1", 12.912, 77.611);
        Location r2 = new Location("R2", 12.913, 77.612);
        Location c1 = new Location("C1", 12.914, 77.613);
        Location c2 = new Location("C2", 12.915, 77.614);

        Order o1 = new Order("O1", r1, c1, 0.0);
        Order o2 = new Order("O2", r2, c2, 0.0);

        IRoutePlanner planner = new GraphRoutePlanner(new HaversineCalculator());
        IRoutePlanner.RouteResult result = planner.findOptimalRoute(aman, List.of(o1, o2));

        assertEquals(4, result.path.size());
        assertTrue(result.totalTime > 0.0);
    }

    @Test
    public void testFarApartLocations() {
        Location aman = new Location("Aman", 12.90, 77.60);
        Location r1 = new Location("R1", 13.00, 77.70);
        Location r2 = new Location("R2", 13.10, 77.80);
        Location c1 = new Location("C1", 13.20, 77.90);
        Location c2 = new Location("C2", 13.30, 78.00);

        Order o1 = new Order("O1", r1, c1, 0.25);
        Order o2 = new Order("O2", r2, c2, 0.25);

        IRoutePlanner planner = new GraphRoutePlanner(new HaversineCalculator());
        IRoutePlanner.RouteResult result = planner.findOptimalRoute(aman, List.of(o1, o2));

        assertEquals(4, result.path.size());
        assertTrue(result.totalTime > 1.0);  // because they are far apart
    }
}