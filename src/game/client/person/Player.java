package game.client.person;

import engine.Global;
import engine.io.KeyboardHandler;
import engine.io.MouseHandler;
import game.client.ClientData;
import game.client.TextureManager;
import org.lwjgl.input.Keyboard;

public class Player extends Person {

    private static final int sendDataEveryTicks = 2;//Отправлять данные каждые N степов
    private int sendDataLastTicks = 0;

    public Player(double x, double y, double dir) {
        super(x, y, dir, TextureManager.player);
    }

    @Override
    public void update(long delta){
        super.update(delta);
        position.setDirectionDraw(Math.atan2(position.getRelativePosition().x - MouseHandler.mouseX, position.getRelativePosition().y - MouseHandler.mouseY) / Math.PI * 180 + 90);

        int deltaX = 0;
        int deltaY = 0;
        if (KeyboardHandler.isKeyDown(Keyboard.KEY_W)) deltaY--;
        if (KeyboardHandler.isKeyDown(Keyboard.KEY_S)) deltaY++;
        if (KeyboardHandler.isKeyDown(Keyboard.KEY_A)) deltaX--;
        if (KeyboardHandler.isKeyDown(Keyboard.KEY_D)) deltaX++;

        movement.speed = 0;
        if (deltaX != 0 || deltaY != 0){
            movement.speed = stats.speedRun;

            if (deltaX == -1 && deltaY == -1) movement.setDirection(135);
            if (deltaX == -1 && deltaY == 0) movement.setDirection(180);
            if (deltaX == -1 && deltaY == 1) movement.setDirection(225);
            if (deltaX == 0 && deltaY == -1) movement.setDirection(90);
            if (deltaX == 0 && deltaY == 1) movement.setDirection(270);
            if (deltaX == 1 && deltaY == -1) movement.setDirection(45);
            if (deltaX == 1 && deltaY == 0) movement.setDirection(0);
            if (deltaX == 1 && deltaY == 1) movement.setDirection(315);
        }

        sendDataLastTicks++;
        if (ClientData.serverStarted && sendDataLastTicks >= sendDataEveryTicks){
            sendDataLastTicks = 0;
            Global.tcpControl.send(2, getData());
        }
    }

    public String getData(){
        return (int) position.x + " " + (int) position.y + " " + (int) movement.getDirection() + " " + (int) movement.speed + " " + (int) position.getDirectionDraw();
    }
}
