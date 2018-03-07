package com.Hhl.Socket;

import com.google.gson.Gson;

public class U3dApplication {
	private static U3dApplication instance = null;  
	  
    private boolean stop;  
  
    private U3dApplication() {  
    }  
  
    public static synchronized U3dApplication getApplication() {  
        if (instance == null) {  
            instance = new U3dApplication();  
        }  
        return instance;  
    }  
      
    public void start() {  
        init();  
        new Thread(new U3dServer(), "U3d Server").start();  
  
        while (!stop) {  
            try {  
                Thread.sleep(1000);  
            } catch (InterruptedException e) {  
            }  
        }  
    }  
      
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
    	System.out.println("系统即将开启...");  
        Runtime.getRuntime().addShutdownHook(new Thread() {  
            @Override  
            public void run() {  
                getApplication().stopMe();  
            }  
        });  
        getApplication().start();  
    }  
      
    public void stopMe() {  
        System.out.println("系统即将关闭...");  
    }  
      
      
    /** 
     * 初始化系统 
     */  
    private void init() {  
          Gson gson = new Gson();
          UserData t = new UserData(1001, "xiaoxiao",1,33111);
          String str = gson.toJson(t);
          System.out.println(str);
          UserData te = gson.fromJson(str, UserData.class);
          System.out.println(te.getId() + "  " + te.getName());
    }  
}  