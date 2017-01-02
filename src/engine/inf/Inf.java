package engine.inf;

import engine.image.TextureHandler;
import engine.obj.Obj;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class Inf  {

    public Obj obj;

    public boolean delete = false;
    public boolean frame = false;
    public Color frameColor;

    public Inf(int x, int y, int width, int height, TextureHandler texture){
        obj = new Obj(x, y, -999, 90, texture);
        obj.position.absolute = false;
        obj.rendering.setWidth(width);
        obj.rendering.setHeight(height);
    }

    public void update(){}

    public void draw(){
        obj.draw();
        if (frameColor != null) drawFrame();
    }

    private void drawFrame(){
        int x = (int) obj.position.x;
        int y = (int) obj.position.y;
        int width = obj.rendering.getWidth();
        int height = obj.rendering.getHeight();

        GL11.glLoadIdentity();
        GL11.glTranslatef(0, 0, 0);
        GL11.glColor3b((byte) (frameColor.getRedByte()+128), (byte) (frameColor.getGreenByte()+128), (byte) (frameColor.getBlueByte()+128));

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBegin(GL11.GL_LINE_LOOP);
            GL11.glTexCoord2f(0,0);
            GL11.glVertex2f(x-width/2, y-height/2);
            GL11.glTexCoord2f(1,0);
            GL11.glVertex2f(x+width/2, y-height/2);
            GL11.glTexCoord2f(1,1);
            GL11.glVertex2f(x+width/2, y+height/2);
            GL11.glTexCoord2f(0,1);
            GL11.glVertex2f(x-width/2, y+height/2);
        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public void setFrame(Color c){
        frame = true;
        frameColor = c;
    }

    public void delete(){
        delete = true;
    }
}
