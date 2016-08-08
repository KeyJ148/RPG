package engine.net.server;

import java.io.IOException;

public class ServerRead extends Thread{

	private int id; //номер соединения в массиве в gameServer
	public boolean disconnect = false;//Отключён ли этот игрок

	public ServerRead(int id){
		this.id = id;
		start();
	}

	@Override
	public void run(){
		//Постоянный обмен данными (на TCP)
		//Только после подключения всех игроков
		String str;
		try{
			while (true){
				str = GameServer.in[id].readUTF();
				synchronized(GameServer.messagePack[id]) {//Защита от одновременной работы с массивом
					GameServer.messagePack[id].add(str);
				}
			}
		} catch (IOException e){
			GameServer.error("Take message (id: " + id + ")");
			GameServer.disconnect++;
		}
	}
	
}