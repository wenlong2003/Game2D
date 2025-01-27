package Main;

import object.OBJ_Heart;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font chary, byteBounce;

    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; // 0: the first screen, 1: the second screen

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/chary.ttf");
            chary = Font.createFont(Font.TRUETYPE_FONT,is);
            is = getClass().getResourceAsStream("/font/ByteBounce.ttf");
            byteBounce = Font.createFont(Font.TRUETYPE_FONT,is);
        }catch (FontFormatException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        //Create HUD Object
        SuperObject heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

    }
    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(chary);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);


        //Title State
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        //Play State
        if (gp.gameState == gp.playState) {
            drawPlayerLife();

        }
        //Pause State
        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }
        //Dialogue State
        if (gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }
    }
    public void drawPlayerLife() {
        //gp.player.life = 4;

        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        //Draw Max Life
        while (i < gp.player.maxLife/2) {
            g2.drawImage(heart_blank,x,y,null);
            i++;
            x += gp.tileSize;
        }

        //Reset
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        //Draw Current Life
        while(i < gp.player.life) {
            g2.drawImage(heart_half,x,y,null);
            i++;
            if(i < gp.player.life) {
                g2.drawImage(heart_full,x,y,null);
            }
            i++;
            x += gp.tileSize;
        }
    }
    public void drawTitleScreen() {
        //Title Background Color
        //g2.setColor(new Color(12,100,220));
        //g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,80F));
        //Title Name
        String text = "AETHERIA FRONTIER";

        int x = getXForCenteredText(text);
        int y = gp.tileSize * 3;

        //Shadow
        g2.setColor(Color.GRAY);
        g2.drawString(text,x,y+6);
        //Main color
        g2.setColor(Color.white);
        g2.drawString(text,x,y);

        //Char Image
        x = gp.screenWidth/2 - (gp.tileSize * 3)/2;
        y += gp.tileSize * 2;
        g2.drawImage(gp.player.down1,x,y,gp.tileSize *2,gp.tileSize * 2, null);

        //Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
        text = "NEW GAME";
        x = getXForCenteredText(text) - 25;
        y +=  gp.tileSize * 3.5;
        g2.drawString(text,x,y);

        if (commandNum == 0) {
            g2.drawString(">", x-gp.tileSize, y);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
        text = "LOAD GAME";
        x = getXForCenteredText(text) - 25;
        y +=  gp.tileSize;
        g2.drawString(text,x,y);

        if (commandNum == 1) {
            g2.drawString(">", x-gp.tileSize, y);
        }
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
        text = "QUIT";
        x = getXForCenteredText(text) - 25;
        y +=  gp.tileSize;
        g2.drawString(text,x,y);

        if (commandNum == 2) {
            g2.drawString(">", x-gp.tileSize, y);
        }

    }
    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text,x,y);
    }
    public void drawDialogueScreen() {
        //Window
        int x = gp.tileSize * 2;
        int y = gp.tileSize /2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;

        drawSubWindow(x,y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,37F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line: currentDialogue.split("\n")) {
            g2.drawString(line,x,y);
            y += 40;
        }
    }
    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0,0,0,220);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height, 35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }
    public int getXForCenteredText(String text) {
        int  length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}
