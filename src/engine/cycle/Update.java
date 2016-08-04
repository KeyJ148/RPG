package engine.cycle;

import engine.Global;
import engine.io.Logger;
import engine.obj.ObjLight;

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

		cameraCalc();//Расчёт положения камеры
	}

	public void cameraCalc(){
		ObjLight cameraShould = (ObjLight) Global.camera;//Объект, за которым следует камера
		if (cameraShould == null){
			Global.cameraX = 0;
			Global.cameraY = 0;
		} else {
			Global.cameraX = cameraShould.x;
			Global.cameraY = cameraShould.y;
		}

		int width = Global.engine.render.getWidth();
		int height = Global.engine.render.getHeight();
		int widthMap = Global.room.width;
		int heightMap = Global.room.height;

		Global.cameraXView = width/2;
		Global.cameraYView = height/2;

		if (Global.cameraX < width/2){
			Global.cameraX = width/2;
		}
		if (Global.cameraY < height/2){
			Global.cameraY = height/2;
		}
		if (Global.cameraX > widthMap-width/2){
			Global.cameraX = widthMap-width/2;
		}
		if (Global.cameraY >heightMap-height/2){
			Global.cameraY = heightMap-height/2;
		}

		if (Global.engine.render.getWidth() > widthMap){
			Global.cameraX = widthMap/2;
		}
		if (Global.engine.render.getHeight() > heightMap){
			Global.cameraY = heightMap/2;
		}
	}
}
