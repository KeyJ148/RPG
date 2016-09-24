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

        int interval = 5;//Расстояние между полями
        int hField = 50;//Высота поля
        int w = 470;//Ширина окна
        int h = hField*2+interval*3;//Высота окна
        int x = Global.engine.render.getWidth()/2-w/2;//Левый верхний угол
        int y = Global.engine.render.getHeight()/2-h/2;//У окна

        Inf window = new Inf(x+w/2, y+h/2, w, h, TextureManager.sys_null);
        TextBox tb = new TextBox(x+w/2, y+interval+hField/2, w-interval*2, hField, TextureManager.sys_null);
        ButtonLogin bl = new ButtonLogin(x+w/2, y+interval*2+hField+ hField/2, w-interval*2, hField, TextureManager.sys_null);

        window.setFrame(new Color(0.0f, 0.0f, 1.0f));
        tb.setFrame(new Color(0.0f, 0.0f, 1.0f));
        bl.setFrame(new Color(0.0f, 0.0f, 1.0f));

        Global.infMain.infs.add(window);
        Global.infMain.infs.add(tb);
        Global.infMain.infs.add(bl);

        bl.setListener(tb, window);
    }

    public class ButtonLogin extends Button {

        TextBox tb;
        Inf window;

        public ButtonLogin(int x, int y, int width, int height, TextureHandler texture){
            super(x, y, width, height, texture);
            this.label = "START!";
        }

        public void setListener(TextBox tb, Inf window){
            this.tb = tb;
            this.window = window;
        }

        @Override
        public void action(){
            String[] data = tb.label.split(":");

            String ip = "127.0.0.1";
            if (tb.label.length() > 0) ip = data[0];

            int port = 25566;
            if (data.length > 1) port = Integer.parseInt(data[1]);

            //Вместо ip может быть записано число игроков для создания сервера
            if (ip.contains(".")) {
                Global.tcpControl.connect(ip, port);
                Global.tcpRead.start();
            } else {
                new ServerLoader(port, Integer.parseInt(ip), false);
                Global.tcpControl.connect("127.0.0.1", port);
                Global.tcpRead.start();
            }

            allDelete();
            game.initAfterConnect();
        }

        public void allDelete(){
            tb.delete();
            window.delete();
            delete();
        }
    }
}
