package com.SweetSpotGames.PewPew;

public interface GameObject {
	
	public void render(RenderHandler renderer, int xZoom, int yZoom);
	
	public void update(Game game);
	
}
