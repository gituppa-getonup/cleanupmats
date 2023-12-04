package org.example;

import java.util.List;
import java.util.Optional;

public class Person implements Runnable {

    private Main m;

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

    @Override
    public void run() {
        startLayingMats();
    }

    public void startLayingMats() {
        boolean finished = this.m.getFloor().getFloorPieces().stream()
                .noneMatch(floorPiece -> floorPiece.getMat() == null) // all floor pieces have a mat...
                || this.m.getCarts().stream().allMatch(aCart -> aCart.getMats().isEmpty()); // or all carts are empty.

        while (!finished) {
            Cart cart = this.getCartFromStorage()
                    .or(() -> this.getCartFromFloor())
                    .orElse(null);

            if (cart == null) {
                if (this.m.getVerbose()) {
                    System.out.println(this.getName() + " is waiting for an available cart that is standing still on the floor.");
                }

                // try again in 10 secs.
//                try {
//                    Thread.sleep(1000L);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }

                if (this.m.getVerbose()) {
                    System.out.println(this.getName() + " is done waiting.");
                }

                continue;
            }


            int numberOfMatsToGetFromCart = Math.min(this.getPersonalLimit() - this.getPersonalStack().size(), cart.getMats().size());

            if (this.m.getVerbose() && numberOfMatsToGetFromCart > 0) {
                System.out.println(this.getName() + " is picking " + numberOfMatsToGetFromCart + " mats from a cart " + cart.getCharacteristic());
            }

            for (int i = 0; i < numberOfMatsToGetFromCart; i++) {
                Mat mat = cart.getMats().remove(0);
                this.getPersonalStack().add(mat);
                if (this.m.getVerbose() && mat.isDamaged()) {
                    System.out.println(this.getName() + " picked up a damaged mat.");
                }
            }

            if (this.m.getVerbose()) {
                System.out.println(this.getName() + " has a personal limit of " + this.getPersonalLimit() + " and is now carrying " + this.getPersonalStack().size() + " mats.");
            }

            this.layDownMats();

            finished = this.m.getFloor().getFloorPieces().stream().noneMatch(floorPiece -> floorPiece.getMat() == null) || this.m.getCarts().stream().allMatch(aCart -> aCart.getMats().isEmpty());
            if (this.m.getVerbose() && finished) {
                System.out.println(this.getName() + " is finished.");
            }
        }
    }

    public Optional<Cart> getCartFromStorage() {
        if (this.m.getVerbose()) {
            System.out.println(this.getName() + " is looking for a cart in the storage.");
        }

        Optional<Cart> possibleCart = this.m.getCarts().stream().filter(c -> c.getPosition().equals("storage") && c.lockIfAvailable()).findAny();

        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Cart cart = null;
        if (possibleCart.isPresent()) {
            cart = possibleCart.get().setMoving(true);
            cart.setPosition("floor");
            cart.setMoving(false);
        }

        if (this.m.getVerbose()) {
            System.out.println(this.getName() + " found " + (cart != null ? "a cart with " + cart.getCharacteristic() : "no cart") + " in the storage.");
        }

        return Optional.ofNullable(cart);
    }

    public Optional<Cart> getCartFromFloor() {
        if (this.m.getVerbose()) {
            System.out.println(this.getName() + " is looking for a cart on the floor.");
        }

        Cart cart = this.m.getCarts().stream().filter(c -> c.getPosition().equals("floor") && !c.isMoving() && !c.getMats().isEmpty()).findAny().get();

        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Optional.of(cart);
    }

    public void layDownMats() {
        int personalStackSize = this.getPersonalStack().size();
        for (int i = 0; i < personalStackSize; i++) {
            Mat mat = this.getPersonalStack().remove(0);
            if (mat.isDamaged()) {
                if (this.m.getVerbose()) {
                    System.out.println(this.getName() + " found a damaged mat.");
                }
                continue;
            }

            Optional<FloorPiece> availableFloorPiece = this.getavailableFloorPiece();
            if (availableFloorPiece.isPresent()) {
                availableFloorPiece.get().tryToGetLock();
                mat.setColorUp(availableFloorPiece.get().getDesiredColor());
                if (m.getVerbose()) {
                    System.out.println(this.getName() + " lays down a " + mat.getColorUp() + " mat on position " + availableFloorPiece.get().getPosX() + ", " + availableFloorPiece.get().getPosY() + ".");
                }
                availableFloorPiece.get().setMatWithValidation(mat);
            }
        }
    }

    public Optional<FloorPiece> getavailableFloorPiece() {
        return this.m.getFloor().getFloorPieces().stream().filter(floorPiece -> floorPiece.getMat() == null && floorPiece.tryToGetLock()).findAny();
    }

    public void setM(Main m) {
        this.m = m;
    }
}
