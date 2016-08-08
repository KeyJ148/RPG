package game.server;

import engine.net.server.GameServer;

public class Server {

    /*
    ��� ���������: ���������� - ����������� ��� ��������� ������ (��� ��������� ��������)
    1: ������ ������� ������
    2: ���������� ������ - int x, int y, int direction (int x, int y, int direction, int id)
    3: ��� ����� - (String background)
    4: ���������� ������� - (int peopleMax)
     */

    public Server(){
        ServerData.mapGenerator = new MapGenerator();
        ServerData.playerData = new PlayerData[GameServer.peopleMax];
        for (int i=0; i<ServerData.playerData.length; i++){
            ServerData.playerData[i] = new PlayerData();
        }
        GameServer.sendAll(4, String.valueOf(GameServer.peopleMax));
    }

    public void read(int id, int type, String str){
        //Engine: ���������� ������ ��� ��� ��������� �������� ���������
        switch (type){
            case 1: take1(id, str); break;//Engine: ������ ������� ������
            case 2: take2(id, str); break;//�������� ������� ������
            //Engine: ��������� �������� � ����������� ���������
        }
    }

    public void take1(int id, String str){
        GameServer.send(id, 1, "");
    }

    public void take2(int id, String str) {
        GameServer.sendAllExceptId(id, 2, str + " " + id);
        String[] data = str.split(" ");
        int x = Integer.parseInt(data[0]);
        int y = Integer.parseInt(data[1]);
        if (ServerData.playerData != null) {
            ServerData.playerData[id].x = x;
            ServerData.playerData[id].y = y;
        }
        ServerData.mapGenerator.update(x, y);
    }
}
