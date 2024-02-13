package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.brick_strategies.CollsionStrategy;

public class Brick extends GameObject{
    CollsionStrategy collsionStrategy;
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, CollsionStrategy collsionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collsionStrategy = collsionStrategy;
    }

//delete me

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collsionStrategy.onCollision(this, other);
        // TODO add call to gameManager onRemoveBrick
    }

}
