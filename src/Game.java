import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {
  private static final long serialVersionUID = 1L;

  public final static int WIDTH = 720;
  public final static int HEIGHT = 360;

  public BufferedImage layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

  public static Player player;
  public static IA ia;
  public static Ball ball;

  public Game() {
    this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    this.addKeyListener(this);
    player = new Player(250, HEIGHT - 15);
    ia = new IA(250, 0);
    ball = new Ball(250, HEIGHT / 2 - 9);
  }

  public static void main(String[] args) {
    Game game = new Game();

    JFrame frame = new JFrame("Game Pong");
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(game);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    new Thread(game).start();
  }

  public void tick() {
    player.tick();
    ia.tick();
    ball.tick();
  }

  public void render() {
    BufferStrategy bs = this.getBufferStrategy();

    if (bs == null) {
      this.createBufferStrategy(3);
      return;
    }

    Graphics g = layer.getGraphics();
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, WIDTH, HEIGHT);

    player.render(g);
    ia.render(g);
    ball.render(g);

    g = bs.getDrawGraphics();
    g.drawImage(layer, 0, 0, WIDTH, HEIGHT, null);

    bs.show();
  }

  public void run() {
    requestFocus();
    while (true) {
      tick();
      render();

      try {
        Thread.sleep(1000 / 60);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_D) {
      player.right = false;
    } else if (e.getKeyCode() == KeyEvent.VK_A) {
      player.left = false;
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_D) {
      player.right = true;
    } else if (e.getKeyCode() == KeyEvent.VK_A) {
      player.left = true;
    } else if (e.getKeyCode() == KeyEvent.VK_R) {
      new Game();
      return;
    }
  }

}
