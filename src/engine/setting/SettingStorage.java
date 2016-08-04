package engine.setting;

public class SettingStorage {

	public boolean DEBUG_CONSOLE;//�������� � ������� ��������� �������?
	public boolean DEBUG_CONSOLE_OBJECT;//�������� � ������� ��������� �������� ��������?
	public boolean DEBUG_CONSOLE_IMAGE;//�������� � ������� ��������� �������� � ��������?
	public boolean DEBUG_CONSOLE_MASK;//�������� � ������� ��������� �������� �����?
	public boolean DEBUG_CONSOLE_FPS;//�������� � ������� ���?
	public boolean DEBUG_MONITOR_FPS;//�������� � ���� ���?
	public boolean MASK_DRAW;//��������� �����
	
	public int WIDTH_SCREEN;
	public int HEIGHT_SCREEN;//������ ����
	public int SYNC;
	public String WINDOW_NAME;
	public boolean FULL_SCREEN;
	
	public boolean MAX_POWER;
	public boolean MAX_POWER_SERVER;
	
	public boolean TCP_NODELAY;
	public boolean KEEP_ALIVE;
	public int SEND_BUF_SIZE;
	public int RECEIVE_BUF_SIZE;
	public int TRAFFIC_CLASS;
	public int PREFERENCE_CON_TIME;
	public int PREFERENCE_LATENCY;
	public int PREFERENCE_BANDWIDTH;
	
	public String fileNameMain = "main.properties";
	public String fileNameNet = "net.properties";
	
	public void init(){
		initMain();
		initNet();
	}
	
	public void initMain(){
		ConfigReader cr = new ConfigReader(fileNameMain);
		
		WIDTH_SCREEN = cr.findInteger("WIDTH_SCREEN");
		HEIGHT_SCREEN = cr.findInteger("HEIGHT_SCREEN");
		WINDOW_NAME = cr.findString("WINDOW_NAME");
		FULL_SCREEN = cr.findBoolean("FULL_SCREEN");
		SYNC = cr.findInteger("SYNC");
		
		MAX_POWER = cr.findBoolean("MAX_POWER");
		MAX_POWER_SERVER = cr.findBoolean("MAX_POWER_SERVER");
		
		DEBUG_CONSOLE = cr.findBoolean("DEBUG_CONSOLE");
		DEBUG_CONSOLE_OBJECT = cr.findBoolean("DEBUG_CONSOLE_OBJECT");
		DEBUG_CONSOLE_IMAGE = cr.findBoolean("DEBUG_CONSOLE_IMAGE");
		DEBUG_CONSOLE_MASK = cr.findBoolean("DEBUG_CONSOLE_MASK");
		DEBUG_CONSOLE_FPS = cr.findBoolean("DEBUG_CONSOLE_FPS");
		DEBUG_MONITOR_FPS = cr.findBoolean("DEBUG_MONITOR_FPS");
		MASK_DRAW = cr.findBoolean("MASK_DRAW");
	}
	
	public void initNet(){
		ConfigReader cr = new ConfigReader(fileNameNet);
		
		TCP_NODELAY = cr.findBoolean("TCP_NODELAY");
		KEEP_ALIVE = cr.findBoolean("KEEP_ALIVE");
		SEND_BUF_SIZE = cr.findInteger("SEND_BUF_SIZE");
		RECEIVE_BUF_SIZE = cr.findInteger("RECEIVE_BUF_SIZE");
		TRAFFIC_CLASS = cr.findInteger("TRAFFIC_CLASS");
		PREFERENCE_CON_TIME = cr.findInteger("PREFERENCE_CON_TIME");
		PREFERENCE_LATENCY = cr.findInteger("PREFERENCE_LATENCY");
		PREFERENCE_BANDWIDTH = cr.findInteger("PREFERENCE_BANDWIDTH");
	}
}
