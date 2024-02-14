package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategyFactory;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.brick_strategies.CollisionStrategy;

/**
 * A class that represents a brick in the game which is static and does something when hit by a ball.
 *
 * @author aviv.shemesh, ram3108_
 */
public class Brick extends GameObject {
    private CollisionStrategy[] CollisionStrategy;
    private boolean isHit = false;
    private final BrickerGameManager gameManager;

    /**
     * Constructor for a brick object
     *
     * @param topLeftCorner position of top left corner of the brick
     * @param dimensions    dimensions of the brick
     * @param renderable    the renderable image of the brick
     * @param gameManager   the game manager
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.gameManager = gameManager;
        CollisionStrategyFactory collisionStrategyFactory = new CollisionStrategyFactory();
        this.CollisionStrategy = collisionStrategyFactory.collisionStrategy(this.gameManager);
    }

    /**
     * the function which is called when an object collides with this brick
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (isHit) {
            return;
        }
        isHit = true;
        for (int i = 0; i < CollisionStrategy.length; i++) {
            if (CollisionStrategy[i] != null) {
                CollisionStrategy[i].onCollision(this, other);
            }
        }
        gameManager.removeBrick(this);
    }
}
