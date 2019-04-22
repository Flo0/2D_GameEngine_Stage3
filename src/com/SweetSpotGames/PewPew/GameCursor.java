package com.SweetSpotGames.PewPew;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.Setter;

public class GameCursor implements OverlayObject{
	
	public static int MAIN_CURSOR = 1;
	public static int EXACT = 999;
	
	public GameCursor(Game game) {
		this.game = game;
		this.cursorZoom = 3;
		this.cursors = Maps.newHashMap();
		
		this.cursors.put(1, this.loadImage("Cursor_One_Alpha.png"));
		this.cursors.put(999, this.loadImage("Cursor_Exact.png"));
		
		this.setCursor(EXACT);
		
		game.getOverlayObjects().add(this);
	}
	
	private int cursorWidth;
	private int cursorHeigth;
	private final Game game;
	private BufferedImage currentCursor;
	private final Map<Integer, BufferedImage> cursors;
	@Getter @Setter
	private int cursorZoom;
	
	@Override
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		
		renderer.renderStaticImage(this.currentCursor, game.cursorScreenPos[0] - ((this.cursorWidth * this.cursorZoom) / 2), game.cursorScreenPos[1] - ((this.cursorHeigth  * this.cursorZoom) / 2), this.cursorZoom, this.cursorZoom);
		
	}
	
	public void setCursor(int cursorID) {
		this.currentCursor = this.cursors.get(cursorID);
		this.cursorWidth = this.currentCursor.getWidth();
		this.cursorHeigth = this.currentCursor.getHeight();
	}
	
	public int[] getPosition() {
		return this.game.cursorScreenPos;
	}
	
	private BufferedImage loadImage(String fileName) {
		try {
			BufferedImage image = ImageIO.read(this.getClass().getResource("/com/SweetSpotGames/PewPew/sprites/cursors/" + fileName));
			BufferedImage formated = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			
			formated.getGraphics().drawImage(image, 0, 0, null);
			
			return formated;
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
