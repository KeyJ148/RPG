package engine.inf;

import java.util.ArrayList;

public class InfMain {

    public ArrayList<Inf> infs = new ArrayList<>();

    public void update(){
        //���������� ��� �������, ���� � ���� ���������������, �� ����� ����������� ���� � ����������
        //���������� �������� � ������ �������� ���� (����������� �������)
        //������� � ������� ������������ �� �������, ������ ������ -- ����� ��������
        //������� ������� �������������� � �������� �������, � ����� ������� ���������
        for (int i=infs.size()-1; i >= 0; i--)
            infs.get(i).update();
    }

    public void draw(){
        for (Inf inf : infs) inf.draw();
    }

}
