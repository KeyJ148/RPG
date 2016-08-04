package engine.map;

import engine.obj.Obj;
import engine.obj.ObjLight;

import java.awt.*;
import java.util.ArrayList;

public class DepthVector {
	
	private int depth;
	private MapControl mc;
    private ArrayList<Chunk> chunks = new ArrayList<>();
	
	public DepthVector(MapControl mc, int depth, ObjLight obj){
		this.mc = mc;
		this.depth = depth;
		
		add(obj);
	}
	
	public void add(ObjLight obj){
		getChunk((int) obj.x, (int) obj.y).add((int) obj.id);
	}
	
	public void del(ObjLight obj){
		getChunk((int) obj.x, (int) obj.y).del((int) obj.id);
	}
	
	public int getDepth(){
		return depth;
	}
	
	public void update(Obj obj){
		Chunk chunkNow = getChunk((int) obj.x,(int) obj.y);
		Chunk chunkLast = getChunk((int) obj.xPrevious,(int) obj.yPrevious);
				
		if (!chunkLast.equals(chunkNow)){
			chunkLast.del((int) obj.id);
			chunkNow.add((int) obj.id);
		}
	}
	
	public void render(int x, int y, int width, int height){
		Chunk chunk = getChunk(x, y);
		int chunkPosX = chunk.getPosWidth();
		int chunkPosY = chunk.getPosHeight();
		int rangeX = (int) Math.ceil((double) width/2/mc.chunkSize);//В чанках
		int rangeY = (int) Math.ceil((double) height/2/mc.chunkSize);//В чанках

		for (int i=chunkPosX-rangeX; i<=chunkPosX+rangeX; i++)
			for (int j=chunkPosY-rangeY; j<=chunkPosY+rangeY; j++)
				if ((i >= 0) && (i < mc.numberWidth) && (j >= 0) && (j < mc.numberHeight)) {
					mc.chunkRender++;
					pointToChunk(new Point(i, j)).render();
				}
	}
	
	private Chunk getChunk(int x, int y){
		return pointToChunk(getPoint(x, y));
	}
	
	private Chunk pointToChunk(Point p){
	    for (Chunk chunk : chunks)
	        if (chunk.getPosWidth() == p.getX() && chunk.getPosHeight() == p.getY())
                return chunk;

        Chunk newChunk = new Chunk((int) p.getX(), (int) p.getY());
        chunks.add(newChunk);
		return newChunk;
	}
	
	private Point getPoint(int x, int y){
		int delta = mc.borderSize/mc.chunkSize-1;//delta=0 (1-1)
		int posWidth = (int) Math.ceil((double) x/mc.chunkSize+delta);//-1 т.к. нумерация в массиве с 0
		int posHeight = (int) Math.ceil((double) y/mc.chunkSize+delta);//+1 т.к. добавлена обводка карты толщиной в 1 чанк для обработки выхода за карту
		return new Point(posWidth, posHeight);
	}
}

