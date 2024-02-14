package bricker.gameobjects;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.Layer;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.gui.ImageReader;

/**
 * This class is the class of the ball.
 * The class extends GameObject class. and add some variables
 */
public class Ball extends GameObject {
    private final Sound collisionSound;
    private int collisionCounter = 0;
    private BrickerGameManager gameManager;

    public Ball(Vector2 topLeftCorner, Vector2 dimensions,
                Renderable renderable, Sound collisionSound, BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.gameManager = gameManager;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
        collisionCounter++;

    }

    public int getCollisionCounter(){
        return collisionCounter;
    }

}
