package game.client.person;

public class Stats {
    public int maxHp, maxMp;
    public int pDmg, mDmg;
    public int pDef, mDef;
    public int pDefBlock, mDefBlock;
    public double pSpeedDmg, mSpeedDmg;
    public double pChanceCrit, mChanceCrit;
    public double pFactorCrit, mFactorCrit;
    public int accuracy, evasion;
    public double speedCasting, speedReload;
    public double speedRun;
    public int loadCapacity;

    public Stats(){
        this(0);
    }

    public Stats(int value){
        maxHp = value; maxMp = value;
        pDmg = value; mDmg = value;
        pDef = value; mDef = value;
        pDefBlock = value; mDefBlock = value;
        pSpeedDmg = value; mSpeedDmg = value;
        pChanceCrit = value; mChanceCrit = value;
        pFactorCrit = value; mFactorCrit = value;
        accuracy = value; evasion = value;
        speedCasting = value; speedReload = value;
        speedRun = value;
        loadCapacity = value;
    }
}
