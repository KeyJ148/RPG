package game.client;

import engine.obj.Obj;
import engine.setting.ConfigReader;

public class AnimationScript {

    public class Script{
        public int x;
        public int y;
        public int dir;

        public Script(int x, int y, int dir){
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }

    private Obj obj;
    private Script[] scripts;
    private int frameSpeed = 0;//Кол-во кадров в секунду
    private boolean loop = false;

    private int frameNow = 0;//Номер текущего кадра
    private long update = 0; //Сколько прошло наносекунд с последней смены кадра
    private boolean delete = false;//Удалять ли объект?

    public AnimationScript(Obj obj){
        this.obj = obj;
    }

    public void loadFromFile(String path){
        ConfigReader cr = new ConfigReader(path);
        int countImage = cr.findInteger("COUNT_IMAGE");
        frameSpeed = cr.findInteger("SPEED");
        loop = cr.findBoolean("LOOP");
        scripts = new Script[countImage];

        for (int i=0; i<countImage; i++){
            int x = cr.findInteger(i + "_X");
            int y = cr.findInteger(i + "_Y");
            int dir = cr.findInteger(i + "_DIR");
            scripts[i] = new Script(x, y, dir);
        }
    }

    public void update(long delta) {
        update += delta;
        if ((frameSpeed != 0) && (update > 1000000000/frameSpeed)) {
            update = 0;
            if (frameNow == scripts.length - 1) {
                if (loop) frameNow = 0;
                else delete = true;
            } else {
                frameNow++;
                obj.position.x += scripts[frameNow].x;
                obj.position.y += scripts[frameNow].y;
                obj.position.setDirectionDraw(obj.position.getDirectionDraw() + scripts[frameNow].dir);
            }
        }
    }

    public boolean isDelete(){
        return delete;
    }
}
