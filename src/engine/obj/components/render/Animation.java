package engine.obj.components.render;

import engine.Vector2;
import engine.image.TextureHandler;
import engine.io.Logger;
import engine.obj.Obj;
import org.lwjgl.opengl.GL11;

public class Animation extends Rendering{
	
    private TextureHandler[] textureHandler;

    private int frameSpeed = 0; //���-�� ������ � ������
    private int frameNow; //����� �������� ����� [0;inf)
    
    private long update = 0; //������� ������ ���������� � ��������� ����� �����
    
    public Animation(Obj obj, TextureHandler[] textureHandler) {
    	super(obj);
		this.textureHandler = textureHandler;
    }

	@Override
    public void update(long delta) {
		update += delta;
		if ((frameSpeed != 0) && (update > 1000000000/frameSpeed)) {
			update = 0;
			if (frameNow == textureHandler.length - 1) {
				frameNow = 0;
			} else {
				frameNow++;
			}
		}
	}

	@Override
    public void draw() {
		Vector2<Integer> relativePosition = getObj().position.getRelativePosition();
		double xView = relativePosition.x;
		double yView = relativePosition.y;
		double directionDraw = getObj().position.getDirectionDraw();

		directionDraw -= 90; //������� ���������� ���� � ������� �� �����
    	
    	int width=(int)(textureHandler[frameNow].getWidth()*scale_x); 
        int height=(int)(textureHandler[frameNow].getHeight()*scale_y); 
    	
        GL11.glLoadIdentity();     
	    GL11.glTranslatef((float) xView, (float) yView, 0);
	    GL11.glRotatef(Math.round(-directionDraw), 0f, 0f, 1f);
	    
	    color.bind(); 
	    textureHandler[frameNow].texture.bind();
	    
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

	public void setFrameSpeed(int frameSpeed) {
		if (frameSpeed < 0){
			Logger.println("Frame speed must be >= 0", Logger.Type.ERROR);
			return;
		}

		this.update = 0;
		this.frameSpeed = frameSpeed;
	}

	public int getFrameNow() {
		return frameNow;
	}

	public int getFrameSpeed() {
		return frameSpeed;
	}

	public void setFrameNow(int frameNow) {
		this.frameNow = frameNow;
	}

	public int getFrameNumber() {
		return textureHandler.length;
	}

	public int getWidth(int frame) {
		return textureHandler[frame].getWidth();
	}

	public int getHeight(int frame) {
		return textureHandler[frame].getHeight();
	}

	@Override
	public int getWidthTexture(){
		return getWidth(getFrameNow());
	}

	@Override
	public int getHeightTexture(){
		return getHeight(getFrameNow());
	}

	@Override
	public int getWidth() {
		return (int) scale_x*getWidth(getFrameNow());
	}

	@Override
	public int getHeight() {
		return (int) scale_y*getHeight(getFrameNow());
	}

	@Override
	public void setWidth(int width) {
		scale_x = width/getWidth();
	}

	@Override
	public void setHeight(int height) {
		scale_y = height/getHeight();
	}

	@Override
	public void setDefaultSize(){
		scale_x = 1;
		scale_y = 1;
	}
}