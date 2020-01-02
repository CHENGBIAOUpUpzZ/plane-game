package code;

import java.awt.Graphics;
import java.awt.Rectangle;

public class EnemyBullet {
	
	int x;
	int y;
	int width;
	int height;
	GameMain gm = null;
	boolean isLive;
	
	
	public EnemyBullet(int x, int y, int width, int height, GameMain gm,
			boolean isLive) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.gm = gm;
		this.isLive = isLive;
	}
	
	public void EnemyBulletDraw(Graphics g){
		if(!isLive){
			return;
		}
		g.drawImage(gm.enemyBulletIMG, x, y, width, height, gm);
		y+=6;
	}
	
	public Rectangle getRec(){
		return new Rectangle(x, y, width, height);
	}
}
