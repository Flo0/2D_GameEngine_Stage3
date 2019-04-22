package com.SweetSpotGames.PewPew;

import java.awt.image.BufferedImage;

import lombok.Getter;

public class SpriteSheet {
	
	public SpriteSheet(BufferedImage image, int spriteX, int spriteY, int paddingSize, String name) {
		this.SIZE_X = image.getWidth();
		this.SIZE_Y = image.getHeight();
		this.image = image;
		
		this.spriteSize_X = spriteX;
		this.spriteSize_Y = spriteY;
		
		this.PADDING = paddingSize;
		
		this.pixels = new int[SIZE_X * SIZE_Y];
		this.pixels = this.image.getRGB(0, 0, SIZE_X, SIZE_Y, pixels, 0, SIZE_X);
		
		this.name = name;
		
		this.loadSprites();
	}
	
	@Getter
	private final String name;
	
	private final int PADDING;
	private boolean spritesLoaded = false;
	private Sprite[] loadedSprites = null;
	@Getter
	private int[] pixels;
	@Getter
	private BufferedImage image;
	
	@Getter
	private final int spriteSize_X;
	@Getter
	private final int spriteSize_Y;
	
	private final int SIZE_X;
	private final int SIZE_Y;
	
	private void loadSprites() {
		this.loadedSprites = new Sprite[(SIZE_X / this.spriteSize_X) * (SIZE_Y / this.spriteSize_Y)];
		
		int spriteID = 0;
		
		for(int y = 0; y < this.SIZE_Y; y += (this.spriteSize_Y + this.PADDING)) {
			for(int x = 0; x < this.SIZE_X; x += (this.spriteSize_X + this.PADDING)) {
				this.loadedSprites[spriteID] = new Sprite(this, x, y, this.spriteSize_X, this.spriteSize_Y);
				spriteID++;
			}
		}
		this.spritesLoaded = true;
	}
	
	public Sprite getSprite(int x, int y) {
		if(this.spritesLoaded) {
			int spriteID = x + y * (SIZE_X / spriteSize_X);
			
			if(spriteID < this.loadedSprites.length) {
				return this.loadedSprites[spriteID];
			}else {
				System.out.println("SpriteID " + spriteID + " is out of bounds...");
			}
			
			
		}else {
			System.out.println("SpriteSheet does not have loaded Sprites...");
		}
		return null;
	}
}
