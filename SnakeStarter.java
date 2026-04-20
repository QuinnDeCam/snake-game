import java.awt.*;
import javax.swing.*;

public class SnakeStarter extends JPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Starter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.add(new SnakeStarter());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private final int gridSize = 20;
    private final int cellSize = 25;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(22, 28, 39));
        g.fillRect(0, 0, gridSize * cellSize, gridSize * cellSize);
        // draw a green 3-segment snake near the center
        g.setColor(Color.GREEN);
        g.fillRect((gridSize / 2 - 1) * cellSize, (gridSize / 2) * cellSize, cellSize, cellSize);
        g.fillRect((gridSize / 2) * cellSize, (gridSize / 2) * cellSize, cellSize, cellSize);
        g.fillRect((gridSize / 2 + 1) * cellSize, (gridSize / 2) * cellSize, cellSize, cellSize);

    }
}