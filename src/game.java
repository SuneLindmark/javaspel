import java.awt.Canvas;

import javax.swing.JFrame;

public class game extends Canvas implements Runnable {
    private static final long serialVersionUID = -1890564841829395437L;

    public static final int WIDTH = 640;
    public static final int HEIGHT = WIDTH / 4 * 3;
    public static final String TITLE = "Mario game";
    private static game game = new game();

    private boolean running = false;
    private Thread thread;

    public void init(){

    }

    public void tick(){

    }

    public void render(){

    }

    @Override
    public void run () {
        init();
        long lastTime = System.nanoTime();
        final double numTicks = 60.0;
        double n = 100000000 / numTicks;
        double delta = 0;
        int frames = 0;
        int ticks = 0;
        long timer = System.currentTimeMillis();

        while(running){
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / n;
            lastTime = currentTime;

            if(delta >= 1){
                ticks++;
                delta--;
            }
        }
        stop();
    }

    public static void main(String args[]){
        JFrame frame = new JFrame(TITLE);
        frame.add(game);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setFocusable(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.pack();
        game.start();
    }

    private synchronized void start(){
        if(running)
            return;
        else
            running = true;
        thread = new Thread(this);
        thread.start();
    }
    private synchronized void stop(){
        if(!running)
            return;
        else
            running = false;

        try {
            thread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        System.exit(1);
    }

}