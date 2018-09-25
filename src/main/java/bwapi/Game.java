package bwapi;

import JavaBWAPIBackend.Client.GameData;
import bwapi.point.Position;
import bwapi.point.TilePosition;
import bwapi.point.WalkPosition;
import bwapi.types.GameType;
import bwapi.types.TechType;
import bwapi.types.UnitType;
import bwapi.types.UpgradeType;
import bwapi.values.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static bwapi.types.Race.Zerg;
import static bwapi.types.UnitType.*;

public class Game {
    final GameData gameData;

    ///CONSTANT
    final Map<Integer, Player> players = new HashMap<>();
    final Map<Integer, Region> regions = new HashMap<>();
    final Map<Integer, Force> forces = new HashMap<>();

    //CHANGING
    final Map<Integer, Unit> units = new HashMap<>();

    final Map<Integer, Bullet> bullets = new HashMap<>();

    public Game(GameData gameData) {
        this.gameData = gameData;
        init();
    }

    private void init() {
        for (int id=0; id < gameData.getForceCount(); id++) {
            forces.put(id, new Force(gameData.getForce(id), this));
        }
        for (int id=0; id < gameData.getPlayerCount(); id++) {
            players.put(id, new Player(gameData.getPlayer(id), this));
        }
        for (int id=0; id < gameData.regionCount(); id++) {
            regions.put(id, new Region(gameData.getRegion(id), this));
        }
    }

    public Collection<Force> getForces() {
        return forces.values();
    }

    public Collection<Player> getPlayers() {
        return players.values();
    }


    public Collection<Unit> getAllUnits() {
        Set<Unit> units = new HashSet<>();
        for (int id=0; id < gameData.getInitialUnitCount(); id++) {
            units.add(new Unit(gameData.unit(id), this));
        }
        return units;
        //return units.values();
    }

    //TODO
    public List<Unit> getMinerals() {
        return null;
    }

    //TODO
    public List<Unit> getGeysers() {
        return null;
    }

    //TODO
    public List<Unit> getNeutralUnits() {
        return null;
    }

    //TODO
    public List<Unit> getStaticMinerals() {
        return null;
    }

    //TODO
    public List<Unit> getStaticGeysers() {
        return null;
    }

    //TODO
    public List<Unit> getStaticNeutralUnits() {
        return null;
    }


    public Collection<Bullet> getBullets() {
        //TODO cache this in onFrame
        final List<Bullet> bullets = new ArrayList<>();
        for (int i=0; i < gameData.bulletCount(); i++) {
            bullets.add(new Bullet(gameData.bullet(i), this));
        }
        return bullets;
    }


    public Set<Position> getNukeDots() {
        return IntStream.range(0, gameData.nukeDotCount())
                .mapToObj(id -> new Position(gameData.getNukeDotX(id), gameData.getNukeDotY((id))))
                .collect(Collectors.toSet());
    }


    public Force getForce(final int forceID) {
        return forces.get(forceID);
    }

    public Player getPlayer(final int playerID) {
        return players.get(playerID);
    }

    public Unit getUnit(final int unitID) {
        return units.get(unitID);
    }

    //TODO
    //public Unit indexToUnit(int unitIndex);

    public Region getRegion(final int regionID) {
        return regions.get(regionID);
    }

    public GameType getGameType() {
        return GameType.gameTypes[gameData.gameType()];
    }

    public int getLatency() {
        return gameData.latency();
    }

    public int getFrameCount() {
        return gameData.frameCount();
    }

    public int getReplayFrameCount() {
        return gameData.replayFrameCount();
    }

    public int getFPS() {
        return gameData.fps();
    }

    public double getAverageFPS() {
        return gameData.averageFPS();
    }

     public Position getMousePosition() {
        return new Position(gameData.mouseX(), gameData.mouseY());
     }

     public boolean getMouseState(final MouseButton button) {
        return gameData.mouseState(button.value);
     }

     public boolean getKeyState(final Key key) {
        return gameData.keyState(key.value);
     }

     public Position getScreenPosition() {
        return new Position(gameData.screenX(), gameData.screenY());
     }

     //TODO
     //public void setScreenPosition(final int x, final int y);

    //public void setScreenPosition(final Position p);

    //public void pingMinimap(final int x, final int y);

    //public void pingMinimap(final Position p);

     public boolean isFlagEnabled(final Flag flag) {
        return gameData.getFlag(flag.value);
     }

