package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;
import bricker.main.ImageSoundFactory;
import danogl.GameObject;

/**
 * Strategy of brick when getting hit. In this case it supposed to Create falling heart.
 * @author aviv.shemesh, ram3108_
 */
public class LifeRecoveryCollisionStrategy implements CollisionStrategy{
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
