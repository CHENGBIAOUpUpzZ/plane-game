package code;

import java.awt.Graphics;

public class BackGround {
	
	GameMain gm = null;
	int x; 
	int y; 
	int width;
	int height;
	
	int y1 = -600;
	
	//���췽��
	public BackGround(GameMain gm, int x, int y, int width, int height) {
		super();
		this.gm = gm;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}



	//��������������
	public void bgDraw(Graphics g){
		g.drawImage(gm.bgIMG, x, y, width, height, gm);
		g.drawImage(gm.bgIMG, x, y1, width, height, gm);
		
		//������ͼƬ���ߵ�������ʱ �ͻ�ԭ
		if(y1 >= 0){
			y1 = -600;
			y = 0;
		}
		
		//�����ƶ����ٶ�
		y+=3;
		y1+=3;
	}
}
