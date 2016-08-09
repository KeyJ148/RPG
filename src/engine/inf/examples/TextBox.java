package engine.inf.examples;

import engine.Global;
import engine.image.TextureHandler;
import engine.inf.Inf;
import engine.inf.TableAccordKey;
import engine.io.KeyboardHandler;
import engine.io.MouseHandler;
import org.lwjgl.input.Keyboard;


public class TextBox extends Label{

    public boolean active = false;//Можно ли вводить в поле символы в данный момент?

    public TextBox(int x, int y, int width, int height, TextureHandler texture){
        super(x, y, width, height, texture);
    }

    @Override
    public void update(){
        if (active){//Если поле ввода активно
            for (int i= 0; i < KeyboardHandler.bufferKey.size(); i++){//Перебираем все действия клавиш
                if (!KeyboardHandler.bufferState.get(i)) {//Если клавиша была отпущена
                    int key = KeyboardHandler.bufferKey.get(i);//Получаем код отпущенной клавиши
                    if (TableAccordKey.get(key) != null){//Если он не равен null
                        char ch = TableAccordKey.get(key);
                        //Если зажат шифт
                        if (KeyboardHandler.isKeyDown(Keyboard.KEY_LSHIFT) || KeyboardHandler.isKeyDown(Keyboard.KEY_RSHIFT)){
                            ch = Character.toUpperCase(ch) ;//То ставим большой регистр
                        }
                        label += ch;//Добавляем этот символ в набранный текст
                    } else {//Если символа нет в нашей таблицы
                        //Если это backspace, то убираем последнйи символ
                        if (key == Keyboard.KEY_BACK && label.length() > 0) label = label.substring(0,label.length()-1);
                    }
                }
            }
            KeyboardHandler.lock();
        } else {
            if (MouseHandler.mouseX > x-width/2 && MouseHandler.mouseX < x+width/2 &&
                    MouseHandler.mouseY > y-height/2 && MouseHandler.mouseY < y+height &&
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
