package com.pedrohlc.j2dsgs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class NetSocket {
	private Socket sock;
	private DataInputStream in;
	private DataOutputStream out;
	
	public NetSocket(){
		sock = null;
		in = null;
		out = null;
	}
	
	@InTest
	public boolean connect(String addr, int port){
		if(sock != null) close();
		Socket temp_sock = null;
		DataInputStream temp_in = null;
		DataOutputStream temp_out = null;
		try {
			temp_sock = new Socket(addr, port);
			temp_in = new DataInputStream(sock.getInputStream());
			temp_out = new DataOutputStream(sock.getOutputStream());
		} catch (Exception e) {
			return false;
		}
		if(temp_sock != null & temp_in != null & temp_out != null){
			sock = temp_sock;
			in = temp_in;
			out = temp_out;
			return true;
		}else
			return false;
	}
	
	@InTest
	public String gets(){
		try {
			return in.readUTF();
		} catch (Exception e) {}
		return null;
	}
	
	@InTest
	public boolean send(String msg){
		try {
			out.writeUTF(msg + '\0');
			return true;
		} catch (Exception e) {}
		return false;
	}
	
	@InTest
	public void close(){
		try {
			in.close();
			out.close();
			sock.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		in = null;
		out = null;
		sock = null;
	}
}
