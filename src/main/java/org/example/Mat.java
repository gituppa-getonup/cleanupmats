package org.example;

public class Mat {

    @Override
    public String toString()
    {
        return this.getColorUp().concat(", ").concat(this.isDamaged() ? "damaged" : "undamaged");
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public void setDamaged(boolean damaged) {
        isDamaged = damaged;
    }

    private boolean isDamaged;


    private String colorUp;

    public String getColorUp() {
        return colorUp;
    }

    public void setColorUp(String colorUp) {
        this.colorUp = colorUp;
    }
}
