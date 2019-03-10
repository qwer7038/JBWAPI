// Original work Copyright (c) 2015, 2017, Igor Dimitrijevic
// Modified work Copyright (c) 2017-2018 OpenBW Team

//////////////////////////////////////////////////////////////////////////
//
// This file is part of the BWEM Library.
// BWEM is free software, licensed under the MIT/X11 License.
// A copy of the license is provided with the library in the LICENSE file.
// Copyright (c) 2015, 2017, Igor Dimitrijevic
//
//////////////////////////////////////////////////////////////////////////

package bwem.map;

import bwapi.Position;
import bwapi.TilePosition;
import bwapi.WalkPosition;

import java.util.ArrayList;
import java.util.List;

public class MapDataImpl implements MapData {
    private final TilePosition tileSize;
    private final WalkPosition walkSize;
    private final Position pixelSize;
    private final Position center;
    private final List<TilePosition> startingLocations;

    public MapDataImpl(
            final int tileWidth, final int tileHeight, final List<TilePosition> startingLocations) {

        this.tileSize = new TilePosition(tileWidth, tileHeight);
        this.walkSize = this.tileSize.toWalkPosition();
        this.pixelSize = this.tileSize.toPosition();

        this.center = new Position(this.pixelSize.getX() / 2, this.pixelSize.getY() / 2);

        this.startingLocations = new ArrayList<>(startingLocations);
    }

    @Override
    public TilePosition getTileSize() {
        return this.tileSize;
    }

    @Override
    public WalkPosition getWalkSize() {
        return this.walkSize;
    }

    @Override
    public Position getPixelSize() {
        return this.pixelSize;
    }

    @Override
    public Position getCenter() {
        return this.center;
    }

    @Override
    public List<TilePosition> getStartingLocations() {
        return this.startingLocations;
    }

    @Override
    public boolean isValid(final TilePosition tilePosition) {
        return isValid(
                tilePosition.getX(), tilePosition.getY(), getTileSize().getX(), getTileSize().getY());
    }

    @Override
    public boolean isValid(final WalkPosition walkPosition) {
        return isValid(
                walkPosition.getX(), walkPosition.getY(), getWalkSize().getX(), getWalkSize().getY());
    }

    @Override
    public boolean isValid(final Position position) {
        return isValid(position.getX(), position.getY(), getPixelSize().getX(), getPixelSize().getY());
    }

    private boolean isValid(final int x, final int y, final int maxX, final int maxY) {
        return (x >= 0 && x < maxX && y >= 0 && y < maxY);
    }
}
