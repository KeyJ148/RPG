package game.client;

import engine.Global;
import engine.io.KeyboardHandler;
import engine.map.Room;
import engine.obj.Obj;
import engine.obj.ObjLight;
import game.client.person.Item;
import game.client.person.Player;
import org.lwjgl.input.Keyboard;

public class Game {

	public void init() {
		//Engine: Инициализация игры перед запуском главного цикла
		Global.room = new Room(Integer.MAX_VALUE, Integer.MAX_VALUE);
		new Menu(this);
	}
	
	public void update(long delta){
		//Engine: Выполняется каждый степ перед обновлением всех игровых объектов
		if (KeyboardHandler.isKeyDown(Keyboard.KEY_ESCAPE)) System.exit(0);

		for (int i=0; i<ClientData.animationScripts.size(); i++){
			AnimationScript as = ClientData.animationScripts.get(i);
			if (!as.isDelete()) {
				as.update(delta);
			} else {
				ClientData.animationScripts.remove(i);
				i--;
			}
		}
	}
	
	public void render(){
		//Engine: Выполняется каждый степ перед перерисовкой всех игровых объектов
	}

	//Методы не относящиеся к движку
	public void initAfterConnect(){
		Global.pingCheck.start();

		ObjLight obj = new ObjLight(Integer.MAX_VALUE/2,Integer.MAX_VALUE/2,90,1,TextureManager.cursor);
		ObjLight obj1 = new ObjLight(Integer.MAX_VALUE/2-100,Integer.MAX_VALUE/2-100,90,1,TextureManager.cursor);
		Obj obj2 = new Obj(Integer.MAX_VALUE/2+200,Integer.MAX_VALUE/2+200,0,90,1,false,TextureManager.enemy);
		Global.room.objAdd(obj);
		Global.room.objAdd(obj1);
		Global.room.objAdd(obj2);

		Player p = new Player(Integer.MAX_VALUE/2+100,Integer.MAX_VALUE/2+100,90);
		Global.camera = p;
		Global.room.objAdd(p);
		p.addItem(new Item(Item.Grade.GRAY, Item.Type.HELMET));
		Item item = new Item(Item.Grade.GREEN, Item.Type.HELMET);
		item.effect.addition.maxHp = 100;
		System.out.println(p.stats.maxHp);
		System.out.println(p.addItem(item));
		p.calcStats();
		System.out.println(p.stats.maxHp);

		AnimationScript as = new AnimationScript(obj2);
		as.loadFromFile("animation/player.properties");
		ClientData.animationScripts.add(as);
	}
}
