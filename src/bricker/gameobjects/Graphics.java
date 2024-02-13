package bricker.gameobjects;

import bricker.main.BrickerGameMananger;
import bricker.main.ImageSoundFactory;
import bricker.main.ImageType;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import org.w3c.dom.Text;

import java.awt.*;

public class Graphics {
    private final int HEART_SIZE = 20;
    private final int MARGIN = 5;
    private final int MAX_HEARTS = 4;
    private final int INITIAL_HEARTS = 3;
    private final WindowController windowController;
    private final GameObjectCollection gameObjects;
    private GameObject[] heartArray = new GameObject[MAX_HEARTS];
    private TextRenderable textRenderable;
    private Renderable heartRenderable;


    public Graphics(WindowController windowController, ImageSoundFactory imageSoundFactory, GameObjectCollection gameObjects){
        this.windowController = windowController;
        heartRenderable = imageSoundFactory.getImageObject(ImageType.HEART);
        this.gameObjects = gameObjects;
        for (int i = 0; i < heartArray.length; i++) {
            heartArray[i] = null;
        }
    }
    public void initializeLifeCounter(){
        for (int i = 0; i < MAX_HEARTS; i++) {
            GameObject heart = new GameObject(
                    new Vector2((i+1) * (HEART_SIZE+MARGIN), windowController.getWindowDimensions().y() - MARGIN - HEART_SIZE),
                    new Vector2(HEART_SIZE, HEART_SIZE),
                    null
            );
            gameObjects.addGameObject(heart, Layer.FOREGROUND); // TODO update layer as constant maybe?
            heartArray[i] = heart;
        }

        textRenderable = new TextRenderable(String.valueOf(INITIAL_HEARTS));
        textRenderable.setColor(Color.GREEN);
        GameObject gameObject = new GameObject(new Vector2(
                MARGIN, windowController.getWindowDimensions().y()-HEART_SIZE-MARGIN
        ), Vector2.ONES.mult(HEART_SIZE), textRenderable);
        gameObjects.addGameObject(gameObject, Layer.FOREGROUND);

        updateHeartCount(INITIAL_HEARTS);
    }

    public void updateHeartCount(int heartCount){
        for (int i = 0; i < MAX_HEARTS; i++) {
            if (i < heartCount){
                heartArray[i].renderer().setRenderable(heartRenderable);
            }
            else{
                heartArray[i].renderer().setRenderable(null);
            }
        }

        textRenderable.setString(String.valueOf(heartCount));
        switch(heartCount){
            case 1:
                textRenderable.setColor(Color.RED);
                break;
            case 2:
                textRenderable.setColor(Color.YELLOW);
                break;
            default:
                textRenderable.setColor(Color.GREEN);
                break;
        }
    }


    public boolean showGameOverScreenAndReturnValue(WindowController windowController){
        return windowController.openYesNoDialog("You Lose! Play again?");
    }

}
