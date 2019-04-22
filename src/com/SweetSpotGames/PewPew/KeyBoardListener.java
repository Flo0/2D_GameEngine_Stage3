package com.SweetSpotGames.PewPew;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;

public class KeyBoardListener implements KeyListener, FocusListener{
	
	public KeyBoardListener() {
		this.keyMap = Maps.newHashMap();
	}
	
	public final Map<Integer, MappedKeyCode> keyMap;
	
	@Override
	public void focusGained(FocusEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent event) {
		this.keyMap.values().forEach(key -> key.currentlyPressed = false);
	}

	@Override
	public void keyPressed(KeyEvent event) {
		int keyCode = event.getKeyCode();
		if(!this.keyMap.containsKey(keyCode)) return;
		
		this.keyMap.get(keyCode).currentlyPressed = true;
		
	}

	@Override
	public void keyReleased(KeyEvent event) {
		int keyCode = event.getKeyCode();
		if(!this.keyMap.containsKey(keyCode)) return;
		
		this.keyMap.get(keyCode).currentlyPressed = false;
		
	}

	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public void registerKeyForUse(MappedKeyCode code) {
		this.keyMap.put(code.keyCode, code);
	}
	
	public Set<Integer> getPressedKeys(){
		return this.keyMap.values().stream().filter(key->key.currentlyPressed).map(key->key.keyCode).collect(Collectors.toSet());
	}
	
}
