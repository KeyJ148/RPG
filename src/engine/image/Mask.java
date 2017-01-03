package engine.image;

import engine.Vector2;
import engine.io.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Mask {

    private int width, height;
    private Vector2<Integer>[] maskCenter;//������� ����� � �������� ����� (������������ ������)
    private Vector2<Integer>[] maskDefault;//������� ����� � �������� ����� (������������ �������� ������ ����)

    public Mask(String path, int width, int height) {
        StringBuffer pathBuffer = new StringBuffer(path);
        pathBuffer.delete(path.lastIndexOf('.'), path.length());
        path = new String(pathBuffer) + ".txt";

        Vector2<Integer>[] mask;
        if (new File(path).exists()) mask = loadFromFile(path, width, height);
        else mask = createDefault(width, height);

        this.maskDefault = mask;
        this.maskCenter = center(mask, width, height);
        this.width = width;
        this.height = height;
    }

    //�������� ����� �� �����
    public Vector2<Integer>[] loadFromFile(String path, int width, int height){
        try{
            BufferedReader fileReader = new BufferedReader(new FileReader(path));
            ArrayList<Vector2<Integer>> maskArr = new ArrayList<>();
            String s;

            while ((s = fileReader.readLine()) != null) {
                int x = Integer.parseInt(s.substring(0, s.indexOf(' ')));
                int y = Integer.parseInt(s.substring(s.indexOf(' ') + 1));
                maskArr.add(new Vector2(x, y));
            }

            Logger.println("Load mask \"" + path + "\" complited", Logger.Type.DEBUG_MASK);
            Vector2<Integer>[] result = new Vector2[maskArr.size()];
            return maskArr.toArray(result);
        } catch (IOException e) {
            Logger.println("Load mask \"" + path +"\" error", Logger.Type.ERROR);
        }

        return createDefault(width, height);
    }

    //�������� ����� �� �������� �������� �������� (������������ ������ �������� ����)
    public Vector2<Integer>[] createDefault(int width, int height){
        Vector2<Integer>[] mask = new Vector2[4];

        mask[0] = new Vector2(0, 0);
        mask[1] = new Vector2(width-1, 0);
        mask[2] = new Vector2(width-1, height-1);
        mask[3] = new Vector2(0, height-1);

        return center(mask, width, height);
    }

    //��������� ��������� ����� ������������ ������ ������� (���������� ������������ ������ �������� ����)
    public Vector2<Integer>[] center(Vector2<Integer>[] mask, int width, int height){
        Vector2<Integer>[] maskDraw = new Vector2[mask.length];

        for (int i=0; i<mask.length; i++){
            maskDraw[i] = new Vector2();
            maskDraw[i].x = mask[i].x - width/2;
            maskDraw[i].y = mask[i].y - height/2;
        }

        return maskDraw;
    }

    public Vector2<Integer>[] getMaskCenter(){
        Vector2<Integer>[] copy = new Vector2[maskCenter.length];
        for (int i=0; i<maskCenter.length; i++)
            copy[i] = new Vector2(maskCenter[i]);

        return copy;
    }

    public Vector2<Integer>[] getMaskDefault(){
        Vector2<Integer>[] copy = new Vector2[maskDefault.length];
        for (int i=0; i<maskDefault.length; i++)
            copy[i] = new Vector2(maskDefault[i]);

        return copy;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}
