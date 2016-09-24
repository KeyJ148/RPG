package engine.io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {

    private static boolean enable = false;
    private static PrintWriter out;

    //Вывод сообщения о ошибке
    public static void error(String s){
        System.out.println("[ERROR] " + s);
        if (Logger.enable) out.println("[ERROR] " + s);
    }

    //Вывод информационного сообщения
    public static void p(String s){
        System.out.println("[INFO] " + s);
        if (Logger.enable) out.println("[INFO] " + s);
    }

    public static void enable(){
        if (!enable){
            try {
                out = new PrintWriter(new FileWriter("res/log.txt"));
            } catch (IOException e) {
                Logger.error("Write log file");
            }
        }
    }
}
