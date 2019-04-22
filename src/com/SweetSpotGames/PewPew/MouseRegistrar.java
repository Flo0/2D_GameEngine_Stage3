package com.SweetSpotGames.PewPew;

import java.awt.event.MouseEvent;
import java.util.Arrays;

import lombok.Getter;

public class MouseRegistrar {
	
	public MouseRegistrar(MouseClickListener listener) {
		this.listener = listener;
		this.game = listener.getGame();
		this.registerLeftClick();
		this.registerHandler();
	}
	
	@Getter
	private final MouseClickListener listener;
	private final Game game;
	
	private void registerHandler() {
		this.listener.registerHandler(new MouseEventHandler() {

			@Override
			public void handle(MouseEvent event) {
				
			}
			
		});
	}
	
	private final void registerLeftClick() {
		this.listener.registerKeyForUse(new MappedMouseCode(1) {

			@Override
			protected void action(Game game) {
				
				int[] pos = game.getTileAtCursorPos();
				game.getMap().setTile(pos[0], pos[1], 2);
				
//				int[] cursorPos = game.cursorGamePos.clone();
//				int[] playerPos = game.getPlayer().getRepresenter().getCenter(20, 0);
//				
//				int[] cursorVec = new int[] {cursorPos[0] - playerPos[0], cursorPos[1] - playerPos[1]};
//				
//				double[] normal = new double[] {cursorVec[0], cursorVec[1]};
//				double length = Math.sqrt(Math.pow(cursorVec[0], 2) + Math.pow(cursorVec[1], 2));
//				normal[0] /= length;
//				normal[1] /= length;
//				
//				new Bullet(playerPos, normal, 10, game);
				
			}

			@Override
			protected void singleAction(Game game) {
				
//				int[] cursorPos = game.cursorGamePos.clone();
//				int[] playerPos = game.getPlayer().getRepresenter().getCenter(20, 0);
//				
//				int[] cursorVec = new int[] {cursorPos[0] - playerPos[0], cursorPos[1] - playerPos[1]};
//				
//				double[] normal = new double[] {cursorVec[0], cursorVec[1]};
//				double length = Math.sqrt(Math.pow(cursorVec[0], 2) + Math.pow(cursorVec[1], 2));
//				normal[0] /= length;
//				normal[1] /= length;
//				
//				new Bullet(playerPos, normal, 2, game);
				
				int[] pos = game.getTileAtCursorPos();
				game.getMap().setTile(pos[0], pos[1], 2);
				
			}
			
		});
	}
	
}
