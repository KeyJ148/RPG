package game.client;

import engine.image.TextureHandler;
import engine.inf.frame.ImageFrame;

public class InterfaceFrame extends ImageFrame {

    public InterfaceFrame(TextureHandler degree, TextureHandler wall){
        super(degree, wall);
    }

    @Override
    protected void drawWall(int x, int y, int width, int height){
        wall.position.x = x;
        wall.position.y = y-height/2+1;
        wall.position.setDirectionDraw(90);
        wall.rendering.scale_x = width/2-17;
        wall.rendering.draw();

        wall.position.x = x;
        wall.position.y = y+height/2;
        wall.position.setDirectionDraw(90);
        wall.rendering.scale_x = width/2-17;
        wall.rendering.draw();

        wall.position.x = x-width/2;
        wall.position.y = y;
        wall.position.setDirectionDraw(0);
        wall.rendering.scale_x = height/2-17;
        wall.rendering.draw();

        wall.position.x = x+width/2-1;
        wall.position.y = y;
        wall.position.setDirectionDraw(0);
        wall.rendering.scale_x = height/2-17;
        wall.rendering.draw();
    }
}
