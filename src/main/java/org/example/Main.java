package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {

    private int width = 11;
    private int length = 11;

    private final boolean verbose = true;

    public boolean getVerbose() {
        return this.verbose;
    }

    private Floor floor;

    public Floor getFloor() {
        return this.floor;
    }

    private List<Cart> carts;

    public List<Cart> getCarts() {
        return this.carts;
    }

    private List<Person> people;

    public List<Person> getPeople() {
        return this.people;
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.initialize();
        m.doSomething();
    }

    public void initialize() {
        FloorHelper floorHelper = new FloorHelper();
        this.floor = floorHelper.generateFloorWithFloorPieces(this.width, this.length);

        if (this.getVerbose()) {
            System.out.println(floor);
            floor.getFloorPieces().forEach(System.out::println);
        }

        CartHelper cartHelper = new CartHelper();
        Cart cartOne = cartHelper.generateFilledCart(75, "a wonky wheel");
        Cart cartTwo = cartHelper.generateFilledCart(80, "a foam bar");

        this.carts = new ArrayList<>();
        this.carts.add(cartOne);
        this.carts.add(cartTwo);

        if (this.getVerbose()) {
            this.carts.stream().forEach(System.out::println);
        }

        PersonHelper personHelper = new PersonHelper();
        this.people = personHelper.generatePeople(this);

        if (this.getVerbose()) {
            this.people.stream().forEach(person -> System.out.println(person.toString() + " walked in."));
        }
    }

    public void doSomething() {
        if (!this.validateProcess()) {
            return;
        }

        ExecutorService es = Executors.newFixedThreadPool(2);

        try {
            people.stream().forEach(person -> es.submit(() -> person.run()));
        } finally {
            es.shutdown();
        }
    }


    public boolean validateProcess() {
        if (this.people == null || this.people.isEmpty()) {
            System.out.println("Nobody here to lay mats");
            return false;
        }

        if (this.carts == null || this.carts.isEmpty()) {
            System.out.println("No carts available");
            return false;
        }

        boolean matsAvailable = this.carts.stream().flatMap(cart -> cart.getMats().stream()).findAny().isPresent();
        if (!matsAvailable) {
            System.out.println("Only empty carts available");
            return false;
        }
        return true;
    }
}