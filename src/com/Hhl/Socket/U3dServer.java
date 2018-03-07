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
 * socket �����
 * 
 */
public class U3dServer implements Runnable {

	public void run() {
		
		System.out.println("ϵͳ������...");  
		
		ServerSocket u3dServerSocket = null;

		FightManager fm = new FightManager();
		fm.Init(); 	
	    HashMap<String, Socket> socketList = new HashMap<String, Socket>();
	    String channelToken;  //socket ����
	    BufferedReader bufferedReader;
		
		while (true) {

			try {

				u3dServerSocket = new ServerSocket(8000);
				System.out.println("u3d�����Ѿ�����,����8000�˿�");
				while (true) {
					Socket socket = u3dServerSocket.accept();
					System.out.println("�ͻ��˽���");
					
					bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					System.out.println("bufferedReader:" + bufferedReader);
//	                channelToken = bufferedReader.readLine();
					channelToken = bufferedReader.toString();
	                System.out.println("channelToken:" + channelToken);
	                socketList.put(channelToken,socket);   //����ỰID��socket
	                System.out.println("clientList : " + socketList.size() + "  ---");
					
	                new RequestReceiver(socket,fm,socketList).start();
				}
			} catch (IOException e) {
				System.err.println("����������ʧ��");
				if (u3dServerSocket != null) {
					try {
						u3dServerSocket.close();
					} catch (IOException ioe) {
					}
					u3dServerSocket = null;
				}
			}

			// ������ʱ����
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {

			}

		}

	}

	/**
	 * �ͻ�����������߳�
	 * 
	 */
	class RequestReceiver extends Thread {

		/** ���ĳ����ֽ��� */
		private int messageLengthBytes = 1024;

		private Socket socket;
		   private  HashMap<String, Socket> clientList = new HashMap<String, Socket>();
		FightManager fm;
		
		/** socket���봦���� */
		private BufferedInputStream bis = null;

		public RequestReceiver(Socket socket,FightManager fm,HashMap<String, Socket> socketList) {
			this.socket = socket;
			this.fm = fm;
			this.clientList = socketList;
			System.out.println("clientList : " + clientList.size() + "  ---");
		}

		@Override
		public void run() {
			try {
				// ��ȡsocket�е�����
				bis = new BufferedInputStream(socket.getInputStream());
				byte[] buf = new byte[messageLengthBytes];


				/**
				 * ��Socket���Ĵ��������,Ӧ����ȷ���ĵ���
				 */
				while (true) {
					System.out.println("fm.vo.size() : " + fm.vo.size() + "  ++--");
					/*
					 * ����ҵ����ʽ�Ǹ��ݲ�ͬ�ı�����,�����߳�,���ò�ͬ��ҵ���߼����д��� ����ҵ���������
					 */
					// ��ȡ�ֽ������е�����
					bis.read(buf);

					String str = new String(buf, "utf-8");

					// ����ӵ�������
					System.out.println(str);
					System.out.println("_______________________");
					// �����л�
					JsonReader jsonReader = new JsonReader(
							new StringReader(str));
					jsonReader.setLenient(true);
					Gson gson = new Gson();
					UserData te = gson.fromJson(jsonReader, UserData.class);
					System.out.println(te.getId() + "  " + te.getName());
					System.out.println("_______________________");
					
					//
					
					
					if(fm.vo.size()<10)
					{
						fm.vo.add(te);
					}

					System.out.println("fm.vo.size() : " + fm.vo.size() + "  +-++");
	
					for(UserData vo:fm.vo)
					{
						// ��ͻ��˴������ݵ��ֽ�����
						String s = gson.toJson(vo);
						System.out.println("_______��ͻ��˷�������______");
//						OutputStream out = socket.getOutputStream();
//						out.write(new String(s).getBytes());
						for (Socket value : clientList.values()) {
							OutputStream out = value.getOutputStream();
							out.write(new String(s).getBytes());
						}
						System.out.println("_______������_______");
					}		
					
				}

			} catch (IOException e) {
				System.err.println("��ȡ���ĳ���");
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