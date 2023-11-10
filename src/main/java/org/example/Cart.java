package org.example;

import java.util.List;

public class Cart {
    private List<Mat> mats;
    private String position;
    private boolean isMoving;

    private String characteristic;

    @Override
    public String toString() {
        return "A cart with " + this.getCharacteristic() + " and " + this.getMats().size() + " mats, at " + this.getPosition() + (this.isMoving() ? ", moving." : ", standing still.");
    }

    public List<Mat> getMats() {
        return mats;
    }

    public void setMats(List<Mat> mats) {
        this.mats = mats;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }
}
