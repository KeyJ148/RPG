package game.client;

import engine.io.KeyboardHandler;
import org.lwjgl.input.Keyboard;

public class Game {

	public void init() {
		//Engine: ������������� ���� ����� �������� �������� �����
	}
	
	public void update(long delta){
		//Engine: ����������� ������ ���� ����� ����������� ���� ������� ��������
		if (KeyboardHandler.isKeyDown(Keyboard.KEY_ESCAPE)) System.exit(0);
	}
	
	public void render(){
		//Engine: ����������� ������ ���� ����� ������������ ���� ������� ��������
	}

}
