package engine.cycle;

import engine.Global;
import org.lwjgl.opengl.Display;

public class Engine{

	public Update update;
	public Render render;
	public Analyzer analyzer;//����� ���������� ������
	
	
	public Engine(){
		update = new Update();
		render = new Render();
	}
	
	public void run(){
		long lastUpdate = System.nanoTime();//��� update
		long startUpdate, startRender;//��� �����������
		
		while(!Display.isCloseRequested()){
			startUpdate = System.nanoTime();
			update.loop(System.nanoTime() - lastUpdate);
			lastUpdate = startUpdate;//������ ����������� update, ����� ������������ update ���� �����������
			if ((Global.setting.DEBUG_CONSOLE_FPS) || (Global.setting.DEBUG_MONITOR_FPS)) analyzer.loopsUpdate(startUpdate);
				
			startRender = System.nanoTime();
			render.loop();
			if ((Global.setting.DEBUG_CONSOLE_FPS) || (Global.setting.DEBUG_MONITOR_FPS)) analyzer.loopsRender(startRender);
			render.sync();//����� ��� ������������� ������, ������ ���� ����� analyzer
		}
		
		System.exit(0);
	}
	
}
