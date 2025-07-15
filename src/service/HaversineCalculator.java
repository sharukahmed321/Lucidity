package service;

import model.Location;

public class HaversineCalculator implements IDistanceCalculator {
    private static final double EARTH_RADIUS = 6371.0;
    private static final double AVERAGE_SPEED_KMPH = 20.0;

    @Override
    public double calculateDistance(Location a, Location b) {
        double dLat = Math.toRadians(b.getLatitude() - a.getLatitude());
        double dLon = Math.toRadians(b.getLongitude() - a.getLongitude());
        double lat1 = Math.toRadians(a.getLatitude());
        double lat2 = Math.toRadians(b.getLatitude());

        double aVal = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(aVal), Math.sqrt(1 - aVal));
        return EARTH_RADIUS * c;
    }

    @Override
    public double calculateTime(Location a, Location b) {
        return calculateDistance(a, b) / AVERAGE_SPEED_KMPH;
    }
}
