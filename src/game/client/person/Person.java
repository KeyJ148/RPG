package game.client.person;

import engine.image.TextureHandler;
import engine.obj.Obj;
import engine.obj.components.Movement;

import java.util.ArrayList;

public class Person extends Obj {

    public int level = 1;//Уровень персонажа
    public static final Stats increment = new Stats();//Увеличесние характеристики за один уровень
    public Stats stats = new Stats();//Характеристики персонажа (текущие)
    private Stats statsDefault = new Stats();//Характеристики персонажа (изначальные, только за счёт уровня)
    public ArrayList<Effect> effects = new ArrayList<>();//Эффекты наложеные на персонажа
    public int hp, mp;

    //Снаряжение персонажа
    private ArrayList<Slot> slots = new ArrayList<>();
    private class Slot{
        public Item.Type type;
        public Item item;

        public Slot(Item.Type type){
            this.type = type;
        }
    }

    //Инициализация статических данных
    static {
        //Увеличесние характеристики с повышением уровня
        increment.maxHp = 10; increment.maxMp = 10;
        increment.pDmg = 2; increment.mDmg = 2;
        increment.pDef = 1; increment.mDef = 1;
        increment.pDefBlock = 2; increment.mDefBlock = 2;
        increment.pSpeedDmg = 0.1; increment.mSpeedDmg = 0.1;
        increment.pChanceCrit = 0.5; increment.mChanceCrit = 0.5;
        increment.pFactorCrit = 0.01; increment.mFactorCrit = 0.01;
        increment.accuracy = 1; increment.evasion = 1;
        increment.speedCasting = 0.5; increment.speedReload = 0.5;
        increment.speedRun = 2;
        increment.loadCapacity = 2;
    }

    public Person(double x, double y, double dir, TextureHandler texture){
        super(x, y, 0, dir, texture);
        movement = new Movement(this);
        movement.directionDrawEquals = false;

        statsDefault.maxHp = 100; statsDefault.maxMp = 100;
        statsDefault.pDmg = 20; statsDefault.mDmg = 20;
        statsDefault.pDef = 10; statsDefault.mDef = 10;
        statsDefault.pDefBlock = 20; statsDefault.mDefBlock = 20;
        statsDefault.pSpeedDmg = 1.0; statsDefault.mSpeedDmg = 1.0;
        statsDefault.pChanceCrit = 10; statsDefault.mChanceCrit = 10;
        statsDefault.pFactorCrit = 1.5; statsDefault.mFactorCrit = 1.5;
        statsDefault.accuracy = 100; statsDefault.evasion = 30;
        statsDefault.speedCasting = 100; statsDefault.speedReload = 100;
        statsDefault.speedRun = 100;//Пикселей в секунду
        statsDefault.loadCapacity = 50;

        calcStats();
        hp = stats.maxHp; mp = stats.maxMp;

        slots.add(new Slot(Item.Type.HELMET));
        slots.add(new Slot(Item.Type.UPPER_ARMOR));
        slots.add(new Slot(Item.Type.LOWER_ARMOR));
        slots.add(new Slot(Item.Type.GLOVES));
        slots.add(new Slot(Item.Type.BOOTS));
        slots.add(new Slot(Item.Type.NECKLACE));
        slots.add(new Slot(Item.Type.HAND_RIGHT));
        slots.add(new Slot(Item.Type.HAND_LEFT));
        for (int i = 0; i < 2; i++) slots.add(new Slot(Item.Type.EARRING));
        for (int i = 0; i < 8; i++) slots.add(new Slot(Item.Type.RING));
    }

    public void calcStats(){
        stats = statsDefault;

        for (Slot slot : slots){
            if (slot.item != null) slot.item.effect.calcAddStats(stats);
        }
        for (Effect effect : effects){
            effect.calcAddStats(stats);
        }
        for (Slot slot : slots){
            if (slot.item != null) slot.item.effect.calcMultiStats(stats);
        }
        for (Effect effect : effects){
            effect.calcMultiStats(stats);
        }
    }

    //В случае если ячейка уже занята, то метод возвращает удаляемое снаряжение
    public Item addItem(Item newItem){
        for (Slot slot : slots){
            if (slot.type == newItem.type && slot.item == null){
                slot.item = newItem;
                calcStats();
                return null;
            }
        }

        for (Slot slot : slots){
            if (slot.type == newItem.type){
                Item lastItem = slot.item;
                slot.item = newItem;
                calcStats();
                return lastItem;
            }
        }

        return null;
    }

}
