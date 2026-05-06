package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePainel extends JPanel implements Runnable {

	
	//Config do Jogo 
	final int originalTileSize = 16; //padrao de blocos do jogo 16x16
	final int scale =3;
	
	final int tileSize = originalTileSize * scale; //somando para converter de 16 para o formato de tela 48px
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol;
	final int screenHeigth = tileSize * maxScreenRow;
	
	//FPS
	int FPS = 60;
	
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	
	//crie o local onde o player vai nascer sempre
	
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
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
	public void run() {
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
		
	}
	
	public void update() {
		
		if(keyH.upPressed == true) {
			//faz o player andar para cima 
			playerY -= playerSpeed;
		}
		else if(keyH.downPressed ==true) {
			playerY += playerSpeed;
		}
		else if(keyH.leftPressed == true) {
			playerX -= playerSpeed;
		}
		else if(keyH.rightPressed) {
			playerX += playerSpeed;
		}
		
		
		
		
			}
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.white);
		g2.fillRect(playerX, playerY, tileSize, tileSize);
		
		g2.dispose();
	}
}
