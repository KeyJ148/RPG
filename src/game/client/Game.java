package game.client;

import engine.io.KeyboardHandler;
import org.lwjgl.input.Keyboard;

public class Game {

	public void init() {
		//Engine: Инициализация игры перед запуском главного цикла
	}
	
	public void update(long delta){
		//Engine: Выполняется каждый степ перед обновлением всех игровых объектов
		if (KeyboardHandler.isKeyDown(Keyboard.KEY_ESCAPE)) System.exit(0);
	}
	
	public void render(){
		//Engine: Выполняется каждый степ перед перерисовкой всех игровых объектов
	}

}
