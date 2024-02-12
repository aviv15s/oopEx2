package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.Layer;
import src.bricker.main.BrickerGameMananger;

public class BasicCollisionStrategy implements CollsionStrategy{
    private BrickerGameMananger gameMananger;
    public BasicCollisionStrategy(BrickerGameMananger gameMananger){
        this.gameMananger = gameMananger;
    }
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        System.out.println("collision with brick detected");
        gameMananger.removeGameObject(gameObject1, Layer.STATIC_OBJECTS);
    }
}
