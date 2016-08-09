package engine.inf;

import engine.image.Sprite;
import engine.image.TextureHandler;

public class Inf {

    public int x, y;
    public int width, height;
    public double direction = Math.PI/2;
    public Sprite sprite;

    public Inf(int x, int y, int width, int height, TextureHandler texture){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = new Sprite(texture);
        this.sprite.scale_x = width/texture.getWidth();
        this.sprite.scale_y = height/texture.getHeight();
    }

    public void update(){}

    public void draw(){
        sprite.draw(x,y,direction);
    }

}
