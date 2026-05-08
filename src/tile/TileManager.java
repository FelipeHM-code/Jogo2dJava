package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePainel;

public class TileManager {

	GamePainel gp;
	public Tile[] tile;
	public int mapTileNum[] [];
	
	public TileManager(GamePainel gp ) {
	this.gp = gp;
	
	
	tile = new Tile[10];
	mapTileNum = new int[gp.maxWorldCol] [gp.maxWorldRow];
	
	getTileImage();
	loadMap("/maps/word01.txt"); //escolho o mapa que vai rodar
}
	
	public void getTileImage() {
		
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/rock.png"));
			tile[1].collision = true;
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[2].collision = true;
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			tile[4].collision = true;
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void loadMap(String filePatch) {
		
		try {
			InputStream is = getClass().getResourceAsStream(filePatch); //importa o texto
			BufferedReader br = new BufferedReader(new InputStreamReader(is)); //converte e le ele como mapa 
			
			
			int col = 0;
			int row = 0;
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();
				
				while(col < gp.maxWorldCol) {
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				if(col ==gp.maxWorldCol) {
					col = 0;
					row++;
				}
				
				
				
			}
			br.close();
			}catch(Exception e) {
			
		}
}
		
	
	public void draw(Graphics2D g2) {
		int worldcol =0;
		int worldrow = 0;

		
		while(worldcol < gp.maxWorldCol && worldrow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldcol][worldrow];
			//mecanica para renderizar o mapa e carregalo no local certo
			int worldX = worldcol * gp.tileSize;
			int worldY = worldrow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			//if para criar limite de renderizacao para otimizar o jogo
			
			if(worldX +gp.tileSize> gp.player.worldX - gp.player.screenX && worldX -gp.tileSize< gp.player.worldX + gp.player.screenX 
					&& worldY +gp.tileSize> gp.player.worldY - gp.player.screenY && worldY -gp.tileSize< gp.player.worldY + gp.player.screenY) {
				
			g2.drawImage(tile[tileNum].image, screenX, screenY,gp.tileSize, gp.tileSize, null);
			
			}
			worldcol++;
			
			
			if(worldcol ==gp.maxWorldCol) {
				worldcol =0;		
				worldrow++;
				
			}
		}
	}
}
