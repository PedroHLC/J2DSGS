package com.pedrohlc.j2dsgs;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;

public class Input{
	
	private InputListener listener;
	private boolean[] keys = new boolean[256];
	private boolean mouse_clicked = false;
	private int[] mousePos = new int[2];
	private Toolkit default_toolkit;
	
	public InputListener getListener(){
		return listener;
	}
	
	public Input(){
		listener = new InputListener();
		default_toolkit = Toolkit.getDefaultToolkit();
		for(int i=0; i<256; i++)
			keys[i] = listener.keys[i] = false;
	}
	
	public boolean getKey(int keycode){
		return(keys[keycode]);
	}
	
	public void fakeKeyRelease(int key) {
		listener.keys[key] = false;
	}
	
	public boolean getMouseBtn(){
		return mouse_clicked;
	}
	
	public void fakeMouseBtnRelease(){
		listener.mouse_released = false;
		listener.mouse_pressed = false;
	}

	public int[] getMousePos(){
		return mousePos;
	}
	
	public void setLockingKeyState(int keyCode, boolean value){
		keys[keyCode] = value;
	}
	
	private void update_lockingkeys(){
		keys[KeyEvent.VK_NUM_LOCK] = default_toolkit.getLockingKeyState(KeyEvent.VK_NUM_LOCK);
		keys[KeyEvent.VK_CAPS_LOCK] = default_toolkit.getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
		keys[KeyEvent.VK_SCROLL_LOCK] = default_toolkit.getLockingKeyState(KeyEvent.VK_SCROLL_LOCK);
	}
	
	public void update(){
		keys = listener.keys;
		mouse_clicked = listener.mouse_pressed;
		if(listener.mouse_released){
			listener.mouse_pressed = false;
			listener.mouse_released = false;
		}
		mousePos = listener.mousePos;
		update_lockingkeys();
	}
	
}
