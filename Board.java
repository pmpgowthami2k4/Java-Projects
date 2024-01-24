import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Board extends JFrame implements ActionListener, KeyListener {
    private Timer timer;
    private int snakeLength;
    private int[] snakeX, snakeY;
    private int appleX, appleY;
    private boolean left, right, up, down;
    private boolean gameOver;
    //pmpgowthami2k4

    public Board() {
        setTitle("Snake Game");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        snakeLength = 3;
        snakeX = new int[400];
        snakeY = new int[400];

        left = false;
        right = true;
        up = false;
        down = false;

        gameOver = false;

        timer = new Timer(100, this);
        timer.start();

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        initializeGame();
    }

    private void initializeGame() {
        snakeLength = 3;
        for (int i = 0; i < snakeLength; i++) {
            snakeX[i] = 50 - i * 10;
            snakeY[i] = 50;
        }

        spawnApple();
    }

    private void spawnApple() {
        Random rand = new Random();
        appleX = rand.nextInt(38) * 10;
        appleY = rand.nextInt(38) * 10;
    }

    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
            checkCollision();
            checkApple();
            repaint();
        }
    }

    private void move() {
        for (int i = snakeLength - 1; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }

        if (left) {
            snakeX[0] -= 10;
        } else if (right) {
            snakeX[0] += 10;
        } else if (up) {
            snakeY[0] -= 10;
        } else if (down) {
            snakeY[0] += 10;
        }
    }

    private void checkCollision() {
        if (snakeX[0] >= 400 || snakeX[0] < 0 || snakeY[0] >= 400 || snakeY[0] < 0) {
            gameOver = true;
        }

        for (int i = 1; i < snakeLength; i++) {
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                gameOver = true;
            }
        }

        if (gameOver) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Game Over!");
            initializeGame();
            gameOver = false;
            timer.start();
        }
    }

    private void checkApple() {
        if (snakeX[0] == appleX && snakeY[0] == appleY) {
            snakeLength++;
            spawnApple();
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        if (!gameOver) {
            g.setColor(Color.RED);
            g.fillRect(appleX, appleY, 10, 10);

            g.setColor(Color.GREEN);
            for (int i = 0; i < snakeLength; i++) {
                g.fillRect(snakeX[i], snakeY[i], 10, 10);
            }
        } else {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Game Over! Press Enter to Play Again", 40, 200);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_ENTER && gameOver) {
            initializeGame();
            gameOver = false;
            timer.start();
        }

        if (key == KeyEvent.VK_LEFT && !right) {
            left = true;
            up = false;
            down = false;
        }

        if (key == KeyEvent.VK_RIGHT && !left) {
            right = true;
            up = false;
            down = false;
        }

        if (key == KeyEvent.VK_UP && !down) {
            up = true;
            right = false;
            left = false;
        }

        if (key == KeyEvent.VK_DOWN && !up) {
            down = true;
            right = false;
            left = false;
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Board board = new Board();
            board.setVisible(true);
        });
    }
}
