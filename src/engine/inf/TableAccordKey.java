package engine.inf;

import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Map;

public class TableAccordKey{

    private static final Map<Integer, Character> accord = new HashMap<>();//—оотвуетствие клавиши и еЄ номера

    public static Character get(Integer key) {
        return accord.get(key);
    }

    //»нициализа€ци€ таблицы соответстви€ клавиш
    static{
        accord.put(Keyboard.KEY_0, '0');
        accord.put(Keyboard.KEY_1, '1');
        accord.put(Keyboard.KEY_2, '2');
        accord.put(Keyboard.KEY_3, '3');
        accord.put(Keyboard.KEY_4, '4');
        accord.put(Keyboard.KEY_5, '5');
        accord.put(Keyboard.KEY_6, '6');
        accord.put(Keyboard.KEY_7, '7');
        accord.put(Keyboard.KEY_8, '8');
        accord.put(Keyboard.KEY_9, '9');
        accord.put(Keyboard.KEY_Q, 'q');
        accord.put(Keyboard.KEY_W, 'w');
        accord.put(Keyboard.KEY_E, 'e');
        accord.put(Keyboard.KEY_R, 'r');
        accord.put(Keyboard.KEY_T, 't');
        accord.put(Keyboard.KEY_Y, 'y');
        accord.put(Keyboard.KEY_U, 'u');
        accord.put(Keyboard.KEY_I, 'i');
        accord.put(Keyboard.KEY_O, 'o');
        accord.put(Keyboard.KEY_P, 'p');
        accord.put(Keyboard.KEY_A, 'a');
        accord.put(Keyboard.KEY_S, 's');
        accord.put(Keyboard.KEY_D, 'd');
        accord.put(Keyboard.KEY_F, 'f');
        accord.put(Keyboard.KEY_G, 'g');
        accord.put(Keyboard.KEY_H, 'h');
        accord.put(Keyboard.KEY_J, 'j');
        accord.put(Keyboard.KEY_K, 'k');
        accord.put(Keyboard.KEY_L, 'l');
        accord.put(Keyboard.KEY_Z, 'z');
        accord.put(Keyboard.KEY_X, 'x');
        accord.put(Keyboard.KEY_C, 'c');
        accord.put(Keyboard.KEY_V, 'v');
        accord.put(Keyboard.KEY_B, 'b');
        accord.put(Keyboard.KEY_N, 'n');
        accord.put(Keyboard.KEY_M, 'm');
        accord.put(Keyboard.KEY_NUMPAD0, '0');
        accord.put(Keyboard.KEY_NUMPAD1, '1');
        accord.put(Keyboard.KEY_NUMPAD2, '2');
        accord.put(Keyboard.KEY_NUMPAD3, '3');
        accord.put(Keyboard.KEY_NUMPAD4, '4');
        accord.put(Keyboard.KEY_NUMPAD5, '5');
        accord.put(Keyboard.KEY_NUMPAD6, '6');
        accord.put(Keyboard.KEY_NUMPAD7, '7');
        accord.put(Keyboard.KEY_NUMPAD8, '8');
        accord.put(Keyboard.KEY_NUMPAD9, '9');
        accord.put(Keyboard.KEY_SPACE, ' ');
    }
}
