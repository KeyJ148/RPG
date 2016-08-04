package engine.image;

import engine.io.Logger;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class Animation implements Rendering{
	
    private TextureHandler[] textureHandler;
    private int frameNumber = 0; //���-�� ������ [1;inf)
    private int frameSpeed = 0; //���-�� ������ � ������
    private int frameNow; //����� �������� ����� [0;inf)

    private Color color = Color.white;
    private int scale_x = 1;
    private int scale_y = 1; 
    
    private long update = 0; //������� ������ ���������� � ��������� ����� �����
    
    public Animation(TextureHandler[] textureHandler) {
		this.textureHandler = textureHandler;
		this.frameNumber = textureHandler.length;
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
    
    public void setFrameSpeed(int frameSpeed) {
    	if (frameSpeed < 0){
			Logger.error("Frame speed must be >= 0");
    		return;
    	}
    	
		this.update = 0;
        this.frameSpeed = frameSpeed;
    }
    
    public int getFrameNumber() {
        return frameNumber;
    }
    
    public int getWidth(int frame) {
        return (int) textureHandler[frame].getWidth();
    }

    public int getHeight(int frame) {
        return (int) textureHandler[frame].getHeight();
    }

    @Override
    public Mask getMask(){
		return textureHandler[0].mask;
	}

	@Override
    public void setMask(Mask mask){
    	for (int i=0;i<textureHandler.length;i++){
    		textureHandler[i].mask = mask;
    	}
    }

	@Override
    public void update(long delta) {
		update += delta;
		if ((frameSpeed != 0) && (update > 1000000000/frameSpeed)) {
			update = 0;
			if (frameNow == frameNumber - 1) {
				frameNow = 0;
			} else {
				frameNow++;
			}
		}
	}

	@Override
    public void draw(int x,int y,double direction) {
    	direction -= Math.PI/2; //������� ���������� ���� � ������� �� �����
    	direction = Math.toDegrees(direction);
    	
    	int width=(int)(textureHandler[frameNow].getWidth()*scale_x); 
        int height=(int)(textureHandler[frameNow].getHeight()*scale_y); 
    	
        GL11.glLoadIdentity();     
	    GL11.glTranslatef(x, y, 0);
	    GL11.glRotatef(Math.round(-direction), 0f, 0f, 1f);
	    
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

	@Override
	public int getWidth() {
		return getWidth(getFrameNow());
	}

	@Override
	public int getHeight() {
		return getHeight(getFrameNow());
	}

	@Override
	public String getPath(){
		return textureHandler[frameNow].path;
	}

	@Override
	public void setColor(Color color){
    	this.color = color;
    }

	@Override
	public boolean isAnim(){
    	return true;
    }
}