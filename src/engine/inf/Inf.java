package engine.inf;

import engine.image.Sprite;
import engine.image.TextureHandler;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class Inf {

    public int x, y;
    public int width, height;
    public double direction = Math.PI/2;
    public Sprite sprite;

    public boolean delete = false;
    public boolean frame = false;
    public Color frameColor;

    public Inf(int x, int y, int width, int height, TextureHandler texture){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = new Sprite(texture);
        this.sprite.scale_x = (double) width/texture.getWidth();
        this.sprite.scale_y = (double) height/texture.getHeight();
    }

    public void update(){}

    public void draw(){
        sprite.draw(x,y,direction);
        if (frame) drawFrame();
    }

    private void drawFrame(){
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
