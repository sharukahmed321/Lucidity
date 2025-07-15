package service;

import model.Location;

public interface IDistanceCalculator {
    double calculateDistance(Location a, Location b);
    double calculateTime(Location a, Location b);
}
