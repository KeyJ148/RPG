package game.client;

import engine.image.TextureHandler;

import java.io.File;

public class TextureManager {
	
	public static TextureHandler sys_null;//Системная пустая текстура
	public static TextureHandler cursor; //Системная текстура курсора

	public static TextureHandler human_body;
	public static TextureHandler human_head_face;
	public static TextureHandler human_head_hair;
	public static TextureHandler peasant_armor_hand_fist_1;
	public static TextureHandler peasant_armor_hand_fist_2;
	public static TextureHandler peasant_armor_hand_fist_3;
	public static TextureHandler peasant_armor_hand_forearm;
	public static TextureHandler peasant_armor_hand_shoulder;

	public static TextureHandler peasant_armor;

	public static TextureHandler enemy;
	public static TextureHandler associate;

	public static TextureHandler background_grass;
	public static TextureHandler background_sand;

	//Для функций
	public static final String pathImage = "res/image/";
	public static final String pathAnim = "res/animation/";
	
	//Загрузка всех текстур
	public static void initTexture(){
		//Engine: Загрузка текстур при инициализации
		
		TextureManager.sys_null = new TextureHandler(pathImage + "Sys/sys_null.png");
		TextureManager.cursor = new TextureHandler(pathImage + "Sys/cursor.png");

		TextureManager.human_body = new TextureHandler(pathImage + "Person/Human/human_body.png");
		TextureManager.human_head_face = new TextureHandler(pathImage + "Person/Human/human_head_face.png");
		TextureManager.human_head_hair = new TextureHandler(pathImage + "Person/Human/human_head_hair.png");
		TextureManager.peasant_armor_hand_fist_1 = new TextureHandler(pathImage + "Person/Human/peasant_armor_hand_fist_1.png");
		TextureManager.peasant_armor_hand_fist_2 = new TextureHandler(pathImage + "Person/Human/peasant_armor_hand_fist_2.png");
		TextureManager.peasant_armor_hand_fist_3 = new TextureHandler(pathImage + "Person/Human/peasant_armor_hand_fist_3.png");
		TextureManager.peasant_armor_hand_forearm = new TextureHandler(pathImage + "Person/Human/peasant_armor_hand_forearm.png");
		TextureManager.peasant_armor_hand_shoulder = new TextureHandler(pathImage + "Person/Human/peasant_armor_hand_shoulder.png");

		TextureManager.peasant_armor = new TextureHandler(pathImage + "Equipment/Armor/peasant_armor.png");

		TextureManager.enemy = new TextureHandler(pathImage + "Person/enemy.png");
		TextureManager.associate = new TextureHandler(pathImage + "Person/associate.png");

		TextureManager.background_grass = new TextureHandler(pathImage + "Background/grass.png");
		TextureManager.background_sand = new TextureHandler(pathImage + "Background/sand.png");
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
