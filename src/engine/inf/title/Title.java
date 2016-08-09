package engine.inf.title;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import java.awt.Font;


public class Title {
	
	public static final int BOLD = 0;
	
	public static final Color defaultColor = Color.black;
	public static final int defaultSize = 12;
	public static final int defaultFont = Font.PLAIN;
	
	private String str;//��� �������� � ������
	private int x;//����
	private int y;
	private Color c;//���� �������
	private int size;//������ ������
	private int font;//����� ������
	
	public Title(int x, int y, String str){
		this.x = x;
		this.y = y;
		this.str = str;
		this.c = defaultColor;
		this.size = defaultSize;
		this.font = defaultFont;
	}
	
	public Title(int x, int y, String str, Color c){
		this(x, y, str);
		this.c = c;
	}
	
	public Title(int x, int y, String str, int size){
		this(x, y, str);
		this.size = size;
	}
	
	public Title(int x, int y, String str, Color c, int size, int font){
		this(x, y, str);
		this.c = c;
		this.size = size;
		this.font = font;
	}
	
	public void draw(){
		int i = FontManager.existFont(size, font);
		if (i == -1) i = FontManager.addFont(size, font);
		TrueTypeFont ttFont = FontManager.getFont(i);
		GL11.glLoadIdentity();
		ttFont.drawString((float) x, (float) y, str, c);
		
	}

	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public String getStr(){
		return str;
	}
	
	public Color getColor(){
		return c;
	}
	
	public int getSize(){
		return size;
	}
	
	public int getFont(){
		return font;
	}
	
}
