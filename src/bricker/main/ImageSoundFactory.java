package bricker.main;

import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;

public class ImageSoundFactory {
    private final ImageReader imageReader;
    private final SoundReader soundReader;

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
     * Generates a Sound object for a desired classs of objects
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
