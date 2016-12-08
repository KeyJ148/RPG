package engine.map;

import engine.obj.Obj;
import engine.obj.ObjLight;

import java.util.ArrayList;

public class DepthVector {
	
	private int depth;
	private MapControl mc;
    private ArrayList<ArrayList<Chunk>> chunks = new ArrayList<>();
	//��������� ������������ ������ ������ ��� �����
	//������� ������ ������ ���������� �������� �� ���������� y
	//���������� ������ ����� ����� � ���������� y, �� ������� x
	
	public DepthVector(MapControl mc, int depth, ObjLight obj){
		this.mc = mc;
		this.depth = depth;
		
		add(obj);
	}

	public void add(ObjLight obj){
		getChunk((int) obj.x, (int) obj.y).add(obj.id);
	}
	
	public void del(ObjLight obj){
		getChunk((int) obj.x, (int) obj.y).del(obj.id);
	}
	
	public int getDepth(){
		return depth;
	}

	//���������� ������� ��� ����������� �� ����� � ����
	public void update(Obj obj){
		Chunk chunkNow = getChunk((int) obj.x,(int) obj.y);
		Chunk chunkLast = getChunk((int) obj.xPrevious,(int) obj.yPrevious);
				
		if (!chunkLast.equals(chunkNow)){
			chunkLast.del(obj.id);
			chunkNow.add(obj.id);
		}
	}

	//��������� ������ ������ ������� x, y
	public void render(int x, int y, int width, int height) {
		Chunk chunk = getChunk(x, y);
		int chunkPosX = chunk.getPosWidth();
		int chunkPosY = chunk.getPosHeight();
		int rangeX = (int) Math.ceil((double) width / 2 / mc.chunkSize);//� ������
		int rangeY = (int) Math.ceil((double) height / 2 / mc.chunkSize);//� ������

		for (int i = chunkPosX - rangeX; i <= chunkPosX + rangeX; i++){
			for (int j = chunkPosY - rangeY; j <= chunkPosY + rangeY; j++) {
				if ((i >= 0) && (i < mc.numberWidth) && (j >= 0) && (j < mc.numberHeight)) {
					mc.chunkRender++;
					pointToChunk(new Vector2(i, j)).render();
				}
			}
		}
	}

	private Chunk getChunk(int x, int y){
		return pointToChunk(getPoint(x, y));
	}

	//���������� ���� �� ������� ����� �� x,y (�� ����������, � ������� �����)
	private Chunk pointToChunk(Vector2 p){
		//����������� �������� ����� ������� (��������), ��� Y ����� �������� Y
		int key = p.y;
		int left = 0;
		int right = chunks.size()-1;//����� ������ ����� ����� ������� ���������� ��������

		while (left < right){
			int mid = (left+right)/2;
			if (key > chunks.get(mid).get(0).getPosHeight()){
				left = mid+1;
			} else {
				right = mid;
			}
		}
		int indexOnY = right;//������ ������� ��� ����������� ������� �� ������� �������

		//���� ������ ������ �� ��� ������, �� ������� ���
		//y = indexOnY, x = 0, �������� ������� �� y
		if (chunks.size() == 0 || key != chunks.get(indexOnY).get(0).getPosHeight()){
			Chunk newChunk = new Chunk(p.x, p.y);
			ArrayList<Chunk> chunksOnX = new ArrayList<>();
			chunksOnX.add(newChunk);

			//��������� �� �������� ������ ���������� ������ � ������������ � �����������
			if (chunks.size() == 0){
				chunks.add(chunksOnX);
			} else {
				if (chunks.get(indexOnY).get(0).getPosHeight() < key) chunks.add(indexOnY+1, chunksOnX);
				else chunks.add(indexOnY, chunksOnX);
			}

			return newChunk;
		}

		//����������� �������� ����� ������� (�����������) �� �������� ������� � �������� Mid, ��� X ����� �������� X
		key = p.x;
		left = 0;
		right = chunks.get(indexOnY).size()-1;//����� ������ ����� ����� ������� ���������� ��������

		while (left < right){
			int mid = (left+right)/2;
			if (key > chunks.get(indexOnY).get(mid).getPosWidth()){
				left = mid+1;
			} else {
				right = mid;
			}
		}
		int indexOnX = right;//������ ������� ��� ����� �� ���������� �������

		//���� ������ ���������� ������ �� ��� ������, �� ������� ���
		//y = indexOnY, x = indexOnX, �������� ������� �� x
		if (chunks.get(indexOnY).size() == 0 || key != chunks.get(indexOnY).get(indexOnX).getPosWidth()){
			Chunk newChunk = new Chunk(p.x, p.y);

			//��������� �� �������� ������ ���������� ������ � ������������ � �����������
			if (chunks.get(indexOnY).size() == 0){
				chunks.get(indexOnY).add(newChunk);
			} else{
				if (chunks.get(indexOnY).get(indexOnX).getPosWidth() < key) chunks.get(indexOnY).add(indexOnX+1, newChunk);
				else chunks.get(indexOnY).add(indexOnX, newChunk);
			}

			return newChunk;
		}

		return chunks.get(indexOnY).get(indexOnX);
	}
	
	private Vector2 getPoint(int x, int y){
		int delta = mc.borderSize/mc.chunkSize-1;//delta=0 (1-1)
		int posWidth = (int) Math.ceil((double) x/mc.chunkSize+delta);//-1 �.�. ��������� � ������� � 0
		int posHeight = (int) Math.ceil((double) y/mc.chunkSize+delta);//+1 �.�. ��������� ������� ����� �������� � 1 ���� ��� ��������� ������ �� �����
		return new Vector2(posWidth, posHeight);
	}

	//��������� ���������� �������� 2 ��������� (������ Point ��� int)
	public static class Vector2{
		int x, y;

		public Vector2(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
}

