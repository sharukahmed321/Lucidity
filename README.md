# Graph-Based Delivery Route Optimizer

This project solves a real-world food delivery routing problem using **graph algorithms** in Java. The goal is to minimize total delivery time by determining the optimal path for a delivery agent (e.g., Aman) who picks up and delivers multiple food orders.

---

## Problem Overview

A delivery agent starts at a fixed location and needs to:
- Pick up orders from two restaurants (R1, R2)
- Deliver them to their respective consumers (C1, C2)
- Wait if the order is not ready (based on preparation time)
- Ensure that each order is delivered **after** its pickup

The objective is to find the **fastest route** that respects all constraints.

---

## Approach

We model the delivery scenario as a **fully connected graph** where:
- Nodes represent real-world locations (`Location`)
- Edges represent travel time calculated via the **Haversine formula**
- `GraphNode` and `Edge` classes model the graph structure

We generate all valid delivery permutations and select the one with the **shortest total time** (including travel and wait time).

---

## Features

- Graph-based routing with nodes & edges
- Travel time calculated using geographic coordinates
- Respects preparation time at restaurants
- Disallows invalid routes (e.g., delivery before pickup)
- Fully tested with JUnit test cases
- Modular design with interfaces and strategy pattern
- Clean logging via decorator
- Easily extendable for N orders or alternate routing strategies

---

## Tech Stack

- Java 17+
- JUnit 5
- Maven / Gradle (optional)
- Haversine distance formula

---

## Project Structure

lucidity
├── model
│   ├── Location.java
│   ├── Order.java
├── graph
│   ├── GraphNode.java
│   ├── Edge.java
├── service
│   ├── IDistanceCalculator.java
│   ├── IRoutePlanner.java
│   ├── HaversineCalculator.java
│   ├── GraphRoutePlanner.java
│   ├── RoutePlannerFactory.java
│   └── LoggingDistanceCalculator.java
├── test
│   └── GraphRoutePlannerTest.java
└── Main.java

## RUN

Compile the project and open the main class and execute the main function to get the output. 
