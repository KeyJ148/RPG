package game.server;

import engine.net.server.GameServer;

public class Server {

    /*
    ��� ���������: ���������� - ����������� ��� ��������� ������ (��� ��������� ��������)
    1: ������ ������� ������
    2: ���������� ������ - int x, int y, int direction, int speed, int directionDraw
                          (int x, int y, int direction, int speed, int directionDraw, int id)
    3: ��� ����� - (String background)
    4: ���������� ������� - (int peopleMax)
    5: ������ �������
    6: ����� ���������
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
            case 6: take6(id, str); break;
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

    public void take6(int id, String str){
        if (ServerData.playerData != null && (!ServerData.playerData[id].connect)) {
            ServerData.playerData[id].connect = true;

            boolean allConnected = true;
            for (PlayerData player : ServerData.playerData){
                if (player.connect == false){
                    allConnected = false;
                    break;
                }
            }

            if (allConnected) GameServer.sendAll(5, "");
        }
    }
}
