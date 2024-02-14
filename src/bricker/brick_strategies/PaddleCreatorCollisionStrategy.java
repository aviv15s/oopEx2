package bricker.brick_strategies;

import bricker.gameobjects.Paddle;
import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;
import danogl.util.Vector2;

public class PaddleCreatorCollisionStrategy implements CollsionStrategy{
    private BrickerGameManager gameManager;

    /**
     * Creates an instance of PaddleCreatorCollisionStrategy.
     * @param gameManager instance of BrickerGameManager.
     */
    public PaddleCreatorCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }
    /**
     * An override function that tells how our brick should act. Create falling another paddle.
     * @param gameObject1 - first object collision with
     * @param gameObject2 - second object collision with
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        gameManager.addPaddle(new Vector2(gameManager.getWindowDimensions().mult(0.5f)));
    }
}
