package game.client.person;

import engine.image.TextureHandler;
import engine.obj.Obj;

public class Enemy extends Obj{

    public Enemy(int x, int y, int direction, TextureHandler texture){
        super(x, y, 0, direction, 0, false, texture);
    }

}
