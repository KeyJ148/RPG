package game.client;

import engine.Global;
import game.client.person.Ally;

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
		int speed = Integer.parseInt(data[3]);
		int directionDraw = Integer.parseInt(data[4]);
		int id = Integer.parseInt(data[5]);

		if (ClientData.allies != null){
			if (ClientData.allies[id] == null){
				ClientData.allies[id] = new Ally(x, y, direction, speed, directionDraw);
				Global.room.objAdd(ClientData.allies[id]);
			}

			ClientData.allies[id].setData(x, y, direction, speed, directionDraw);
		}
	}

	public static void take3(String str){
		if (str.equals("grass")) Global.room.background = TextureManager.background_grass;
		if (str.equals("sand")) Global.room.background = TextureManager.background_sand;
	}

	public static void take4(String str){
		ClientData.peopleMax = Integer.parseInt(str);
		ClientData.allies = new Ally[ClientData.peopleMax];//ќдин будет пустой, соответствующий id этого клиента
	}

	public static void take5(String str) {
		ClientData.serverStarted = true;
		Global.pingCheck.start();
	}

}