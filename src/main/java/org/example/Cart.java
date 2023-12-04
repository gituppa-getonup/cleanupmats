package org.example;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Cart {
    private List<Mat> mats;
    private String position;

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

    private boolean isMoving = false;

    private ReentrantLock lock = new ReentrantLock();

    public boolean isMoving() {
        return isMoving;
    }

    public synchronized boolean lockIfAvailable() {
        if (!lock.isLocked()) {
            if (!this.isMoving) {
                try {
                    return lock.tryLock(5, TimeUnit.SECONDS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    public Cart setMoving(boolean moving) {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (lock.isLocked() && this.isMoving && !moving) {
            lock.unlock();
        }
        this.isMoving = moving;
        return this;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }
}
