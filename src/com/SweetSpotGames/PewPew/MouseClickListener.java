package com.SweetSpotGames.PewPew;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.Getter;

public class MouseClickListener implements MouseListener, FocusListener, MouseWheelListener, MouseMotionListener{
	
	public MouseClickListener(Game game) {
		this.keyMap = Maps.newHashMap();
		this.game = game;
		this.handlers = Sets.newHashSet();
	}
	
	@Getter
	private final Game game;
	public final Map<Integer, MappedMouseCode> keyMap;
	private final Set<MouseEventHandler> handlers;
	
	public void removeHandler(MouseEventHandler handler) {
		this.handlers.remove(handler);
	}
	
	public void registerHandler(MouseEventHandler handler) {
		this.handlers.add(handler);
	}
	
	@Override
	public void focusLost(FocusEvent event) {
		
		this.keyMap.values().forEach(key -> key.currentlyPressed = false);
		
	}



	@Override
	public void mousePressed(MouseEvent event) {
		this.updateMousePos(event);
		
		int mouseCode = event.getButton();
		if(!this.keyMap.containsKey(mouseCode)) return;
		
		this.keyMap.get(mouseCode).currentlyPressed = true;
		
		this.handlers.forEach(handler -> handler.handle(event));
		
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		
		this.updateMousePos(event);
		
		int mouseCode = event.getButton();
		if(!this.keyMap.containsKey(mouseCode)) return;
		
		this.keyMap.get(mouseCode).currentlyPressed = false;
		
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {}

	@Override
	public void mouseEntered(MouseEvent event) {}

	@Override
	public void mouseExited(MouseEvent event) {}
	
	@Override
	public void focusGained(FocusEvent event) {}
	
	public void registerKeyForUse(MappedMouseCode code) {
		this.keyMap.put(code.mouseCode, code);
	}



	@Override
	public void mouseWheelMoved(MouseWheelEvent event) {
		
		this.updateMousePos(event);
		
		int amount = event.getWheelRotation();
		
		if(amount == -1) {
			this.game.zoomIn();
		}else if(amount == 1) {
			this.game.zoomOut();
		}
		
	}



	@Override
	public void mouseDragged(MouseEvent event) {
		this.updateMousePos(event);
	}

	

	@Override
	public void mouseMoved(MouseEvent event) {
		this.updateMousePos(event);
	}
	
	public void updateMousePos(MouseEvent event) {
		game.cursorScreenPos[0] = event.getPoint().x;
		game.cursorScreenPos[1] = event.getPoint().y;
		int[] playerCenter = game.getPlayer().getRepresenter().getCenter(0, 0);
		game.cursorGamePos[0] = (int) (playerCenter[0] + (game.cursorScreenPos[0] - (game.getWidth() / 2.0)));
		game.cursorGamePos[1] = (int) (playerCenter[1] + (game.cursorScreenPos[1] - (game.getHeight() / 2.0)));
	}
}
