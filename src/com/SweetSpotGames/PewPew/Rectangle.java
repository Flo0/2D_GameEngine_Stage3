package com.SweetSpotGames.PewPew;

public class Rectangle {
	
	public Rectangle() {
		this(0,0,0,0);
	}
	
	public Rectangle(int x, int y, int width, int heigth) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.heigth = heigth;
	}
	
	private int[] pixels;
	public int x;
	public int y;
	public int width;
	public int heigth;
	
	public int[] getCenter(int offsetVert, int offsetHoriz) {
		return new int[] {(this.x +(this.width / 2)) + offsetHoriz, (this.y+(this.heigth / 2)) + offsetVert};
	}
	
	public void generateGraphics(int color) {
		
		this.pixels = new int[width * heigth];
		
		for(int y = 0; y < heigth; y++) {
			for(int x = 0; x < width; x++) {
				pixels[x + y * width] = color;
			}
		}
	}
	
	public void generateGraphics(int color, int borderWidth) {
		this.pixels = new int[width * heigth];
		
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = Game.ALPHA;
		}
		
		for(int y = 0; y < borderWidth; y++) {
			for(int x = 0; x < this.width; x++) {
				pixels[x + y * this.width] = color;
			}
		}
		
		for(int y = 0; y < this.heigth; y++) {
			for(int x = 0; x < borderWidth; x++) {
				pixels[x + y * this.width] = color;
			}
		}
		
		for(int y = 0; y < this.heigth; y++) {
			for(int x = this.width - borderWidth; x < this.width; x++) {
				pixels[x + y * this.width] = color;
			}
		}
		
		for(int y = this.heigth - borderWidth; y < this.heigth; y++) {
			for(int x = 0; x < this.width; x++) {
				pixels[x + y * this.width] = color;
			}
		}
		
	}
	
	public int[] getPixels() throws IllegalStateException{
		if(this.pixels == null) throw new IllegalStateException("Pixel array is not filled!");
		return this.pixels;
	}
}
