package org.example;

import java.util.List;

public class Floor {
    private List<FloorPiece> floorPieces;

    public List<FloorPiece> getFloorPieces() {
        return floorPieces;
    }

    public void setFloorPieces(List<FloorPiece> floorPieces) {
        this.floorPieces = floorPieces;
    }

    @Override
    public String toString() {
        return "A floor with " + this.getFloorPieces().size() + " pieces.";

    }
}
