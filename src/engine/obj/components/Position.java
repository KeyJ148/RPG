package engine.obj.components;

import engine.Vector2;
import engine.image.Camera;
import engine.map.Room;
import engine.obj.Obj;

public class Position extends Component{

    public double x;
    public double y;
    public int depth;
    private double directionDraw; //0, 360 - � �����, ������ ������� - ���������
    public boolean absolute = true; //������� ������������ ���� �����? (����� ������������ ���� ������)

    public Room room;//������� � ������� ��������� ������
    public int id;//���������� ����� ������� � �������

    public Position(Obj obj, double x, double y, int depth){
        this(obj, x, y, depth, 90);
    }

    public Position(Obj obj, double x, double y, int depth, double directionDraw) {
        super(obj);
        this.x = x;
        this.y = y;
        this.depth = depth;
        setDirectionDraw(directionDraw);
    }

    public double getDirectionDraw(){
        if (directionDraw%360 >= 0){
            return directionDraw%360;
        } else {
            return 360-Math.abs(directionDraw%360);
        }
    }

    public void setDirectionDraw(double directionDraw){
        this.directionDraw = directionDraw;
    }

    public Vector2<Integer> getRelativePosition(){
        if (!absolute){
            Vector2<Integer> relativePosition = new Vector2<>();
            relativePosition.x = (int) x;
            relativePosition.y = (int) y;
            return relativePosition;
        } else {
            return Camera.toRelativePosition(new Vector2(x, y));
        }
    }

}
