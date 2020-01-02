package code;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class MyBullet {
	
	int x;
	int y;
	int width;
	int height;
	GameMain gm = null;
	boolean isLive;
	
	
	
	public MyBullet(int x, int y, int width, int height, GameMain gm,
			boolean isLive) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.gm = gm;
		this.isLive = isLive;
	}
	
	//double d = 0.2
	public void MyBulletDraw(Graphics g){
		if(!isLive){
			return;
		}
		g.drawImage(gm.myBulletIMG, x, y, width, height, gm);
		
		//子弹速度
		y-=3;
		
		//y-=3*Math.cos(d);
		//x+=10*Math.sin(d);
		//d+=0.2
	}
	
	//拿我方子弹矩形框
	public Rectangle getRec(){
		return new Rectangle(x,y,width,height);
	}
	
	//判断我方子弹是否与敌方飞机集合相交
	public void isXJ(ArrayList<EnemyPlane> enemyPlane){
		
		for (int i = 0; i < enemyPlane.size(); i++) {
			EnemyPlane ep = enemyPlane.get(i);
			if(ep.isLive && this.isLive && ep.getRec().intersects(this.getRec())){
				
				ep.isLive = false;
				this.isLive = false;
				gm.score+=10;
				
				
				//实例化爆炸对象
				gm.bomb = new Bomb(x, y, 70, 70, gm);
				
				GameSound gs = new GameSound();
				gs.playSound("music/Explode.mp3");
			}
		}
	}
	
}
