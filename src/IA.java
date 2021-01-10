import java.awt.Color;
import java.awt.Graphics;

public class IA {

  public double x, y;
  public int width, height, points;

  public IA(int x, int y) {
    this.x = x;
    this.y = y;
    this.width = 200;
    this.height = 15;
    this.points = 0;
  }

  public void tick() {
    x += (Game.ball.x - x - 10) * 0.1;

    if (x + width > Game.WIDTH) {
      x = Game.WIDTH - width;
    } else if (x < 0) {
      x = 0;
    }
  }

  public void render(Graphics g) {
    g.setColor(Color.RED);
    g.fillRect((int) x, (int) y, width, height);
  }

}
