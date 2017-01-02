package game.client.person;

import engine.obj.Obj;
import engine.obj.components.Movement;
import game.client.TextureManager;

public class Associate extends Obj{

    public Associate(int x, int y, int direction, int speed, int directionDraw){
        super(x, y, 0, directionDraw, TextureManager.associate);
        movement = new Movement(this, speed, direction);
        movement.directionDrawEquals = false;
    }

    public void setData(int x, int y, int direction, int speed, int directionDraw){
        this.position.x = x;
        this.position.y = y;
        this.movement.speed = speed;
        this.movement.setDirection(direction);
        this.position.setDirectionDraw(directionDraw);
    }
}
