package engine.image;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class Sprite implements Rendering{
	
    private TextureHandler textureHandler;
    private Color color = Color.white;
    public double scale_x = 1;
    public double scale_y = 1;
    
    public Sprite(TextureHandler textureHandler) {
		this.textureHandler = textureHandler;
    }

    @Override
    public int getWidth() {
        return textureHandler.getWidth();
    }

    @Override
    public int getHeight() {
        return textureHandler.getHeight();
    }

    @Override
    public Mask getMask(){
		return textureHandler.mask;
	}

    @Override
    public void setMask(Mask mask){
    	textureHandler.mask = mask;
    }

    @Override
    public String getPath(){
		return textureHandler.path;
	}

    @Override
    public void update(long delta){}//Наследуется от Rendering, нужен для анимации, вызывается каждый степ

    @Override
    public void draw(int x, int y, double direction) {
    	direction -= Math.PI/2; //смещена начального угла с Востока на Север
    	direction = Math.toDegrees(direction);
    	
    	int width=(int)(getWidth()*scale_x); 
        int height=(int)(getHeight()*scale_y);

        GL11.glLoadIdentity();     
	    GL11.glTranslatef(x, y, 0);
	    GL11.glRotatef(Math.round(-direction), 0f, 0f, 1f);
	    
	    color.bind(); 
	    textureHandler.texture.bind();
	    
	    GL11.glBegin(GL11.GL_QUADS);
		    GL11.glTexCoord2f(0,0); 
		    GL11.glVertex2f(-width/2, -height/2); 
		    GL11.glTexCoord2f(1,0); 
		    GL11.glVertex2f(width/2, -height/2); 
		    GL11.glTexCoord2f(1,1); 
		    GL11.glVertex2f(width/2, height/2); 
		    GL11.glTexCoord2f(0,1); 
		    GL11.glVertex2f(-width/2, height/2); 
	    GL11.glEnd(); 
    }

    @Override
    public void setColor(Color color){
    	this.color = color;
    }

    @Override
    public boolean isAnim(){
    	return false;
    }
}