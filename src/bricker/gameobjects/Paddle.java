package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * a class for the paddle object
 * @author aviv.shemesh, ram3108_
 */
public class Paddle  extends GameObject {
    private static final int MAX_HITS_ANOTHER_PADDLE = 4;
    private final UserInputListener inputListener;
    private final BrickerGameManager gameManager;
    private int num_collisions;


    /**
     * a constructor for the class
     * @param topLeftCorner top left corner of the object
     * @param dimensions dimensions of the object
     * @param renderable the renderable to display the image
     * @param inputListener input listener to move paddle by player
     * @param gameManager game manager
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions,
                  Renderable renderable, UserInputListener inputListener, BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.gameManager = gameManager;
        this.num_collisions = 0;
    }

    /**
     * update is called on each frame. makes sure the paddle moves by the player correctly.
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime){
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(gameManager.getPaddleSpeed()));

        if (getTopLeftCorner().x() < 0){
            setTopLeftCorner(new Vector2(0, getTopLeftCorner().y()));
        }

        if (getTopLeftCorner().x() + getDimensions().x() > gameManager.getWindowDimensions().x()){
            setTopLeftCorner(new Vector2(gameManager.getWindowDimensions().x() -  getDimensions().x(),
                    getTopLeftCorner().y()));
        }
    }

    /**
     * this function is called whenever something collides with this paddle
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (other.getTag().equals(gameManager.getBallTag()) ||
                other.getTag().equals(gameManager.getPuckTag())){
            num_collisions += 1;
        }
        if (this.getTag().equals(gameManager.getExtraPaddleTag()) &&
                num_collisions >= MAX_HITS_ANOTHER_PADDLE){
           gameManager.removeGameObject(this, gameManager.getPaddleLayer());
           gameManager.setExtraPaddle(false);
        }
    }
}
