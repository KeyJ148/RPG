package engine.cycle;

import engine.Global;
import engine.io.Logger;
import engine.net.client.Ping;

public class Analyzer {

	//��� �������� fps
	public int loopsRender = 0;
	public int loopsUpdate = 0;
	public long lastAnalysis;

	//��� �������� ��������������
	long durationUpdate = 0;
	long durationRender = 0;

	//����
	public int ping=0, pingMin=0, pingMax=0, pingMid=0;

	//�������� ����
	public int send=0, load=0;

	public Analyzer(){
		lastAnalysis = System.currentTimeMillis();
		Global.pingCheck = new Ping();
	}

	public void loopsUpdate(long startUpdate){
		durationUpdate += System.nanoTime() - startUpdate;
		loopsUpdate++;
		if (System.currentTimeMillis() >= lastAnalysis + 1000){
			analysisData();
		}
	}

	public void loopsRender(long startRender){
		durationRender += System.nanoTime() - startRender;
		loopsRender++;
	}

	public void analysisData(){
		ping = Global.pingCheck.ping();
		pingMin = Global.pingCheck.pingMin();
		pingMid = Global.pingCheck.pingMid();
		pingMax = Global.pingCheck.pingMax();

		send = Math.round(Global.tcpControl.sizeDataSend/1024);
		load = Math.round(Global.tcpControl.sizeDataRead/1024);
		Global.tcpControl.sizeDataSend = 0;
		synchronized (Global.tcpControl.sizeDataReadMonitor){
			Global.tcpControl.sizeDataRead = 0;
		}

		//��� ����� �������, ����� ������ �� 0
		if (loopsRender == 0) loopsRender = 1;
		if (loopsUpdate == 0) loopsUpdate = 1;
		int chunkInDepthVector = (Global.room.mapControl.getCountDepthVectors() == 0)? 0:Global.room.mapControl.chunkRender/Global.room.mapControl.getCountDepthVectors();

		//������ �������
		String strFPS1 = 	 "Ping: " + ping + " (" + pingMin + "-" + pingMid + "-" + pingMax + ")"
				+ "          Speed S/L: " + send + "/" + load + " kb/s";
		String strFPS2 =	 "FPS: " + loopsRender
				+ "          Duration update/render: " + (durationUpdate/loopsUpdate/1000) + "/" + (durationRender/loopsRender/1000) + " mks"
				+ "          Objects: " + Global.room.objCount()
				+ "          Chunks: " + Global.room.mapControl.chunkRender +
				" (" + (chunkInDepthVector) + "*" + (Global.room.mapControl.getCountDepthVectors()) + ")";

		Logger.println(strFPS1, Logger.Type.CONSOLE_FPS);
		Logger.println(strFPS2, Logger.Type.CONSOLE_FPS);

		if (Global.setting.DEBUG_MONITOR_FPS){
			Global.engine.render.strAnalysis1 = strFPS1;
			Global.engine.render.strAnalysis2 = strFPS2;
		}

		lastAnalysis = System.currentTimeMillis();
		loopsUpdate = 0;
		loopsRender = 0;
		durationUpdate = 0;
		durationRender = 0;
	}

}
