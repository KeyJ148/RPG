package engine.inf;

import java.util.ArrayList;

public class InfMain {

    public ArrayList<Inf> infs = new ArrayList<>();

    public void update(){
        //���������� ��� �������, ���� � ���� ���������������, �� ����� ����������� ���� � ����������
        //���������� �������� � ������ �������� ���� (����������� �������)
        //������� � ������� ������������ �� �������, ������ ������ -- ����� ��������
        //������� ������� �������������� � �������� �������, � ����� ������� ���������
        for (int i=infs.size()-1; i >= 0; i--){
            Inf inf = infs.get(i);
            if (!inf.delete) inf.update();
            else infs.remove(i);
        }
    }

    public void draw(){
        for (Inf inf : infs) inf.draw();
    }

}
