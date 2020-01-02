package code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

public class GameMain extends Frame {

	Toolkit tool = Toolkit.getDefaultToolkit(); // 实例化一个工具包对象
	// 拿到背景图片
	Image bgIMG = tool.getImage(GameMain.class.getResource("/images/bg05.jpg"));
	// 拿到我方飞机图片
	Image myPlaneIMG = tool.getImage(GameMain.class
			.getResource("/images/player05.png"));
	// 拿到Boss飞机图片
	Image bossPlaneIMG = tool.getImage(GameMain.class
			.getResource("/images/boss_3.png"));
	// 拿到我方子弹图片
	Image myBulletIMG = tool.getImage(GameMain.class
			.getResource("/images/bul01.png"));
	// 拿到敌方飞机图片
	Image enemyPlaneIMG = tool.getImage(GameMain.class
			.getResource("/images/enemy01.png"));
	// 拿到敌方飞机子弹图片
	Image enemyBulletIMG = tool.getImage(GameMain.class
			.getResource("/images/en_bul01.png"));
	// 拿到爆炸特效图片
	Image bombIMG = tool.getImage(GameMain.class
			.getResource("/images/bomb_enemy_2.png"));
	// 实例化一个背景对象
	BackGround bgOBJ = new BackGround(this, 0, 0, 500, 600);
	// 实例化一个我方飞机对象
	MyPlane myPlaneOBJ = new MyPlane(225, 500, 50, 50, this, true);
	// 实例化一个boss飞机对象
	BossPlane bossPlaneOBJ = new BossPlane(200, 100, 100, 100, this, true);
	// 实例化一个爆炸特效对象
	Bomb bomb = null;
	// 实例化一个我方子弹集合对象
	ArrayList<MyBullet> myBulletList = new ArrayList<MyBullet>();
	// 实例化一个敌方飞机集合对象
	ArrayList<EnemyPlane> enemyPlaneList = new ArrayList<EnemyPlane>();
	// 实例化一个敌方飞机子弹集合对象
	ArrayList<EnemyBullet> enemyBulletList = new ArrayList<EnemyBullet>();

	/**
	 * @param args
	 */
	// 初始化游戏
	public void init() {
		GameSound gs = new GameSound();
		gs.playBgSound("music/BGM_0001.mp3");
		this.setTitle("战机游戏1.0");
		this.setSize(500, 600); // 大小
		this.setLocationRelativeTo(null); // 居中
		this.setResizable(false); // 禁用最大化按钮
		this.setVisible(true); // 显示
		new MyThread().start(); // 启动线程

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0); // 退出系统
			}
		});

		// 添加一个键盘事件
		this.addKeyListener(new KeyAdapter() {

			// 按下按钮方法
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				myPlaneOBJ.MyPlaneMove(e);
			}

			// 松开按钮方法
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				myPlaneOBJ.MyPlaneStop(e);
			}
		});
	}

	// 创建一个image对象
	Image tmpIMG = null;

	@Override
	public void update(Graphics g) {
		// TODO Auto-generated method stub
		if (tmpIMG == null) {
			tmpIMG = this.createImage(500, 600);
		}

		// 拿画笔
		Graphics tmpG = tmpIMG.getGraphics();

		// 成像
		g.drawImage(tmpIMG, 0, 0, 500, 600, this);

		// 传入换缓冲里面的笔
		paint(tmpG);
	}

	// 画笔方法 ，绘画元素
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		bgOBJ.bgDraw(g); // 调用背景对象的画背景方法
		myPlaneOBJ.isXJ(enemyBulletList);
		myPlaneOBJ.MyPlaneDraw(g); // 调用我方飞机对象的画飞机方法
		bossPlaneOBJ.BossDraw(g); // 调用boss飞机对象的画飞机方法

		if (bomb != null) {
			bomb.BombDraw(g); // 调用爆炸对象的画爆炸方法
		}
		bomb = null;

		// 循环输出所有子弹
		for (int i = 0; i < myBulletList.size(); i++) {
			MyBullet myBullet = myBulletList.get(i); // 一个一个的拿出我方子弹对象
			myBullet.isXJ(enemyPlaneList); // 调用 判断是否相交 方法
			myBullet.MyBulletDraw(g); // 一颗一颗的画子弹对象

			if (!myBullet.isLive || myBullet.y < -50) {
				myBulletList.remove(myBullet);
			}
		}

		// 循环显示所有敌机
		for (int i = 0; i < enemyPlaneList.size(); i++) {
			EnemyPlane ep = enemyPlaneList.get(i);
			ep.EnemyPlaneDraw(g);
			
			if(!ep.isLive || ep.y>650){
				enemyPlaneList.remove(ep);
			}
		}

		// 循环显示敌方子弹
		for (int i = 0; i < enemyBulletList.size(); i++) {
			EnemyBullet enemyBullet = enemyBulletList.get(i);
			enemyBullet.EnemyBulletDraw(g);
			
			if(!enemyBullet.isLive || enemyBullet.y > 750){
				enemyBulletList.remove(enemyBullet);
			}
		}

		g.drawImage(myPlaneIMG, 20, 50, 20, 20, this); // 画小图标
		g.setColor(Color.red);
		g.drawRect(50, 50, 200, 20); // 画血条
		g.fillRect(50, 50, blood, 20); // 实心血条

		// 分数
		g.setFont(new Font("黑体", Font.PLAIN, 20));
		g.setColor(Color.black);
		g.drawString("游戏得分：" + score, 350, 50);
	}

	int score = 0;
	int blood = 200;
	int time = 0, eptime = 0;
	Random r = new Random(); // 随机数

	// 内部类
	class MyThread extends Thread {
		@Override
		// 重写run()方法
		public void run() {
			// TODO Auto-generated method stub
			// 线程里面写死循环
			while (true) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				repaint(); // 重复调用paint()方法

				if (blood != 0) {
					time++;
					if (time % 5 == 0) {
						// 实例化一个子弹对象
						MyBullet myBullet = new MyBullet(myPlaneOBJ.x + 20,
								myPlaneOBJ.y - 10, 10, 10, GameMain.this, true);
						// 添加集合对象
						myBulletList.add(myBullet);
						time = 0;

						// GameSound gs = new GameSound();
						// gs.playSound("music/Beam.mp3");
					}

					// 添加敌机
					eptime++;
					if (eptime % 20 == 0) {
						EnemyPlane ep = new EnemyPlane(r.nextInt(450), 0, 40,
								40, GameMain.this, true);
						enemyPlaneList.add(ep); // 添加敌机对象到集合
						eptime = 0;
					}

				}
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new GameMain().init();
	}

}
