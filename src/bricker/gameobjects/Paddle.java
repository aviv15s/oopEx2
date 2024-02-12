package bricker.gameobjects;

import bricker.main.BrickerGameMananger;
import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle  extends GameObject {
    private static final float MOVEMENT_SPEED = 300;
    private final UserInputListener inputListener;
    private final BrickerGameMananger gameMananger;

    public Paddle(Vector2 topLeftCorner, Vector2 dimensions,
                  Renderable renderable, UserInputListener inputListener, BrickerGameMananger gameMananger) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.gameMananger = gameMananger;
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
        setVelocity(movementDir.mult(MOVEMENT_SPEED));

        if (getTopLeftCorner().x() < 0){
            setTopLeftCorner(new Vector2(0, getTopLeftCorner().y()));
        }

        if (getTopLeftCorner().x() + getDimensions().x() > gameMananger.getWindowDimensions().x()){
            setTopLeftCorner(new Vector2(gameMananger.getWindowDimensions().x() -  getDimensions().x(), getTopLeftCorner().y()));
        }
    }

}
