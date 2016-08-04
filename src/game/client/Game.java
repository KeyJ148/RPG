package game.client;

import engine.Global;
import engine.io.KeyboardHandler;
import engine.map.Room;
import engine.obj.ObjLight;
import game.client.person.Item;
import game.client.person.Person;
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

        Person p = new Person(Integer.MAX_VALUE/2+100,Integer.MAX_VALUE/2+100,90,TextureManager.cursor);
        Global.room.objAdd(p);
        p.addItem(new Item(Item.Grade.GRAY, Item.Type.HELMET));
        Item item = new Item(Item.Grade.GREEN, Item.Type.HELMET);
        item.effect.addition.maxHp = 100;
        System.out.println(p.stats.maxHp);
        System.out.println(p.addItem(item));
        p.calcStats();
        System.out.println(p.stats.maxHp);
	}
	
	public void update(long delta){
		//Engine: Выполняется каждый степ перед обновлением всех игровых объектов
		if (KeyboardHandler.isKeyDown(Keyboard.KEY_ESCAPE)) System.exit(0);
	}
	
	public void render(){
		//Engine: Выполняется каждый степ перед перерисовкой всех игровых объектов
	}

}
