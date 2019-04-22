package com.SweetSpotGames.PewPew;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import lombok.Getter;


public class RenderHandler 
{
	private BufferedImage view;
	private final int[] pixels;
	
	@Getter
	private Rectangle camera;
	
	private final int width;
	//private final int height;
	
	//private final Game game;
	
	public RenderHandler(int width, int height, Game game) 
	{
		//this.game = game;
		//Create a BufferedImage that will represent our view.
		this.view = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		this.width = this.view.getWidth();
		//this.height = this.view.getHeight();
		
		this.camera = new Rectangle(0, 0, width, height);
		
		this.camera.x = 0;
		this.camera.y = 0;
		
		//Create an array for pixels
		this.pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
		
		
	}
	
	public void render(Graphics graphics)
	{
		graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
 	}
	
	
	
	public void renderSprite(Sprite sprite, int xPos, int yPos, int xZoom, int yZoom) {
		this.renderArray(sprite.getPixels(), xPos, yPos, sprite.getWidth(), sprite.getHeigth(), xZoom, yZoom);
	}
	
	public void renderRectangle(Rectangle rect, int xZoom, int yZoom) {
		int[] rectPixels = rect.getPixels();
		this.renderArray(rectPixels, rect.x, rect.y, rect.width, rect.heigth, xZoom, yZoom);
	}
	
	public void renderArray(int[] renderPixels, int xPos, int yPos, int renderWidth, int renderHeight, int xZoom, int yZoom) {
		
		for(int imageY = 0; imageY < renderHeight; imageY++) {
			
			for(int imageX = 0; imageX < renderWidth; imageX++) {
				
				for(int yZoomPosition = 0; yZoomPosition < yZoom; yZoomPosition++) {
					
					for(int xZoomPosition = 0; xZoomPosition < yZoom; xZoomPosition++) {
						
						if(!this.setGlobalPixel(renderPixels[imageX + imageY * renderWidth], ((imageX * xZoom) + xPos + xZoomPosition), ((imageY * yZoom) + yPos + yZoomPosition))) {
							return;
						}
						
					}
					
				}
				
			}
			
		}
	}
	
	public void renderStaticArray(int[] renderPixels, int xPos, int yPos, int renderWidth, int renderHeight, int xZoom, int yZoom) {
		
		for(int imageY = 0; imageY < renderHeight; imageY++) {
			
			for(int imageX = 0; imageX < renderWidth; imageX++) {
				
				for(int yZoomPosition = 0; yZoomPosition < yZoom; yZoomPosition++) {
					
					for(int xZoomPosition = 0; xZoomPosition < yZoom; xZoomPosition++) {
						
						if(!this.setScreenPixel(renderPixels[imageX + imageY * renderWidth], ((imageX * xZoom) + xPos + xZoomPosition), ((imageY * yZoom) + yPos + yZoomPosition))) {
							return;
						}
						
					}
					
				}
				
			}
			
		}
	}
	
	public boolean setGlobalPixel(int color, int x, int y) {
		if(x < this.camera.x || y < camera.y || x >= (this.camera.width + this.camera.x) || y >= (this.camera.heigth + camera.y)) return true;
		
		int pixelIndex = (x - this.camera.x) + (y - this.camera.y) * this.width;
		
		if(this.pixels.length > pixelIndex) {
			if(color == Game.ALPHA) return true;
			this.pixels[pixelIndex] = color;
			return true;
		}
		return true;
		
	}
	
	public boolean setScreenPixel(int color, int x, int y) {
		
		int pixelIndex = x + y * this.width;
		if(this.pixels.length > pixelIndex && pixelIndex >= 0) {
			if(color == Game.ALPHA) return true;
			this.pixels[pixelIndex] = color;
			return true;
		}
		return true;
		
	}
	
	public void setImagePixel(BufferedImage image, int color, int x, int y) {
		int[] imagePixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
		imagePixels[x + y * image.getWidth()] = color;
	}
	
	public void drawFPS(double fps) {
		Graphics g = this.view.getGraphics();
		g.setColor(Color.DARK_GRAY);
		g.fill3DRect(0, 0, 80, 22, true);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Bold", 1, 12));
		g.drawString(fps + " FPS", 8, 16);
	}
	
	public void drawString(String string, Color color, Font font, int x, int y) {
		Graphics g = this.view.getGraphics();
		g.setColor(color);
		g.setFont(font);
		g.drawString(string, x, y);
	}
	
	public void renderImage(BufferedImage image, int xPos, int yPos, int xZoom, int yZoom) {
		
		int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		this.renderArray(imagePixels, xPos, yPos, image.getWidth(), image.getHeight(), xZoom, yZoom);
		
	}
	
	public void renderStaticImage(BufferedImage image, int xPos, int yPos, int xZoom, int yZoom) {
		
		int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		this.renderStaticArray(imagePixels, xPos, yPos, image.getWidth(), image.getHeight(), xZoom, yZoom);
		
	}
	
	public void clearScreen() {
		for(int i = 0; i < pixels.length; i++) {
			this.pixels[i] = 0;
		}
	}
	
}