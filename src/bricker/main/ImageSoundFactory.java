package bricker.main;

import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;

/**
 * ImageSoundFactory to pick sound and image.
 * @author aviv.shemesh, ran3108_
 */
public class ImageSoundFactory {
    private final ImageReader imageReader;
    private final SoundReader soundReader;

    /**
     * Constructor for ImageSoundFactory.
     * @param imageReader - imageReader object to look with.
     * @param soundReader - soundReader object to look with.
     */

    public ImageSoundFactory(ImageReader imageReader, SoundReader soundReader){
        this.imageReader = imageReader;
        this.soundReader = soundReader;
    }

    /**
     * Generates a renderable object for a desired classs of objects
     * @param imageType enum representing the image
     * @return renderable object or null if imageType is invalid
     */
    public Renderable getImageObject(ImageType imageType){
        switch(imageType){
            case BALL :
                return imageReader.readImage("assets/ball.png", true);
            case PUCK :
                return imageReader.readImage("assets/mockBall.png", true);
            case PADDLE:
                return imageReader.readImage("assets/paddle.png", true);
            case BRICK:
                return imageReader.readImage("assets/brick.png", true);
            case HEART:
                return imageReader.readImage("assets/heart.png", true);
            case BACKGROUND:
                return imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
        }
        return null;
    }

    /**
     * Generates a Sound object for a desired class of objects.
     * @param soundType enum representing the sound
     * @return sound object or null if soundType is invalid
     */
    public Sound getSoundObject(SoundType soundType){
        switch(soundType){
            case BLOP:
                return soundReader.readSound("assets/blop_cut_silenced.wav");
        }
        return null;
    }
}
