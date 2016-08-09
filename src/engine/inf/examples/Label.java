package engine.inf.examples;

import engine.Global;
import engine.image.TextureHandler;
import engine.inf.Inf;
import engine.inf.title.Title;
import org.newdawn.slick.Color;

public class Label extends Inf {

    public String label = new String();
    public int limit;//����������� ���-�� ��������
    //�������� �������
    public int size;
    public Color color = Title.defaultColor;
    public int font = Title.defaultFont;

    public int startTextX = 0, startTextY = 0; //������ ������ ������������ �������� ������ ���� �������

    public Label(int x, int y, int width, int height, TextureHandler texture){
        super(x, y, width, height, texture);
        size = (int) (height/1.3);
        limit = (int) (width/size*1.9);
    }

    @Override
    public void draw(){
        super.draw();
        String drawLabel = label;
        if (label.length() > limit) drawLabel = label.substring(0, limit-1) + "..";
        Global.engine.render.addTitle(new Title(x-width/2+startTextX, y-height/2+startTextY, drawLabel, color, size, font));
    }
}
