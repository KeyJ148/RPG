package game.server;

import engine.net.server.GameServer;

public class Server {

    public void read(int id, int type, String str){
        //Engine: ���������� ������ ��� ��� ��������� �������� ���������
        switch (type){
            case 1: take1(id, str); break;//Engine: ������ ������� ������
            //Engine: ��������� �������� � ����������� ���������
        }
    }

    public void take1(int id, String str){
        GameServer.send(id, 1, "");
    }
}
