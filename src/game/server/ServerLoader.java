package game.server;

import engine.net.server.GameServer;

public class ServerLoader extends Thread{

    private int port;
    private int peopleMax;
    private boolean maxPower;

    public ServerLoader(int port, int peopleMax, boolean maxPower){
        this.port = port;
        this.peopleMax = peopleMax;
        this.maxPower = maxPower;

        start();
    }

    @Override
    public void run(){
        String[] args = new String[3];
        args[0] = String.valueOf(port);
        args[1] = String.valueOf(peopleMax);
        args[2] = String.valueOf(maxPower);

        GameServer.main(args);
    }

}
