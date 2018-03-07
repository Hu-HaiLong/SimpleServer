package com.Hhl.Socket;

import java.util.ArrayList;
import java.util.List;


public class UserData {
	
	private int id;
	private String name; 
	private int level;
    private long money;
    private List<MovementsCon> skill = new ArrayList<MovementsCon>(); 	
    
	public UserData(int i, String str, int l, long m) {
		id = i;
		name = str;
		setLevel(l);
		setMoney(m);
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}
}

