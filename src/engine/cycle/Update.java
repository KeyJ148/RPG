package engine.cycle;

import engine.Global;
import engine.image.Camera;
import engine.io.Logger;

public class Update {
	
	public void loop(long delta){
		Global.game.update(delta);//�������� ������� ������� ����� ��� �������������

		Global.engine.render.clearTitle();//������ ��� ������� � �������� �������
		Global.tcpRead.update();//���������� ��� ���������� ���������
		Global.infMain.update();

		if (Global.room != null) {
			Global.room.update(delta);//�������� ��� ������� � �������
		}else {
			Logger.error("No create room! (Global.room)");
			System.exit(0);
		}

		Camera.calc();//������ ��������� ������
	}

}
