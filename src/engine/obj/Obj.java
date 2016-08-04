package engine.obj;

import engine.Global;
import engine.io.Logger;
import engine.map.Room;
import engine.image.Animation;
import engine.image.Mask;
import engine.image.TextureHandler;

public class Obj extends ObjLight{
	
	public double speed; //На сколько пикселей объект смещается за 1 секунду
	public double direction; //0, 360 - в право, против часовой - движение
	
	private String[] collObj;//список объектов с которыми надо проверять столкновения
	private boolean collHave = false;//есть ли столкновения
	
	public double xPrevious;//коры объекта в предыдущем шаге
	public double yPrevious;//(для столкновения)
	public double directionPrevious;//директион объекта в предыдущем шаге (для столкновения)
	
	public Mask mask;
	
	public Obj(double x, double y, double speed, double direction, int depth, boolean maskDynamic, TextureHandler textureHandler){
		super(x, y, direction, depth, textureHandler);
		create(speed, direction, maskDynamic);
	}
	
	public Obj(double x, double y, double speed, double direction, int depth, boolean maskDynamic, TextureHandler[] textureHandler){
		super(x, y, direction, depth, textureHandler);
		create(speed, direction, maskDynamic);
	}
	
	private void create(double speed, double direction,  boolean maskDynamic){
		try{
			this.mask = image.getMask().clone();
		} catch (CloneNotSupportedException e) {
			Logger.error("Failed with clone object. Id = " + id);
		}
		
		this.speed = speed;
		this.direction = direction;
		mask.dynamic = maskDynamic;
		
		mask.calc(this.x, this.y, getDirectionDraw());//расчёт маски
		
		if (Global.setting.DEBUG_CONSOLE_OBJECT) Logger.p("Object \"" + image.getPath() + "\" create. Id = " + id);
	}

	@Override
	public void draw(){
		//для отрисовки объекта с поворотом на direction
		directionDrawEqulas();
		
		//для движения камеры
		xView = Global.cameraXView - (Global.cameraX - x);
		yView = Global.cameraYView - (Global.cameraY - y);
	
		image.draw((int) Math.round(xView),(int) Math.round(yView), Math.toRadians(getDirectionDraw()));
		if (Global.setting.MASK_DRAW) mask.draw();
	}

	@Override
	public void update(long delta) {
		updateChildStart(delta);
		
		//должен быть раньше updateChildMid, что бы танк не мог повернуть в стену
		xPrevious = x;
		yPrevious = y;
		directionPrevious = direction;
		
		updateChildMid(delta);//step у дочерних объектов
		
		x = x + speed * Math.cos(Math.toRadians(direction)) * ((double) delta/1000000000);
		y = y - speed * Math.sin(Math.toRadians(direction)) * ((double) delta/1000000000);
		Global.room.mapControl.update(this);
		
		directionDrawEqulas();
		
		image.update(delta);
		
		mask.calcInThisStep = false;
		if (!mask.bullet){
			if(collHave){
				mask.collCheck(x, y, getDirectionDraw(), collObj, this);
			} else {
				if (mask.dynamic) mask.calc(x,  y, getDirectionDraw());
			}
		} else {
			mask.collCheckBullet(x, y, getDirectionDraw(), collObj, this);
		}
		
		updateChildFinal(delta);//step у дочерних объектов
		
		if (destroy){
			room.objDel(id);
		}
	}
	
	public void directionDrawEqulas(){
		setDirectionDraw(direction);
	}

	@Override
	public boolean isLight(){
		return false;
	}
	
	/*
	 * 
	 * ДАЛЬШЕ МЕТОДЫ SET, GET
	 * И МЕТОДЫ ПЕРЕОПРЕДЕЛЯЕМЫЕ У НАСЛЕДНИКОВ (updateChild, collReport);
	 * ТАК ЖЕ МЕТОДЫ P(ВЫВОД СООБЩЕНИЙ В КОНСОЛЬ)
	 * 
	 */

	public void setDirection(double direction){
		this.direction = direction;
	}
	
	public void setCollObj(String[] collObj){
		this.collObj = collObj;
		this.collHave = true;
	}

	public String[] getCollObj(){
		return this.collObj;
	}
	
	public double getDirection(){
		if (direction%360 >= 0){
			return direction%360;
		} else {
			return 360-Math.abs(direction%360);
		}
	}
	
	public Animation getAnimation(){
		if (image.isAnim()){
			return (Animation) image;
		} else {
			Logger.error("Try receive animation from sprite");
			return null;
		}
	}

	@Override
    public void setRoom(Room room){
        this.room = room;
        this.mask.start = 0;
    }
	
	public void updateChildStart(long delta){}
	public void updateChildMid(long delta){}
	public void updateChildFinal(long delta){}
	public void collReport(Obj obj){}

}