package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;
import bricker.main.ImageSoundFactory;
import danogl.GameObject;

public class LifeRecoveryCollisionStrategy implements CollsionStrategy{
    private BrickerGameManager brickerGameManager;


    public LifeRecoveryCollisionStrategy(BrickerGameManager gameManager){
        this.brickerGameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        brickerGameManager.createFallingHeartObject(gameObject1.getCenter());
        System.out.println("created heart!");
    }
}
