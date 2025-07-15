package service;

import model.Location;

public class LoggingDistanceCalculator implements IDistanceCalculator {
    private final IDistanceCalculator wrapped;

    public LoggingDistanceCalculator(IDistanceCalculator wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public double calculateDistance(Location a, Location b) {
        double d = wrapped.calculateDistance(a, b);
        System.out.printf("Distance [%s -> %s]: %.3f km%n", a.getName(), b.getName(), d);
        return d;
    }

    @Override
    public double calculateTime(Location a, Location b) {
        double t = wrapped.calculateTime(a, b);
        System.out.printf("Time [%s -> %s]: %.3f hrs%n", a.getName(), b.getName(), t);
        return t;
    }
}
