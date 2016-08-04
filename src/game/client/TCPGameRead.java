package game.client;

public class TCPGameRead{

	public static void read(int type, String str){
		switch (type){
			case 2: take2(str); break;
			//Engine: Различные действия с уникальными индексами
		}
	}

	public static void take2(String str){
		//Engine: Другой клиент информирует нас о каком-то его действие
	}

}
