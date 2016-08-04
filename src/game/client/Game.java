package game.client;

import engine.Global;
import engine.io.KeyboardHandler;
import engine.map.Room;
import engine.obj.ObjLight;
import game.server.ServerLoader;
import org.lwjgl.input.Keyboard;

public class Game {

	public void init() {
		//Engine: Инициализация игры перед запуском главного цикла
		new ServerLoader(25566, 1, false);

		Global.tcpControl.connect("127.0.0.1", 25566);
		Global.tcpRead.start();

		Global.room = new Room(Integer.MAX_VALUE, Integer.MAX_VALUE);
		ObjLight obj = new ObjLight(Integer.MAX_VALUE/2,Integer.MAX_VALUE/2,90,0,TextureManager.cursor);
		ObjLight obj1 = new ObjLight(Integer.MAX_VALUE/2-100,Integer.MAX_VALUE/2-100,90,0,TextureManager.cursor);
		Global.camera = obj;
		Global.room.objAdd(obj);
		Global.room.objAdd(obj1);
	}
	
	public void update(long delta){
		//Engine: Выполняется каждый степ перед обновлением всех игровых объектов
		if (KeyboardHandler.isKeyDown(Keyboard.KEY_ESCAPE)) System.exit(0);
	}
	
	public void render(){
		//Engine: Выполняется каждый степ перед перерисовкой всех игровых объектов
	}

}
