package org.example;

public class FloorPiece {

    @Override
    public String toString() {
        return "A floorpiece with" + (this.mat == null ? "out" : "") + " a mat at " + this.getPosX() + ", " + this.getPosY() + ".";
    }

    private Mat mat;

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    private int posX;
    private int posY;

    private String desiredColor;

    public String getDesiredColor() {
        return desiredColor;
    }

    public void setDesiredColor(String desiredColor) {
        this.desiredColor = desiredColor;
    }

    public Mat getMat() {
        return mat;
    }

    public void setMat(Mat mat) {
        this.mat = mat;
    }

    public void setMatWithValidation(Mat mat)
    {
        if (this.mat == null)
        {
            this.mat = mat;
        } else {
            System.out.println("Can't place a mat when there's already one there at position " + this.getPosX() + ", " + this.getPosY() + ".");
        }

    }
}
