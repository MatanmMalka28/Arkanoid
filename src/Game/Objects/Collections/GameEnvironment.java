package Game.Objects.Collections;

import Game.Objects.Collidable;
import Game.Objects.DataStructers.CollisionInfo;
import Geometry.Line;
import Geometry.Point;

import java.util.ArrayList;
import java.util.List;

public class GameEnvironment {
    private List<Collidable> collidables;

    public GameEnvironment(List<Collidable> collidables) {
        this.collidables = new ArrayList<>(collidables);
    }

    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    private GameEnvironment(GameEnvironment gameEnvironment) {
        this.collidables = new ArrayList<>(gameEnvironment.collidables);
    }

    public List<Collidable> getCollidables() {
        return new ArrayList<>(this.collidables);
    }

    // add the given collidable to the environment.
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    public void addCollidable(List<Collidable> collidables) {
        this.collidables.addAll(collidables);
    }

    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    // Assume an object moving from line.start() to line.end().
    // If this object will not collide with any of the collidables
    // in this collection, return null. Else, return the information
    // about the closest collision that is going to occur.
    public CollisionInfo getClosestCollision(Line trajectory) {
        List<CollisionInfo> collisionInfoList = new ArrayList<>();
        for (Collidable collidable : this.collidables) {
            CollisionInfo info = collidable.getCollisionInfo(trajectory);
            if (info != null) {
                collisionInfoList.add(info);
            }

        }
        if (collisionInfoList.isEmpty()) {
            return null;
        } else {
            CollisionInfo bestMatch = collisionInfoList.get(0);
            collisionInfoList.remove(0);
            for (CollisionInfo info : collisionInfoList) {
                if (info.distance(trajectory) < bestMatch.distance(trajectory)) {
                    bestMatch = info;
                }
            }
            return bestMatch;
        }
    }


    public GameEnvironment copy() {
        return new GameEnvironment(this);
    }
}
