import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class grafik {

    private boolean energyRight = false,energyLeft = false;
    private boolean isJump = false;
    private boolean isFalling = false;
    private int jumpEnergy = 1;



    public void draw(Graphics g) {
        drawGround(g);
    }

    public void drawGround(Graphics g) {
        g.setColor(new Color(0x8B4513));
        g.fillRect(0, 600, 2000, 600);
    }



    private class KL implements KeyListener {
        @Override
        public void keyTyped(KeyEvent keyEvent) {

        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyChar() == 'd') {
                energyRight = true;
            } else if (keyEvent.getKeyChar() == 'a') {
                energyLeft = true;
            } else if (keyEvent.getKeyChar() == 'w') {
                if (!isJump && !isFalling) {
                isJump = true;
                jumpEnergy = 20;
                }
            }

        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {

        }
    }
}