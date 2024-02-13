package bricker.brick_strategies;

import bricker.main.BrickerGameMananger;
import danogl.GameObject;

public class PaddleCreatorCollisionStrategy implements CollsionStrategy{
    private BrickerGameMananger gameMananger;

    public PaddleCreatorCollisionStrategy(BrickerGameMananger gameMananger) {
        this.gameMananger = gameMananger;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {

        gameMananger.removeGameObject(gameObject1, gameMananger.BRICKS_LAYER);
    }
}
