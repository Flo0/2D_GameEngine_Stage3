package com.SweetSpotGames.PewPew;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.Getter;

public class Tiles {
	
	private static Tiles TILE_LOADER = new Tiles();
	
	private static final Map<Integer, Tile> ALL_TILES = Maps.newHashMap();
	public static Tile ofID(int ID) {
		return ALL_TILES.get(ID);
	}
	
	public static int loadTile(String imageName, String gameName) {
		int id = ALL_TILES.size();
		ALL_TILES.put(id, TILE_LOADER.new Tile(gameName, new Sprite(imageName), id));
		return id;
	}
	
	public Tiles() {
		
	}
	
	public Tiles(File tilesFile, SpriteSheet spriteSheet) {
		this.spriteSheet = spriteSheet;
		
		int size = ALL_TILES.size();
		int currentID = ALL_TILES.size();
		
		try {
			Scanner textScanner = new Scanner(tilesFile);
			while(textScanner.hasNextLine()) {
				String line = textScanner.nextLine();
				if(!line.startsWith("//") && line.contains("#")) {
					String[] split = line.split("#");
					Tile tile = new Tile(split[1], spriteSheet.getSprite(Integer.parseInt(split[2]), Integer.parseInt(split[3])), Integer.parseInt(split[0]));
					this.tileList.add(tile);
					this.tileMap.put(tile.tileName, tile);
					ALL_TILES.put(currentID++, tile);
				}
			}
			textScanner.close();
			System.out.println("Loaded " + (ALL_TILES.size() - size) + " Tiles from " + spriteSheet.getName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	@Getter
	private SpriteSheet spriteSheet;
	private ArrayList<Tile> tileList = Lists.newArrayList();
	private Map<String, Tile> tileMap = Maps.newHashMap();
	
	public static void renderGlobalTile(int tileID, RenderHandler renderer, int xPos, int yPos, int xZoom, int yZoom) {
		if(!ALL_TILES.containsKey(tileID)) { System.out.println("TileID is too big!"); return;}
		renderer.renderSprite(ALL_TILES.get(tileID).sprite, xPos, yPos, xZoom, yZoom);
	}
	
	public void renderTile(int tileID, RenderHandler renderer, int xPos, int yPos, int xZoom, int yZoom) {
		if(tileID >= this.tileList.size()) { System.out.println("TileID is too big!"); return;}
		renderer.renderSprite(this.tileList.get(tileID).sprite, xPos, yPos, xZoom, yZoom);
	}
	
	public void renderTile(String tileName, RenderHandler renderer, int xPos, int yPos, int xZoom, int yZoom) {
		if(!this.tileMap.containsKey(tileName)) { System.out.println("TileName is not present!"); return;}
		renderer.renderSprite(this.tileMap.get(tileName).sprite, xPos, yPos, xZoom, yZoom);
	}
	
	private class Tile{
		
		public final String tileName;
		public final Sprite sprite;
		@Getter
		public final int ID;
		
		private Tile(String name, Sprite sprite, int id) {
			this.tileName = name;
			this.sprite = sprite;
			this.ID = id;
			if(ALL_TILES.containsKey(id)) System.out.println("Tile-ID is invalid!");
		}
	}
	
}