     public void enableFlag(final Flag flag) {
        gameData.setFlag(flag.value, true);
     }

     //TODO
//     public List<Unit> getUnitsOnTile(int tileX, int tileY);
//
//     public List<Unit> getUnitsOnTile(TilePosition tile);
//
    //TODO
     public Set<Unit> getUnitsInRectangle(int left, int top, int right, int bottom, final UnitFilter filter) {
        return null;
     }

    public Set<Unit> getUnitsInRectangle(final Position leftTop, final Position rightBottom, final UnitFilter filter) {
        return getUnitsInRectangle(leftTop.x, leftTop.y, rightBottom.x, rightBottom.y, filter);
    }
//
//
//     public List<Unit> getUnitsInRadius(int x, int y, int radius);
//
//     public List<Unit> getUnitsInRadius(Position center, int radius);

     public int mapWidth() {
        return gameData.mapWidth();
     }

     public int mapHeight() {
        return gameData.mapHeight();
     }

     public String mapFileName() {
        return gameData.mapFileName();
     }

     public String mapPathName() {
        return gameData.mapPathName();
     }

     public String mapName() {
        return gameData.mapName();
     }

     public String mapHash() {
        return gameData.mapHash();
     }

     public boolean isWalkable(final int walkX, final int walkY) {
        //TODO bounds check
        return gameData.walkable(walkX, walkY);
     }

     public boolean isWalkable(final WalkPosition position) {
        return isWalkable(position.x, position.y);
     }

     public int getGroundHeight(final int tileX, final int tileY) {
         //TODO bounds check
         return gameData.groundHeight(tileX, tileY);
     }

     public int getGroundHeight(final TilePosition position) {
        return getGroundHeight(position.x, position.y);
     }

     public boolean isBuildable(final int tileX, final int tileY) {
         //TODO bounds check
         return gameData.buildable(tileX, tileY);
     }

     public boolean isBuildable(final int tileX, final int tileY, final boolean includeBuildings) {
        //TODO add building checks
         //https://github.com/bwapi/bwapi/blob/456ad612abc84da4103162ba0bf8ec4f053a4b1d/bwapi/BWAPIClient/Source/GameImpl.cpp#L589
        return isBuildable(tileX, tileY);
     }

     public boolean isBuildable(final TilePosition position) {
        return isBuildable(position.x, position.y);
     }

     public boolean isBuildable(final TilePosition position, final boolean includeBuildings) {
        return isBuildable(position.x, position.y, includeBuildings);
     }

     public boolean isVisible(final int tileX, final int tileY) {
         //TODO add bound checks
         return gameData.visible(tileX, tileY);
     }

     public boolean isVisible(final TilePosition position) {
        return isVisible(position.x, position.y);
     }

     public boolean isExplored(final int tileX, final int tileY) {
         //TODO add bound checks
         return gameData.explored(tileX, tileY);
     }

     public boolean isExplored(final TilePosition position) {
        return isExplored(position.x, position.y);
     }

     public boolean hasCreep(final int tileX, final int tileY) {
         //TODO add bound checks
         return gameData.hasCreep(tileX, tileY);
     }

     public boolean hasCreep(final TilePosition position) {
        return hasCreep(position.x, position.y);
     }

    //TODO
    public boolean hasPowerPrecise(final int x, final int y) {
        return false;
     }
    //TODO
     public boolean hasPowerPrecise(final int x, final int y, final UnitType unitType){
         return false;
     }
    //TODO
     public boolean hasPowerPrecise(final Position position){
         return false;
     }
    //TODO
     public boolean hasPowerPrecise(final Position position, final UnitType unitType){
         return false;
     }
    //TODO
     public boolean hasPower(final int tileX, final int tileY){
         return false;
     }
    //TODO
     public boolean hasPower(final int tileX, final int tileY, final UnitType unitType){
         return false;
     }
    //TODO
     public boolean hasPower(final TilePosition position){
         return false;
     }
    //TODO
     public boolean hasPower(final TilePosition position, final UnitType unitType){
         return false;
     }
    //TODO
     public boolean hasPower(final int tileX, final int tileY, final int tileWidth, final int tileHeight){
         return false;
     }
    //TODO
     public boolean hasPower(final int tileX, final int tileY, final int tileWidth, final int tileHeight, final UnitType unitType){
         return false;
     }
    //TODO
     public boolean hasPower(final TilePosition position, final int tileWidth, final int tileHeight){
         return false;
     }
    //TODO
     public boolean hasPower(final TilePosition position, final int tileWidth, final int tileHeight, final UnitType unitType){
         return false;
     }

