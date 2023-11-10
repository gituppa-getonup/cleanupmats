package org.example;

import java.util.ArrayList;

public class FloorHelper {

    public Floor generateFloorWithFloorPieces(int width, int length) {
        Floor floor = new Floor();
        floor.setFloorPieces(new ArrayList<>());

        for (int w = 0; w < width; w++) {
            for (int l = 0; l < length; l++) {
                FloorPiece floorPiece = new FloorPiece();
                floorPiece.setPosX(w);
                floorPiece.setPosY(l);
                floorPiece.setDesiredColor(getColorFromPosition(w, l));
                floor.getFloorPieces().add(floorPiece);
            }
        }
        return floor;
    }

    private String getColorFromPosition(int posX, int posY) {
        if (posX == 0 || posX == 10) {
            return "blue";
        }

        if (posY == 0 || posY == 10) {
            return "blue";
        }

        if (posX == 5 || posY == 5) {
            return "red";
        }

        if (posX == 1 || posX == 9) {
            return "red";
        }

        if (posY == 1 || posY == 9) {
            return "red";
        }

        return "blue";
    }
}
