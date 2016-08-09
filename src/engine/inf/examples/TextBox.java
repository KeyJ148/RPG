package engine.inf.examples;

import engine.image.TextureHandler;
import engine.inf.TableAccordKey;
import engine.io.KeyboardHandler;
import engine.io.MouseHandler;
import org.lwjgl.input.Keyboard;


public class TextBox extends Label{

    public boolean active = true;//����� �� ������� � ���� ������� � ������ ������?

    public TextBox(int x, int y, int width, int height, TextureHandler texture){
        super(x, y, width, height, texture);
    }

    @Override
    public void update(){
        if (active){//���� ���� ����� �������
            for (int i= 0; i < KeyboardHandler.bufferKey.size(); i++){//���������� ��� �������� ������
                if (!KeyboardHandler.bufferState.get(i)) {//���� ������� ���� ��������
                    int key = KeyboardHandler.bufferKey.get(i);//�������� ��� ���������� �������
                    if (TableAccordKey.get(key) != null){//���� �� �� ����� null
                        char ch = TableAccordKey.get(key);
                        //���� ����� ����
                        if (KeyboardHandler.isKeyDown(Keyboard.KEY_LSHIFT) || KeyboardHandler.isKeyDown(Keyboard.KEY_RSHIFT)){
                            ch = Character.toUpperCase(ch) ;//�� ������ ������� �������
                        }
                        label += ch;//��������� ���� ������ � ��������� �����
                    } else {//���� ������� ��� � ����� �������
                        //���� ��� backspace, �� ������� ��������� ������
                        if (key == Keyboard.KEY_BACK && label.length() > 0) label = label.substring(0,label.length()-1);
                    }
                }
            }
            KeyboardHandler.lock();
            MouseHandler.lock();
        }
    }
}
