package game.server;

import engine.net.server.GameServer;

import java.util.Random;

public class MapGenerator {

    public enum Biom {GRASS, SAND};

    public Biom biom;

    public MapGenerator(){
        Random rand = new Random();
        biom = Biom.values()[rand.nextInt(Biom.values().length)];

        String background = "";
        if (biom == Biom.GRASS) background = "grass";
        if (biom == Biom.SAND) background = "sand";

        GameServer.sendAll(3, background);
    }

    public void update(int x, int y){

    }

}
