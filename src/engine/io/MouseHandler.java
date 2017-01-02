package engine.io;

import engine.Global;
import engine.obj.Obj;
import engine.obj.components.Position;
import engine.obj.components.render.Sprite;
import game.client.TextureManager;
import org.lwjgl.input.Mouse;

public class MouseHandler {
	
	public static Obj cursor;

	public static int mouseX;
	public static int mouseY;
	public static boolean mouseDown1;
	public static boolean mouseDown2;
	public static boolean mouseDown3;

	public static void init() {
		//���������� ������������ �������
		Mouse.setGrabbed(true);
				
		//���������� ������ �������
		cursor = new Obj();
		cursor.position = new Position(cursor, 0, 0, -1000);
		cursor.position.absolute = false;
		cursor.rendering = new Sprite(cursor, TextureManager.cursor);

		mouseX = Mouse.getX();
		mouseY = Global.engine.render.getHeight()-Mouse.getY();
	}
	
	public static void update(){
		mouseX = Mouse.getX();
		mouseY = Global.engine.render.getHeight()-Mouse.getY();
		mouseDown1 = Mouse.isButtonDown(0);
		mouseDown2 = Mouse.isButtonDown(1);
		mouseDown3 = Mouse.isButtonDown(2);
			
	}
	
	public static void draw(){
		cursor.position.x = mouseX;
		cursor.position.y = mouseY;
		cursor.draw();
	}

	//������������ ������, ���������� ��� ������ � �����������
	public static void lock(){
		mouseDown1 = false;
		mouseDown2 = false;
		mouseDown3 = false;
	}
}
