package entity;

import Main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = setup("/npc/Back");
        up2 = setup("/npc/Back2");
        down1 = setup("/npc/Front");
        down2 = setup("/npc/Front2");
        left1 = setup("/npc/Left");
        left2 = setup("/npc/Left2");
        right1 = setup("/npc/Right");
        right2 = setup("/npc/Right2");
    }

    public void setDialogue() {
        dialogues[0] = "Hello, Welcome to this World.";
        dialogues[1] = "So you've come to this island\nto find the treasure.";
        dialogues[2] = "I used to be a great wizard but \nnow... I'm a bit too old for \ntaking an adventure.";
        dialogues[3] = "Well, good luck on your \njourney.";
    }

    public void setAction() {

        actionLockCounter++;

        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }
    public void speak() {
        //Do this character specific stuff
        super.speak();
    }
}
