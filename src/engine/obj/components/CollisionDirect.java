package engine.obj.components;

import engine.Global;
import engine.Vector2;
import engine.image.Camera;
import engine.image.Mask;
import engine.obj.Obj;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class CollisionDirect extends Collision{

	private int range; //������������ ��������� �������� �������
	private ArrayList<Integer> dynamicId = new ArrayList();//�� ������������ ��������, � �������� ���� ��������� ������������
	private Vector2<Integer> positionCollision; //������� ������������
	private int start = 0;//� ������� ���� ��������� �������� �� ����� ������������ ������� � ������� id<start
	private boolean nearCollision = false;//��������� ������ � ������� ������������

	public CollisionDirect(Obj obj, Mask mask, int range) {
		super(obj, mask);
		this.range = range;
		separationCollisions();
	}

	@Override
	public void update(long delta){
		calcInThisStep = false;

		separationCollisions();
		for (Integer id :dynamicId){ //��������� ������������ �� ����� ��������������� ���������
			Obj objectFromRoom = Global.room.objects.get(dynamicId.get(id));
			if (objectFromRoom != null && objectFromRoom.collision != null && checkCollision(objectFromRoom)){
				informListeners(objectFromRoom); //����������� �� ���� ���� ����������
			}
		}

		//���� ���� ����� ������������ � ��� ������
		if (positionCollision != null) {
			double distantionToCollision = Math.sqrt(sqr(getObj().position.x - positionCollision.x) + sqr(getObj().position.y - positionCollision.y));
			if (distantionToCollision <= getObj().movement.speed * 3) nearCollision = true;
		}

		//���� ������������ �������� � ����� ������������, �� ��������� ��� ������� ������
		if (nearCollision) super.update(delta);
	}

	//��������� �����
	@Override
	public void draw(){
		if (!Global.setting.MASK_DRAW) return;

		GL11.glLoadIdentity();     
	    GL11.glTranslatef(0, 0, 0);
	    GL11.glColor3d(0.0, 0.0, 1.0);
	    GL11.glDisable(GL11.GL_TEXTURE_2D);

		Vector2<Integer> relativePosition = Camera.toRelativePosition(new Vector2(positionCollision.x-10, positionCollision.y-10));
		int x = relativePosition.x;
		int y = relativePosition.y;
		int w = 20;
		int h = 20;
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(x, y);
			GL11.glTexCoord2f(1,0);
			GL11.glVertex2f(x+w, y);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(x+w, y+h);
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(x, y+h);
		GL11.glEnd();
		
	    GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	//����� � ����� ������� id, ������� ��������� � ������������ � ���� ��������
	//����� ����� ��������� �������� ��� �������� ������������ ��� ������ ����������
	private void separationCollisions(){
		for(int i=start; i<Global.room.objects.size(); i++){//���� �������� �������� � �������
			Obj objFromRoom = Global.room.objects.get(i);
			if (objFromRoom != null && objFromRoom.collision != null){//���� ������ �� ��� ��������� � � ���� ���� �����
				for (Class collisionObject : collisionObjects) {//���� �������� �������� � �������� ���� ��������� ������������
					if (objFromRoom.getClass().equals(collisionObject)) {//���� � �������� �� ������� ���� ��������� ������������
						if (objFromRoom.movement != null) dynamicId.add(i);//���� ������ �� ������� ���������
						else checkCollisionDirect(objFromRoom);//���� ������ �� ������� ��������
					}
				}
			}
		}

		this.start = Global.room.objects.size();
	}

	//������ ������������ ������������ ��������������� ������� � ��������� �������� obj
	private void checkCollisionDirect(Obj obj){
		double startX = getObj().position.x;
		double startY = getObj().position.y;
		double directionDraw = getObj().position.getDirectionDraw();

		double gipMe = Math.sqrt(sqr(getMask().getWidth()) + sqr(getMask().getHeight())); //���������� �������
		double gipOther = Math.sqrt(sqr(obj.collision.getMask().getWidth()) + sqr(obj.collision.getMask().getHeight())); //���������� �������, � ������� ����������
		double disMeToOther = Math.sqrt(sqr(startX-obj.position.x) + sqr(startY-obj.position.y)); //���������� �� ������ �� ������
		
		if (disMeToOther < gipOther/2+gipMe/2+30){//���� ������� ��������� ������ � ������� �������
			nearCollision = true ;//� ���������� ������������ ��� ������� ������
		} else if (disMeToOther < range+gipOther/2+gipMe/2+30){
			double k, b, x0, y0, r;
			if ((directionDraw == 90.0) || (directionDraw == 270.0) || (directionDraw == 0.0) || (directionDraw == 180.0)){//�������, ����� �������
				directionDraw += Math.pow(0.1, 10);
			}
			k = Math.tan(Math.toRadians(directionDraw));
			b = -(startY+k*startX);
			x0 = obj.position.x;
			y0 = -obj.position.y;
			r = gipOther/2;
			
			double aSqr, bSqr, cSqr, D;//������������ � ����� ����������� ���������
			aSqr = 1+sqr(k);
			bSqr = 2*k*b-2*x0-2*k*y0;
			cSqr = sqr(x0)+sqr(b)-2*b*y0+sqr(y0)-sqr(r);
			D = sqr(bSqr) - 4*aSqr*cSqr;
			
			if (D >= 0){//������������ ����
				double collX=0, collY=0;//����� ����������� � �����
				double x1Coll, x2Coll, y1Coll, y2Coll;//����� ��������� (����� ������������)
				x1Coll = (-bSqr+Math.sqrt(D))/(2*aSqr);
				x2Coll = (-bSqr-Math.sqrt(D))/(2*aSqr);
				y1Coll = k*x1Coll + b;
				y2Coll = k*x2Coll + b;
				
				//������ ��������� � ���� ������������, � ��� ����� ������ ���� (���)
				if (	((directionDraw >= 0) && (directionDraw < 90) && (x1Coll >= startX) && (y1Coll > -startY)) ||
						((directionDraw > 90) && (directionDraw < 180) && (x1Coll <= startX) && (y1Coll > -startY)) ||
						((directionDraw >= 180) && (directionDraw < 270) && (x1Coll <= startX) && (y1Coll < -startY)) ||
						((directionDraw > 270) && (directionDraw < 360) && (x1Coll >= startX) && (y1Coll < -startY))	){
					
					//����� �� ������ ��������� ����� � ������
					double coll1Gip = Math.sqrt(sqr(x1Coll-startX) + sqr(-y1Coll-startY));//-y1Coll ������� �� ���. ���. ��� � �������
					double coll2Gip = Math.sqrt(sqr(x2Coll-startX) + sqr(-y2Coll-startY));
					if (coll1Gip < coll2Gip){
						collX = x1Coll;
						collY = y1Coll;
					} else {
						collX = x2Coll;
						collY = y2Coll;
					}
				}
				
				//��� ����� ����� ��� ��� ���������?
				if (positionCollision != null){
					double collOld = Math.sqrt(sqr(positionCollision.x-startX) + sqr(positionCollision.y-startY));
					double collNew = Math.sqrt(sqr(collX-startX) + sqr(-collY-startY));//-collY ������� �� ���. ���. ��� � �������
					if (collNew < collOld){
						positionCollision.x = (int) collX;
						positionCollision.y = (int) -collY;
					}
				} else {
					positionCollision.x = (int) collX;
					positionCollision.y = (int) -collY;//������� �� �������������� �������� � �������
				}
			}
		}
	}
}