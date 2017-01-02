package engine.obj;

import engine.image.TextureHandler;
import engine.obj.components.Collision;
import engine.obj.components.Movement;
import engine.obj.components.Position;
import engine.obj.components.render.Animation;
import engine.obj.components.render.Rendering;
import engine.obj.components.render.Sprite;

public class Obj {
	public boolean destroy = false;

	public Position position;
	public Movement movement;
	public Rendering rendering;
	public Collision collision;
	
	private boolean collHave = false;//есть ли столкновения

	public Obj(){}

	public Obj(double x, double y, int depth){
		this();
		position = new Position(this, x, y, depth);
	}

	public Obj(double x, double y, int depth, double directionDraw){
		this(x, y, depth);
		position.setDirectionDraw(directionDraw);
	}

	public Obj(double x, double y, int depth, double directionDraw, TextureHandler textureHandler){
		this(x, y, depth, directionDraw);
		this.rendering = new Sprite(this, textureHandler);
	}
	
	public Obj(double x, double y, int depth, double directionDraw, TextureHandler[] textureHandler){
		this(x, y, depth, directionDraw);
		rendering = new Animation(this, textureHandler);
	}

	public void update(long delta) {
		if (position != null) position.update(delta);
		if (movement != null) movement.update(delta);
		if (rendering != null) rendering.update(delta);
		if (collision != null) collision.update(delta);

		if (destroy) position.room.objDel(position.id);
	}

	public void draw(){
		if (position != null) position.draw();
		if (movement != null) movement.draw();
		if (rendering != null) rendering.draw();
		if (collision != null) collision.draw();
	}

	public void destroy(){
		destroy = true;
	}
}