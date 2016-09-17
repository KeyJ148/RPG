package game.client;

import engine.Global;
import engine.image.TextureHandler;
import engine.inf.Inf;
import engine.inf.examples.Button;
import engine.inf.examples.TextBox;
import game.server.ServerLoader;
import org.newdawn.slick.Color;

public class Menu {

    private Game game;

    public Menu(Game game){
        this.game = game;

        int d = 5;//Расстояние между полями
        int hf = 50;//Высота поля
        int wf = 100;//Длина поля для 3 символов
        int w = wf*6+d*7, h = hf*2+d*3;
        int x = Global.engine.render.getWidth()/2;
        int y = Global.engine.render.getHeight()/2;
        int lx = x-w/2;//Левый верхний угол
        int ly = y-h/2;

        Inf window = new Inf(x, y, w, h, TextureManager.sys_null);
        TextBox tb1 = new TextBox(lx+d+wf/2, ly+d+hf/2, wf, hf, TextureManager.sys_null);
        TextBox tb2 = new TextBox(lx+wf+d*2+wf/2, ly+d+hf/2, wf, hf, TextureManager.sys_null);
        TextBox tb3 = new TextBox(lx+wf*2+d*3+wf/2, ly+d+hf/2, wf, hf, TextureManager.sys_null);
        TextBox tb4 = new TextBox(lx+wf*3+d*4+wf/2, ly+d+hf/2, wf, hf, TextureManager.sys_null);
        TextBox tb5 = new TextBox(lx+wf*4+d*5+wf*2/2, ly+d+hf/2, wf*2, hf, TextureManager.sys_null);
        ButtonLogin bl = new ButtonLogin(lx+d+(w-2*d)/2, ly+hf+d*2+hf/2, w-2*d, hf, TextureManager.sys_null);

        window.setFrame(new Color(0.0f, 0.0f, 1.0f));
        tb1.setFrame(new Color(0.0f, 0.0f, 1.0f));
        tb2.setFrame(new Color(0.0f, 0.0f, 1.0f));
        tb3.setFrame(new Color(0.0f, 0.0f, 1.0f));
        tb4.setFrame(new Color(0.0f, 0.0f, 1.0f));
        tb5.setFrame(new Color(0.0f, 0.0f, 1.0f));
        bl.setFrame(new Color(0.0f, 0.0f, 1.0f));

        Global.infMain.infs.add(window);
        Global.infMain.infs.add(tb1);
        Global.infMain.infs.add(tb2);
        Global.infMain.infs.add(tb3);
        Global.infMain.infs.add(tb4);
        Global.infMain.infs.add(tb5);
        Global.infMain.infs.add(bl);

        bl.setListener(tb1, tb2, tb3, tb4, tb5, window);
    }

    public class ButtonLogin extends Button {

        TextBox tb1, tb2, tb3, tb4, tb5;
        Inf window;

        public ButtonLogin(int x, int y, int width, int height, TextureHandler texture){
            super(x, y, width, height, texture);
            this.label = "START!";
        }

        public void setListener(TextBox tb1, TextBox tb2, TextBox tb3, TextBox tb4, TextBox tb5, Inf window){
            this.tb1 = tb1;
            this.tb2 = tb2;
            this.tb3 = tb3;
            this.tb4 = tb4;
            this.tb5 = tb5;
            this.window = window;
        }

        @Override
        public void action(){
            int port = Integer.parseInt(tb5.label);

            if (tb2.label.length() == 0) {
                new ServerLoader(port, Integer.parseInt(tb1.label), false);
                Global.tcpControl.connect("127.0.0.1", port);
                Global.tcpRead.start();
            } else {
                String ip = tb1.label + "." + tb2.label + "." + tb3.label + "." + tb4.label;
                Global.tcpControl.connect(ip, port);
                Global.tcpRead.start();
            }

            allDelete();
            game.initAfterConnect();
        }

        public void allDelete(){
            tb1.delete();
            tb2.delete();
            tb3.delete();
            tb4.delete();
            tb5.delete();
            window.delete();
            delete();
        }
    }
}
