package com.pedrohlc.j2dsgs;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

public class Audio {
	private AudioData bgm_data, bgs_data, me_data, se_data;
	private String bgm_name, bgs_name, me_name, se_name;
	public Sequencer midi_sequencer;
	
	public Audio(){
		try {
			midi_sequencer = MidiSystem.getSequencer();
			midi_sequencer.open();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void playBGM(String file_path, int type){
		if(file_path == bgm_name){
			bgm_data.loop();
			return;
		}
		AudioData temp_data = new AudioData();
		temp_data.load(file_path, type);
		if(temp_data.isLoaded()){
			bgm_data = temp_data;
			bgm_name = file_path;
			bgm_data.loop();
		}else
			System.out.println("J2DSGS Falhou ao tentar carregar audio '" + file_path + '\'');
	}
	
	public void stopBGM(){
		if(bgm_data != null)
			bgm_data.stop();
	}
	
	public void playBGS(String file_path, int type){
		if(file_path == bgs_name){
			bgs_data.loop();
			return;
		}
		AudioData temp_data = new AudioData();
		temp_data.load(file_path, type);
		if(temp_data.isLoaded()){
			bgs_data = temp_data;
			bgs_name = file_path;
			bgs_data.loop();
		}else
			System.out.println("J2DSGS Falhou ao tentar carregar audio '" + file_path + '\'');
	}
	
	public void stopBGS(){
		if(bgm_data != null)
			bgm_data.stop();
	}
	
	public void playME(String file_path, int type){
		if(file_path == me_name){
			me_data.play();
			return;
		}
		AudioData temp_data = new AudioData();
		temp_data.load(file_path, type);
		if(temp_data.isLoaded()){
			me_data = temp_data;
			me_name = file_path;
			me_data.play();
		}else
			System.out.println("J2DSGS Falhou ao tentar carregar audio '" + file_path + '\'');
	}
	
	public void stopME(){
		if(bgm_data != null)
			bgm_data.stop();
	}
	
	public void playSE(String file_path, int type){
		if(file_path == se_name){
			se_data.play();
			return;
		}
		AudioData temp_data = new AudioData();
		temp_data.load(file_path, type);
		if(temp_data.isLoaded()){
			se_data = temp_data;
			se_name = file_path;
			se_data.play();
		}else
			System.out.println("J2DSGS Falhou ao tentar carregar audio '" + file_path + '\'');
	}
	
	public void stopSE(){
		if(bgm_data != null)
			bgm_data.stop();
	}
	
	public int getAudioTypeByPath(String file_path){
		String file_pathd = file_path.toLowerCase();
		if(file_pathd.endsWith(".ogg"))
			return AudioData.TYPE_OGG;
		if(file_pathd.endsWith(".mid"))
			return AudioData.TYPE_MID;
		if(file_pathd.endsWith(".wav"))
			return AudioData.TYPE_WAV;
		else
			System.out.println("J2DSGS Falhou ao determinar tipo de audio.");
		return 0;
	}
	
	public void playBGM(String file_path){
		playBGM(file_path, getAudioTypeByPath(file_path));
	}
	
	public void playBGS(String file_path){
		playBGS(file_path, getAudioTypeByPath(file_path));
	}
	
	public void playME(String file_path){
		playME(file_path, getAudioTypeByPath(file_path));
	}
	
	public void playSE(String file_path){
		playSE(file_path, getAudioTypeByPath(file_path));
	}
	
}
