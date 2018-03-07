package com.Hhl.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FightManager {
	Map m;
	ArrayList<UserData> vo;
	public void Init() {
		m = new HashMap(); 
		vo = new ArrayList<UserData>();
	}
	
	public void m_Put(int roomId, ArrayList<UserData> vo)
	{
		m.put(roomId, vo);
	}
	
}
