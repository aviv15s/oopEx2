package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
/**
 * Strategy of brick when getting hit. In this case it supposed to follow ball with Camera.
 * @author aviv.shemesh, ram3108_
 */
public class ChangeCameraCollisionStrategy implements CollisionStrategy{
    private BrickerGameManager gameManager;
    /**
     * Creates an instance of ChangeCameraCollisionStrategy.
     * @param gameManager instance of BrickerGameManager.
     */
    public ChangeCameraCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * An override function that tells how our brick should act. In this case it supposed to follow ball with Camera.
     * @param gameObject1 - first object collision with
     * @param gameObject2 - second object collision with
     */@Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        gameManager.cameraFollowBall(gameObject2);

    }
}
