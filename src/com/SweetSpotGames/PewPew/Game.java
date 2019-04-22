package com.SweetSpotGames.PewPew;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.google.common.collect.Sets;

import lombok.Getter;

public class Game extends JFrame implements Runnable
{
	public static final int ALPHA = (int) 0xFFFF00DC;
	public static final int GAME_TPS = 60;
	public static final int TILE_SIZE = 16;
	
	private static final long serialVersionUID = 56084204084315109L;
	
	private final Canvas canvas = new Canvas();
	
	@Getter
	private final DebugConsole console;
	@Getter
	private RenderHandler renderer;
	@Getter
	private final Tiles grassTiles;
	@Getter
	private final Tiles tutorialTiles;
	@Getter
	private GameMap map;
	@Getter
	private final Set<GameObject> gameObjects;
	@Getter
	private final Set<OverlayObject> overlayObjects;
	@Getter
	private final KeyBoardListener keyListener;
	@Getter
	private final MouseClickListener mouseListener;
	@Getter
	private final GameCursor gameCursor;
	
	@Getter
	private final Player player;
	
	public Game(int windowWidth, int windowHeight, boolean fullscreen)
	{
		
		if(fullscreen) {
			super.setExtendedState(JFrame.MAXIMIZED_BOTH); 
			super.setUndecorated(true);
		}else {
			super.setBounds(0, 0, windowWidth, windowHeight);
		}
		//Make our program shutdown when we exit out.
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Set the position and size of our frame.
		
		//Put our frame in the center of the screen.
		super.setLocationRelativeTo(null);
		
		//Add our graphics compoent
		super.add(this.canvas);
		
		//Make our frame visible.
		super.setVisible(true);
		
		super.setResizable(false);
		
		// Set the blank cursor to the JFrame.
		super.getContentPane().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "blank cursor"));
		
		this.gamePath = this.getClass().getProtectionDomain().getCodeSource().getLocation();
		
		this.cursorScreenPos = new int[] {0,0};
		this.cursorGamePos = new int[] {0,0};
		
		//Create our object for buffer strategy.
		this.canvas.createBufferStrategy(3);
		
		this.keyListener = new KeyRegistrar(new KeyBoardListener()).getListener();
		this.mouseListener = new MouseRegistrar(new MouseClickListener(this)).getListener();
		
		this.grassTiles = this.loadTiles("Flo_Grass_Tiles");
		this.tutorialTiles = this.loadTiles("Tutorial_Tiles");
		
		this.renderer = new RenderHandler(getWidth(), getHeight(), this);
		
		//this.gameObjects = Sets.newHashSet();
		this.gameObjects = Sets.newConcurrentHashSet();
		this.overlayObjects = Sets.newHashSet();
		
		this.player = new Player(this);
		this.gameCursor = new GameCursor(this);
		
		this.canvas.addKeyListener(this.keyListener);
		this.canvas.addMouseListener(this.mouseListener);
		this.canvas.addMouseWheelListener(this.mouseListener);
		this.canvas.addMouseMotionListener(this.mouseListener);
		
		this.canvas.requestFocus();
		
		this.map = new GameMap("TutorialMap");
		
		Tiles.loadTile("Bullet.png", "Bullet");
		
		this.console = new DebugConsole(256, 6, 0, 48, this.renderer);
	}
	
	public void shutdown() {
		super.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	private Tiles loadTiles(String tileName) {
		File file = new File(this.getClass().getResource("/com/SweetSpotGames/PewPew/sprites/" + tileName + ".txt").getFile());
		Scanner scanner;
		int xSize = 16;
		int ySize = 16;
		int padding = 0;
		try {
			scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(line.startsWith("<")) {
					line = line.substring(1);
					if(line.contains("X")) {
						xSize = Integer.parseInt(line.split(">")[1]);
					}else if(line.contains("Y")) {
						ySize = Integer.parseInt(line.split(">")[1]);
					}else if(line.startsWith("Padding")) {
						padding = Integer.parseInt(line.split(">")[1]);
					}
					
				}
			}
			scanner.close();
			SpriteSheet sheet = new SpriteSheet(loadImage(tileName + ".png"), xSize, ySize, padding, tileName);
			return new Tiles(file, sheet);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static BufferedImage loadImage(String fileName) {
		try {
			BufferedImage image = ImageIO.read(Game.class.getResource("/com/SweetSpotGames/PewPew/sprites/" + fileName));
			BufferedImage formated = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			
			formated.getGraphics().drawImage(image, 0, 0, null);
			
			return formated;
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public void update() {
		
		for(GameObject object : this.gameObjects) {
			object.update(this);
		}
		
		this.ticks++;
		if(this.ticks % GAME_TPS == 0) {
			this.everySecondUpdate();
		}
	}
	
	int seconds = 0;
	
	public void everySecondUpdate() {
		this.fps = this.framecount;
		this.framecount = 0;
		seconds++;
		
		this.console.pushLine("Running for " + this.seconds + " seconds");
	}
	
	public void zoomIn() {
		if(this.currentZoom < this.MAX_ZOOM) {
			this.currentZoom++;
		}
	}
	
	public void zoomOut() {
		if(this.currentZoom > this.MIN_ZOOM) {
			this.currentZoom--;
		}
	}
	
	private int framecount = 0;
	@Getter
	private double fps = 0D;
	private long ticks = 0L;
	public int currentZoom = 3;
	private final int MAX_ZOOM = 10;
	private final int MIN_ZOOM = 1;
	public final int[] cursorScreenPos;
	public final int[] cursorGamePos;
	@Getter
	private final URL gamePath;
	
	public void render() {
			
			BufferStrategy bufferStrategy = canvas.getBufferStrategy();
			Graphics graphics = bufferStrategy.getDrawGraphics();
			super.paint(graphics);
			
			this.renderer.clearScreen();
			
			this.map.render(this.renderer, this.currentZoom, this.currentZoom);
			
			for(GameObject object : this.gameObjects) {
				object.render(this.renderer, this.currentZoom, this.currentZoom);
			}
			
			this.overlayObjects.forEach(overlay -> overlay.render(this.renderer, this.currentZoom, this.currentZoom));
			
			this.renderer.drawFPS(this.fps);
			
			this.console.printBoard();
			
			this.renderer.render(graphics);
			this.framecount++;
			
			graphics.dispose();
			bufferStrategy.show();
	}
	
	public int[] getTileAtPixel(int x, int y) {
		return new int[] {(int)Math.floor((x + renderer.getCamera().x) / ((double)TILE_SIZE * this.currentZoom)), (int)Math.floor((y + renderer.getCamera().y) / ((double)TILE_SIZE * this.currentZoom))};
	}
	
	public int[] getTileAtCursorPos() {
		return this.getTileAtPixel(this.cursorScreenPos[0], this.cursorScreenPos[1]);
	}
	
	@Override
	public void run() {
		
		long lastTime = System.nanoTime();
		double nanoSecondConversion = 1000000000.0 / GAME_TPS;
		double changeInSeconds = 0;
		
		while(true) {
			long now = System.nanoTime();
			changeInSeconds += (now - lastTime) / nanoSecondConversion;
			while(changeInSeconds >= 1) {
				update();
				changeInSeconds--;
			}

			this.render();
			lastTime = now;
		}

	}
	
	public static void main(String[] args)
	{
		Game game = new Game(1024, 768, false);
		Thread gameThread = new Thread(game);
		gameThread.start();
	}

}