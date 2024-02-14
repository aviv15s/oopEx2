package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.Layer;
import bricker.main.BrickerGameManager;

/**
 * Strategy of brick when getting hit. In this case it supposed to do nothing
 * @author aviv.shemesh, ram3108_
 */
public class BasicCollisionStrategy implements CollisionStrategy{
    private BrickerGameManager gameManager;

    /**
     *
     * @param gameManager
     */
    public BasicCollisionStrategy(BrickerGameManager gameManager){
        this.gameManager = gameManager;
    }

    /**
     * An override function that tells how our brick should act. In this case it supposed to do nothing.
     * @param gameObject1 - first object collision with
     * @param gameObject2 - second object collision with
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
    }
}
