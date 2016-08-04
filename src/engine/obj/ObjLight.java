package engine.obj;

import engine.Global;
import engine.map.Room;
import engine.image.Animation;
import engine.image.Rendering;
import engine.image.Sprite;
import engine.image.TextureHandler;

public class ObjLight {
	
	public double x;
	public double y;
	public double xView = 0; //��� �������� ������
	public double yView = 0; //���� ������� � ����
	public int depth;
	private double directionDraw; //0, 360 - � �����, ������ ������� - ���������
	
	public boolean anim; //������� �������� ������ ��� ��������?(true = ��������)
	public boolean destroy = false;
	
	public Rendering image;

	public Room room;//������� � ������� ��������� ������
	public int id; //���������� ����� ��� �������
	
	public ObjLight(double x, double y, double directionDraw, int depth, TextureHandler textureHandler){
		this(x, y, directionDraw, depth);
		
		this.image = new Sprite(textureHandler);
		this.anim = false;
	}
	
	public ObjLight(double x, double y, double directionDraw, int depth, TextureHandler[] textureHandler){
		this(x, y, directionDraw, depth);
		
		this.image = new Animation(textureHandler);
		this.anim = true;
		
	}
	
	private ObjLight(double x, double y, double directionDraw, int depth){
		this.x = x;
		this.y = y;
		this.directionDraw = directionDraw;
		this.depth = depth;
	}
	
	public void draw(){
		//��� �������� ������
		xView = Global.cameraXView - (Global.cameraX - x);
		yView = Global.cameraYView - (Global.cameraY - y);
	
		image.draw((int) Math.round(xView), (int) Math.round(yView), Math.toRadians(directionDraw));
	}
	
	public void update(long delta) {
		image.update(delta);
		
		if (destroy){
			room.objDel(id);
		}
	}
	
	public void destroy(){
		destroy = true;
	}
	
	public boolean isLight(){
		return true;
	}
	
	/*
	 * ������ ������ SET/GET
	 */

	public void setRoom(Room room){
	    this.room = room;
    }

    public Room getRoom(){
        return room;
    }

	public void setDirectionDraw(double directionDraw){
	    this.directionDraw = directionDraw;
    }

	public double getDirectionDraw(){
		if (directionDraw%360 >= 0){
			return directionDraw%360;
		} else {
			return 360-Math.abs(directionDraw%360);
		}
	}
}
