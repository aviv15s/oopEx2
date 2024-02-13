package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategyFactory;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.brick_strategies.CollsionStrategy;

public class Brick extends GameObject{
    private CollsionStrategy[] collsionStrategy;
    private boolean isHit = false;
    private final BrickerGameManager gameManager;
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, CollsionStrategy collsionStrategy, BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.gameManager = gameManager;
        CollisionStrategyFactory collisionStrategyFactory = new CollisionStrategyFactory();
        this.collsionStrategy = collisionStrategyFactory.collisionStrategy(this.gameManager);
    }

//delete me

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(isHit){
            return;
        }
        isHit = true;
        for (int i=0;i<collsionStrategy.length;i++){
            if(collsionStrategy[i] != null) {
                collsionStrategy[i].onCollision(this, other);
            }
        }
        gameManager.removeBrick(this);

    }

}
