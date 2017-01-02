package engine.cycle;

import engine.Global;
import engine.image.Camera;
import engine.io.Logger;

public class Update {
	
	public void loop(long delta){
		Global.game.update(delta);//Обновить главный игровой класс при необходимости

		Global.engine.render.clearTitle();//Убрать все надписи с прошлого рендера
		Global.tcpRead.update();//Обработать все полученные сообщения
		Global.infMain.update();

		if (Global.room != null) {
			Global.room.update(delta);//Обновить все объекты в комнате
		}else {
			Logger.error("No create room! (Global.room)");
			System.exit(0);
		}

		Camera.calc();//Расчёт положения камеры
	}

}
