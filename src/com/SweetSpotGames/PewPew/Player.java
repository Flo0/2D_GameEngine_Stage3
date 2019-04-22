package com.SweetSpotGames.PewPew;

import lombok.Getter;

public class Player implements GameObject{
	
	public Player(Game game) {
		this.representer = new Rectangle(32, 32, 12, 28);
		this.representer.generateGraphics((int) 0xFFffcb28, 3);
		this.playerCamera = game.getRenderer().getCamera();
		game.getGameObjects().add(this);
	}
	
	@Getter
	private final Rectangle representer;
	private int speed = 8;
	private final Rectangle playerCamera;
	
	public void updatePos(Direction dir) {
		switch(dir) {
		case UP: this.representer.y -= this.speed;
		break;
		case DOWN: this.representer.y += this.speed;
		break;
		case LEFT: this.representer.x -= this.speed;
		break;
		case RIGHT: this.representer.x += this.speed;
		break;
		}
	}
	
	private void updateCamera() {
		this.playerCamera.x = this.representer.x - (this.playerCamera.width / 2);
		this.playerCamera.y = this.representer.y - (this.playerCamera.heigth / 2);
	}
	
	@Override
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		renderer.renderRectangle(this.representer, xZoom, yZoom);
	}

	@Override
	public void update(Game game) {
		game.getMouseListener().keyMap.values().forEach(key->key.whilePressed(game));
		game.getKeyListener().keyMap.values().forEach(key->key.whilePressed(game));
		this.updateCamera();
	}
	
}
