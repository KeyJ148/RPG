package game.client.person;

public class Item {

    public enum Grade {GRAY, GREEN, BLUE, ORANGE, RED};
    public enum Type {HELMET, UPPER_ARMOR, LOWER_ARMOR, GLOVES, BOOTS, RING, EARRING, NECKLACE, HAND_RIGHT, HAND_LEFT};

    public Grade grade;//���� ����������
    public Type type;//��� ����������
    public Effect effect = new Effect();//�������, ������� ����������� ����������
    public int stones = 0;//���-�� ������ ����������� � ����������
    
    public Item(Grade grade, Type type){
        this.grade = grade;
        this.type = type;
    }
}
