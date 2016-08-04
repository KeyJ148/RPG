package engine.image;

import engine.Global;
import engine.io.Logger;
import engine.obj.Bullet;
import engine.obj.Obj;
import engine.obj.ObjLight;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class Mask implements Cloneable{
	
	public int[] maskX; //����� ������������ ������ ������
	public int[] maskY;
	public int[] maskXDraw; //����� ������������ ������ ���������
	public int[] maskYDraw;
	
	public int width; //������ � ������ �������
	public int height;
	
	public boolean calcInThisStep = false;
	public boolean dynamic; //���������� ����� ������ ��� (true = ����������)
							//����� ��� ���������� ��� ���������������� ��������
	
	public boolean bullet; //������ ���������� ��������
	public ArrayList<Integer> dynamicId;//�� ������������ ��������, � �������� ���� ��������� ������������
	public double collX = -1;//-1 ������� null
	public double collY = -1;
	public int start = 0;//id �� ��������, � ������� ���� �������� �������
	
	public Mask (String path, int width, int height) {
		this.width = width;
		this.height = height;
		//�������� ����� �� �����
		StringBuffer Bpath = new StringBuffer(path); 
		Bpath.delete(path.lastIndexOf('.'),path.length());
		path = new String(Bpath);
		path = path + ".txt";
		if (new File(path).exists()){
			try{
				BufferedReader fileReader = new BufferedReader(new FileReader(path));
				parser(fileReader);
				if (Global.setting.DEBUG_CONSOLE_MASK) Logger.p("Load mask \"" + path + "\" complited.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else { //�������� ����� ��� ���������� �����
			maskX = new int[] {0,this.width-1,this.width-1,0};
			maskY = new int[] {0,0,this.height-1,this.height-1};
		}
		center();
	}
	
	//������������ �������
    @Override
	public Mask clone() throws CloneNotSupportedException {
		return (Mask)super.clone();
	}
	
	//���������� ����� �� �����
    public void parser(BufferedReader fileReader) throws IOException{
		String s;
		int x;
		int y;
		int lengthArray = 0;
		Vector<Integer> maskXVec = new Vector<Integer>();
		Vector<Integer> maskYVec = new Vector<Integer>();
		while (true){ 
			s = fileReader.readLine();
			if (s == null){
				break;
			}
			x = Integer.parseInt(s.substring(0,s.indexOf(' ')));
			y = Integer.parseInt(s.substring(s.indexOf(' ')+1));
			maskXVec.add(x);
			maskYVec.add(y);
			lengthArray++;
		}
		maskX = new int[lengthArray];
		maskY = new int[lengthArray];
		for (int i=0;i<maskX.length;i++){
			maskX[i] = maskXVec.get(i);
			maskY[i] = maskYVec.get(i);
		}
	}
	
	//��������� ��� ����� ������������ ������ �����
	public void center(){
		for (int i=0;i<maskX.length;i++){
			maskX[i] -= this.width/2;
			maskY[i] -= this.height/2;
		}
	}
	
	//��������: ����� �� ������ � ���� �������� �������� ������������
	public void collCheck(double x, double y, double directionDraw, String[] collObj, Obj obj){
		ObjLight objGlobal; //������ �� ����������� �������
		
		for(ObjLight objRoom : Global.room.objects){//objRoom - ���� �������� �������� � �������

			if ((objRoom != null) && (!objRoom.isLight())){
				for (String strColl : collObj){ 	//strColl - ���� �������� ��������, � �������� ���� ��������� ������������
					if ((objRoom.getClass().getName().equals(strColl)) && (collCheckConti(x, y, directionDraw, (Obj) objRoom))){
						obj.collReport((Obj) objRoom);
					}
				}
			}
		}
	}
	
	//�������� ������������
	public boolean collCheckConti(double x, double y, double directionDraw, Obj obj){
		//�������� ���������� �� ������� ������������
		double gipMe = Math.sqrt(sqr(height) + sqr(width)); //���������� �������
		double gipOther = Math.sqrt(sqr(obj.mask.height) + sqr(obj.mask.width)); //���������� �������, � ������� ����������
		double disMeToOther = Math.sqrt(sqr(x-obj.x)+sqr(y-obj.y)); //���������� �� ������ �� ������
		
		if (disMeToOther > gipMe/2 + gipOther/2 + 30){//���� �� ������� ������� ������
			return false;
		} else {
			if ((dynamic) && (!calcInThisStep)){
				calc(x, y, directionDraw);
				calcInThisStep = true;
			}
		}
		
		//������� ������������
		Polygon pMe = new Polygon(maskXDraw,maskYDraw,maskXDraw.length);
		Polygon pOther = new Polygon(obj.mask.maskXDraw,obj.mask.maskYDraw,obj.mask.maskXDraw.length);
		for (int i = 0;i<obj.mask.maskXDraw.length;i++){
			Point point = new Point(obj.mask.maskXDraw[i],obj.mask.maskYDraw[i]);
			if (pMe.contains(point)){
				return true;
			}
		}
		for (int i = 0;i<maskXDraw.length;i++){
			Point point = new Point(maskXDraw[i],maskYDraw[i]);
			if (pOther.contains(point)){
				return true;
			}
		}
		return false;
	}
	
	public int sqr(int x){
		return x*x;
	}
	
	public double sqr(double x){
		return x*x;
	}
	
	//������ ����� ������������ ������ ���������
	public void calc(double x, double y, double direction){
		direction = Math.toRadians(direction)-Math.PI/2;//������� ���������� ���� � ������� �� �����
			
		//������� �����
		this.maskXDraw = new int[maskX.length];
		this.maskYDraw = new int[maskX.length];
		double XDouble;
		double YDouble; 
		double XDouble2;
		double YDouble2; 
		double cos = Math.cos(direction);
		double sin = Math.sin(direction);
		for (int i=0;i<maskX.length;i++){
			XDouble = cos * maskX[i];//������ ������
			YDouble = sin * maskX[i];//"�����"
			XDouble2 = sin * maskY[i];//������ ������ //Math.cos(direction-Math.PI/2) * ...
			YDouble2 = -cos * maskY[i];//"� ���" //Math.sin(direction-Math.PI/2) * ...
			this.maskXDraw[i] = (int) (x + XDouble + XDouble2);
			this.maskYDraw[i] = (int) (y - YDouble - YDouble2);
		}
	}
	
	public void draw(){
		int maskXDrawView[] = new int[maskXDraw.length];
		int maskYDrawView[] = new int[maskYDraw.length];
		double xd,yd;
		for (int i=0;i<maskXDraw.length;i++){
			xd = Global.cameraXView - (Global.cameraX - maskXDraw[i]);
			yd = Global.cameraYView - (Global.cameraY - maskYDraw[i]);
			maskXDrawView[i] = (int) xd;
			maskYDrawView[i] = (int) yd;
		}
		
		GL11.glLoadIdentity();     
	    GL11.glTranslatef(0, 0, 0);
	    GL11.glColor3d(0.0, 0.0, 1.0);
	    
	    GL11.glDisable(GL11.GL_TEXTURE_2D);
	    
	    GL11.glBegin(GL11.GL_LINE_LOOP);
	    	for (int i=0; i<maskXDrawView.length;i++)
	    		GL11.glVertex2f(maskXDrawView[i], maskYDrawView[i]); 
	    GL11.glEnd();
	    
		if (bullet){
			int x = (int) (Global.cameraXView - (Global.cameraX - (collX-10)));
			int y = (int) (Global.cameraYView - (Global.cameraY - (collY-10)));
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
		}
		
	    GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	public void thisBullet(int range, String[] collObj, double x, double y, double directionDraw){
		bullet = true;
		dynamicId = new ArrayList<Integer>();
		findDynamicIdAndStaticColl(collObj, x, y, directionDraw, range);
	}
	
	//����� � ����� ������� id, ������� ��������� � ������������ � ���� ��������
	//����� ����� ��������� �������� ��� �������� ������������ ��� ������ ����������
	private void findDynamicIdAndStaticColl(String[] collObj, double x, double y, double directionDraw, int range){
		int start = this.start;
		ObjLight objGlobalLight;
		
		for(int vectorON=start;vectorON<Global.room.objects.size();vectorON++){//objRoom - ���� �������� �������� � �������
			objGlobalLight = Global.room.objects.get(vectorON);
			
			if ((objGlobalLight != null) && (!objGlobalLight.isLight())){
				Obj objGlobal = (Obj) objGlobalLight;
				if (objGlobal.mask.dynamic){//���� ������ �� ������� ���������
					
					for (int stringON=0;stringON<collObj.length;stringON++){ 	//String Object Number - ���� �������� �������� �� ������� �������
						if ((objGlobal.getClass().getName().equals(collObj[stringON]))){
							dynamicId.add(vectorON);
						}
					}
					
				} else {//���� ������ �� ������� ��������
					
					for (int stringON=0;stringON<collObj.length;stringON++){ 	//String Object Number - ���� �������� �������� �� ������� �������
						if ((objGlobal.getClass().getName().equals(collObj[stringON]))){
							bulletColl(x, y, directionDraw, objGlobal, range);
						}
					}
					
				}
			}
		}

		this.start = Global.room.objects.size();
	}
	
	//������ ������������ �� ������ ���������
	private void bulletColl(double startX, double startY, double directionDraw, Obj obj, int range){
		double gipMe = Math.sqrt(sqr(height) + sqr(width)); //���������� �������
		double gipOther = Math.sqrt(sqr(obj.mask.height) + sqr(obj.mask.width)); //���������� �������, � ������� ����������
		double disMeToOther = Math.sqrt(sqr(startX-obj.x) + sqr(startY-obj.y)); //���������� �� ������ �� ������
		
		if (disMeToOther < gipOther/2+gipMe+30){//���� ����� ��������� ������ ����
			bullet = false;
		} else if (disMeToOther < range+gipOther/2+gipMe/2+30){
			double k, b, x0, y0, r;
			if ((directionDraw == 90.0) || (directionDraw == 270.0) || (directionDraw == 0.0) || (directionDraw == 180.0)){//�������, ����� �������
				directionDraw += Math.pow(0.1, 10);
			}
			k = Math.tan(Math.toRadians(directionDraw));
			b = -(startY+k*startX);
			x0 = obj.x;
			y0 = -obj.y;
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
				if ((this.collX != -1) && (this.collY != -1)){
					double collOld = Math.sqrt(sqr(this.collX-startX) + sqr(this.collY-startY));
					double collNew = Math.sqrt(sqr(collX-startX) + sqr(-collY-startY));//-collY ������� �� ���. ���. ��� � �������
					if (collNew < collOld){
						this.collX = collX;
						this.collY = -collY;
					}
				} else {
					this.collX = collX;
					this.collY = -collY;//������� �� �������������� �������� � �������
				}
				
			}
		}
	}
	
	public void collCheckBullet(double x, double y, double directionDraw, String[] collObj, Obj obj){
		double range = ((Bullet) obj).getRange();
		findDynamicIdAndStaticColl(collObj, x, y, directionDraw, (int) range);
		
		Obj objGlobal;//� ������� ������������
		for (int i=0; i<dynamicId.size(); i++){
			objGlobal = (Obj) Global.room.objects.get(dynamicId.get(i));
			if ((objGlobal != null) && (collCheckConti(x, y, directionDraw, objGlobal))){
				obj.collReport(objGlobal);;
			}
		}
		
		if (Math.sqrt(sqr(x-this.collX) + sqr(y-this.collY)) <= obj.speed*3){//���� ������������ �������� � ����� �����������, �� ������������
			bullet = false;
		}
	}
}