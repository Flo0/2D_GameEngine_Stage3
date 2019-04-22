package com.SweetSpotGames.PewPew;

public abstract class MappedMouseCode {
	
	public MappedMouseCode(int mouseCode) {
		this.currentlyPressed = false;
		this.mouseCode = mouseCode;
		this.firstPress = true;
	}
	
	public final int mouseCode;
	public boolean currentlyPressed;
	public boolean firstPress;
	
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
