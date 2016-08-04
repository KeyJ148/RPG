package engine.io;

import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class KeyboardHandler {
	
	public static ArrayList<Integer> bufferKey = new ArrayList<Integer>();
	public static ArrayList<Boolean> bufferState = new ArrayList<Boolean>();//True - нажато, false - отпущено

	public static boolean lock = false;

	public static void update(){
		lock = false;

		bufferKey.clear();
		bufferState.clear();
		while (Keyboard.next()) {
			bufferKey.add(Keyboard.getEventKey());
			bufferState.add(Keyboard.getEventKeyState());
		}
	}

	public static boolean isKeyDown(int key){
		if (lock) return false;
		return Keyboard.isKeyDown(key);
	}

	//Блокирование данных, происходит при работе с интерфейсом
	public static void lock(){
		lock = true;

		for (int i=0; i<bufferState.size(); i++){
			if (bufferState.get(i)){
				bufferKey.remove(i);
				bufferState.remove(i);
			}
		}
	}

}
