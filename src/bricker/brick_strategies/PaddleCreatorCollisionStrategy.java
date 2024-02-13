package bricker.brick_strategies;

import bricker.gameobjects.Paddle;
import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;
import danogl.util.Vector2;

public class PaddleCreatorCollisionStrategy implements CollsionStrategy{
    private BrickerGameManager gameManager;

    public PaddleCreatorCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        gameManager.addPaddle(new Vector2(gameManager.getWindowDimensions().mult(0.5f)));
    }
}
