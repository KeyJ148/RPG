package engine.io;

import org.lwjgl.input.Mouse;

import engine.Global;
import engine.image.Sprite;
import game.client.TextureManager;

public class MouseHandler {
	
	public static Sprite cursor;

	public static int mouseX;
	public static int mouseY;
	public static boolean mouseDown1;
	public static boolean mouseDown2;
	public static boolean mouseDown3;

	public static void init() {
		//���������� ������������ �������
		Mouse.setGrabbed(true);
				
		//���������� ������ �������
		cursor = new Sprite(TextureManager.cursor);
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
		cursor.draw(mouseX, mouseY, 0.0);
	}

	//������������ ������, ���������� ��� ������ � �����������
	public static void lock(){
		mouseDown1 = false;
		mouseDown2 = false;
		mouseDown3 = false;
	}
}
