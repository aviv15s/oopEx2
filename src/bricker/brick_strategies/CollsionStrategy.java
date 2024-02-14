package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.Layer;

/**
 * Interface for collisions strategies.
 * @author aviv.shemsh, ram3108_
 */

public interface CollsionStrategy {
    /**
     * An override function that tells how our brick should act.
     * @param gameObject1 - first object collision with
     * @param gameObject2 - second object collision with
     */
    public void onCollision(GameObject gameObject1, GameObject gameObject2);
}
