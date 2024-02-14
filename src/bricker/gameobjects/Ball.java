package bricker.gameobjects;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * This class is the class of the ball.
 * The class extends GameObject class. and add some variables
 * @author aviv.shemesh, ram3108_
 */
public class Ball extends GameObject {
    private final Sound collisionSound;
    private int collisionCounter = 0;

    /**
     * A constructor for the Ball class
     * @param topLeftCorner top left corner of the created ball
     * @param dimensions dimensions for the ball
     * @param renderable the renderable for the object
     * @param collisionSound collision sound to play when hitting another object
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions,
                Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
    }

    /**
     * a function that is called whenever a collision with this object occurs.
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
        collisionCounter++;

    }

    /**
     * a getter function for the collision counter
     * @return collision counter
     */
    public int getCollisionCounter(){
        return collisionCounter;
    }
}
