package engine.map;

import engine.image.Sprite;
import engine.obj.ObjLight;

import java.util.Vector;

public class Room {
	
	public Sprite background;
	public int width, height;

	public Vector<ObjLight> objects; //Массив со всеми объектами
	public MapControl mapControl; //Массив со всеми чанками и объектами

	public Room(int width, int height) {
		this.width = width;
		this.height = height;
		
		objects = new Vector<ObjLight>();
		mapControl = new MapControl(width, height);
	}

	public void update(long delta){
		for (ObjLight obj : objects)
			if (obj != null)
				obj.update(delta);
	}

	public int objCount(){
		int count = 0;
		for (ObjLight obj : objects)
			if (obj != null)
				count++;

		return count;
	}

	//Добавление объекта в комнату
	public void objAdd(ObjLight objLight){
		objLight.id = objects.size();
		objLight.setRoom(this);

		objects.add(objLight);
		mapControl.add(objLight);
	}

	//Удаление объекта из комнаты по id
	public void objDel(int id){
		mapControl.del(id);//Используется objects, так что должно быть раньше
		objects.set(id, null);
	}
}
