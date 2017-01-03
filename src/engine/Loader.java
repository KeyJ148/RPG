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
import org.newdawn.slick.util.Log;

public class Loader {
	

	public static void main (String args[]) {
		//�������� ���������
		try{
			System.loadLibrary("jinput-dx8");
			System.loadLibrary("jinput-raw");
			System.loadLibrary("lwjgl");
			System.loadLibrary("OpenAL32");
            Logger.println("32-bit native module load complite", Logger.Type.DEBUG);
		} catch (UnsatisfiedLinkError e32){
			try{
				System.loadLibrary("jinput-dx8_64");
				System.loadLibrary("jinput-raw_64");
				System.loadLibrary("lwjgl64");
				System.loadLibrary("OpenAL64");
                Logger.println("64-bit native module load complite", Logger.Type.DEBUG);
			} catch (UnsatisfiedLinkError e64){
                Logger.println("Native module not loading", Logger.Type.ERROR);
				System.exit(0);
			}
		}
		
		init();
		Global.engine.run();//������ �������� �����
	}
	
	//������������� ������ ����� ��������
	public static void init() {
		Log.setVerbose(false); //���������� ����� � Slick-util

		Global.setting = new SettingStorage();//�������� ��������� ��������
		Global.setting.init();//�������� ��������
		if (Global.setting.DEBUG_CONSOLE) Logger.enable(Logger.Type.DEBUG);
		if (Global.setting.DEBUG_CONSOLE_OBJECT) Logger.enable(Logger.Type.DEBUG_OBJECT);
		if (Global.setting.DEBUG_CONSOLE_IMAGE) Logger.enable(Logger.Type.DEBUG_IMAGE);
		if (Global.setting.DEBUG_CONSOLE_MASK) Logger.enable(Logger.Type.DEBUG_MASK);
		if (Global.setting.DEBUG_CONSOLE_FPS) Logger.enable(Logger.Type.CONSOLE_FPS);

		Global.engine = new Engine();//�������� ������ ��� �������� �����
		Global.engine.render.initGL();//������������� OpenGL
			
		Global.tcpControl = new TCPControl();
		Global.tcpRead = new TCPRead();
			
		TextureManager.initTexture();

		Global.infMain = new InfMain();
		MouseHandler.init();
			
		if ((Global.setting.DEBUG_CONSOLE_FPS) || (Global.setting.DEBUG_MONITOR_FPS)) 
			Global.engine.analyzer = new Analyzer();
		
		Logger.println("Inicialization end", Logger.Type.DEBUG);
		
		//������������� ����
		Global.game = new Game();
		Global.game.init();
	}
}
