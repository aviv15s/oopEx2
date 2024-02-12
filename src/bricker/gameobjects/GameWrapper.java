package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class GameWrapper {
    private final int WALL_THICKNESS = 2;

    public void initializeBackground(GameObjectCollection gameObjects, Vector2 windowDimensions, Renderable backgroundImage){
        GameObject background = new GameObject(Vector2.ZERO,
                windowDimensions,
                backgroundImage);
        gameObjects.addGameObject(background, -200);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
    }
}