     public boolean canBuildHere(final TilePosition position, final UnitType type, final Unit builder) {
         return canBuildHere(position, type, builder, false);
     }

     public boolean canBuildHere(final TilePosition position, final UnitType type) {
         return canBuildHere(position, type, null);
     }

     public boolean canBuildHere(final TilePosition position, final UnitType type, final Unit builder, final boolean checkExplored) {
         // lt = left top, rb = right bottom
         final TilePosition lt = (builder != null && type.isAddon()) ?
                 position.add(new TilePosition(4, 1)) : // addon build offset
                 position;
         final TilePosition rb = lt.add(type.tileSize());

         // Map limit check
         if ( !lt.isValid(this) || !(rb.toPosition().subtract(new Position(1,1)).isValid(this))) {
             return false;
         }

         //if the unit is a refinery, we just need to check the set of geysers to see if the position
         //matches one of them (and the type is still vespene geyser)
         if ( type.isRefinery() ) {
             for (final Unit g : getGeysers()) {
                 if (g.getTilePosition().equals(lt)) {
                     if (g.isVisible() && g.getType() != Resource_Vespene_Geyser)
                         return false;
                     return true;
                 }
             }
             return false;
         }

         // Tile buildability check
         for (int x = lt.x; x < rb.x; ++x ){
             for (int y = lt.y; y < rb.y; ++y ) {
                 // Check if tile is buildable/unoccupied and explored.
                 if (!isBuildable(x, y) || (checkExplored && !isExplored(x,y))) {
                     return false;
                 }
             }
         }

         // Check if builder is capable of reaching the building site
         if (builder != null) {
             if (!builder.getType().isBuilding()) {
                 if (!builder.hasPath(lt.toPosition().add(type.tileSize().toPosition().divide(2)))) {
                     return false;
                 }
             }
             else if (!builder.getType().isFlyingBuilding() && type != Zerg_Nydus_Canal && !type.isFlagBeacon()) {
                 return false;
             }
         }

         // Ground unit dimension check
         if (type != Special_Start_Location) {
             final Position targPos = lt.toPosition().add(type.tileSize().toPosition().divide(2));
             Set<Unit> unitsInRect = getUnitsInRectangle(lt.toPosition(), rb.toPosition(),
                     (u -> !u.isFlying() && !u.isLoaded() && builder != null || type == Zerg_Nydus_Canal
                             && u.getLeft() <= targPos.x + type.dimensionRight()
                             && u.getTop() <= targPos.y + type.dimensionDown()
                             && u.getRight() <= targPos.x + type.dimensionLeft()
                             && u.getBottom() <= targPos.y + type.dimensionUp()));

             for (Unit u : unitsInRect){
                 // Addons can be placed over units that can move, pushing them out of the way
                 if ( !(type.isAddon() && u.getType().canMove()) )
                     return false;
             }

             // Creep Check
             // Note: Zerg structures that don't require creep can still be placed on creep
             boolean needsCreep = type.requiresCreep();
             if (type.getRace() != Zerg || needsCreep) {
                 for (int x = lt.x; x < rb.x; ++x) {
                     for (int y = lt.y; y < rb.y; ++y) {
                         if (needsCreep != hasCreep(x, y)) {
                             return false;
                         }
                     }
                 }
             }

             // Power Check
             if (type.requiresPsi() && hasPower(lt, type)) {
                 return false;
             }

         } //don't ignore units

         // Resource Check (CC, Nex, Hatch)
         if (type.isResourceDepot())  {
             for (final Unit m : getStaticMinerals()) {
                 final TilePosition tp = m.getInitialTilePosition();
                 if ( (isVisible(tp) || isVisible(tp.x + 1, tp.y)) && !m.isVisible()) {
                     continue; // tile position is visible, but mineral is not => mineral does not exist
                 }
                 if (tp.x > lt.x - 5 &&
                         tp.y > lt.y - 4 &&
                         tp.x < lt.x + 7 &&
                         tp.y < lt.y + 6) {
                     return false;
                 }
             }
             for (final Unit g : getStaticGeysers()) {
                 final TilePosition tp = g.getInitialTilePosition();
                 if (tp.x > lt.x - 7 &&
                         tp.y > lt.y - 5 &&
                         tp.x < lt.x + 7 &&
                         tp.y < lt.y + 6) {
                     return false;
                 }
             }
         }

         // A building can build an addon at a different location (i.e. automatically lifts (if not already lifted)
         // then lands at the new location before building the addon), so we need to do similar checks for the
         // location that the building will be when it builds the addon.
         if ( builder != null && !builder.getType().isAddon() && type.isAddon() ) {
             if (!canBuildHere(lt.subtract(new TilePosition(4, 1)), builder.getType(), builder, checkExplored)) {
                 return false;
             }
         }

         //if the build site passes all these tests, return true.
         return true;
     }

