package engine.map;

import engine.image.Mask;
import engine.obj.Obj;
import game.client.TextureManager;

public class Border extends Obj {
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	private static final int size = 100;//Размер толщины каждой линии, ибо маска танка должна попасть внутрь

	public Border(int roomWidth, int roomHeight, int type) {
		super(0, 0, 0, 0, 0, false, TextureManager.sys_null);
		
		int x, y, w, h;
		
		switch (type){
			case NORTH: 
				x = roomWidth/2;
				y = -size/2;
				w = roomWidth;
				h = size;
			break;
			case EAST: 
				x = roomWidth+size/2;
				y = roomHeight/2;
				w = size;
				h = roomHeight;
			break;
			case SOUTH: 
				x = roomWidth/2;
				y = roomHeight+size/2;
				w = roomWidth;
				h = size;
			break;
			case WEST: 
				x = -size/2;
				y = roomHeight/2;
				w = size;
				h = roomHeight;
			break;
			default:
				x = 0;
				y = 0;
				w = 1;
				h = 1;
			break;
		}
		
		this.x = x;
		this.y = y;
		
		mask = new Mask("mask.png", h, w);//Путь должен быть, иначе mask выкинет ошибку при парсе; height и width наоборот -- магия
		mask.calc(x, y, 0);
		mask.dynamic = false;
	}
	
	public static void createAll(Room room){
		room.objAdd(new Border(room.width, room.height, Border.NORTH));
		room.objAdd(new Border(room.width, room.height, Border.EAST));
		room.objAdd(new Border(room.width, room.height, Border.SOUTH));
		room.objAdd(new Border(room.width, room.height, Border.WEST));
	}

}
