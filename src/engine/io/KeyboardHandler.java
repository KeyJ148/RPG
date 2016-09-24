package engine.io;

import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class KeyboardHandler {
	
	public static ArrayList<Integer> bufferKey = new ArrayList<Integer>();
	public static ArrayList<Character> bufferChar = new ArrayList<>();
	public static ArrayList<Boolean> bufferState = new ArrayList<Boolean>();//True - ������, false - ��������

	//��������� ��� ����������� �������
	public static String availableChars = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM" +
			   							  "������������������������������������������������������������������" +
										  "1234567890`~!@#$%^&*()-_+=[]{};:'\"<>,./?\\|";
	public static boolean lock = false;

	public static void update(){
		lock = false;

		bufferKey.clear();
		bufferChar.clear();
		bufferState.clear();
		while (Keyboard.next()) {
			bufferKey.add(Keyboard.getEventKey());
			bufferChar.add(isAvailableChar(Keyboard.getEventCharacter())?Keyboard.getEventCharacter():null);
			bufferState.add(Keyboard.getEventKeyState());
		}
	}

	public static boolean isKeyDown(int key){
		if (lock) return false;
		return Keyboard.isKeyDown(key);
	}

	//������������ ������, ���������� ��� ������ � �����������
	public static void lock(){
		lock = true;

		for (int i=0; i<bufferState.size(); i++){
			if (bufferState.get(i)){
				bufferKey.remove(i);
				bufferChar.remove(i);
				bufferState.remove(i);
			}
		}
	}

	public static boolean isAvailableChar(char c){
		for (int i=0; i<availableChars.length(); i++){
			if (availableChars.charAt(i) == c) return true;
		}

		return false;
	}

}
