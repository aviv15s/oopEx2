package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.Layer;
import bricker.main.BrickerGameMananger;

public class BasicCollisionStrategy implements CollsionStrategy{
    private BrickerGameMananger gameMananger;

    /**
     *
     * @param gameMananger
     */
    public BasicCollisionStrategy(BrickerGameMananger gameMananger){
        this.gameMananger = gameMananger;
    }

    /**
     *
     * @param gameObject1
     * @param gameObject2
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        gameMananger.removeGameObject(gameObject1, gameMananger.BRICKS_LAYER);
    }
}
