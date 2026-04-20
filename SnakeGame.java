import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(630, 630);
        frame.add(new GamePanel());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class GamePanel extends JPanel {
    private static final int GRID_WIDTH = 20;
    private static final int GRID_HEIGHT = 20;
    private static final int CELL_SIZE = 30;
    
    private static final int DIR_UP = 0;
    private static final int DIR_DOWN = 1;
    private static final int DIR_LEFT = 2;
    private static final int DIR_RIGHT = 3;
    
    private static final int STATE_START = 0;
    private static final int STATE_PLAYING = 1;
    private static final int STATE_GAME_OVER = 2;
    
    private int[][] snake;
    private int snakeLength;
    private int currentDirection = DIR_RIGHT;
    private int nextDirection = DIR_RIGHT;
    private Timer gameTimer;
    private int[] foodPos;
    private int score;
    private int highScore = 0;
    private int gameState = STATE_START;

    public GamePanel() {
        super();
        setBackground(new Color(50, 50, 50));
        setFocusable(true);
        setupKeyListener();
        startGameTimer();
    }

    private void initializeGame() {
        score = 0;
        gameState = STATE_PLAYING;
        currentDirection = DIR_RIGHT;
        nextDirection = DIR_RIGHT;
        initializeSnake();
        spawnFood();
    }

    private void initializeSnake() {
        // Create a 3-segment snake near the center, facing right
        snake = new int[400][2]; // max size for the board
        snake[0] = new int[]{10, 8};  // tail
        snake[1] = new int[]{10, 9};  // middle
        snake[2] = new int[]{10, 10}; // head (rightmost)
        snakeLength = 3;
    }

    private void setupKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                
                // Start screen
                if (gameState == STATE_START && keyCode == KeyEvent.VK_SPACE) {
                    initializeGame();
                    return;
                }
                
                // Game over screen
                if (gameState == STATE_GAME_OVER) {
                    if (keyCode == KeyEvent.VK_R) {
                        initializeGame();
                        return;
                    }
                    if (keyCode == KeyEvent.VK_S) {
                        gameState = STATE_START;
                        repaint();
                        return;
                    }
                }
                
                // Playing
                if (gameState == STATE_PLAYING) {
                    switch (keyCode) {
                        case KeyEvent.VK_UP:
                            if (currentDirection != DIR_DOWN) {
                                nextDirection = DIR_UP;
                            }
                            break;
                        case KeyEvent.VK_DOWN:
                            if (currentDirection != DIR_UP) {
                                nextDirection = DIR_DOWN;
                            }
                            break;
                        case KeyEvent.VK_LEFT:
                            if (currentDirection != DIR_RIGHT) {
                                nextDirection = DIR_LEFT;
                            }
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (currentDirection != DIR_LEFT) {
                                nextDirection = DIR_RIGHT;
                            }
                            break;
                    }
                }
            }
        });
    }

    private void startGameTimer() {
        gameTimer = new Timer(150, e -> moveSnake());
        gameTimer.start();
    }

    private void spawnFood() {
        boolean validSpot;
        int foodRow, foodCol;
        do {
            foodRow = (int) (Math.random() * GRID_HEIGHT);
            foodCol = (int) (Math.random() * GRID_WIDTH);
            validSpot = true;
            // Check if the spot is occupied by the snake
            for (int i = 0; i < snakeLength; i++) {
                if (snake[i][0] == foodRow && snake[i][1] == foodCol) {
                    validSpot = false;
                    break;
                }
            }
        } while (!validSpot);
        foodPos = new int[]{foodRow, foodCol};
    }

    private void moveSnake() {
        if (gameState != STATE_PLAYING) {
            return;
        }
        
        currentDirection = nextDirection;
        
        // Calculate new head position
        int headRow = snake[snakeLength - 1][0];
        int headCol = snake[snakeLength - 1][1];
        
        int newRow = headRow;
        int newCol = headCol;
        
        switch (currentDirection) {
            case DIR_UP:
                newRow = headRow - 1;
                break;
            case DIR_DOWN:
                newRow = headRow + 1;
                break;
            case DIR_LEFT:
                newCol = headCol - 1;
                break;
            case DIR_RIGHT:
                newCol = headCol + 1;
                break;
        }
        
        // Check for wall collision
        if (newRow < 0 || newRow >= GRID_HEIGHT || newCol < 0 || newCol >= GRID_WIDTH) {
            endGame();
            return;
        }
        
        // Check for self-collision
        for (int i = 0; i < snakeLength; i++) {
            if (snake[i][0] == newRow && snake[i][1] == newCol) {
                endGame();
                return;
            }
        }
        
        // Check for food collision
        if (newRow == foodPos[0] && newCol == foodPos[1]) {
            score += 10;
            snakeLength++;
            spawnFood();
        } else {
            // Shift snake segments
            for (int i = 0; i < snakeLength - 1; i++) {
                snake[i][0] = snake[i + 1][0];
                snake[i][1] = snake[i + 1][1];
            }
        }
        
        // Place new head
        snake[snakeLength - 1][0] = newRow;
        snake[snakeLength - 1][1] = newCol;
        
        repaint();
    }
    
    private void endGame() {
        if (score > highScore) {
            highScore = score;
        }
        gameState = STATE_GAME_OVER;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (gameState == STATE_START) {
            drawStartScreen(g);
        } else if (gameState == STATE_PLAYING) {
            drawGameplay(g);
        } else if (gameState == STATE_GAME_OVER) {
            drawGameplay(g);
            drawGameOverScreen(g);
        }
    }
    
        private void drawStartScreen(Graphics g) {
        // Fill background with dark color
        g.setColor(new Color(30, 30, 30));
        g.fillRect(0, 0, getWidth(), getHeight());
        
        // Draw title
        g.setColor(new Color(80, 173, 105));
        g.setFont(new Font("Monospaced", Font.BOLD, 60));
        String title = "SNAKE";
        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(title)) / 2;
        g.drawString(title, x, 100);
        
        // Draw high score
        g.setFont(new Font("Monospaced", Font.PLAIN, 24));
        fm = g.getFontMetrics();
        String highScoreText = "High Score: " + highScore;
        x = (getWidth() - fm.stringWidth(highScoreText)) / 2;
        g.drawString(highScoreText, x, 200);
        
        // Draw start instruction
        g.setFont(new Font("Monospaced", Font.PLAIN, 28));
        fm = g.getFontMetrics();
        String startText = "Press SPACE to Start";
        x = (getWidth() - fm.stringWidth(startText)) / 2;
        g.drawString(startText, x, getHeight() / 2);
    }
    
    private void drawGameplay(Graphics g) {
        // Draw grid
        g.setColor(new Color(80, 80, 80));
        for (int i = 0; i <= GRID_WIDTH; i++) {
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, GRID_HEIGHT * CELL_SIZE);
        }
        for (int i = 0; i <= GRID_HEIGHT; i++) {
            g.drawLine(0, i * CELL_SIZE, GRID_WIDTH * CELL_SIZE, i * CELL_SIZE);
        }
        
        // Draw food
        if (foodPos != null) {
            g.setColor(new Color(224, 181, 61));
            int x = foodPos[1] * CELL_SIZE;
            int y = foodPos[0] * CELL_SIZE;
            g.fillOval(x, y, CELL_SIZE, CELL_SIZE);
        }
        
        // Draw snake
        g.setColor(new Color(80, 173, 105));
        for (int i = 0; i < snakeLength; i++) {
            int x = snake[i][1] * CELL_SIZE;
            int y = snake[i][0] * CELL_SIZE;
            g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
        }
        
        // Draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.PLAIN, 16));
        g.drawString("Score: " + score, 10, 20);
    }
    
    private void drawGameOverScreen(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(80, 173, 105));
        g.setFont(new Font("Monospaced", Font.BOLD, 40));
        String gameOverText = "Game Over";
        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(gameOverText)) / 2;
        g.drawString(gameOverText, x, getHeight() / 2 - 60);
        
        g.setFont(new Font("Monospaced", Font.PLAIN, 24));
        String scoreText = "Final Score: " + score;
        x = (getWidth() - fm.stringWidth(scoreText)) / 2;
        g.drawString(scoreText, x, getHeight() / 2 - 10);
        
        String highText = "High Score: " + highScore;
        x = (getWidth() - fm.stringWidth(highText)) / 2;
        g.drawString(highText, x, getHeight() / 2 + 30);
        
        g.setFont(new Font("Monospaced", Font.PLAIN, 18));
        String resetText = "Press R to Play Again";
        x = (getWidth() - fm.stringWidth(resetText)) / 2;
        g.drawString(resetText, x, getHeight() / 2 + 80);
        
        String menuText = "Press S for Menu";
        x = (getWidth() - fm.stringWidth(menuText)) / 2;
        g.drawString(menuText, x, getHeight() / 2 + 120);
    }
}