package code;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class MyPlane {
	
	int x;
	int y;
	int width;
	int height;
	GameMain gm = null;
	boolean isLive;  //生命状态
	boolean U,D,L,R;
	
	
	public MyPlane(int x, int y, int width, int height, GameMain gm,
			boolean isLive) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.gm = gm;
		this.isLive = isLive;
	}
	
	
	public void MyPlaneDraw(Graphics g){
		if(!isLive){
			return;
		}
		g.drawImage(gm.myPlaneIMG, x, y, width, height, gm);
		
		if(U){
			if(y >= 50){
				y-=10;
			}
		}
		if(D){
			if(y <= 550){
				y+=10;
			}
		}
		if(L){
			if(x >= 0){
				x-=10;
			}
		}
		if(R){
			if(x <= 450){
				x+=10;
			}
		}
	}
	
	//飞机移动方法
	public void MyPlaneMove(KeyEvent e){
		int code = e.getKeyCode();  //拿到键盘按下的键
		if(code == KeyEvent.VK_UP){
			U = true;
		}else if(code == KeyEvent.VK_DOWN){
			D = true;
		}else if(code == KeyEvent.VK_LEFT){
			L = true;
		}else if(code == KeyEvent.VK_RIGHT){
			R = true;
		}
	}
	
	//飞机停止方法
	public void MyPlaneStop(KeyEvent e){
		int code = e.getKeyCode();  //拿到键盘按下的键
		if(code == KeyEvent.VK_UP){
			U = false;
		}else if(code == KeyEvent.VK_DOWN){
			D = false;
		}else if(code == KeyEvent.VK_LEFT){
			L = false;
		}else if(code == KeyEvent.VK_RIGHT){
			R = false;
		}
	}
	
	public Rectangle getRec(){
		return new Rectangle(x, y, width, height);
	}
	
	public void isXJ(ArrayList<EnemyBullet> enemyBullet){
		
		for (int i = 0; i < enemyBullet.size(); i++) {
			EnemyBullet eb = enemyBullet.get(i);
			if(eb.isLive && this.isLive && eb.getRec().intersects(this.getRec())){
				gm.blood-=100;
				eb.isLive = false;
				if(gm.blood == 0){
					this.isLive = false;
					JOptionPane.showMessageDialog(null, "你已经死亡","很遗憾",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}
	
}
