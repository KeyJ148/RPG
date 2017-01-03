package engine.map;

import engine.image.TextureHandler;
import engine.obj.Obj;

import java.util.Vector;

public class Room {
	
	public TextureHandler background;
	public int width, height;

	public Vector<Obj> objects; //������ �� ����� ���������
	public MapControl mapControl; //������ �� ����� ������� � ���������

	public Room(int width, int height) {
		this.width = width;
		this.height = height;
		
		objects = new Vector<>();
		mapControl = new MapControl(width, height);
	}

	public void update(long delta){
		for (Obj obj : objects) {
			if (obj != null) obj.update(delta);
		}
		for (Obj obj : objects) {
			if (obj != null) obj.updateFollow();
		}
	}

	public int objCount(){
		int count = 0;
		for (Obj obj : objects)
			if (obj != null)
				count++;

		return count;
	}

	//���������� ������� � �������
	public void objAdd(Obj obj){
		obj.position.id = objects.size();
		obj.position.room = this;

		objects.add(obj);
		mapControl.add(obj);
	}

	//�������� ������� �� ������� �� id
	public void objDel(int id){
		mapControl.del(id);//������������ objects, ��� ��� ������ ���� ������
		objects.set(id, null);
	}
}
