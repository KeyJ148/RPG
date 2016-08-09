package game.client;

import engine.image.TextureHandler;

import java.io.File;

public class TextureManager {
	
	public static TextureHandler sys_null;//Системная пустая текстура
	public static TextureHandler cursor; //Системная текстура курсора

	public static TextureHandler player;
	public static TextureHandler enemy;
	public static TextureHandler associate;

	public static TextureHandler background_grass;
	public static TextureHandler background_sand;

	public static TextureHandler home_1;
	public static TextureHandler home_2;
	public static TextureHandler tree;

	public static TextureHandler button;
	public static TextureHandler item;
	public static TextureHandler window;

	public static TextureHandler skill_1;
	public static TextureHandler skill_2;

	//Для функций
	public static final String pathImage = "res/image/";
	public static final String pathAnim = "res/animation/";
	
	//Загрузка всех текстур
	public static void initTexture(){
		//Engine: Загрузка текстур при инициализации
		
		TextureManager.sys_null = new TextureHandler(pathImage + "Sys/sys_null.png");
		TextureManager.cursor = new TextureHandler(pathImage + "Sys/cursor.png");

		TextureManager.player = new TextureHandler(pathImage + "Person/player.png");
		TextureManager.enemy = new TextureHandler(pathImage + "Person/enemy.png");
		TextureManager.associate = new TextureHandler(pathImage + "Person/associate.png");

		TextureManager.background_grass = new TextureHandler(pathImage + "Background/grass.png");
		TextureManager.background_sand = new TextureHandler(pathImage + "Background/sand.png");

		TextureManager.home_1 = new TextureHandler(pathImage + "Home/home_1.png");;
		TextureManager.home_2 = new TextureHandler(pathImage + "Home/home_2.png");;
		TextureManager.tree = new TextureHandler(pathImage + "Home/tree.png");;

		TextureManager.button = new TextureHandler(pathImage + "Interface/button.png");;
		TextureManager.item = new TextureHandler(pathImage + "Interface/item.png");;
		TextureManager.window = new TextureHandler(pathImage + "Interface/window.png");;

		TextureManager.skill_1 = new TextureHandler(pathImage + "Skill/skill_1.png");;
		TextureManager.skill_2 = new TextureHandler(pathImage + "Skill/skill_2.png");;
	}
	
	public static TextureHandler[] parseAnimation(String path){
		int n=0;
		while (new File(path + "/" + (n+1) + ".png").exists()){
			n++;
		}
		
		TextureHandler[] textureHandler = new TextureHandler[n];
		
		//Загрузка изображений
		for(int i=0;i<n;i++){
			textureHandler[i] = new TextureHandler(path + "/" + (i+1) + ".png");
		}
		
		return textureHandler;
	}

}
