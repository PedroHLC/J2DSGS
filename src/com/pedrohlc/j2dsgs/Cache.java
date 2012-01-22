package com.pedrohlc.j2dsgs;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import org.newdawn.easyogg.OggClip;

public class Cache {
	private Map<String, BufferedImage> cache_images = new HashMap<String, BufferedImage>();
	private Map<String, AudioClip> cache_waves = new HashMap<String, AudioClip>();
	private Map<String, Sequence> cache_midis = new HashMap<String, Sequence>();
	private Map<String, OggClip> cache_oggs = new HashMap<String, OggClip>();
	
	public BufferedImage getImage(String file_path){
		if(cache_images.containsKey(file_path))
			return cache_images.get(file_path);
		BufferedImage img_data = null;
		try {
			img_data = ImageIO.read(new File(file_path));
			cache_images.put(file_path, img_data);
		} catch (IOException e) {
			System.out.println("A Imagem \"" + file_path + "\" não foi encontrada.");
			System.exit(1);
		}
		return img_data;
	}
	
	public boolean freeImage(String file_path){
		if(cache_images.containsKey(file_path))
			cache_images.remove(file_path);
		else
			return false;
		return true;
	}
	
	public AudioClip getWAVE(String file_path){
		if(cache_waves.containsKey(file_path))
			return cache_waves.get(file_path);
		AudioClip audio_data = null;
		try {
			File tmp_file = new File(file_path);
			if(!tmp_file.canRead())
				new Exception("Can't read file!").notify();
			audio_data = Applet.newAudioClip(tmp_file.toURI().toURL());
		} catch (Exception e) {
			System.out.println("O audio \"" + file_path + "\" não foi encontrado.");
			System.exit(1);
		}
		cache_waves.put(file_path, audio_data);
		return audio_data;
	}
	
	public boolean freeWAVE(String file_path){
		if(cache_waves.containsKey(file_path))
			cache_waves.remove(file_path);
		else
			return false;
		return true;
	}
	
	public Sequence getMIDI(String file_path){
		if(cache_midis.containsKey(file_path))
			return cache_midis.get(file_path);
		Sequence sequence = null;
        try {
        	File tmp_file = new File(file_path);
        	sequence = MidiSystem.getSequence(tmp_file);
		} catch (Exception e) {
			System.out.println("O MIDI \"" + file_path + "\" não pode ser lida.");
			System.exit(1);
		}
        return sequence;
	}
	
	public boolean freeMIDI(String file_path){
		if(cache_midis.containsKey(file_path))
			cache_midis.remove(file_path);
		else
			return false;
		return true;
	}
	
	public OggClip getOGG(String file_path){
		if(cache_oggs.containsKey(file_path))
			return cache_oggs.get(file_path);
		OggClip audio_data = null;
		try {
			FileInputStream tmp_file = new FileInputStream(file_path);
			if(tmp_file.getFD() == null)
				new Exception("Can't read file!").notify();
			audio_data = new OggClip(tmp_file);
		} catch (Exception e) {
			System.out.println("O OGG \"" + file_path + "\" não pode ser carregado.");
			System.exit(1);
		}
		cache_oggs.put(file_path, audio_data);
        return audio_data;
	}
	
	public boolean freeAudio(String file_path){
		boolean r=false;
		if(cache_oggs.containsKey(file_path)){
			cache_oggs.remove(file_path);
			r=true;
		}if(cache_midis.containsKey(file_path)){
			cache_midis.remove(file_path);
			r=true;
		}if(cache_waves.containsKey(file_path)){
			cache_waves.remove(file_path);
			r=true;
		}
		return r;
	}
	
	public boolean freeOGG(String file_path){
		if(cache_oggs.containsKey(file_path))
			cache_oggs.remove(file_path);
		else
			return false;
		return true;
	}
	
	
	public void freeALL(){
		cache_images.clear();
		cache_waves.clear();
		cache_midis.clear();
		cache_oggs.clear();
	}
}
