package game.client.person;

import engine.io.KeyboardHandler;
import game.client.TextureManager;
import org.lwjgl.input.Keyboard;

public class Player extends Person {

    public Player(double x, double y, double dir) {
        super(x, y, dir, TextureManager.cursor);
    }

    @Override
    public void updateChildMid(long delta){
        int deltaX = 0;
        int deltaY = 0;
        if (KeyboardHandler.isKeyDown(Keyboard.KEY_W)) deltaY--;
        if (KeyboardHandler.isKeyDown(Keyboard.KEY_S)) deltaY++;
        if (KeyboardHandler.isKeyDown(Keyboard.KEY_A)) deltaX--;
        if (KeyboardHandler.isKeyDown(Keyboard.KEY_D)) deltaX++;

        speed = 0;
        if (deltaX != 0 || deltaY != 0){
            speed = stats.speedRun*10;

            if (deltaX == -1 && deltaY == -1) direction = 135;
            if (deltaX == -1 && deltaY == 0) direction = 180;
            if (deltaX == -1 && deltaY == 1) direction = 225;
            if (deltaX == 0 && deltaY == -1) direction = 90;
            if (deltaX == 0 && deltaY == 1) direction = 270;
            if (deltaX == 1 && deltaY == -1) direction = 45;
            if (deltaX == 1 && deltaY == 0) direction = 0;
            if (deltaX == 1 && deltaY == 1) direction = 315;
        }
    }
}
