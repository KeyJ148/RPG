package engine.inf.examples;

import engine.Global;
import engine.image.TextureHandler;
import engine.inf.Inf;
import engine.io.KeyboardHandler;
import engine.io.MouseHandler;
import org.lwjgl.input.Keyboard;


public class TextBox extends Label{

    public boolean active = false;//����� �� ������� � ���� ������� � ������ ������?

    public TextBox(int x, int y, int width, int height, TextureHandler texture){
        super(x, y, width, height, texture);
    }

    @Override
    public void update(){
        if (active){//���� ���� ����� �������
            for (int i= 0; i < KeyboardHandler.bufferKey.size(); i++){//���������� ��� �������� ������
                if (KeyboardHandler.bufferState.get(i)) {//���� ������� ���� ������
                    int key = KeyboardHandler.bufferKey.get(i);//�������� ��� ���������� �������
                    if (key == Keyboard.KEY_BACK && label.length() > 0){ //���� ��� backspace, �� ������� ��������� ������
                        label = label.substring(0,label.length()-1);
                    } else {
                        if (KeyboardHandler.bufferChar.get(i) != null) label += KeyboardHandler.bufferChar.get(i);
                    }
                }
            }
            KeyboardHandler.lock();
        } else {
            if (MouseHandler.mouseX > obj.position.x-obj.rendering.getWidth()/2 &&
                MouseHandler.mouseX < obj.position.x+obj.rendering.getWidth()/2 &&
                MouseHandler.mouseY > obj.position.y-obj.rendering.getHeight()/2 &&
                MouseHandler.mouseY < obj.position.y+obj.rendering.getHeight() &&
                MouseHandler.mouseDown1){
                allDeactivation();
                active = true;
                MouseHandler.lock();
            }
        }
    }

    private void allDeactivation(){
        for (Inf inf : Global.infMain.infs){
            if ((inf instanceof TextBox) && (!inf.equals(this))) ((TextBox) inf).active = false;
        }
    }
}
