package game.client.person;

import engine.obj.Obj;
import game.client.TextureManager;

public class Associate extends Obj{

    public Associate(int x, int y, int direction){
        super(x, y, 0, direction, 0, true, TextureManager.associate);
    }

}
