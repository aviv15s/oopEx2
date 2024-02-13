package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.Layer;
import bricker.main.BrickerGameManager;

public class BasicCollisionStrategy implements CollsionStrategy{
    private BrickerGameManager gameManager;

    /**
     *
     * @param gameManager
     */
    public BasicCollisionStrategy(BrickerGameManager gameManager){
        this.gameManager = gameManager;
    }

    /**
     *
     * @param gameObject1
     * @param gameObject2
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        gameManager.removeGameObject(gameObject1, gameManager.getBricksLayer());
    }
}