     public boolean canMake(final UnitType type) {
        return canMake(type, null);
     }

     //TODO
     public boolean canMake(final UnitType type, final Unit builder) {
        return false;
     }

     public boolean canResearch(final TechType type, final Unit unit) {
         return canResearch(type, unit, true);
     }

     public boolean canResearch(final TechType type) {
        return canResearch(type, null);
     }

     //TODO
     public boolean canResearch(final TechType type, final Unit unit, final boolean checkCanIssueCommandType) {
         return false;
     }

     public boolean canUpgrade(final UpgradeType type, final Unit unit) {
         return canUpgrade(type, unit, true);
     }

     public boolean canUpgrade(final UpgradeType type) {
         return canUpgrade(type, null);
     }

     //TODO
     public boolean canUpgrade(final UpgradeType type, final Unit unit, final boolean checkCanIssueCommandType) {
        return false;
     }

     public List<TilePosition> getStartLocations() {
         //TODO cache this?
         return IntStream.range(0, gameData.startLocationCount())
                 .mapToObj(i -> new TilePosition(gameData.startLocationX(i), gameData.startLocationY(i)))
                 .collect(Collectors.toList());
     }

     /*
     public void printf(final String cstr_format);

     public void sendText(final String cstr_format);

     public void sendTextEx(final boolean toAllies, final String cstr_format);
    */

     public boolean isInGame() {
         return gameData.isInGame();
     }

     public boolean isMultiplayer() {
         return gameData.isMultiplayer();
     }

     public boolean isBattleNet() {
         return gameData.isBattleNet();
     }

     public boolean isPaused() {
         return gameData.isPaused();
     }

     public boolean isReplay() {
         return gameData.isReplay();
     }

     //TODO
//     public void pauseGame();
//
//     public void resumeGame();
//
//     public void leaveGame();
//
//     public void restartGame();
//
//     public void setLocalSpeed(int speed);
//
//     public boolean issueCommand(final Collection<Unit> units, final UnitCommand command) {
//         units.forEach(u -> u.issueCommand(command));
//     }

     public Set<Unit> getSelectedUnits() {
         return getAllUnits().stream().filter(Unit::isSelected).collect(Collectors.toSet());
     }

    public Player self() {
        return players.get(gameData.self());
    }


    public Player enemy() {
        return players.get(gameData.enemy());
    }

    public Player neutral() {
        return players.get(gameData.neutral());
    }


    public Set<Player> allies() {
        final Player self = self();
        return getPlayers().stream()
                .filter(self::isAlly)
                .collect(Collectors.toSet());
    }

    public Set<Player> enemies() {
        final Player self = self();
        return getPlayers().stream()
                .filter(p -> !(p.isObserver() || p.isNeutral() || self.isAlly(p)))
                .collect(Collectors.toSet());
    }

    public Set<Player> observers() {
        return getPlayers().stream()
                .filter(Player::isObserver)
                .collect(Collectors.toSet());
    }


    public void setTextSize() {
        setTextSize(TextSize.Default);
    }

    public void setTextSize(final TextSize size) {
        //TODO
    }

