package org.example;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CartHelper {
    public Cart generateFilledCart(int numberOfMats, String characteristic)
    {
        Cart cart = new Cart();
        cart.setCharacteristic(characteristic);
        IntStream ints = IntStream.range(0, numberOfMats);

        List<Mat> mats = ints.mapToObj(i -> {
            Mat mat = new Mat();
            mat.setColorUp(Math.random() < 0.5 ? "blue" : "red");
            mat.setDamaged(Math.random() < 0.01 ? true : false);
            return mat;
        }).collect(Collectors.toList());

        cart.setMats(mats);
        cart.setPosition("storage");
        return cart;
    }
}
