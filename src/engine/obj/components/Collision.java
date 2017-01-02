package engine.obj.components;

import engine.Global;
import engine.Vector2;
import engine.image.Camera;
import engine.image.Mask;
import engine.obj.Obj;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class Collision extends Component{

	private Mask mask;//����� ��� �������� ����� �������
	private Vector2<Integer>[] maskAbsolute; //���������� ���������� ����� �� ������ �������� ���� �����

	protected ArrayList<Class> collisionObjects = new ArrayList();//������ �������� � �������� ���� ��������� ������������
	private ArrayList<CollisionListener> listeners = new ArrayList();//������ �������� ������� ����� ��������� ��� ��������
	protected boolean calcInThisStep = false;//������������� �� ����� ��� � ���� �����?

	public Collision(Obj obj, Mask mask) {
		super(obj);
		this.mask = mask;
	}

	@Override
	public void update(long delta){
		calcInThisStep = false;
		if (getObj().movement != null) calc();
		checkCollisionFromRoom();
	}

	@Override
	public void draw(){
		if (!Global.setting.MASK_DRAW) return;

		Vector2<Integer>[] maskDrawView = mask.getMaskCenter();
		for (int i=0;i<maskDrawView.length;i++)
			maskDrawView[i] = Camera.toRelativePosition(maskDrawView[i]);

		GL11.glLoadIdentity();
		GL11.glTranslatef(0, 0, 0);
		GL11.glColor3d(0.0, 0.0, 1.0);
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		GL11.glBegin(GL11.GL_LINE_LOOP);
		for (int i=0; i<maskDrawView.length;i++)
			GL11.glVertex2f(maskDrawView[i].x, maskDrawView[i].y);
		GL11.glEnd();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	//�������� �������� ������������ � ������ �������� � �������
	public void checkCollisionFromRoom(){
		if (collisionObjects.size() == 0) return;

		for(Obj objectFromRoom : Global.room.objects){//���� �������� ���� �������� � �������
			if (objectFromRoom != null && objectFromRoom.collision != null){//���� ������ �� ��� ��������� � � ���� ���� �����
				for (Class collisionObject : collisionObjects){ //���� �������� ��������, � �������� ���� ��������� ������������
					if ((objectFromRoom.getClass().equals(collisionObject)) //���� � ��� �������� ���� ��������� �����������
						&& (checkCollision(objectFromRoom))){ //� � ���� �������� ���������� ������������
						informListeners(objectFromRoom); //����������� �� ���� ���� ����������
					}
				}
			}
		}
	}

	//�������� ������������ � �������� obj2
	public boolean checkCollision(Obj obj2){
		Obj obj1 = getObj();

		if (obj1.position == null || obj1.collision == null || obj2.position == null || obj2.collision == null) return false;
		Position pos1 = obj1.position;
		Position pos2 = obj2.position;
		Collision coll1 = obj1.collision;
		Collision coll2 = obj2.collision;

		//�������� ���������� �� ������� ������������
		double gip1 = Math.sqrt(sqr(coll1.mask.getWidth()) + sqr(coll1.mask.getHeight())); //���������� �������
		//���������� �������, � ������� ����������
		double gip2 = Math.sqrt(sqr(coll2.mask.getWidth()) + sqr(coll2.mask.getHeight()));
		//���������� �� ������ �� ������
		double dis1To2 = Math.sqrt(sqr(pos1.x-pos2.x)+sqr(pos1.y-pos2.y));

		//���� �� ������� ������� ������, �� ������������ ���
		if (dis1To2 > gip1/2 + gip2/2 + 30){
			return false;
		} else {
			if (obj1.movement != null && !coll1.calcInThisStep) coll1.calc();
			if (obj2.movement != null && !coll2.calcInThisStep) coll2.calc();
		}
		
		//������� ������������
		//�������� ���������
		Polygon p1 = new Polygon();
		for (int i = 0; i < coll1.maskAbsolute.length; i++){
			p1.addPoint(coll1.maskAbsolute[i].x, coll1.maskAbsolute[i].y);
		}

		Polygon p2 = new Polygon();
		for (int i = 0; i < coll2.maskAbsolute.length; i++){
			p1.addPoint(coll2.maskAbsolute[i].x, coll2.maskAbsolute[i].y);
		}

		//�������� �� �������� ���������
		for (int i = 0; i<p2.npoints; i++){
			Point point = new Point(p2.xpoints[i], p2.ypoints[i]);
			if (p1.contains(point)) return true;
		}

		for (int i = 0; i<p1.npoints; i++){
			Point point = new Point(p1.xpoints[i], p1.ypoints[i]);
			if (p2.contains(point)) return true;
		}

		return false;
	}

	//���������� ����� ������������ ������ ��������� �����
	public void calc(){
		if (getObj().position == null) return;

		//������� ���������� ���� � ������� �� �����
		double direction = getObj().position.getDirectionDraw();
		direction = Math.toRadians(direction)-Math.PI/2;

		//������� �����
		this.maskAbsolute = new Vector2[mask.getMaskCenter().length];
		double cos = Math.cos(direction);
		double sin = Math.sin(direction);
		for (int i=0;i<mask.getMaskCenter().length;i++){
			double XDouble = cos * mask.getMaskCenter()[i].x;//������ ������
			double YDouble = sin * mask.getMaskCenter()[i].x;//"�����"
			double XDouble2 = sin * mask.getMaskCenter()[i].y;//������ ������ //Math.cos(direction-Math.PI/2) * ...
			double YDouble2 = -cos * mask.getMaskCenter()[i].y;//"� ���" //Math.sin(direction-Math.PI/2) * ...
			this.maskAbsolute[i].x = (int) (getObj().position.x + XDouble + XDouble2);
			this.maskAbsolute[i].y = (int) (getObj().position.y - YDouble - YDouble2);
		}

		calcInThisStep = true;
	}

	public void addCollisionObjects(Class[] collisionObjects){
		for (Class obj : collisionObjects)
			this.collisionObjects.add(obj);
	}

	public void deleteCollisionObject(Class obj){
		this.collisionObjects.remove(obj);
	}

	public void cleanCollisionObjects(){
		this.collisionObjects = new ArrayList();
	}

	public Mask getMask(){
		return mask;
	}

	public static int sqr(int x){
		return x*x;
	}

	public static double sqr(double x){
		return x*x;
	}

	//��������� ��������� ��� ���������
	//��� ������� � �������� �� ������ �������� ���������� ����� collision
	public interface CollisionListener{
		void collision(Obj obj);
	}

	public void addListener(CollisionListener listener){
		this.listeners.add(listener);
	}

	public void removeListener(CollisionListener listener){
		this.listeners.remove(listener);
	}

	protected void informListeners(Obj obj){
		for (CollisionListener listener : listeners)
			listener.collision(obj);
	}

}