    /*
    public void drawText(Coordinate ctype, int x, int y, String cstr_format);

    public void drawTextMap(int x, int y, String cstr_format);

    public void drawTextMap(Position p, String cstr_format);

    public void drawTextMouse(int x, int y, String cstr_format);

    public void drawTextMouse(Position p, String cstr_format);

    public void drawTextScreen(int x, int y, String cstr_format);

    public void drawTextScreen(Position p, String cstr_format);

    public void drawBox(Coordinate ctype, int left, int top, int right, int bottom, Color color);

    public void drawBox(Coordinate ctype, int left, int top, int right, int bottom, Color color, boolean isSolid);

    public void drawBoxMap(int left, int top, int right, int bottom, Color color);

    public void drawBoxMap(int left, int top, int right, int bottom, Color color, boolean isSolid);

    public void drawBoxMap(Position leftTop, Position rightBottom, Color color);

    public void drawBoxMap(Position leftTop, Position rightBottom, Color color, boolean isSolid);

    public void drawBoxMouse(int left, int top, int right, int bottom, Color color);

    public void drawBoxMouse(int left, int top, int right, int bottom, Color color, boolean isSolid);

    public void drawBoxMouse(Position leftTop, Position rightBottom, Color color);

    public void drawBoxMouse(Position leftTop, Position rightBottom, Color color, boolean isSolid);

    public void drawBoxScreen(int left, int top, int right, int bottom, Color color);

    public void drawBoxScreen(int left, int top, int right, int bottom, Color color, boolean isSolid);

    public void drawBoxScreen(Position leftTop, Position rightBottom, Color color);

    public void drawBoxScreen(Position leftTop, Position rightBottom, Color color, boolean isSolid);

    public void drawTriangle(Coordinate ctype, int ax, int ay, int bx, int by, int cx, int cy, Color color);

    public void drawTriangle(Coordinate ctype, int ax, int ay, int bx, int by, int cx, int cy, Color color, boolean isSolid);

    public void drawTriangleMap(int ax, int ay, int bx, int by, int cx, int cy, Color color);

    public void drawTriangleMap(int ax, int ay, int bx, int by, int cx, int cy, Color color, boolean isSolid);

    public void drawTriangleMap(Position a, Position b, Position c, Color color);

    public void drawTriangleMap(Position a, Position b, Position c, Color color, boolean isSolid);

    public void drawTriangleMouse(int ax, int ay, int bx, int by, int cx, int cy, Color color);

    public void drawTriangleMouse(int ax, int ay, int bx, int by, int cx, int cy, Color color, boolean isSolid);

    public void drawTriangleMouse(Position a, Position b, Position c, Color color);

    public void drawTriangleMouse(Position a, Position b, Position c, Color color, boolean isSolid);

    public void drawTriangleScreen(int ax, int ay, int bx, int by, int cx, int cy, Color color);

    public void drawTriangleScreen(int ax, int ay, int bx, int by, int cx, int cy, Color color, boolean isSolid);

    public void drawTriangleScreen(Position a, Position b, Position c, Color color);

    public void drawTriangleScreen(Position a, Position b, Position c, Color color, boolean isSolid);

    public void drawCircle(Coordinate ctype, int x, int y, int radius, Color color);

    public void drawCircle(Coordinate ctype, int x, int y, int radius, Color color, boolean isSolid);

    public void drawCircleMap(int x, int y, int radius, Color color);

    public void drawCircleMap(int x, int y, int radius, Color color, boolean isSolid);

    public void drawCircleMap(Position p, int radius, Color color);

    public void drawCircleMap(Position p, int radius, Color color, boolean isSolid);

    public void drawCircleMouse(int x, int y, int radius, Color color);

    public void drawCircleMouse(int x, int y, int radius, Color color, boolean isSolid);

    public void drawCircleMouse(Position p, int radius, Color color);

    public void drawCircleMouse(Position p, int radius, Color color, boolean isSolid);

    public void drawCircleScreen(int x, int y, int radius, Color color);

    public void drawCircleScreen(int x, int y, int radius, Color color, boolean isSolid);

    public void drawCircleScreen(Position p, int radius, Color color);

    public void drawCircleScreen(Position p, int radius, Color color, boolean isSolid);

    public void drawEllipse(Coordinate ctype, int x, int y, int xrad, int yrad, Color color);

    public void drawEllipse(Coordinate ctype, int x, int y, int xrad, int yrad, Color color, boolean isSolid);

    public void drawEllipseMap(int x, int y, int xrad, int yrad, Color color);

    public void drawEllipseMap(int x, int y, int xrad, int yrad, Color color, boolean isSolid);

    public void drawEllipseMap(Position p, int xrad, int yrad, Color color);

    public void drawEllipseMap(Position p, int xrad, int yrad, Color color, boolean isSolid);

    public void drawEllipseMouse(int x, int y, int xrad, int yrad, Color color);

    public void drawEllipseMouse(int x, int y, int xrad, int yrad, Color color, boolean isSolid);

    public void drawEllipseMouse(Position p, int xrad, int yrad, Color color);

    public void drawEllipseMouse(Position p, int xrad, int yrad, Color color, boolean isSolid);

    public void drawEllipseScreen(int x, int y, int xrad, int yrad, Color color);

    public void drawEllipseScreen(int x, int y, int xrad, int yrad, Color color, boolean isSolid);

    public void drawEllipseScreen(Position p, int xrad, int yrad, Color color);

    public void drawEllipseScreen(Position p, int xrad, int yrad, Color color, boolean isSolid);

    public void drawDot(Coordinate ctype, int x, int y, Color color);

    public void drawDotMap(int x, int y, Color color);

    public void drawDotMap(Position p, Color color);

    public void drawDotMouse(int x, int y, Color color);

    public void drawDotMouse(Position p, Color color);

    public void drawDotScreen(int x, int y, Color color);

    public void drawDotScreen(Position p, Color color);

    public void drawLine(Coordinate ctype, int x1, int y1, int x2, int y2, Color color);

    public void drawLineMap(int x1, int y1, int x2, int y2, Color color);

    public void drawLineMap(Position a, Position b, Color color);

    public void drawLineMouse(int x1, int y1, int x2, int y2, Color color);

    public void drawLineMouse(Position a, Position b, Color color);

    public void drawLineScreen(int x1, int y1, int x2, int y2, Color color);

    public void drawLineScreen(Position a, Position b, Color color);
    */
    public int getLatencyFrames() {
        return gameData.latencyFrames();
    }

