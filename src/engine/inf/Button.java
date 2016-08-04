package engine.inf;

import engine.image.TextureHandler;
import engine.io.KeyboardHandler;
import engine.io.MouseHandler;

public class Button extends Label{

    public int hotKey;

    public Button(int x, int y, int width, int height, TextureHandler texture){
        super(x, y, width, height, texture);
    }

    @Override
    public void update(){
        if (KeyboardHandler.isKeyDown(hotKey)){
            KeyboardHandler.lock();
            action();
        }

        if (MouseHandler.mouseX > x-width/2 && MouseHandler.mouseX < x+width/2 &&
            MouseHandler.mouseY > y-height/2 && MouseHandler.mouseY < y+height &&
            MouseHandler.mouseDown1){
                MouseHandler.lock();
                action();
        }

    }

    public void action(){}

}
