package engine.net.server;

import engine.io.Logger;
import engine.setting.SettingStorage;
import game.server.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer{
	//������������� ��������
	public static int port;
	public static int peopleMax;
	public static int peopleNow;
	public static int disconnect = 0; //�� ��������� � peopleNow
	public static boolean maxPower;//������������ ������������������ � �����������
	//������
	public static DataInputStream[] in;
	public static DataOutputStream[] out;
	//����� ��� ����� ���������
	public static ServerRead[] serverRead;
	//�������� �������� ���������, ������� �� ���������
	public static volatile MessagePack[] messagePack;
	//���-�� ������������ ��������� �������
	public static int[] numberSend;
	
	public static void initSettings(String args[]){
		Logger.enable(Logger.Type.SERVER_INFO);
		Logger.enable(Logger.Type.SERVER_ERROR);
		Logger.enable(Logger.Type.MPS);

		try{
			BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
			String str;

			if (args.length > 0) {
				port = Integer.parseInt(args[0]);
			} else {
				System.out.print("Port (Default 25566): ");
				str = bReader.readLine();
				if (str.equals("")) {
					port = 25566;
				} else {
					port = Integer.parseInt(str);
				}
			}

			if (args.length > 1) {
				peopleMax = Integer.parseInt(args[1]);
			} else {
				System.out.print("Max people (Default 1): ");
				str = bReader.readLine();
				if (str.equals("")) {
					peopleMax = 1;
				} else {
					peopleMax = Integer.parseInt(str);
				}
			}

			if (args.length > 2) {
				maxPower = Boolean.valueOf(args[2]);
			} else {
				System.out.print("Max power (Default false): ");
				str = bReader.readLine();
				if (str.equals("t") || str.equals("T") || str.equals("True") || str.equals("true")) {
					maxPower = true;
				} else {
					maxPower = false;
				}
			}
		} catch (IOException e){
			Logger.println("Failed io text", Logger.Type.ERROR);
			System.exit(0);
		}
	}

	public static void waitConnect(){
		try {
			in = new DataInputStream[peopleMax];
			out = new DataOutputStream[peopleMax];
			serverRead = new ServerRead[peopleMax];
			messagePack = new MessagePack[peopleMax];
			numberSend = new int[peopleMax];

			SettingStorage setting = new SettingStorage();
			setting.init();

			ServerSocket ServerSocket = new ServerSocket(port);
			peopleNow = 0;

			Logger.println("Server started", Logger.Type.SERVER_INFO);

			while (peopleNow != peopleMax) {
				Socket sock = ServerSocket.accept();
				sock.setTcpNoDelay(setting.TCP_NODELAY);
				sock.setKeepAlive(setting.KEEP_ALIVE);
				sock.setSendBufferSize(setting.SEND_BUF_SIZE);
				sock.setReceiveBufferSize(setting.RECEIVE_BUF_SIZE);
				sock.setPerformancePreferences(setting.PREFERENCE_CON_TIME, setting.PREFERENCE_LATENCY, setting.PREFERENCE_BANDWIDTH);
				sock.setTrafficClass(setting.TRAFFIC_CLASS);

				in[peopleNow] = new DataInputStream(sock.getInputStream());
				out[peopleNow] = new DataOutputStream(sock.getOutputStream());
				messagePack[peopleNow] = new MessagePack(peopleNow);
				Logger.println("New client (" + (peopleNow + 1) + "/" + peopleMax + ")", Logger.Type.SERVER_INFO);
				serverRead[peopleNow] = new ServerRead(peopleNow);
				peopleNow++;
			}
			ServerSocket.close();

			Logger.println("All users connected", Logger.Type.SERVER_INFO);

			new AnalyzerThread().start();//����� ���������� ������ ��� �����������
			processingData();//����� ����������� ����� � ���������� ������
		} catch (IOException e){
            Logger.println("Failed server start", Logger.Type.SERVER_ERROR);
			System.exit(0);
		}
	}

	public static void processingData(){
		Server server = new Server();

		while (disconnect != peopleMax) {


			for (int i=0; i<GameServer.peopleMax; i++){
				String str = "";

				synchronized(GameServer.messagePack[i]) {//������ �� ������������� ������ � ��������
					if (GameServer.messagePack[i].haveMessage()){//���� � ������ ������� ���������
						str = GameServer.messagePack[i].get();//������ ���������
					}
				}

				if (str.length() > 0){
					int type = Integer.parseInt(str.split(" ")[0]);
					String mes = str.substring(str.indexOf(" ")+1);
					server.read(i, type, mes);
				}

			}

			if (!GameServer.maxPower) try {Thread.sleep(0,1);} catch (InterruptedException e) {}
		}

		Logger.println("All user disconnect!", Logger.Type.SERVER_INFO);
		System.exit(0);
	}



	public static void send(int id, int type, String str){
		try {
			synchronized (GameServer.out[id]) {
				GameServer.out[id].flush();
				GameServer.out[id].writeUTF(type + " " + str);
			}
			numberSend[id]++; //���-�� ������������ �������
		} catch (IOException e) {
			if (!GameServer.serverRead[id].disconnect) Logger.print("Send message failed", Logger.Type.SERVER_ERROR);
		}
	}

	public static void sendAllExceptId(int id, int type, String str){
		for(int i=0; i<peopleMax; i++){//���������� ��������� ����
			if (i != id){//����� ������, ����������� ���������
				GameServer.send(i, type, str);
			}
		}
	}
	
	public static void sendAll(int type, String str){
		for(int i=0; i<peopleMax; i++){//���������� ��������� ����
			GameServer.send(i, type, str);
		}
	}
	
	public static void main(String args[]){
		initSettings(args);
		waitConnect();
	}

}

