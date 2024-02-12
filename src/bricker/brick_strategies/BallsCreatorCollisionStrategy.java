package bricker.brick_strategies;

import bricker.main.BrickerGameMananger;
import danogl.GameObject;

public class BallsCreatorCollisionStrategy implements CollsionStrategy{
    private BrickerGameMananger gameMananger;

    /**
     * Creates an instance of BallsCreatorCollisionStrategy.
     * @param gameMananger instance of BrickerGameMananger.
     */
    public BallsCreatorCollisionStrategy(BrickerGameMananger gameMananger) {
        this.gameMananger = gameMananger;
    }

    /**
     * An override function that tells how our brick should act. In this case it supposed to create two new balls.
     * @param gameObject1
     * @param gameObject2
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {

    }
}
