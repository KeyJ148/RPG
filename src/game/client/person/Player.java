package game.client.person;

import engine.Global;
import engine.io.KeyboardHandler;
import engine.io.MouseHandler;
import engine.obj.Obj;
import engine.obj.components.Movement;
import game.client.ClientData;
import game.client.TextureManager;
import game.client.particles.ParticlesTraces;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Iterator;

public class Player extends Person {

    private static final int sendDataEveryTicks = 2;//Отправлять данные каждые N степов
    private int sendDataLastTicks = 0;//Как давно отправляли данные

    private static final int createTracesEveryTicks = 30;//Создавать следы ходьбы каждые N степов
    private int createTracesLastTicks = 0;//Как давно создавали следы ходьбы
    private ArrayList<Obj> tracesArray = new ArrayList<>();

    private Obj armor;
    private Obj face;
    private Obj hair;

    public Player(double x, double y, double dir) {
        super(x, y, dir, TextureManager.human_body);
        position.depth = -1;
        
        armor = new Obj(x, y, -2, dir, TextureManager.peasant_armor);
        face = new Obj(x, y, -3, dir, TextureManager.human_head_face);
        hair = new Obj(x, y, -4, dir, TextureManager.human_head_hair);
        armor.movement = new Movement(armor, 0, 0);
        face.movement = new Movement(face, 0, 0);
        hair.movement = new Movement(hair, 0, 0);
        armor.follow = this;
        face.follow = this;
        hair.follow = this;
        Global.room.objAdd(armor);
        Global.room.objAdd(face);
        Global.room.objAdd(hair);
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

            createTracesLastTicks++;
            if (createTracesLastTicks >= createTracesEveryTicks){
                createTracesLastTicks = 0;
                Obj traces = new Obj(position.x, position.y, 0, movement.getDirection());
                traces.particles = new ParticlesTraces(traces);

                tracesArray.add(traces);
                Global.room.objAdd(traces);
            }

            Iterator<Obj> iterator = tracesArray.iterator();
            while(iterator.hasNext()){
                Obj obj = iterator.next();
                if (obj.particles.parts.size() == 0) obj.destroy();
            }
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
