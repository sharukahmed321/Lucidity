package model;

public class Order {
    private final String id;
    private final Location restaurant;
    private final Location consumer;
    private final double preparationTimeHours;

    public Order(String id, Location restaurant, Location consumer, double preparationTimeHours) {
        this.id = id;
        this.restaurant = restaurant;
        this.consumer = consumer;
        this.preparationTimeHours = preparationTimeHours;
    }

    public String getId() { return id; }
    public Location getRestaurant() { return restaurant; }
    public Location getConsumer() { return consumer; }
    public double getPreparationTimeHours() { return preparationTimeHours; }
}

