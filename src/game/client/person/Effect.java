package game.client.person;

public class Effect {
    public Stats addition = new Stats();//Увеличение характеристик (+)
    public Stats multi = new Stats(1);//Увеличение характеристик (*)
    
    public Stats calcAddStats(Stats stats){
        stats.maxHp += addition.maxHp; stats.maxMp += addition.maxMp;
        stats.pDmg += addition.pDmg; stats.mDmg += addition.mDmg;
        stats.pDef += addition.pDef; stats.mDef += addition.mDef;
        stats.pDefBlock += addition.pDefBlock; stats.mDefBlock += addition.mDefBlock;
        stats.pSpeedDmg += addition.pSpeedDmg; stats.mSpeedDmg += addition.mSpeedDmg;
        stats.pChanceCrit += addition.pChanceCrit; stats.mChanceCrit += addition.mChanceCrit;
        stats.pFactorCrit += addition.pFactorCrit; stats.mFactorCrit += addition.mFactorCrit;
        stats.accuracy += addition.accuracy; stats.evasion += addition.evasion;
        stats.speedCasting += addition.speedCasting; stats.speedReload += addition.speedReload;
        stats.speedRun += addition.speedRun;
        stats.loadCapacity += addition.loadCapacity;
        
        return stats;
    }

    public Stats calcMultiStats(Stats stats){
        stats.maxHp *= multi.maxHp; stats.maxMp *= multi.maxMp;
        stats.pDmg *= multi.pDmg; stats.mDmg *= multi.mDmg;
        stats.pDef *= multi.pDef; stats.mDef *= multi.mDef;
        stats.pDefBlock *= multi.pDefBlock; stats.mDefBlock *= multi.mDefBlock;
        stats.pSpeedDmg *= multi.pSpeedDmg; stats.mSpeedDmg *= multi.mSpeedDmg;
        stats.pChanceCrit *= multi.pChanceCrit; stats.mChanceCrit *= multi.mChanceCrit;
        stats.pFactorCrit *= multi.pFactorCrit; stats.mFactorCrit *= multi.mFactorCrit;
        stats.accuracy *= multi.accuracy; stats.evasion *= multi.evasion;
        stats.speedCasting *= multi.speedCasting; stats.speedReload *= multi.speedReload;
        stats.speedRun *= multi.speedRun;
        stats.loadCapacity *= multi.loadCapacity;
        
        return stats;
    }
}
