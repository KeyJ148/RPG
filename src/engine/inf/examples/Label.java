package engine.inf.examples;

import engine.Global;
import engine.image.TextureHandler;
import engine.inf.Inf;
import engine.inf.title.Title;
import org.newdawn.slick.Color;

public class Label extends Inf {

    public String label = new String();
    //Свойства надписи
    public int size;
    public Color color = Title.defaultColor;
    public int font = Title.defaultFont;

    public int startTextX = 0, startTextY = 0; //Начало текста относительно верхнего левого угла спрайта

    public Label(int x, int y, int width, int height, TextureHandler texture){
        super(x, y, width, height, texture);
        size = (int) (height/1.3);
    }

    @Override
    public void draw(){
        super.draw();
        Global.engine.render.addTitle(new Title(x-width/2+startTextX, y-height/2+startTextY, label, color, size, font));
    }
}
