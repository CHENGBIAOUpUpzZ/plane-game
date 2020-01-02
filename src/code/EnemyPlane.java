package code;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class EnemyPlane {
	
	int x;
	int y;
	int width;
	int height;
	GameMain gm = null;
	boolean isLive;
	
	
	public EnemyPlane(int x, int y, int width, int height, GameMain gm,
			boolean isLive) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.gm = gm;
		this.isLive = isLive;
	}


	int time = 0;
	
	public void EnemyPlaneDraw(Graphics g){
		
		if(!isLive){
			return;
		}
		g.drawImage(gm.enemyPlaneIMG, x, y, width, height, gm);
		y+=4; //子弹速度
		
		//添加敌方子弹
		time++;
		if(time % 30 == 0){
			EnemyBullet enemyBullet = new EnemyBullet(x+18, y+25, 10, 10, gm, true);
			gm.enemyBulletList.add(enemyBullet);
			time = 0;
		}
		
	}
	
	//拿敌方飞机的矩形框
	public Rectangle getRec(){
		return new Rectangle(x,y,width,height);
	}
}
