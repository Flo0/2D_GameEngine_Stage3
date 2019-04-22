package com.SweetSpotGames.PewPew;

import java.awt.image.BufferedImage;

import lombok.Getter;

public class Sprite {
	
	public Sprite(SpriteSheet sheet, int startX, int startY, int width, int heigth) {
		this.width = width;
		this.heigth = heigth;
		
		this.pixels = new int[width * heigth];
		sheet.getImage().getRGB(startX, startY, width, heigth, this.pixels, 0, width);
	}
	
	public Sprite(BufferedImage image) {
		this.width = image.getWidth();
		this.heigth = image.getHeight();
		
		this.pixels = new int[this.width * this.heigth];
		image.getRGB(0, 0, this.width, this.heigth, this.pixels, 0, this.width);
	}
	
	public Sprite(String name) {
		BufferedImage image = Game.loadImage(name);
		this.width = image.getWidth();
		this.heigth = image.getHeight();
		
		this.pixels = new int[this.width * this.heigth];
		image.getRGB(0, 0, this.width, this.heigth, this.pixels, 0, this.width);
	}
	
	@Getter
	private final int width;
	@Getter
	private final int heigth;
	@Getter
	private int[] pixels;
	
}