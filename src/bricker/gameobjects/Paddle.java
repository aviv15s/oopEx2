package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle  extends GameObject {
    private static final String ANOTHER_PADDLE_TAG = "another paddle";
    private static final int MAX_HITS_ANOTHER_PADDLE = 4;
    private final UserInputListener inputListener;
    private final BrickerGameManager gameManager;
    private int num_collisions;

    public UserInputListener getInputListener() {
        return inputListener;
    }

    public Paddle(Vector2 topLeftCorner, Vector2 dimensions,
                  Renderable renderable, UserInputListener inputListener, BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.gameManager = gameManager;
        this.num_collisions = 0;
    }
    public void setLabelAnotherPaddle(){
        this.setTag(ANOTHER_PADDLE_TAG);

    }
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
            setTopLeftCorner(new Vector2(gameManager.getWindowDimensions().x() -  getDimensions().x(), getTopLeftCorner().y()));
        }
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (other.getTag().equals(gameManager.getBallTag()) || other.getTag().equals(gameManager.getPuckTag())){
            num_collisions += 1;
        }
        if (this.getTag().equals(ANOTHER_PADDLE_TAG) && num_collisions >= MAX_HITS_ANOTHER_PADDLE){
           gameManager.removeGameObject(this, gameManager.getPaddleLayer());
           gameManager.setExtraPaddle(false);
        }
        if (other.getTag().equals(gameManager.getHeartsTag())){
            gameManager.onPaddleHitHeart(other);
        }
    }
}
