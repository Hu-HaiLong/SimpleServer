package com.Hhl.Socket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

/**
 * socket 服务端
 * 
 */
public class U3dServer implements Runnable {

	public void run() {
		
		System.out.println("系统运行中...");  
		
		ServerSocket u3dServerSocket = null;

		FightManager fm = new FightManager();
		fm.Init(); 	
	    HashMap<String, Socket> socketList = new HashMap<String, Socket>();
	    String channelToken;  //socket 令牌
	    BufferedReader bufferedReader;
		
		while (true) {

			try {

				u3dServerSocket = new ServerSocket(8000);
				System.out.println("u3d服务已经启动,监听8000端口");
				while (true) {
					Socket socket = u3dServerSocket.accept();
					System.out.println("客户端接入");
					
//					bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//	                channelToken = bufferedReader.readLine();
//					channelToken = bufferedReader.toString();
//	                socketList.put(channelToken,socket);   //保存会话ID和socket
//	                System.out.println("clientList : " + socketList.size() + "  ---");
//					
//	                new RequestReceiver(socket,fm,socketList).start();
					
	                new RequestReceiver(socket).start();
				}
			} catch (IOException e) {
				System.err.println("服务器接入失败");
				if (u3dServerSocket != null) {
					try {
						u3dServerSocket.close();
					} catch (IOException ioe) {
					}
					u3dServerSocket = null;
				}
			}

			// 服务延时重启
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {

			}

		}

	}

	/**
	 * 客户端请求接收线程
	 * 
	 */
	class RequestReceiver extends Thread {

		/** 报文长度字节数 */
		private int messageLengthBytes = 1024;

		private Socket socket;
		
		private  HashMap<String, Socket> clientList = new HashMap<String, Socket>();
		FightManager fm;
		
		/** socket输入处理流 */
		private BufferedInputStream bis = null;

		public RequestReceiver(Socket socket) {
			this.socket = socket;
		}
		
		public RequestReceiver(Socket socket,FightManager fm,HashMap<String, Socket> socketList) {
			this.socket = socket;
			this.fm = fm;
			this.clientList = socketList;
		}

		@Override
		public void run() {
			try {
				// 获取socket中的数据
				bis = new BufferedInputStream(socket.getInputStream());
				byte[] buf = new byte[messageLengthBytes];

				/**
				 * 在Socket报文传输过程中,应该明确报文的域
				 */
				while (true) {
					/*
					 * 这种业务处理方式是根据不同的报文域,开启线程,采用不同的业务逻辑进行处理 依据业务需求而定
					 */
					// 读取字节数组中的内容
					bis.read(buf);

					String str = new String(buf, "utf-8");

					// 输出接到的数据
					System.out.println(str);
					// 反序列化
					JsonReader jsonReader = new JsonReader(
							new StringReader(str));
					jsonReader.setLenient(true);
					Gson gson = new Gson();
					
					
					//向客户端传输数据的字节数组
					UserAccount te = gson.fromJson(jsonReader, UserAccount.class);
					System.out.println(te.ServerAccount +"  "+ te.ServerPwd +"     asd  ");
					if(te.ServerAccount.equals("111111") && te.ServerPwd.equals("111111"))
					{
						OutputStream out = socket.getOutputStream();
						out.write(new String("20").getBytes());
						//
						MySQLConnect.getAll();
					}
					else
					{
						OutputStream out = socket.getOutputStream();
						out.write(new String("21").getBytes());
					}
				}
			} catch (IOException e) {
				System.err.println("读取报文出错");
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
					}
					socket = null;
				}
			}
		}
	}
}