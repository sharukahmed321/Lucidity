package service;

import model.Location;
import model.Order;

import java.util.List;

public interface IRoutePlanner {
    RouteResult findOptimalRoute(Location start, List<Order> orders);

    class RouteResult {
        public final List<Location> path;
        public final double totalTime;

        public RouteResult(List<Location> path, double totalTime) {
            this.path = path;
            this.totalTime = totalTime;
        }
    }
}
