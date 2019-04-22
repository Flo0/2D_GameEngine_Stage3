package com.SweetSpotGames.PewPew;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;


public class GameMap {
	
	public GameMap(String mapName) {
		
		this.TILE_POSITIONS = HashBasedTable.create();
		this.mapFile = new File(Game.class.getResource("/com/SweetSpotGames/PewPew/maps/" + mapName + ".txt").getFile());
		
		try {
			Scanner scanner = new Scanner(mapFile);
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(line.startsWith("//")) continue;
				if(line.contains("Fill:")) {
					this.fillID = Integer.parseInt(line.split(":")[1]);
					continue;
				}
				
				String[] split = line.split(",");
				MappedTile mapped = new MappedTile(
						Integer.parseInt(split[0]), 
						Integer.parseInt(split[1]), 
						Integer.parseInt(split[2]));
				this.setMappedTile(mapped, mapped.x, mapped.y);
				
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private final Table<Integer, Integer, MappedTile> TILE_POSITIONS;
	private final File mapFile;
	private int fillID = -1;
	
	public void setTile(int x, int y, int ID) {
		MappedTile tile = this.getMappedTileof(x, y);
		if(tile == null) {
			this.setMappedTile(new MappedTile(ID,x,y), x, y);
		}else {
			tile.ID = ID;
		}
	}
	
	public void saveMap() {
		
		try {
			
			File test = new File("/C:/Saros/TestMap.txt");
			
			PrintWriter printer = new PrintWriter(test);
			
			if(fillID >= 0) {
				printer.println("Fill:" + fillID);
			}
			
			printer.close();
			
			mapFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setMappedTile(MappedTile mapped, int x, int y) {
		this.TILE_POSITIONS.put(x, y, mapped);
	}
	
	public MappedTile getMappedTileof(int xPos, int yPos) {
		return this.TILE_POSITIONS.get(xPos, yPos);
	}
	
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		
		int tileWidth = Game.TILE_SIZE * xZoom;
		int tileHeigth = Game.TILE_SIZE * yZoom;
		
		Rectangle camera = renderer.getCamera();
		
		//Render background
		if(this.fillID >= 0) {
			for(int y = camera.y - tileHeigth - (camera.y % tileHeigth); y < camera.y + camera.heigth ; y += tileHeigth) {
				for(int x = camera.x - tileWidth - (camera.x % (tileWidth)); x < camera.x + camera.width ; x += tileWidth) {
					Tiles.renderGlobalTile(this.fillID, renderer, x, y, xZoom, yZoom);
				}
			}
		}
		
		//Render mappedTiles
		this.TILE_POSITIONS.rowMap().values().forEach(map->map.values().forEach(mapped->{
			Tiles.renderGlobalTile(mapped.ID, renderer, mapped.x * tileWidth, mapped.y * tileHeigth, xZoom, yZoom);
		}));
		
	}
	
	private class MappedTile{
		
		private MappedTile(int ID, int x, int y) {
			this.ID = ID;
			this.x = x;
			this.y = y;
		}
		
		public int ID;
		public final int x, y;
		
	}
}
