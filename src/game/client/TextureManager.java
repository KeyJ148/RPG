package game.client;

import engine.image.TextureHandler;

import java.io.File;

public class TextureManager {
	
	public static TextureHandler sys_null;//��������� ������ ��������
	public static TextureHandler cursor; //��������� �������� �������
	
	//��� �������
	public static final String pathImage = "res/image/";
	public static final String pathAnim = "res/animation/";
	
	//�������� ���� �������
	public static void initTexture(){
		//Engine: �������� ������� ��� �������������
		
		TextureManager.sys_null = new TextureHandler(pathImage + "Sys/sys_null.png");
		TextureManager.cursor = new TextureHandler(pathImage + "Sys/cursor.png");
	}
	
	public static TextureHandler[] parseAnimation(String path){
		int n=0;
		while (new File(path + "/" + (n+1) + ".png").exists()){
			n++;
		}
		
		TextureHandler[] textureHandler = new TextureHandler[n];
		
		//�������� �����������
		for(int i=0;i<n;i++){
			textureHandler[i] = new TextureHandler(path + "/" + (i+1) + ".png");
		}
		
		return textureHandler;
	}

}
