package game.client;

import engine.Global;
import engine.image.Sprite;
import game.client.person.Associate;

public class TCPGameRead{

	public static void read(int type, String str){
		switch (type){
			case 2: take2(str); break;
			case 3: take3(str); break;
			case 4: take4(str); break;
			case 5: take5(str); break;
			//Engine: –азличные действи€ с уникальными индексами
		}
	}

	public static void take2(String str){
		String[] data = str.split(" ");
		int x = Integer.parseInt(data[0]);
		int y = Integer.parseInt(data[1]);
		int direction = Integer.parseInt(data[2]);
		int id = Integer.parseInt(data[3]);

		if (ClientData.associates != null){
			if (ClientData.associates[id] == null){
				ClientData.associates[id] = new Associate(x, y, direction);
				Global.room.objAdd(ClientData.associates[id]);
			}

			ClientData.associates[id].x = x;
			ClientData.associates[id].y = y;
			ClientData.associates[id].direction = direction;
		}
	}

	public static void take3(String str){
		if (str.equals("grass")) Global.room.background = new Sprite(TextureManager.background_grass);
		if (str.equals("sand")) Global.room.background = new Sprite(TextureManager.background_sand);
	}

	public static void take4(String str){
		ClientData.peopleMax = Integer.parseInt(str);
		ClientData.associates = new Associate[ClientData.peopleMax];//ќдин будет пустой, соответствующий id этого клиента
	}

	public static void take5(String str) {
		ClientData.serverStarted = true;
	}

}