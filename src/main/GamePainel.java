package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePainel extends JPanel implements Runnable {

	
	//Config do Jogo 
	final int originalTileSize = 16; //padrao de blocos do jogo 16x16
	final int scale =3;
	
	public final int tileSize = originalTileSize * scale; //somando para converter de 16 para o formato de tela 48px
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeigth = tileSize * maxScreenRow;
	
	
	//config do mundo
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeigth = tileSize * maxWorldRow;
	
	
	//FPS
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	public CollisionChecker cChecker = new CollisionChecker(this); //instanciando o checker de colisoes 
	public Player player = new Player(this,keyH);
	
	
	public GamePainel() {
		this.setPreferredSize(new Dimension (screenWidth,screenHeigth));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH); // faz com que o projeto reconheca o teclado 
		this.setFocusable(true); // faz que funcione quando estiver focado
		
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	
	public void run() { //testando outro tipo de loop mais simples e igualmente funcional
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			
			delta +=((currentTime - lastTime) / drawInterval);
			lastTime = currentTime;
			if(delta >=1) {
				update();
				repaint();
				delta--;
			}
		}
	}
	
	public void update() {
			player.update();
}
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		tileM.draw(g2);
		
		
		player.draw(g2);
		g2.dispose();
	}
}
