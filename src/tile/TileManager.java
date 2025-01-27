package tile;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[60];
        mapTileNum = new int [gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/worldMap2.txt");
    }

    public void getTileImage() {

        //Placeholder
        setup(0, "edgeCornerTL", false);
        setup(1, "edgeCornerTL", false);
        setup(2, "edgeCornerTL", false);
        setup(3, "edgeCornerTL", false);
        setup(4, "edgeCornerTL", false);
        setup(5, "edgeCornerTL", false);
        setup(6, "edgeCornerTL", false);
        setup(7, "edgeCornerTL", false);
        setup(8, "edgeCornerTL", false);
        setup(9, "edgeCornerTL", false);

        //Water Hollow
        setup(10, "edgeCornerTL", true);
        setup(11, "edgeT", true);
        setup(12, "edgeCornerTR", true);
        setup(13, "edgeCornerBL", true);
        setup(14, "edgeB", true);
        setup(15, "edgeCornerBR", true);
        setup(16, "edgeL", true);
        setup(17, "edgeR", true);

        //Road
        setup(18, "grassSideTL", false);
        setup(19, "grassSideT", false);
        setup(20, "grassSideTR", false);
        setup(21, "grassSideL", false);
        setup(22, "grassSideR", false);
        setup(23, "grassSideBL", false);
        setup(24, "grassSideB", false);
        setup(25, "grassSideBR", false);

        //Water Edge
        setup(26, "waterEdgeT", true);
        setup(27, "waterEdgeTL", true);
        setup(28, "waterEdgeTR", true);
        setup(29, "waterEdgeL", true);
        setup(30, "waterEdgeR", true);
        setup(31, "waterEdgeBL", true);
        setup(32, "waterEdgeB", true);
        setup(33, "waterEdgeBR", true);

        //Extras
        setup(34, "water", true);
        setup(35, "grass1", false);
        setup(36, "grass2", false);
        setup(37, "island", true);
        setup(38, "rock", true);
        setup(39, "sand", false);
        setup(40, "tree", true);
        setup(41, "wall", true);
        setup(42, "dirt", false);
        setup(43, "water2", true);

        //Water Corner
        setup(44, "waterCornerTL", true);
        setup(45, "waterCornerTR", true);
        setup(46, "waterCornerBL", true);
        setup(47, "waterCornerBR", true);

        //Path corner
        setup(48, "grassCornerTL", false);
        setup(49, "grassCornerTR", false);
        setup(50, "grassCornerBL", false);
        setup(51, "grassCornerBR", false);

    }

    public void setup(int index, String imageName, boolean collision) {

        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imageName +".png")));
            tile[index].image = uTool.scaleImage(tile[index].image,gp.tileSize,gp.tileSize);
            tile[index].collision = collision;
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadMap (String filePath) {
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                while (col < gp.maxWorldCol) {
                    String [] numbers  = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        }catch(Exception ignored) {
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY,null);
            }
            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
