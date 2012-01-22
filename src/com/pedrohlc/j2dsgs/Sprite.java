package com.pedrohlc.j2dsgs;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Sprite {
	private int x, y, z;
	private Graphics2D graph;
	
	public Sprite(){
		x = 0;
		y = 0;
		z = 0;
		graph = null;
		Global.Sprites.add(this);
	}
	
	@InTest
	public void setGraphics(Graphics2D value){
		if(graph != null)
			graph.dispose();
		graph = value;
	}
	
	@InTest
	public void setBitmap(String img_path){
		if(graph != null)
			graph.dispose();
		BufferedImage img = Global.Cache.getImage(img_path);
		int w = img.getWidth(),
			h = img.getHeight();
		graph = (Graphics2D) Global.Graphics.create(x, y, w, h);
		graph.drawImage(img, w, h, null);
	}
	
	@InTest
	public Graphics2D getGraphics(){
		return graph;
	}
	
	@InTest
	public void dispose(){
		if(graph != null)
			graph.dispose();
		Global.Sprites.remove(this);
	}
	
	@InTest
	public int getY(){
		return y;
	}
	
	@InTest
	public int getX(){
		return x;
	}
	
	@InTest
	public Point getPosition(){
		return new Point(x, y);
	}
	
	@InTest
	public int getZ(){
		return z;
	}
	
	@InTest
	public void setY(int value){
		y = value;
	}
	
	@InTest
	public void setX(int value){
		x = value;
	}
	
	@InTest
	public void setPosition(Point value){
		y = (int) value.getY();
		x = (int) value.getX();
	}
	
	@InTest
	public void setPosition(int valuex, int valuey){
		y = valuey;
		x = valuex;
	}
	
	@InTest
	public void setZ(int value){
		z = value;
	}
	
}
