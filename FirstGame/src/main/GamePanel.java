package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import entity.Player;
import tile.TileManager;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable{

		//Screen Settings
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3;  
	
	public final int tileSize = originalTileSize * scale; // 48x48 tile
	public final int maxScreenCol = 32;
	public final int maxScreenRow = 20;  
	public final int screenWidth = tileSize * maxScreenCol; // ?? pixels
	public final int screenHeight = tileSize * maxScreenRow; // ?? pixels 
	
	//FPS
	int FPS = 60;
	
	
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	Player player = new Player(this,keyH);
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		
		while(gameThread != null) {
			
			long currentTime = System.nanoTime();
			
			update();
			
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
public void update() {
	player.update();
}
	
	
	@Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    
	    Graphics2D g2 = (Graphics2D) g;
	    
	    tileM.draw(g2);
	    
	    player.draw(g2);
	    
	    g2.dispose();
	}
	
}
