package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategyFactory;
import bricker.main.BrickerGameMananger;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.brick_strategies.CollsionStrategy;

public class Brick extends GameObject{
    private CollsionStrategy[] collsionStrategy;
    private final BrickerGameMananger gameMananger;
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, CollsionStrategy collsionStrategy, BrickerGameMananger gameMananger) {
        super(topLeftCorner, dimensions, renderable);
        this.gameMananger = gameMananger;
        CollisionStrategyFactory collisionStrategyFactory = new CollisionStrategyFactory();
        this.collsionStrategy = collisionStrategyFactory.collisionStrategy(gameManager);
    }

//delete me

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        for (int i=0;i<collsionStrategy.length;i++){
            if(collsionStrategy[i] != null) {
                collsionStrategy[i].onCollision(this, other);
            }
        }

    }

}
