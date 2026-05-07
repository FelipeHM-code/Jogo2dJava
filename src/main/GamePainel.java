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
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol;
	final int screenHeigth = tileSize * maxScreenRow;
	
	//FPS
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	Player player = new Player(this,keyH);
	
	
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
	/*public void run() {
		while(gameThread != null) {
			
			double drawInterval = 1000000000/FPS; //aqui atualiza os desenhos 0.0166x por sec
			double nextDrawTime = System.nanoTime()+ drawInterval;
			
			update();//atualiza as informacoes e posicoes do personagem
			
			
			repaint();//desenha na tela enquanto atualiza a informacao
			
			
			
			
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000; // convertendo de nanosec para milisec
				
				if(remainingTime <0) {
					remainingTime =0;
				}
				
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		
		}
		
	}*/
	
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
