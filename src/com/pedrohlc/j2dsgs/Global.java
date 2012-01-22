package com.pedrohlc.j2dsgs;

import java.awt.Graphics2D;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Global {
	public static final String version = "0.1.34c";
	public static boolean canPaint = true;
	public static com.pedrohlc.j2dsgs.SceneBase Scene = null;
	public static com.pedrohlc.j2dsgs.Cache Cache;
	public static com.pedrohlc.j2dsgs.Input Input;
	public static com.pedrohlc.j2dsgs.Audio Audio;
	public static com.pedrohlc.j2dsgs.NetSocket Socket;
	public static com.pedrohlc.j2dsgs.Window Window;
	public static com.pedrohlc.j2dsgs.Drawer Drawer;
	public static ArrayList<com.pedrohlc.j2dsgs.Sprite> Sprites = new ArrayList<com.pedrohlc.j2dsgs.Sprite>();
	public static java.awt.Robot Robot;
	public static Graphics2D Graphics;
	private static char usingWindows = 2;
	
	public static void create(String windowTitle, int windowWidth, int windowHeight, boolean useSocket) throws IOException{
		Cache = new Cache();
		Input = new Input();
		Audio = new Audio();
		Drawer = new Drawer();
		if(useSocket) Socket = new NetSocket();
		Window = new Window(windowTitle, windowWidth, windowHeight);
	}
	
	public static String getNowTimeToStr(String format){
		return new SimpleDateFormat(format).format(new Date());
	}
	
	protected static boolean isUsingWindows(){
		switch(usingWindows){
			case 1:
				return true;
			case 0:
				return false;
			default:
				if(System.getProperty("os.name").contains("Windows")){
					usingWindows = 1;
					return true;
				}else{
					usingWindows = 0;
					return false;
				}
		}
	}
	
	
}
