package engine.obj.components;

import engine.Global;
import engine.obj.Obj;

public class Movement extends Component{
    public double speed; //�� ������� �������� ������ ��������� �� 1 �������
    private double direction; //0, 360 - � �����, ������ ������� - ��������

    private double xPrevious;//���� ������� � ���������� ����
    private double yPrevious;//(��� ������������)
    private double directionPrevious;//��������� ������� � ���������� ���� (��� ������������)

    public boolean directionDrawEquals = true;//���� ������ ������� ����� ���� ��������

    public Movement(Obj obj){
        this(obj, 0, 90);
    }

    public Movement(Obj obj, double speed, double direction) {
        super(obj);
        this.speed = speed;
        setDirection(direction);
    }

    @Override
    public void update(long delta){
        xPrevious = getObj().position.x;
        yPrevious = getObj().position.y;
        directionPrevious = direction;

        getObj().position.x = getObj().position.x + speed * Math.cos(Math.toRadians(direction)) * ((double) delta/1000000000);
        getObj().position.y = getObj().position.y - speed * Math.sin(Math.toRadians(direction)) * ((double) delta/1000000000);
        Global.room.mapControl.update(getObj());

        if (directionDrawEquals) getObj().position.setDirectionDraw(direction);
    }

    public double getDirection(){
        if (direction%360 >= 0){
            return direction%360;
        } else {
            return 360-Math.abs(direction%360);
        }
    }

    public void setDirection(double direction){
        this.direction = direction;
    }

    public double getDirectionPrevious(){
        if (directionPrevious%360 >= 0){
            return directionPrevious%360;
        } else {
            return 360-Math.abs(directionPrevious%360);
        }
    }

    public double getXPrevious(){
        return xPrevious;
    }

    public double getYPrevious() {
        return yPrevious;
    }
}
