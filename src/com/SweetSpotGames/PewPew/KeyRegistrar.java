package com.SweetSpotGames.PewPew;

import java.awt.event.KeyEvent;

import lombok.Getter;

public class KeyRegistrar {
	
	public KeyRegistrar(KeyBoardListener listener) {
		this.listener = listener;
		this.registerEsc();
		this.registerUP();
		this.registerDOWN();
		this.registerLEFT();
		this.registerRIGHT();
		this.registerCTRL();
	}
	
	@Getter
	private final KeyBoardListener listener;
	
	private final void registerCTRL() {
		this.listener.registerKeyForUse(new MappedKeyCode(KeyEvent.VK_CONTROL) {

			@Override
			protected void action(Game game) {
				
				
			}

			@Override
			public void singleAction(Game game) {
				
				
			}
			
		});
	}
	
	private final void registerEsc() {
		this.listener.registerKeyForUse(new MappedKeyCode(KeyEvent.VK_ESCAPE) {

			@Override
			protected void action(Game game) {
				
				game.shutdown();
				
			}

			@Override
			public void singleAction(Game game) {
				
				
			}
			
		});
	}
	
	private final void registerUP() {
		this.listener.registerKeyForUse(new MappedKeyCode(KeyEvent.VK_W) {

			@Override
			protected void action(Game game) {
				
				game.getPlayer().updatePos(Direction.UP);
				
				
			}

			@Override
			public void singleAction(Game game) {
				
				
			}
			
		});
	}
	
	private final void registerDOWN() {
		this.listener.registerKeyForUse(new MappedKeyCode(KeyEvent.VK_S) {

			@Override
			protected void action(Game game) {
				
				game.getPlayer().updatePos(Direction.DOWN);
				
			}

			@Override
			public void singleAction(Game game) {
				
				if(listener.getPressedKeys().contains(KeyEvent.VK_CONTROL)) {
					game.getMap().saveMap();
				}
				
			}
			
		});
	}
	
	private final void registerLEFT() {
		this.listener.registerKeyForUse(new MappedKeyCode(KeyEvent.VK_A) {

			@Override
			protected void action(Game game) {
				
				game.getPlayer().updatePos(Direction.LEFT);
				
			}

			@Override
			public void singleAction(Game game) {
				
				
			}
			
		});
	}
	
	private final void registerRIGHT() {
		this.listener.registerKeyForUse(new MappedKeyCode(KeyEvent.VK_D) {

			@Override
			protected void action(Game game) {
				
				game.getPlayer().updatePos(Direction.RIGHT);
				
			}

			@Override
			public void singleAction(Game game) {
				
				
			}
			
		});
	}
	
}
