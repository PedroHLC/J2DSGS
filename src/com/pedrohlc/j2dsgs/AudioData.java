package com.pedrohlc.j2dsgs;

import java.applet.AudioClip;
import javax.sound.midi.Sequence;
import org.newdawn.easyogg.OggClip;

public class AudioData {
	public static final int TYPE_WAV = 1, TYPE_MID = 2, TYPE_OGG = 3;
	private Object data;
	private int type;
	private String file_path;
	
	public String getFilePath(){
		return file_path;
	}
	
	public AudioData(){
		data = null;
		type = 0;
		file_path = null;
	}
	
	public boolean load(String lfile_path, int ltype){
		if(ltype <= 0) return false;
		boolean r = false;
		switch(ltype){
			case TYPE_WAV:
				r = load_wav(lfile_path);
				break;
			case TYPE_MID:
				r = load_mid(lfile_path);
				break;
			case TYPE_OGG:
				r = load_ogg(lfile_path);
				break;
		}
		return r;
	}
	
	private boolean load_wav(String lfile_path){
		AudioClip temp_data;
		temp_data = Global.Cache.getWAVE(lfile_path);
		if(temp_data != null){
			data = temp_data;
			file_path = lfile_path;
			type = 1;
			return true;
		}
		return false;
	}
	
	private boolean load_mid(String lfile_path){
		Sequence temp_data;
		temp_data = Global.Cache.getMIDI(lfile_path);
		if(temp_data != null){
			data = temp_data;
			file_path = lfile_path;
			type = 2;
			return true;
		}
		return false;
	}
	
	private boolean load_ogg(String lfile_path){;
		OggClip temp_data;
		temp_data = Global.Cache.getOGG(lfile_path);
		if(temp_data != null){
			data = temp_data;
			file_path = lfile_path;
			type = 3;
			return true;
		}
		return false;
	}
	
	public void play(){
		if(type <= 0) return;
		switch(type){
			case TYPE_WAV:
				play_wav();
				break;
			case TYPE_MID:
				play_mid();
				break;
			case TYPE_OGG:
				play_ogg();
				break;
		}
	}
	
	private void play_wav(){
		((AudioClip)data).play();
	}
	
	private void play_mid(){
		try {
			Global.Audio.midi_sequencer.setSequence((Sequence)data);
			Global.Audio.midi_sequencer.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private void play_ogg(){
		((OggClip)data).play();
	}
	
	public void loop(){
		if(type <= 0) return;
		switch(type){
			case TYPE_WAV:
				loop_wav();
				break;
			case TYPE_MID:
				System.out.println("J2DSGS ainda não está pronto para fazer loops em MID.");
				break;
			case TYPE_OGG:
				loop_ogg();
				break;
		}
	}
	
	private void loop_wav(){
		((AudioClip)data).loop();
	}
	
	private void loop_ogg(){
		((OggClip)data).loop();
	}
	
	public void stop(){
		if(type <= 0) return;
		switch(type){
			case TYPE_WAV:
				stop_wav();
				break;
			case TYPE_MID:
				stop_mid();
				break;
			case TYPE_OGG:
				stop_ogg();
				break;
		}
	}
	
	private void stop_wav(){
		((AudioClip)data).stop();
	}
	
	private void stop_mid(){
		Global.Audio.midi_sequencer.stop();
	}
	
	private void stop_ogg(){
		((OggClip)data).stop();
	}
	
	public boolean isLoaded(){
		return(type>=1);
	}
}
