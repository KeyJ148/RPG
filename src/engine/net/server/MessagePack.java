package engine.net.server;

import engine.io.Logger;

import java.util.LinkedList;

public class MessagePack {
	
	public LinkedList<String> message;//Список сообщений
	public long lastTrim;
	public int id;
	
	public MessagePack(int id){
		this.message = new LinkedList<String>();
		this.id = id;
	}
	
	public void add(String str){
		message.add(str);
	}
	
	public String get(){
		if (size()%10 == 0){
			Logger.println("Messages detained: " + size() + " Id: " + id, Logger.Type.DEBUG);
		}
		
		return message.removeFirst();
	}
	
	public int size(){
		return message.size();
	}
	
	public void clear(){
		message.clear();
	}
	
	public boolean haveMessage(){
		if (message.isEmpty()) return false;
		return true;
	}
	
}
