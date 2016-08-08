package game.client;

import engine.Global;
import engine.image.Sprite;
import game.client.person.Enemy;

public class TCPGameRead{

	public static void read(int type, String str){
		switch (type){
			case 2: take2(str); break;
			case 3: take3(str); break;
			case 4: take4(str); break;
			//Engine: –азличные действи€ с уникальными индексами
		}
	}

	public static void take2(String str){
		String[] data = str.split(" ");
		int x = Integer.parseInt(data[0]);
		int y = Integer.parseInt(data[1]);
		int direction = Integer.parseInt(data[2]);
		int id = Integer.parseInt(data[3]);

		if (ClientData.enemies != null){
			if (ClientData.enemies[id] == null){
				ClientData.enemies[id] = new Enemy(x, y, direction, TextureManager.player);
				Global.room.objAdd(ClientData.enemies[id]);
			}

			ClientData.enemies[id].x = x;
			ClientData.enemies[id].y = y;
			ClientData.enemies[id].direction = direction;
		}
	}

	public static void take3(String str){
		if (str.equals("grass")) Global.room.background = new Sprite(TextureManager.background_grass);
		if (str.equals("sand")) Global.room.background = new Sprite(TextureManager.background_sand);
	}

	public static void take4(String str){
		ClientData.peopleMax = Integer.parseInt(str);
		ClientData.enemies = new Enemy[ClientData.peopleMax];//ќдин будет пустой, соответствующий id этого клиента
	}

}
