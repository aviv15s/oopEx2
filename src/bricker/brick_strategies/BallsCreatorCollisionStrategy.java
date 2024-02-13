package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

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
     * @param gameObject1
     * @param gameObject2
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        gameManager.initializePuck(gameObject1.getCenter());
        gameManager.initializePuck(gameObject1.getCenter());
    }
}
