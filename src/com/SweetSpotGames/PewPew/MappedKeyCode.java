package com.SweetSpotGames.PewPew;

public abstract class MappedKeyCode {
	
	public MappedKeyCode(int keyCode) {
		this.currentlyPressed = false;
		this.keyCode = keyCode;
		this.firstPress = true;
	}
	
	public final int keyCode;
	public boolean firstPress;
	public boolean currentlyPressed;
	
	public void whilePressed(Game game) {
		if(this.currentlyPressed) {
			if(this.firstPress) {
				this.singleAction(game);
				this.firstPress = false;
			}
			this.action(game);
		}else if(!this.firstPress) {
			this.firstPress = true;
		}
	}
	
	protected abstract void action(Game game);
	protected abstract void singleAction(Game game);
}