    public int getLatencyTime() {
        return gameData.latencyTime();
    }

    public int getRemainingLatencyFrames() {
        return gameData.remainingLatencyFrames();
    }

    public int getRemainingLatencyTime() {
        return gameData.remainingLatencyTime();
    }

    public int getRevision() {
        return gameData.getRevision();
    }

    public boolean isDebug() {
        return gameData.isDebug();
    }

    public boolean isLatComEnabled() {
        return gameData.hasLatCom();
    }


    public void setLatCom(final boolean isEnabled) {
        gameData.setLatcom(isEnabled);
    }

    public int getInstanceNumber() {
        return gameData.getInstanceID();
    }

    public int getAPM(final boolean includeSelects) {
        return includeSelects ? gameData.getBotAPM_selects() : gameData.getBotAPM_noselects();
    }

    //TODO
    //public boolean setMap(final String cstr_mapFileName);

    //public void setFrameSkip(int frameSkip)

    //TODO
    public boolean hasPath(Position source, Position destination) {
        return false;
    }

//    public boolean setAlliance(Player player, boolean allied);
//
//    public boolean setAlliance(Player player);
//
//    public boolean setAlliance(Player player, boolean allied, boolean alliedVictory);
//
//    public boolean setVision(Player player);
//
//    public boolean setVision(Player player, boolean enabled);

    public int elapsedTime() {
        return gameData.elapsedTime();
    }

    //public void setCommandOptimizationLevel(int level)

    public int countdownTimer() {
        return gameData.countdownTimer();
    }

    public Collection<Region> getAllRegions() {
        return regions.values();
    }

    //TODO
    public Region getRegionAt(final int x, final int y) {
        return null;
    }

    //TODO
    public Region getRegionAt(final Position position) {
        return null;
    }

    /*
    public int getLastEventTime();

    public boolean setRevealAll();

    public boolean setRevealAll(boolean reveal);

    public TilePosition getBuildLocation(UnitType type, TilePosition desiredPosition, int maxRange);

    public TilePosition getBuildLocation(UnitType type, TilePosition desiredPosition);

    public TilePosition getBuildLocation(UnitType type, TilePosition desiredPosition, int maxRange, boolean creep);

    public int getDamageFrom(UnitType fromType, UnitType toType, Player fromPlayer);

    public int getDamageFrom(UnitType fromType, UnitType toType);

    public int getDamageFrom(UnitType fromType, UnitType toType, Player fromPlayer, Player toPlayer);

    public int getDamageTo(UnitType toType, UnitType fromType, Player toPlayer);

    public int getDamageTo(UnitType toType, UnitType fromType);

    public int getDamageTo(UnitType toType, UnitType fromType, Player toPlayer, Player fromPlayer);
    */
}