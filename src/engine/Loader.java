package engine;

import engine.cycle.Analyzer;
import engine.cycle.Engine;
import engine.inf.InfMain;
import engine.io.Logger;
import engine.io.MouseHandler;
import engine.net.client.TCPControl;
import engine.net.client.TCPRead;
import engine.setting.SettingStorage;
import game.client.Game;
import game.client.TextureManager;

public class Loader {
	

	public static void main (String args[]) {
		//Загрузка библиотек
		try{
			System.loadLibrary("jinput-dx8");
			System.loadLibrary("jinput-raw");
			System.loadLibrary("lwjgl");
			System.loadLibrary("OpenAL32");
            Logger.p("32-bit native module load complite");
		} catch (UnsatisfiedLinkError e32){
			try{
				System.loadLibrary("jinput-dx8_64");
				System.loadLibrary("jinput-raw_64");
				System.loadLibrary("lwjgl64");
				System.loadLibrary("OpenAL64");
                Logger.p("64-bit native module load complite");
			} catch (UnsatisfiedLinkError e64){
                Logger.error("Native module not loading");
				System.exit(0);
			}
		}
		
		init();
		Global.engine.run();//Запуск главного цикла
	}
	
	//Инициализация движка перед запуском
	public static void init() {
		Global.setting = new SettingStorage();//Создание хранилища настроек
		Global.setting.init();//Загрузка настроек
		Global.engine = new Engine();//Создание класса для главного цикла
		
		Global.engine.render.initGL();//Инициализация OpenGL
			
		Global.tcpControl = new TCPControl();
		Global.tcpRead = new TCPRead();
			
		TextureManager.initTexture();

		Global.infMain = new InfMain();
		MouseHandler.init();
			
		if ((Global.setting.DEBUG_CONSOLE_FPS) || (Global.setting.DEBUG_MONITOR_FPS)) 
			Global.engine.analyzer = new Analyzer();
		
		if (Global.setting.DEBUG_CONSOLE) Logger.p("Inicialization end.");
		
		//Инициализация игры
		Global.game = new Game();
		Global.game.init();
	}
}
