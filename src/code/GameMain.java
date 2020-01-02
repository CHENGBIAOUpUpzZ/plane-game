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

	Toolkit tool = Toolkit.getDefaultToolkit(); // ʵ����һ�����߰�����
	// �õ�����ͼƬ
	Image bgIMG = tool.getImage(GameMain.class.getResource("/images/bg05.jpg"));
	// �õ��ҷ��ɻ�ͼƬ
	Image myPlaneIMG = tool.getImage(GameMain.class
			.getResource("/images/player05.png"));
	// �õ�Boss�ɻ�ͼƬ
	Image bossPlaneIMG = tool.getImage(GameMain.class
			.getResource("/images/boss_3.png"));
	// �õ��ҷ��ӵ�ͼƬ
	Image myBulletIMG = tool.getImage(GameMain.class
			.getResource("/images/bul01.png"));
	// �õ��з��ɻ�ͼƬ
	Image enemyPlaneIMG = tool.getImage(GameMain.class
			.getResource("/images/enemy01.png"));
	// �õ��з��ɻ��ӵ�ͼƬ
	Image enemyBulletIMG = tool.getImage(GameMain.class
			.getResource("/images/en_bul01.png"));
	// �õ���ը��ЧͼƬ
	Image bombIMG = tool.getImage(GameMain.class
			.getResource("/images/bomb_enemy_2.png"));
	// ʵ����һ����������
	BackGround bgOBJ = new BackGround(this, 0, 0, 500, 600);
	// ʵ����һ���ҷ��ɻ�����
	MyPlane myPlaneOBJ = new MyPlane(225, 500, 50, 50, this, true);
	// ʵ����һ��boss�ɻ�����
	BossPlane bossPlaneOBJ = new BossPlane(200, 100, 100, 100, this, true);
	// ʵ����һ����ը��Ч����
	Bomb bomb = null;
	// ʵ����һ���ҷ��ӵ����϶���
	ArrayList<MyBullet> myBulletList = new ArrayList<MyBullet>();
	// ʵ����һ���з��ɻ����϶���
	ArrayList<EnemyPlane> enemyPlaneList = new ArrayList<EnemyPlane>();
	// ʵ����һ���з��ɻ��ӵ����϶���
	ArrayList<EnemyBullet> enemyBulletList = new ArrayList<EnemyBullet>();

	/**
	 * @param args
	 */
	// ��ʼ����Ϸ
	public void init() {
		GameSound gs = new GameSound();
		gs.playBgSound("music/BGM_0001.mp3");
		this.setTitle("ս����Ϸ1.0");
		this.setSize(500, 600); // ��С
		this.setLocationRelativeTo(null); // ����
		this.setResizable(false); // ������󻯰�ť
		this.setVisible(true); // ��ʾ
		new MyThread().start(); // �����߳�

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0); // �˳�ϵͳ
			}
		});

		// ���һ�������¼�
		this.addKeyListener(new KeyAdapter() {

			// ���°�ť����
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				myPlaneOBJ.MyPlaneMove(e);
			}

			// �ɿ���ť����
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				myPlaneOBJ.MyPlaneStop(e);
			}
		});
	}

	// ����һ��image����
	Image tmpIMG = null;

	@Override
	public void update(Graphics g) {
		// TODO Auto-generated method stub
		if (tmpIMG == null) {
			tmpIMG = this.createImage(500, 600);
		}

		// �û���
		Graphics tmpG = tmpIMG.getGraphics();

		// ����
		g.drawImage(tmpIMG, 0, 0, 500, 600, this);

		// ���뻻��������ı�
		paint(tmpG);
	}

	// ���ʷ��� ���滭Ԫ��
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		bgOBJ.bgDraw(g); // ���ñ�������Ļ���������
		myPlaneOBJ.isXJ(enemyBulletList);
		myPlaneOBJ.MyPlaneDraw(g); // �����ҷ��ɻ�����Ļ��ɻ�����
		bossPlaneOBJ.BossDraw(g); // ����boss�ɻ�����Ļ��ɻ�����

		if (bomb != null) {
			bomb.BombDraw(g); // ���ñ�ը����Ļ���ը����
		}
		bomb = null;

		// ѭ����������ӵ�
		for (int i = 0; i < myBulletList.size(); i++) {
			MyBullet myBullet = myBulletList.get(i); // һ��һ�����ó��ҷ��ӵ�����
			myBullet.isXJ(enemyPlaneList); // ���� �ж��Ƿ��ཻ ����
			myBullet.MyBulletDraw(g); // һ��һ�ŵĻ��ӵ�����

			if (!myBullet.isLive || myBullet.y < -50) {
				myBulletList.remove(myBullet);
			}
		}

		// ѭ����ʾ���ел�
		for (int i = 0; i < enemyPlaneList.size(); i++) {
			EnemyPlane ep = enemyPlaneList.get(i);
			ep.EnemyPlaneDraw(g);
			
			if(!ep.isLive || ep.y>650){
				enemyPlaneList.remove(ep);
			}
		}

		// ѭ����ʾ�з��ӵ�
		for (int i = 0; i < enemyBulletList.size(); i++) {
			EnemyBullet enemyBullet = enemyBulletList.get(i);
			enemyBullet.EnemyBulletDraw(g);
			
			if(!enemyBullet.isLive || enemyBullet.y > 750){
				enemyBulletList.remove(enemyBullet);
			}
		}

		g.drawImage(myPlaneIMG, 20, 50, 20, 20, this); // ��Сͼ��
		g.setColor(Color.red);
		g.drawRect(50, 50, 200, 20); // ��Ѫ��
		g.fillRect(50, 50, blood, 20); // ʵ��Ѫ��

		// ����
		g.setFont(new Font("����", Font.PLAIN, 20));
		g.setColor(Color.black);
		g.drawString("��Ϸ�÷֣�" + score, 350, 50);
	}

	int score = 0;
	int blood = 200;
	int time = 0, eptime = 0;
	Random r = new Random(); // �����

	// �ڲ���
	class MyThread extends Thread {
		@Override
		// ��дrun()����
		public void run() {
			// TODO Auto-generated method stub
			// �߳�����д��ѭ��
			while (true) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				repaint(); // �ظ�����paint()����

				if (blood != 0) {
					time++;
					if (time % 5 == 0) {
						// ʵ����һ���ӵ�����
						MyBullet myBullet = new MyBullet(myPlaneOBJ.x + 20,
								myPlaneOBJ.y - 10, 10, 10, GameMain.this, true);
						// ��Ӽ��϶���
						myBulletList.add(myBullet);
						time = 0;

						// GameSound gs = new GameSound();
						// gs.playSound("music/Beam.mp3");
					}

					// ��ӵл�
					eptime++;
					if (eptime % 20 == 0) {
						EnemyPlane ep = new EnemyPlane(r.nextInt(450), 0, 40,
								40, GameMain.this, true);
						enemyPlaneList.add(ep); // ��ӵл����󵽼���
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
