package game.client.person;

import engine.obj.Obj;
import game.client.TextureManager;

public class Associate extends Obj{

    public Associate(int x, int y, int direction, int speed, int directionDraw){
        super(x, y, speed, direction, 0, true, TextureManager.associate);
        setDirectionDraw(directionDraw);
    }

    public void setData(int x, int y, int direction, int speed, int directionDraw){
        this.x = x;
        this.y = y;
        this.speed = speed;
        setDirection(direction);
        setDirectionDraw(directionDraw);
    }

    @Override
    public void directionDrawEqulas(){}//Ибо персонаж смотрит в сторону атаки, а движется при этом в любую сторону

}
