package com.SweetSpotGames.PewPew;

public class Bullet implements GameObject{
	
	public Bullet(int[] origin, double[] direction, int speed, Game game) {
		this.origin = origin;
		this.direction = direction;
		this.speed = speed;
		this.currentPos = origin;
		
		game.getGameObjects().add(this);
	}
	
	private final int[] origin;
	private final double[] direction;
	private final int speed;
	private final int[] currentPos;
	private long ticks = 0;
	
	@Override
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		
		Tiles.renderGlobalTile(31, renderer, currentPos[0], currentPos[1], 1, 1);
		
	}
	
	@Override
	public void update(Game game) {
		
		if(ticks++ >= 50) {
			game.getGameObjects().remove(this);
			
		}
		
		this.currentPos[0] += this.direction[0] * speed;
		this.currentPos[1] += this.direction[1] * speed;
		
	}
	
}
