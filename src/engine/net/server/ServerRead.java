package engine.net.server;

import java.io.IOException;

public class ServerRead extends Thread{

	private int id; //����� ���������� � ������� � gameServer
	public boolean disconnect = false;//�������� �� ���� �����

	public ServerRead(int id){
		this.id = id;
		start();
	}

	@Override
	public void run(){
		//���������� ����� ������� (�� TCP)
		//������ ����� ����������� ���� �������
		String str;
		try{
			while (true){
				str = GameServer.in[id].readUTF();
				synchronized(GameServer.messagePack[id]) {//������ �� ������������� ������ � ��������
					GameServer.messagePack[id].add(str);
				}
			}
		} catch (IOException e){
			GameServer.p("Player disconnect (id: " + id + ")");
			GameServer.disconnect++;
			disconnect = true;
		}
	}
	
}