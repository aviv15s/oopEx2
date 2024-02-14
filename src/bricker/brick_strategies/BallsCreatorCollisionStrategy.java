package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * Class for puck collision strategy
 * @author aviv.shemsh, ram3108_
 */
public class BallsCreatorCollisionStrategy implements CollsionStrategy{

    private BrickerGameManager gameManager;

    /**
     * Creates an instance of BallsCreatorCollisionStrategy.
     * @param gameManager instance of BrickerGameManager.
     */
    public BallsCreatorCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * An override function that tells how our brick should act. In this case it supposed to create two new balls.
     * @param gameObject1 - first object collision with
     * @param gameObject2 - second object collision with
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        gameManager.initializePuck(gameObject1.getCenter());
        gameManager.initializePuck(gameObject1.getCenter());
    }
}
