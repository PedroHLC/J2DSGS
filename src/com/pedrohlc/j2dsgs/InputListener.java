package com.pedrohlc.j2dsgs;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Classe de baixo nível, não prescisa ter variaves/métodos ajeitados.
 */

public class InputListener implements KeyListener, MouseListener, MouseMotionListener, FocusListener, MouseWheelListener  {
	
	public boolean[] keys = new boolean[256];
	public boolean mouse_pressed = false;
	public boolean mouse_released = false;
	public int[] mousePos = {0, 0};
	public boolean pause_whenLostFocus = true;
	private boolean pause_becauseOfFocus = true;
	
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		mouse_pressed = true;
		mouse_released = true;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouse_pressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouse_released = true;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(Global.Window == null) return;
		mousePos[0] = e.getX() - Global.Window.getMarginL();
		mousePos[1] = e.getY() - Global.Window.getMarginT();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(Global.Window == null) return;
		mousePos[0] = e.getX() - Global.Window.getMarginL();
		mousePos[1] = e.getY() - Global.Window.getMarginT();
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		for(int i=0; i<256; i++)
			keys[i] = false;
		mouse_released = true;
		if(pause_becauseOfFocus){
			Global.Window.setPaused(false);
			pause_becauseOfFocus = true;
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		if(pause_whenLostFocus){
			Global.Window.setPaused(true);
			pause_becauseOfFocus = true;
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) { //Trabalhando
		// TODO Auto-generated method stub
	}
	
}
