package code;

import java.awt.Graphics;

public class Bomb {
	int x;
	int y;
	int width;
	int height;
	GameMain gm = null;
	
	
	public Bomb(int x, int y, int width, int height, GameMain gm) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.gm = gm;
	}
	
	public void BombDraw(Graphics g){
		g.drawImage(gm.bombIMG, x, y, width, height, gm);
	}
}
