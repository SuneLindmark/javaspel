import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class grafiken extends Canvas implements Runnable {
    int x, y;
    int x1 = 400;
    int y1 = 300;
    double angle = 0;
    BufferStrategy bs;
    int width = 800;
    int height = 600;
    BufferedImage img = null;
    private Thread thread;
    private boolean running = false;
    private boolean energyRight = false, energyLeft = false;
    private boolean isJump = false;
    private boolean isFalling = false;
    private int jumpEnergy = 1;
    private int mariox = 300, mariony = 500, blockX = 500, blockY = 400, groundY = 600;
    private boolean onGround = true;

    public grafiken() {
/*        try {
            img = ImageIO.read(new File("supermario.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        setSize(width, height);
        JFrame frame = new JFrame("Super Maroh");
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(new KL());
        frame.pack();
        frame.setVisible(true);
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        double ns = 1000000000.0 / 30.0;
        double delta = 0;
        long lastTime = System.nanoTime();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                // Uppdatera koordinaterna
                update();
                // Rita ut bilden med updaterad data
                render();
                delta--;
            }
        }
        stop();
    }


    public void render() {
        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        draw(g);
        g.dispose();
        bs.show();
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0x00c5ff));
        g.fillRect(0, 0, 2000, 3000);
        g.setColor(new Color(0x8B4513));
        g.fillRect(0, 600, 2000, 600);
        g.setColor((new Color(0x2fff00)));
        g.fillRect(0,600,2000,100);
        drawMario(g);
        drawBlock(g);
        drawBlock2(g);
    }

    private void R1(Graphics g){
    }

    private void drawBlock(Graphics g) {
        g.setColor(new Color(0xffb400));
        g.fillRect(500,430,50,50);
        g.setColor(new Color( 0xFFF400));
        g.fillRect(500,430,45,45);
        g.setColor(new Color(0xffb400));
        g.fillRect(600,430,50,50);
        g.setColor(new Color( 0xFFF400));
        g.fillRect(600,430,45,45);
    }

    private void drawBlock2(Graphics g) {
        g.setColor(new Color(0x8B4513));
        g.fillRect(550,430,50,50);
        g.fillRect(450,430,50,50);
        g.fillRect(650,430,50,50);
    }

    private void drawMario(Graphics g) {
        g.setColor(new Color(0xff0000));
        g.fillRect(mariox, mariony, 50, 100);
    }

    private void update() {
        if (jumpEnergy == 0) {
            isJump = false;
            isFalling = true;
            mariony += 10;
        } else if (isJump) {
            mariony -= 10;
            jumpEnergy--;
            onGround = false;
        }
        if (mariony + 100 == groundY) {
            jumpEnergy = 1;
            isFalling = false;
            onGround = true;
        }
        if (energyRight) {
            mariox += 10;
            if (!onGround && !isJump) {
            }
        } else if (energyLeft) {
            mariox -= 10;
                if (!onGround && !isJump) {
                }
            }
        }

    private void drawHouse(int x, int y, Graphics g) {
        g.setColor(new Color(0xAA1111));
        g.fillRect(x, y, 50, 50);
        g.setColor(new Color(0x444444));
        int[] xcoords = {x, x + 25, x + 50};
        int[] ycoords = {y, y - 50, y};
        g.fillPolygon(xcoords, ycoords, 3);
    }

    public static void main(String[] args) {
        grafiken minGrafik = new grafiken();
        minGrafik.start();
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
            if (keyEvent.getKeyChar() == 'd') {
                energyRight = false;
            } else if (keyEvent.getKeyChar() == 'a') {
                energyLeft = false;
            }
        }
    }
}