package org.example;

import java.util.List;
import java.util.Optional;

public class Person {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " with a capacity of " + this.personalLimit + " mats";
    }

    private int personalLimit;
    private List<Mat> personalStack;

    public List<Mat> getPersonalStack() {
        return personalStack;
    }

    public void setPersonalStack(List<Mat> personalStack) {
        this.personalStack = personalStack;
    }

    public int getPersonalLimit() {
        return personalLimit;
    }

    public void setPersonalLimit(int personalLimit) {
        this.personalLimit = personalLimit;
    }


    public void startLayingMats(Main m) {
        boolean finished = m.getFloor().getFloorPieces().stream().noneMatch(floorPiece -> floorPiece.getMat() == null) || m.getCarts().stream().allMatch(aCart -> aCart.getMats().isEmpty());

        while (!finished) {
            Cart cart = m.getCarts().stream().filter(aCart -> !aCart.isMoving() && aCart.getPosition() == "floor" && !aCart.getMats().isEmpty()).findAny().or(() -> this.getCartFromStorage(m)).orElse(null);

            if (cart == null) {
                if (m.getVerbose()) {
                    System.out.println(this.getName() + " can't find anything to do.");
                }

                finished = true;
                continue;
            }


            int numberOfMatsToGetFromCart = Math.min(this.getPersonalLimit() - this.getPersonalStack().size(), cart.getMats().size());
            if (m.getVerbose() && numberOfMatsToGetFromCart > 0) {
                System.out.println(this.getName() + " is picking " + numberOfMatsToGetFromCart + " mats from a cart " + cart.getCharacteristic());
            }
            for (int i = 0; i < numberOfMatsToGetFromCart; i++) {
                Mat mat = cart.getMats().remove(0);
                this.getPersonalStack().add(mat);
                if (m.getVerbose() && mat.isDamaged()) {
                    System.out.println(this.getName() + " picked up a damaged mat.");
                }
            }

            if (m.getVerbose()) {
                System.out.println(this.getName() + " has a personal limit of " + this.getPersonalLimit() + " and is now carrying " + this.getPersonalStack().size() + " mats.");
            }

            this.layDownMats(m);

            finished = m.getFloor().getFloorPieces().stream().noneMatch(floorPiece -> floorPiece.getMat() == null) || m.getCarts().stream().allMatch(aCart -> aCart.getMats().isEmpty());
            if (m.getVerbose() && finished) {
                System.out.println(this.getName() + " is finished.");
            }
        }
    }

    public Optional<Cart> getCartFromStorage(Main m) {
        if (m.getVerbose()) {
            System.out.println(this.getName() + " is looking for a cart in the storage.");
        }

        Optional<Cart> possibleCart = m.getCarts().stream().filter(c -> c.getPosition().equals("storage")).findAny();

        if (m.getVerbose()) {
            System.out.println(possibleCart.isPresent() ? "Found one with " + possibleCart.get().getCharacteristic() : "No cart.");
        }

        possibleCart.ifPresent(cart -> {
            cart.setMoving(true);
            cart.setPosition("floor");
            cart.setMoving(false);
        });
        return possibleCart;
    }

    public void layDownMats(Main m) {
        if (m.getVerbose()) {
            this.personalStack.stream().filter(Mat::isDamaged).forEach(dm -> System.out.println(this.getName() + " found a damaged mat."));
        }
        this.getPersonalStack().removeIf(Mat::isDamaged);

        for (int i = 0; i < this.getPersonalStack().size(); i++) {
            Mat mat = this.getPersonalStack().remove(0);
            Optional<FloorPiece> availableFloorPiece = m.getFloor().getFloorPieces().stream().filter(floorPiece -> floorPiece.getMat() == null).findAny();
            if (availableFloorPiece.isPresent()) {
                mat.setColorUp(availableFloorPiece.get().getDesiredColor());
                availableFloorPiece.get().setMat(mat);
            }
        }
    }


}
