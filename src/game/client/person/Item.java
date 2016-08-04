package game.client.person;

public class Item {

    public enum Grade {GRAY, GREEN, BLUE, ORANGE, RED};
    public enum Type {HELMET, UPPER_ARMOR, LOWER_ARMOR, GLOVES, BOOTS, RING, EARRING, NECKLACE, HAND_RIGHT, HAND_LEFT};

    public Grade grade;//Цвет снаряжения
    public Type type;//Тип снаряжения
    public Effect effect = new Effect();//Эффекты, которые накладывает снаряжение
    public int stones = 0;//Кол-во камней вставляемое в снаряжение
    
    public Item(Grade grade, Type type){
        this.grade = grade;
        this.type = type;
    }
}
