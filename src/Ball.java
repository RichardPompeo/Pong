import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import java.util.Random;

public class Ball {

  public double x, y;
  public int width, height;
  public double dx, dy;
  public double speed = 6;

  public Ball(int x, int y) {
    this.x = x;
    this.y = y;
    this.width = 18;
    this.height = 18;

    int angle = new Random().nextInt(120 - 45) + 45;
    dx = Math.cos(Math.toRadians(angle));
    dy = Math.sin(Math.toRadians(angle));
  }

  public void tick() {
    if (x + (dx * speed) + width >= Game.WIDTH) {
      dx *= -1;
    } else if (x + (dx * speed) < 0) {
      dx *= -1;
    }

    if (y + (dy * speed) + height >= Game.HEIGHT) {
      dy *= -1;
      if (Game.ia.points >= 4) {
        new Game();
        System.out.println("Você perdeu!");
        return;
      }
      Game.ia.points++;
    } else if (y + (dy * speed) < 0) {
      dy *= -1;
      if (Game.player.points >= 4) {
        new Game();
        System.out.println("Você venceu!");
        return;
      }
      Game.player.points++;
    }

    Rectangle bounds = new Rectangle((int) (x + (dx * speed)), (int) (y + (dy * speed)), width, height);
    Rectangle boundsPlayer = new Rectangle(Game.player.x, Game.player.y, Game.player.width, Game.player.height);
    Rectangle boundsIA = new Rectangle((int) (Game.ia.x), (int) (Game.ia.y), Game.ia.width, Game.ia.height);

    if (bounds.intersects(boundsPlayer)) {
      int angle = new Random().nextInt(120 - 45) + 45;
      dx = Math.cos(Math.toRadians(angle));
      dy = Math.sin(Math.toRadians(angle));
      if (dy > 0)
        dy *= -1;
    } else if (bounds.intersects(boundsIA)) {
      int angle = new Random().nextInt(120 - 45) + 45;
      dx = Math.cos(Math.toRadians(angle));
      dy = Math.sin(Math.toRadians(angle));
      if (dy < 0)
        dy *= -1;
    }

    x += dx * speed;
    y += dy * speed;
  }

  public void render(Graphics g) {
    g.setColor(Color.BLUE);
    g.fillOval((int) x, (int) y, width, height);
    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial", Font.BOLD, 20));
    g.drawString(Game.player.points + " x " + Game.ia.points, 20, Game.HEIGHT - 20);
  }

}
