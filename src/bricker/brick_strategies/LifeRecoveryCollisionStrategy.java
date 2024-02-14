package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;
import bricker.main.ImageSoundFactory;
import danogl.GameObject;

public class LifeRecoveryCollisionStrategy implements CollsionStrategy{
    private BrickerGameManager brickerGameManager;

    /**
     * Creates an instance of LifeRecoveryCollisionStrategy.
     * @param gameManager instance of BrickerGameManager.
     */
    public LifeRecoveryCollisionStrategy(BrickerGameManager gameManager){
        this.brickerGameManager = gameManager;
    }

    /**
     * An override function that tells how our brick should act. Create falling heart.
     * @param gameObject1 - first object collision with
     * @param gameObject2 - second object collision with
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        brickerGameManager.createFallingHeartObject(gameObject1.getCenter());
    }
}
