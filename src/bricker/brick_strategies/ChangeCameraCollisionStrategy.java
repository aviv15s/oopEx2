package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;

public class ChangeCameraCollisionStrategy implements CollsionStrategy{
    private BrickerGameManager gameManager;
    public ChangeCameraCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        gameManager.cameraFollowBall(gameObject2);

    }
}
