package code;

import java.awt.Graphics;

public class BossPlane {
	int x;
	int y;
	int width;
	int height;
	GameMain gm = null;
	boolean isLive;
	int fx = 0;

	public BossPlane(int x, int y, int width, int height, GameMain gm, boolean isLive) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.gm = gm;
		this.isLive = isLive;
	}


	public void BossDraw(Graphics g){
		g.drawImage(gm.bossPlaneIMG, x, y, width, height, gm);
		
		if(fx == 0){
			x-=3;
			if(x <= 0){
				fx = 1;
			}
		}else if(fx == 1){
			x+=3;
			if(x > 400){
				fx = 0;
			}
		}
	}
}
