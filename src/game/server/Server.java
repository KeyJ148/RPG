package game.server;

import engine.net.server.GameServer;

public class Server {

    public void read(int id, int type, String str){
        //Engine: вызываетс€ каждый раз при получение сервером сообщени€
        switch (type){
            case 1: take1(id, str); break;//Engine:  лиент пингует сервер
            //Engine: –азличные действи€ с уникальными индексами
        }
    }

    public void take1(int id, String str){
        GameServer.send(id, 1, "");
    }
}
