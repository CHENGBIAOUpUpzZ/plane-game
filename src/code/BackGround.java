package code;

import java.awt.Graphics;

public class BackGround {
	
	GameMain gm = null;
	int x; 
	int y; 
	int width;
	int height;
	
	int y1 = -600;
	
	//构造方法
	public BackGround(GameMain gm, int x, int y, int width, int height) {
		super();
		this.gm = gm;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}



	//创建画背景方法
	public void bgDraw(Graphics g){
		g.drawImage(gm.bgIMG, x, y, width, height, gm);
		g.drawImage(gm.bgIMG, x, y1, width, height, gm);
		
		//当两张图片都走到最下面时 就还原
		if(y1 >= 0){
			y1 = -600;
			y = 0;
		}
		
		//背景移动的速度
		y+=3;
		y1+=3;
	}
}
