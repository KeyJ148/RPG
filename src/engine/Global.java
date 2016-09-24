package engine;

import engine.cycle.Engine;
import engine.inf.InfMain;
import engine.map.Room;
import engine.net.client.Ping;
import engine.net.client.TCPControl;
import engine.net.client.TCPRead;
import engine.obj.ObjLight;
import engine.setting.SettingStorage;
import game.client.Game;

public class Global {
	
	public static Engine engine; //������� ������� �����
	public static Game game; //������� ������ ���� ��� ������
	public static Room room; //������� �������

	public static InfMain infMain; //������� ����� ����������
	public static SettingStorage setting;//������ �������� �������� ���������

	public static TCPRead tcpRead; //���� ���������� ������ � �������
	public static TCPControl tcpControl; //������ ��������� � �������� � �����
	public static Ping pingCheck;//������ ��� �������� �����

	public static ObjLight camera;//������, �� ������� ������� ������
	public static double cameraX;
	public static double cameraY;
	public static double cameraXView;
	public static double cameraYView;
}

