package engine.inf;

import engine.Global;
import engine.image.TextureHandler;
import engine.title.Title;

public class Label extends Inf{

    public String label;

    public Label(int x, int y, int width, int height, TextureHandler texture){
        super(x, y, width, height, texture);
    }

    @Override
    public void draw(){
        super.draw();
        if (label != null)
            Global.engine.render.addTitle(new Title(x, y, label, height/2));
    }
}
