package engine.obj.components.particles;

import engine.image.TextureHandler;
import org.newdawn.slick.Color;

public class Part{

    public enum Type{FILL, HOLLOW}
    public Type type;

    public TextureHandler textureHandler;

    public Color color;
    public double x, y;
    public double speed;
    public double direction;
    public double directionDraw;
    public double width;
    public double height;
    public double life;//����� ����� � ��������

    public Part(){
        this(0, 0, 0, 0, 0, 0, 0, 0);
    }

    public Part(int x, int y, double life, double speed, double direction, int width, int height, TextureHandler textureHandler){
        this(x, y, life, speed, direction, direction, width, height);
        this.textureHandler = textureHandler;
    }

    public Part(int x, int y, double life, double speed, double direction, double directionDraw, int width, int height, TextureHandler textureHandler){
        this(x, y, life, speed, direction, directionDraw, width, height);
        this.textureHandler = textureHandler;
    }

    public Part(int x, int y, double life, double speed, double direction, double directionDraw, int width, int height){
        this(x, y, life, speed, direction, directionDraw, width, height, Type.FILL);
    }

    public Part(int x, int y, double life, double speed, double direction, double directionDraw, int width, int height, Type type){
        this(x, y, life, speed, direction, directionDraw, width, height, Color.white, type);
    }

    public Part(int x, int y, double life, double speed, double direction, double directionDraw, int width, int height, Color color, Type type){
        this.x = x;
        this.y = y;
        this.life = life;
        this.speed = speed;
        this.width = width;
        this.height = height;
        this.direction = direction;
        this.directionDraw = directionDraw;
        this.color = color;
        this.type = type;
    }
}