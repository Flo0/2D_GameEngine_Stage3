package com.SweetSpotGames.PewPew;

import java.awt.Color;
import java.awt.Font;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import lombok.Getter;
import lombok.Setter;

public class DebugConsole extends Rectangle{
	
	public DebugConsole(int width, int lineCount, int x, int y, RenderHandler renderer) {
		super(x, y, width, (lineCount + 2) * 8);
		super.generateGraphics(0xFF555555);
		this.lineCount = lineCount;
		this.board = new CircularFifoQueue<String>(lineCount);
		this.renderer = renderer;
		
		for(int line = 0; line < lineCount; line++) {
			this.board.add("- Line " + line);
		}
	}
	
	private final RenderHandler renderer;
	private final CircularFifoQueue<String> board;
	private final int lineCount;
	@Getter @Setter
	private Color color = Color.WHITE;
	@Getter @Setter
	private Font font = new Font("Boldius", Font.BOLD, 10);
	
	public void pushLine(String line) {
		this.board.add(line);
	}
	
	public void printBoard() {
		this.renderer.renderStaticArray(super.getPixels(), super.x, super.y, super.width, super.heigth, 1, 1);
		int x = super.x;
		int yAdd = this.font.getSize();
		int y = super.y;
		
		for(int line = this.lineCount - 1; line >= 0; line--) {
			y += yAdd;
			this.printLine(x, y, line);
		}
	}
	
	private void printLine(int x, int y, int line) {
		this.renderer.drawString(this.board.get(line), this.color, this.font, x, y);
	}
}
