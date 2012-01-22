package com.pedrohlc.j2dsgs;

public class Drawer {
	public boolean drawImage(String filepath, int x, int y){
		return Global.Graphics.drawImage(
			Global.Cache.getImage(filepath),
			x, y,
			null);
	}
		
	public boolean drawImage(String filepath, int x, int y, int width, int height){
		return Global.Graphics.drawImage(
			Global.Cache.getImage(filepath),
			x, y,
			width, height,
			null);
	}
	
	public boolean drawImage(String filepath, int[] pos_size){
		if(pos_size.length <= 2){
			return Global.Graphics.drawImage(
				Global.Cache.getImage(filepath),
				pos_size[0],
				pos_size[1],
				null);
		}else
			return Global.Graphics.drawImage(
				Global.Cache.getImage(filepath),
				pos_size[0],
				pos_size[1],
				pos_size[2],
				pos_size[3],
				null);
	}
	
	public void drawString(String text, int x, int y){
		Global.Graphics.drawString(text, x, y);
	}
	
}